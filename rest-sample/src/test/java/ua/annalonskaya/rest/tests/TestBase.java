package ua.annalonskaya.rest.tests;

import org.testng.SkipException;
import ua.annalonskaya.rest.appmanager.RestHelper;
import ua.annalonskaya.rest.model.Issue;

import java.io.IOException;

public class TestBase {

  RestHelper rest = new RestHelper();

//  public Boolean isIssueOpen(int issueId) throws IOException {
//    Issue issue = rest.getIssue(issueId);
//    if (issue.getStateName().equals("resolved") || (issue.getStateName().equals("closed"))) {
//      return false;
//    }
//    return true;
//  }
//
//  public void skipIfNotFixed(int issueId) throws IOException {
//    if (isIssueOpen(issueId)) {
//      throw new SkipException("Ignored because of issue " + issueId);
//    }
//  }

}
