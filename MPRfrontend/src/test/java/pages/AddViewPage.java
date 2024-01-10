package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddViewPage {
    public static final String URL= "http://localhost:8081/addCar";
    @FindBy(id="brand")
    public WebElement brand;
    @FindBy(id="model")
    public WebElement model;
    @FindBy(id="price")
    public WebElement price;
    @FindBy(id="submit")
    WebElement submitButton;
    WebDriver webDriver;
    public AddViewPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }
    public void open(){
        webDriver.get(URL);
    }
    public void fillInBrand(String brand){
        this.brand.sendKeys(brand);
    }
    public void fillInModel(String model){
        this.model.sendKeys(model);
    }
    public void fillInPrice(String price){
        this.price.sendKeys(price);
    }
    public void submit(){
        submitButton.click();
    }
}
