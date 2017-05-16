package ua.annalonskaya.mantis.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyFirstTest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void setUp(){
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 10);
  }

  @Test
  public void firstTest() {
    driver.get("http://www.google.com");
    driver.findElement(By.name("q")).sendKeys("webdriver");
    driver.findElement(By.name("btnG")).click();
    wait.until(ExpectedConditions.titleIs("webdriver - Поиск в Google"));
  }

  @After
  public void tearDown() {
    driver.quit();
    driver = null;
  }

}
