package lib.ui.mobile_web;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.MyListsPageObject;

public class MWMyListsPageObject extends MyListsPageObject
{
    static {
        ARTICLE_BY_TITLE_TEMPLATE = "xpath://*[@text='{TITLE}']";
        REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class,'watchlist')]//h3[contains(text(),'{TITLE}')]/../../div[contains(@class,'watched')]";
        WATCHLIST_ARTICLES = "#mw-content-text > ul > li";
    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
