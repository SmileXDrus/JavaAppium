package lib.ui.android;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.MyListsPageObject;

public class AndroidMyListsPageObject extends MyListsPageObject {
    static {
        FOLDER_BY_NAME_TEMPLATE = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TEMPLATE = "xpath://*[@text='{TITLE}']";
    }

    public AndroidMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
