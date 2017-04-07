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
    UserData changedUser = before.stream().filter((s) -> !s.getName().equals("administrator")).findFirst().get();
    String newPassword = String.format("password%s", random);
    app.login().loginAsAdmin();
    app.admin().initManageUsers();
    app.admin().initUserModificationById(changedUser.getId());
    app.admin().resetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, changedUser.getEmail());
    app.admin().finished(confirmationLink, newPassword);
    assertTrue(app.newSession().login(changedUser.getName(), newPassword));
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

