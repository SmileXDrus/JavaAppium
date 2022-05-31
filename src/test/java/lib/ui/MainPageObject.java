package lib.ui;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import lib.Platform;

public class MainPageObject {
    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public boolean assertExpectedTextMatchesTheTextAttribute(String locator_with_type, String expected_text, String attribute, String error_message) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.attributeContains(by, attribute, expected_text)
        );
    }

    public List<WebElement> getListOfAllElementsLocatedBy(String locator_with_type, String error_message) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(by)
        );
    }

    public WebElement waitForElementPresent(String locator_with_type, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public boolean assertElementPresent(String locator_with_type) {
        By by = this.getLocatorByString(locator_with_type);
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e_selenium) {
            return false;
        }
    }

    public WebElement waitForElementPresent(String locator_with_type, String error_message) {
        // в java нет параметра по умолчанию, используют перегрузку методов
        return waitForElementPresent(locator_with_type, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator_with_type, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeysByUseMobileElement(String locator_with_type, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSeconds);
        MobileElement elMobile = (MobileElement) element;
        elMobile.setValue(value);
        return elMobile;
    }

    public WebElement waitForElementAndSendKeys(String locator_with_type, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator_with_type, String error_message, long timeoutInSecond) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator_with_type, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSecond);
        element.clear();
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction<?> action = new TouchAction<>((AppiumDriver<?>) driver);
            Dimension size = driver.manage().window().getSize();
            int x = (int) (size.width * 0.8);
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);
            action
                    .press(PointOption.point(x, start_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, end_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeUpQuick() {
        swipeUp(100);
    }

    public void swipeUpToFindElement(String locator_with_type, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator_with_type);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(locator_with_type, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeTillElementAppear(String locator_with_type, String err_msg, int max_swipe) {
        int already_swiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator_with_type)) {
            if (already_swiped > max_swipe) {
                Assert.assertTrue(err_msg, this.isElementLocatedOnTheScreen(locator_with_type));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    public boolean isElementLocatedOnTheScreen(String locator_with_type) {
        int element_location_by_y = this.waitForElementPresent(
                locator_with_type,
                "Cannot find element by locator",
                1
        ).getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }

        int screen_size_by_y = driver
                .manage()
                .window()
                .getSize()
                .getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void swipeElementToLeft(String locator_with_type, String err_msg) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(
                    locator_with_type,
                    err_msg,
                    10);

            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction<?> action = new TouchAction<>((AppiumDriver<?>) driver);
            action.press(PointOption.point(right_x, middle_y));
            action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));
            if (Platform.getInstance().isAndroid()) {
                action.moveTo(PointOption.point(left_x / 2, middle_y));
            } else {
                int offset_x = (-1 * element.getSize().getWidth());
                action.moveTo(PointOption.point(offset_x, 0));
            }
            action.release();
            action.perform();
        } else {
            System.out.println("Method swipeElementToLeft() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void clickElementToTheRightUpperCorner(String locator_with_type, String err_msg) {
        if (driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator_with_type + "/..", err_msg); //переход на локатор выше
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int point_to_click_y = (upper_y + lower_y) / 2;
            int width = element.getSize().getWidth();

            int point_to_click_x = (right_x + width) - 3;

            TouchAction<?> action = new TouchAction<>((AppiumDriver<?>) driver);
            action
                    .tap(PointOption.point(point_to_click_x, point_to_click_y))
                    .perform();
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public int getAmountOfElements(String locator_with_type) {
        By by = this.getLocatorByString(locator_with_type);
        List<?> elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator_with_type, String err_msg) {
        int amount_of_elements = getAmountOfElements(locator_with_type);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + locator_with_type + "' supposed to be not present";
            throw new AssertionError(default_message + " " + err_msg);
        }
    }

    public String waitForElementAndGetAttribute(String locator_with_type, String attribute, String err_msg, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, err_msg, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        switch (by_type) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return By.id(locator);
            case "css":
                return By.cssSelector(locator);
            default:
                throw new IllegalArgumentException("Cannot get type of locator. Locator value: " + locator_with_type);
        }
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp() does  nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swipes) {
        int already_swiped = 0;

        WebElement element = this.waitForElementPresent(locator, error_message);

        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++already_swiped;
            if (already_swiped > max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

    public void tryClickElementWithFewAttempts(String locator, String error_message, int amount_of_attempts) {
        int current_attempts = 0;
        boolean need_more_attempts = true;

        while (need_more_attempts) {
            try {
                this.waitForElementAndClick(locator, error_message, 1);
                need_more_attempts = false;
            } catch (Exception e) {
                if (current_attempts > amount_of_attempts) {
                    // last attempt
                    this.waitForElementAndClick(locator, error_message, 1);
                }
            }
            ++current_attempts;
        }
    }

    // скрин в текущую папку проекта
    public String takeScreenshot(String name) {
        TakesScreenshot ts = (TakesScreenshot) this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("This screenshot was taken: " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    // скрин в отчет аллюр
    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));

        } catch (IOException e) {
            System.out.println("Cannot get butes from screenshot. Error: " +e.getMessage());
        }
        return bytes;
    }
}
