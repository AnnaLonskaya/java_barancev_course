package ua.annalonskaya.rest.tests;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.annalonskaya.rest.appmanager.RestAssuredHelper;
import ua.annalonskaya.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

  RestAssuredHelper restAssured = new RestAssuredHelper();

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
  }

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = restAssured.getIssues();
    Issue newIssue = new Issue().withSubject("Test issue5").withDescription("New test issue5");
    int issueId = restAssured.createIssue(newIssue);
    Set<Issue> newIssues = restAssured.getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

}
