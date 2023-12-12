package com.example;

import org.junit.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class InternetHerokuappTests {

    private WebDriver driver;

    @Before
    public void setUp() {

        //Link para baixar o cromedriver
        //https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/120.0.6099.71/win64/chromedriver-win64.zip
        System.setProperty("webdriver.chrome.driver", "C:/Users/marcu/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginPage() {

        driver.get("https://the-internet.herokuapp.com/login");
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.sendKeys("tomsmith");
        passwordInput.sendKeys("SuperSecretPassword!");
        loginButton.click();

        WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
        assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testCheckboxes() {

        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement checkbox1 = driver.findElement(By.cssSelector("input[type='checkbox']:first-of-type"));
        WebElement checkbox2 = driver.findElement(By.cssSelector("input[type='checkbox']:nth-of-type(2)"));

        assertFalse(checkbox1.isSelected());
        assertTrue(checkbox2.isSelected());

        checkbox1.click();
        checkbox2.click();

        assertTrue(checkbox1.isSelected());
        assertFalse(checkbox2.isSelected());
    }

     @Test
    public void testDynamicLoading() {

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement startButton = driver.findElement(By.cssSelector("#start button"));
        startButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement finishText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        assertTrue(finishText.getText().contains("Hello World!"));
    }

    @Test
    public void testDragAndDrop() {

        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        WebElement source = driver.findElement(By.id("column-a"));
        WebElement target = driver.findElement(By.id("column-b"));

        new Actions(driver).dragAndDrop(source, target).perform();

        assertEquals("A", target.getText());
    }

    @Test
    public void testKeyPress() {

        driver.get("https://the-internet.herokuapp.com/key_presses");
        WebElement target = driver.findElement(By.id("result"));

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();

        assertEquals("You entered: ENTER", target.getText());
    }

    

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void testHovers() {

        driver.get("https://the-internet.herokuapp.com/hovers");
        WebElement user1 = driver.findElement(By.cssSelector(".figure:nth-of-type(1)"));
        Actions actions = new Actions(driver);
        actions.moveToElement(user1).perform();

        assertTrue(driver.findElement(By.cssSelector(".figure:nth-of-type(1) .figcaption")).isDisplayed());
    }

    @Test
    public void testJavaScriptAlerts() {

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement jsAlertButton = driver.findElement(By.cssSelector("button[onclick='jsAlert()']"));

        jsAlertButton.click();
        Alert alert = driver.switchTo().alert();
        assertEquals("I am a JS Alert", alert.getText());
        alert.accept();
    }

    @Test
    public void testInfiniteScroll() {

        driver.get("https://the-internet.herokuapp.com/infinite_scroll");
        WebElement lastParagraph = driver.findElement(By.cssSelector(".jscroll-added:last-of-type"));

        Actions actions = new Actions(driver);
        actions.moveToElement(lastParagraph).perform();

        assertTrue(lastParagraph.isDisplayed());
    }

    @Test
    public void testJavaScriptError() {

        driver.get("https://the-internet.herokuapp.com/javascript_error");

    }

    @Test
    public void testDropdownSelection() {

        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement dropdown = driver.findElement(By.id("dropdown"));

        Select select = new Select(dropdown);
        select.selectByVisibleText("Option 1");

        assertEquals("Option 1", select.getFirstSelectedOption().getText());
    }
}
