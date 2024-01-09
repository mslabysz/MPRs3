package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Comparator;
import java.util.List;

public class IndexPage {
    public static final String URL = "http://localhost:8081/index";
    WebDriver webDriver;

    public IndexPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @FindBy(id="add-new-car")
    WebElement addCarButton;
    public void open(){
        webDriver.get(URL);
        PageFactory.initElements(webDriver, this);
    }
    public void clickAddCarButton(){
        addCarButton.click();
    }
    public void clickEditButton() {
        List<WebElement> editButtons = webDriver.findElements(By.cssSelector("a[id^='edit-']"));
        WebElement elementToEdit = editButtons.stream()
                .min(Comparator.comparingInt(e -> Integer.parseInt(e.getAttribute("id").split("-")[1])))
                .orElseThrow(() -> new NoSuchElementException("No edit button found"));

        elementToEdit.click();
    }
    public void clickDeleteButton() {
        List<WebElement> deleteButtons = webDriver.findElements(By.cssSelector("a[id^='delete-']"));
        WebElement elementToDelete = deleteButtons.stream()
                .min(Comparator.comparingInt(e -> Integer.parseInt(e.getAttribute("id").split("-")[1])))
                .orElseThrow(() -> new NoSuchElementException("No delete button found"));

        elementToDelete.click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }
}
