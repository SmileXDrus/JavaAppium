package lib.ui.ios;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TEMPLATE = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
    }

    public IOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
