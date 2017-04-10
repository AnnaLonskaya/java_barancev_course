package ua.annalonskaya.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("83db20dc544552157b90e318ab7fdef697eabf90");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("AnnaLonskaya", "java_barancev_course")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}


