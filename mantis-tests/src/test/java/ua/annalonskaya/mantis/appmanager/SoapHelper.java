package ua.annalonskaya.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ua.annalonskaya.mantis.model.Issue;
import ua.annalonskaya.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

  private ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app=app;
  }

  public Set<Project> getProjects() throws MalformedURLException, RemoteException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
    return Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
            .collect(Collectors.toSet());
  }

  public Set<Issue> getIssues() throws MalformedURLException, RemoteException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    Set<Project> projects = getProjects();
    IssueData[] issues = mc.mc_project_get_issues("administrator", "root", BigInteger.valueOf(projects.iterator().next().getId()),
            BigInteger.valueOf(1), BigInteger.valueOf(-1));
    return Arrays.asList(issues).stream()
            .map((i) -> new Issue().withId(i.getId().intValue()).withSummary(i.getSummary()).withStatus((i.getStatus().getName())))
            .collect(Collectors.toSet());
  }

  public Issue getIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Issue> issues = app.soap().getIssues();
    Issue next = issues.iterator().next();
    return new Issue().withId(next.getId()).withStatus(next.getStatus());
  }

  public Issue getIssue(int id) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData issueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(id));
    return new Issue().withId(issueData.getId().intValue()).withStatus(issueData.getStatus().getName());
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator()
            .getMantisConnectPort(new URL(app.getProperty("mantisUrl")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
    return new Issue().withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary()).withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                    .withName(createdIssueData.getProject().getName()));
  }

}

