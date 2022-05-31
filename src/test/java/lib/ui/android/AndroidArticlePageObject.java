package lib.ui.android;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {
    static {
            TITLE = "id:org.wikipedia:id/view_page_title_text";
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
            MY_LIST_OK_BUTTON = "id:android:id/button1";
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
            CLICK_TO_EXISTING_FOLDER_IN_MY_LIST = "xpath://*[@text='{SUBSTRING}']";
            COUNT_OF_ARTICLES = "xpath://android.widget.ListView/android.widget.LinearLayout";
    }

    public AndroidArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
