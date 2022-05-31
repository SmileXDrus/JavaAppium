package lib.ui.android;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {
    static String
            MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public AndroidNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
