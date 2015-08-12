package com.hspring.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.hspring.EmployeeDirectoryApplication;
import com.hspring.data.EmployeeRepository;
import com.hspring.data.entity.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SeleniumLoginFrontendTest {

    private WebDriver browser;


    @Before
    public void setup() {
        browser = new ChromeDriver();
    }

    private void login(){
        browser.get("http://localhost:8080/");

        browser.findElement(By.name("username")).sendKeys("jsmith@hspring.com");
        browser.findElement(By.name("password")).sendKeys("password");

        browser.findElement(By.name("submit")).click();

    }

    @Test
    public void testLogin() {
        login();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals("Logout", browser.findElement(By.name("logout")).getText());
    }

    @Test
    public void testLoginLogout(){
        login();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        browser.findElement(By.name("logout")).click();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertTrue(browser.findElement(By.name("username")).isDisplayed());
        assertTrue(browser.findElement(By.name("password")).isDisplayed());
        assertTrue(browser.findElement(By.name("submit")).isDisplayed());
    }

    @Test
    public void testCreateEmployee(){
        login();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String newEmailAddress = createEmployee();

        assertTrue(browser.findElement(By.name("search")).isDisplayed());
// delete user
    }

    private String createEmployee() {
        browser.findElement(By.name("newuser")).click();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        browser.findElement(By.name("firstName")).sendKeys("Selenium");
        browser.findElement(By.name("lastName")).sendKeys("Test");
        browser.findElement(By.name("location")).sendKeys("Austin");
        String newEmailAddress = "stest"+new java.util.Date().getTime()+"@hspring.com";
        browser.findElement(By.name("email")).sendKeys(newEmailAddress);
        browser.findElement(By.name("cellPhone")).sendKeys("000-000-0000");
        browser.findElement(By.name("title")).sendKeys("Da Tester");
        browser.findElement(By.name("workPhone")).sendKeys("111-111-1111");
        browser.findElement(By.name("password")).sendKeys("password");
        browser.findElement(By.name("submit")).click();
        return newEmailAddress;
    }

    @Test
    public void testSearchEmployee(){
        login();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String newEmailAddress = createEmployee();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        while(true){
            boolean doBreak = true;
            try {
                browser.findElement(By.name("email")).sendKeys(newEmailAddress);
            }
            catch(Exception e){
                if (e.getMessage().contains("element is not attached")) {
                    doBreak = false;
                }
            }
            if(doBreak){
                break;
            }
        }
        browser.findElement(By.name("search")).click();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        int index = 0;
        WebElement baseTable = browser.findElement(By.className("table"));
        List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
        tableRows.get(index).getText();
    }


    @After
    public void tearDown() {
        browser.close();
    }
}
