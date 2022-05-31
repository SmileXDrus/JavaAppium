package lib.ui.factories;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.ios.IOSArticlePageObject;
import lib.ui.mobile_web.MWArticlePageObject;

// pattern factory https://javarush.ru/groups/posts/2370-pattern-proektirovanija-factory
public class ArticlePageObjectFactory {

    public static ArticlePageObject get(RemoteWebDriver driver){
        if (Platform.getInstance().isAndroid()) {
            return new AndroidArticlePageObject(driver);
        }
        else if (Platform.getInstance().isIOS()) {
            return new IOSArticlePageObject(driver);
        }
        else {
            return new MWArticlePageObject(driver);
        }
    }
}
