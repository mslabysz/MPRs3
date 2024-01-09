import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.IndexPage;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IndexPageTest {
    public static final String URL = "http://localhost:8081/index";
    WebDriver webDriver;
    @BeforeEach
    public void setUp(){
        webDriver = new FirefoxDriver();
    }
    @AfterAll
    public void tearDown(){
        webDriver.quit();
    }
    @Test
    public void open(){
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage.open();
        assertEquals(URL, webDriver.getCurrentUrl());
    }
    @Test
    public void clickAddCarButton(){
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage.open();
        indexPage.clickAddCarButton();
        assertEquals("http://localhost:8081/addCar", webDriver.getCurrentUrl());
    }
    @Test
    public void clickEditButton(){
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage.open();
        indexPage.clickEditButton();
        assertEquals("http://localhost:8081/editCar/1", webDriver.getCurrentUrl());
    }
    @Test
    public void clickDeleteButton(){
        IndexPage indexPage = new IndexPage(webDriver);
        indexPage.open();
        indexPage.clickDeleteButton();
        assertEquals("http://localhost:8081/index", webDriver.getCurrentUrl());
    }
}
