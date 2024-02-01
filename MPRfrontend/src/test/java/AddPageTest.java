import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.AddViewPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddPageTest {
    public static final String URL = "http://localhost:8081/addCar";
    WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = new FirefoxDriver();
    }

    @AfterAll
    public void tearDown() {
        webDriver.quit();
    }
    @Test
    public void open(){
        AddViewPage addViewPage = new AddViewPage(webDriver);
        addViewPage.open();
        assertEquals(URL, webDriver.getCurrentUrl());
    }
    @Test
    public void fillInBrand(){
        AddViewPage addViewPage = new AddViewPage(webDriver);
        addViewPage.open();
        addViewPage.fillInBrand("brand");
        assertEquals("brand", addViewPage.brand.getAttribute("value"));
    }
    @Test
    public void fillInModel(){
        AddViewPage addViewPage = new AddViewPage(webDriver);
        addViewPage.open();
        addViewPage.fillInModel("model");
        assertEquals("model", addViewPage.model.getAttribute("value"));
    }
    @Test
    public void fillInPrice(){
        AddViewPage addViewPage = new AddViewPage(webDriver);
        addViewPage.open();
        addViewPage.fillInPrice("price");
        assertEquals("price", addViewPage.price.getAttribute("value"));
    }
    @Test
    public void submit(){
        AddViewPage addViewPage = new AddViewPage(webDriver);
        addViewPage.open();
        addViewPage.fillInBrand("brand");
        addViewPage.fillInModel("model");
        addViewPage.fillInPrice("price");
        addViewPage.submit();
        String actualUrl = webDriver.getCurrentUrl();
        String baseUrl = actualUrl.split(";jsessionid")[0];
        assertEquals("http://localhost:8081/addCar", baseUrl);
    }
}
