package RegistrationPage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RegistrationTestSuite {

    private WebDriver driver;

    // Constants
    private final String BASE_URL = "http://localhost/registracia.php";
    private final String EMAIL_FIELD_NAME = "email";
    private final String FIRST_NAME_FIELD_NAME = "name";
    private final String LAST_NAME_FIELD_NAME = "surname";
    private final String PASSWORD_FIELD_NAME = "password";
    private final String CONFIRM_PASSWORD_FIELD_NAME = "password-repeat";
    private final String CHECKBOX_ID = "checkbox";
    private final String SUBMIT_BUTTON_XPATH = "//button[@type='submit']";
    private final String ERROR_MESSAGE_XPATH = "//div[contains(@class, 'alert-danger')]";
    private final String SUCCESS_MESSAGE_XPATH = "//div[contains(@class, 'alert-success')]";

    // Valid test data
    private String email = "matko@gmail.com";
    private String firstName = "Matej";
    private String lastName = "Sliacky";
    private String password = "pwd123";
    private String anotherPassword = "pwd1234";

    @Before
    public void SetUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @Test
    public void testEmptyForm() {

        fillDataAndSubmit("", "", "", "", "");
        checkResult(ERROR_MESSAGE_XPATH);
    }

    @Test
    public void testEmptyPasswords() {

        fillDataAndSubmit(email, firstName, lastName, "", "");
        checkResult(ERROR_MESSAGE_XPATH);

    }

    @Test
    public void testMismatchedPasswords() {

        fillDataAndSubmit(email, firstName, lastName, password, anotherPassword);
        checkResult(ERROR_MESSAGE_XPATH);
    }

    @Test
    public void successfulRegistrationTest() {

        fillDataAndSubmit(email, firstName, lastName, password, password);
        checkResult(SUCCESS_MESSAGE_XPATH);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    // Helper functions
    private void fillDataAndSubmit(String email, String firstName, String lastName, String pwd, String confirmPwd) {
        driver.findElement(By.name(EMAIL_FIELD_NAME)).sendKeys(email);
        driver.findElement(By.name(FIRST_NAME_FIELD_NAME)).sendKeys(firstName);
        driver.findElement(By.name(LAST_NAME_FIELD_NAME)).sendKeys(lastName);
        driver.findElement(By.name(PASSWORD_FIELD_NAME)).sendKeys(pwd);
        driver.findElement(By.name(CONFIRM_PASSWORD_FIELD_NAME)).sendKeys(confirmPwd);
        driver.findElement(By.id(CHECKBOX_ID)).click();
        driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH)).click();
    }

    private void checkResult(String elementXpath) {
        Assert.assertTrue(driver.findElement(By.xpath(elementXpath)).isDisplayed());
    }


}
