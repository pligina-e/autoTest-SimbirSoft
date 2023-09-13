package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    public ChromeDriver driver;
    public WebDriverWait wait;
    //конструктор
    public BasePage(ChromeDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }
    //убеждаемся, что элементы отображены на странице
    public void waitVisibility(By elementBy) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
    }
    //Метод нахождения веб-элемента
    public WebElement find (By elementBy) {
        waitVisibility(elementBy);
        return driver.findElement(elementBy);
    }
    //Метод нажатия по веб-элементу
    public void click (By elementBy) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).click();
    }
    //Ввод текста в поле веб-элемента
    public void writeText (By elementBy, String text) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).sendKeys(text);
    }
    //Ввод текста в поле веб-элемента и нажатие клавиши "Enter"
    public void writeTextAndPressEnter (By elementBy, String text) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).sendKeys(text, Keys.ENTER);
    }
    //Assert-ы:
    public void assertEqualsString (String message, String actual, String expectedText) {
        Assert.assertEquals(message, actual, expectedText);
    }
    /*
    public void assertEqualsAttribute (By elementBy, String expectedText) { //использование закомментировано
        waitVisibility(elementBy);
        Assert.assertEquals(driver.findElement(elementBy).getAttribute("value"), expectedText);
        //getAttribute() извлекает текст, содержащийся в атрибуте HTML-документа
    }
    public void assertEqualsText (By elementBy, String expectedText) { //использование в StudentRegFormPage закомментировано
        waitVisibility(elementBy);
        Assert.assertEquals(driver.findElement(elementBy).getText(), expectedText);
        //getText() возвращает внутренний текст элемента
    }
    */
}

