package ua.annalonskaya.mantis.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.mantis.model.Issue;
import ua.annalonskaya.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase{

  private int issueId = 4;

  @BeforeMethod
  public void checkIssueStatus() throws IOException, ServiceException {
    try {
      skipIfNotFixed(issueId);
    } catch (SkipException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue description").withProject(projects.iterator().next());
    Issue created = app.soap().addIssue(issue);
    assertEquals(issue.getSummary(), created.getSummary());
  }

  @Test
  public void testGetIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Issue> issues = app.soap().getIssues();
    Issue next = issues.iterator().next();
    System.out.println(next.getStatus());
  }

}



