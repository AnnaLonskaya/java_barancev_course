package ua.annalonskaya.rest.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.rest.appmanager.RestHelper;
import ua.annalonskaya.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase{

  RestHelper rest = new RestHelper();

  private int issueId = 5;

//  @BeforeMethod
//  public void checkIssueStatus() throws IOException {
//    try {
//      skipIfNotFixed(issueId);
//    } catch (SkipException e) {
//      e.printStackTrace();
//    }
//  }

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = rest.getIssues();
    Issue newIssue = new Issue().withSubject("Test issue6").withDescription("New test issue5");
    int issueId = rest.createIssue(newIssue);
    Set<Issue> newIssues = rest.getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testGetIssues() throws IOException {
    Set<Issue> oldIssues = rest.getIssues();
    for (Issue issue : oldIssues) {
      System.out.println(issue.getStateName());
    }
  }

//  @Test
//  public void testGetIssue() throws IOException {
//    Issue issue = rest.getIssue(issueId);
//    System.out.println(issue);
//  }

}
