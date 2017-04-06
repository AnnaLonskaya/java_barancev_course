package ua.annalonskaya.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ua.annalonskaya.mantis.model.MailMessage;
import ua.annalonskaya.mantis.model.UserData;
import ua.annalonskaya.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;
import static ua.annalonskaya.mantis.appmanager.ApplicationManager.random;

public class ChangePasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void changePasswordByAdminTest() throws IOException, MessagingException {
    Users before = app.db().users();
    UserData changedUser = before.iterator().next();
    String newPassword = String.format("password%s", random);
    UserData user = new UserData().withId(changedUser.getId()).withName(changedUser.getName())
            .withEmail(changedUser.getEmail()).withPassword(newPassword);
    app.login().loginAsAdmin();
    app.admin().initManageUsers();
    app.admin().initUserModificationById(changedUser.getId());
    app.admin().resetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, changedUser.getEmail());
    app.admin().finished(confirmationLink, newPassword);
    Users after = app.db().users();
    assertTrue(app.newSession().login(changedUser.getName(), newPassword));
    assertThat(after, equalTo(before.without(changedUser).withAdded(user)));
  }


  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}

