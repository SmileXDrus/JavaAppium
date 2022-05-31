package lib.ui.mobile_web;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.SearchPageObject;

public class MWSearchPageObject extends SearchPageObject
{
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BTN = "css:button.cancel";
        SEARCH_DESCRIPTION_RESULT_TEMPLATE = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT = "css:p.without-results";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
