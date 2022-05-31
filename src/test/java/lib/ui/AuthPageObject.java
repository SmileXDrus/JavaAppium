package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthPageObject extends MainPageObject{
    private static final String
            LOGIN_BUTTON = "css:a.mw-ui-button.mw-ui-progressive", //a.mw-ui-button.mw-ui-progressive
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AuthPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void clickAuthButton()
    {
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button.", 5);
        this.tryClickElementWithFewAttempts(LOGIN_BUTTON, "Cannot find and click auth button.", 5);
    }

    public void enterLogInData(String login, String password)
    {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input.", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and put a password to the password input.", 5);
    }

    public void submitForm()
    {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button.", 5);
    }
}
