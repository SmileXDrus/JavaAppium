package lib.ui.factories;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.ios.IOSMyListsPageObject;
import lib.ui.mobile_web.MWMyListsPageObject;

public class MyListsPageObjectFactory {
    public static MyListsPageObject get(RemoteWebDriver driver){
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMyListsPageObject(driver);
        }
        else if (Platform.getInstance().isIOS()) {
            return new IOSMyListsPageObject(driver);
        }
        else {
            return new MWMyListsPageObject(driver);
        }
    }
}
