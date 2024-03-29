import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.EditViewPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EditPageTest {
    public static final String URL = "http://localhost:8081/editCar/2";
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
        EditViewPage editViewPage = new EditViewPage(webDriver);
        editViewPage.open();
        assertEquals(URL, webDriver.getCurrentUrl());
    }
    @Test
    public void fillInBrand(){
        EditViewPage editViewPage = new EditViewPage(webDriver);
        editViewPage.open();
        editViewPage.clearBrand();
        editViewPage.fillInBrand("brand");
        assertEquals("brand", editViewPage.brand.getAttribute("value"));
    }
    @Test
    public void fillInModel(){
        EditViewPage editViewPage = new EditViewPage(webDriver);
        editViewPage.open();
        editViewPage.clearModel();
        editViewPage.fillInModel("model");
        assertEquals("model", editViewPage.model.getAttribute("value"));
    }
    @Test
    public void fillInPrice(){
        EditViewPage editViewPage = new EditViewPage(webDriver);
        editViewPage.open();
        editViewPage.clearPrice();
        editViewPage.fillInPrice("price");
        assertEquals("price", editViewPage.price.getAttribute("value"));
    }
   @Test
public void submit(){
    EditViewPage editViewPage = new EditViewPage(webDriver);
    editViewPage.open();
    editViewPage.submit();
    String actualUrl = webDriver.getCurrentUrl();
    String baseUrl = actualUrl.split(";jsessionid")[0];
    assertEquals("http://localhost:8081/index", baseUrl);
}
}
