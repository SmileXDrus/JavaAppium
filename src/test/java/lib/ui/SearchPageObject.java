package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BTN,
        SEARCH_DESCRIPTION_RESULT_TEMPLATE,
        SEARCH_TITLE_RESULT_TEMPLATE,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT,
        SEARCH_FOR_ANY_TEXT,
        SEARCH_ARTICLE_TITLE;

    /* TEMPLATES METHODS */
    private static String getResultSearchElementByDescription(String substring) {
        return SEARCH_DESCRIPTION_RESULT_TEMPLATE.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitle(String substring) {
        return SEARCH_TITLE_RESULT_TEMPLATE.replace("{SUBSTRING}", substring);
    }

    private static String getResultStringSearchForByText(String substring) {
        return SEARCH_FOR_ANY_TEXT.replace("{TEXT}", substring);
    }
    /*/ TEMPLATES METHODS /*/

    public void waitForSearchResultContainsText(String substring) {
        this.waitForElementPresent(getResultStringSearchForByText(substring), "Cannot find search result with text: " + substring);
    }

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Initializing the search field")
    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5);
    }

    @Step("Typing '{search_line}' to the search line")
    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type for search line",
                5);
    }

    @Step("Waiting for search result and select an article by substring '{substring}' in article title")
    public void waitForSearchResultByDescription(String substring) {
        this.waitForElementPresent(
                getResultSearchElementByDescription(substring),
                "Cannot find search result with substring: " + substring);
    }

    public void clickByArticleWithSubstringByDescription(String substring) {
        this.waitForElementAndClick(
                getResultSearchElementByDescription(substring),
                "Cannot find and click search result with substring: " + substring,
                10);
    }

    public void waitForSearchResultByTitle(String substring) {
        this.waitForElementPresent(
                getResultSearchElementByTitle(substring),
                "Cannot find search result with substring: " + substring);
    }

    public void clickByArticleWithSubstringByTitle(String substring) {
        String search_result_xpath = getResultSearchElementByDescription(substring);
        this.waitForElementAndClick(getResultSearchElementByTitle(substring),
                "Cannot find and click search result with substring: " + substring,
                10);
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BTN, "Cannot find search cancel button", 5);
    }

    @Step("Waiting for button to cancel search to be disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BTN, "Search cancel button is still present", 5);
    }

    public void clickCancelButton() {
        this.waitForElementAndClick(SEARCH_CANCEL_BTN, "Cannot find and click cancel button", 5);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT,
                "Cannot find empty result label by the request",
                15
        );

    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "Find some unexpected result by request"
        );
    }

    public ArrayList<String> getTitleListOfArticlesContainsSearchWord() {
        List<WebElement> elements = this.getListOfAllElementsLocatedBy(
                SEARCH_ARTICLE_TITLE,
                "No one articles after search"
        );

        ArrayList<String> list_title_of_articles = new ArrayList<>();
        for (WebElement element : elements) {
            list_title_of_articles.add(element
                    .getAttribute("text")
                    .toLowerCase());
        }
        return list_title_of_articles;
    }
}
