package lib.ui.ios;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
        SEARCH_CANCEL_BTN = "id:Close";
        SEARCH_DESCRIPTION_RESULT_TEMPLATE = ""; //TODO check locator before usage
        SEARCH_TITLE_RESULT_TEMPLATE = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_FOR_ANY_TEXT = "xpath://*[@text = '{TEXT}']";
        SEARCH_ARTICLE_TITLE = "xpath://XCUIElementTypeLink"; //TODO check locator before usage
    }

    public IOSSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
