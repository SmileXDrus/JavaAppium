package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;


abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            CLICK_TO_EXISTING_FOLDER_IN_MY_LIST,
            COUNT_OF_ARTICLES,
            OPTION_REMOVE_FROM_MY_LIST_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getNameFolderString(String substring) {
        return CLICK_TO_EXISTING_FOLDER_IN_MY_LIST.replace("{SUBSTRING}", substring);
    }
    /*/ TEMPLATES METHODS /*/

    @Step("Waiting for title on the article page")
    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article title on the page", 15);
    }

    @Step("Get article title")
    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else
             return title_element.getText();
    }

    @Step("Swiping to footer on article page")
    public void  swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        }
        else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        }
    }

    @Step("Adding the article to my list")
    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find 'more option button'",
                5
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got id' button",
                5
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find text input for set name",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text for set name",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    @Step("Adding the article to MyList in the existing folder")
    public void addArticleToMyListInExistingFolder(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find 'more option button'",
                5
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                getNameFolderString(name_of_folder),
                "Cannot find existing folder with name " + name_of_folder,
                5
        );
    }

    @Step("Return amount of articles")
    public int getAmountOfArticles() {
        return this.getAmountOfElements(
                COUNT_OF_ARTICLES
        );
    }

    @Step("Close article (Does nothing for platform 'mobile_web')")
    public void closeArticle() {
        if(Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X button",
                    5
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    public void addArticlesToMySaved() {
        if(Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
    }
    public void removeArticleFromSavedIfItAdded()
    {
        if (this.isElementPresent(OPTION_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTION_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before."
            );
        }
    }
    public boolean checkArticleSavedInWatchlist() {
        return this.isElementPresent(OPTION_REMOVE_FROM_MY_LIST_BUTTON);
    }
}

