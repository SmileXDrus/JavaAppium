package lib.ui.android;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://android.widget.ImageView[@content-desc=\"Search Wikipedia\"]";
        SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
        SEARCH_CANCEL_BTN = "id:org.wikipedia:id/search_close_btn";
        SEARCH_DESCRIPTION_RESULT_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING}']";
        SEARCH_TITLE_RESULT_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT = "xpath://*[@text = 'No results found']";
        SEARCH_FOR_ANY_TEXT = "xpath://*[@text = '{TEXT}']";
        SEARCH_ARTICLE_TITLE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']";
    }

    public AndroidSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
