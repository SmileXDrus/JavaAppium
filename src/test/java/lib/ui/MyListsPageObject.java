package lib.ui;


import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TEMPLATE,
        ARTICLE_BY_TITLE_TEMPLATE,
        REMOVE_FROM_SAVED_BUTTON,
        WATCHLIST_ARTICLES;

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String substring_1) {
        return FOLDER_BY_NAME_TEMPLATE.replace("{FOLDER_NAME}", substring_1);
    }

    private static String getSavedArticleXpathByTitle(String substring) {
        return ARTICLE_BY_TITLE_TEMPLATE.replace("{TITLE}", substring);
    }

    private static String getRemoveButtonByTitle(String substring) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", substring);
    }
    /*/ TEMPLATES METHODS /*/

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        this.waitForElementAndClick(
                getFolderXpathByName(name_of_folder),
                "Cannot find created folder in My lists",
                10
        );
    }


    public void swipeByArticleToDelete(String name_of_article) {
        this.waitForArticleToAppearByTitle(name_of_article);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    getSavedArticleXpathByTitle(name_of_article),
                    "Cannot find saved article"
            );
        } else {
            String remove_locator = getRemoveButtonByTitle(name_of_article);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved",
                    10
            );
        }

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(
                    name_of_article,
                    "Cannot find saved article"
            );
        }
        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(name_of_article);
    }

    public void waitForArticleToDisappearByTitle(String name_of_article) {
        this.waitForElementNotPresent(
                getSavedArticleXpathByTitle(name_of_article),
                "Saved article still present with title " + name_of_article,
                10
        );
    }
    public int getCountOfArticlesInWatchlist() {
        return this.getAmountOfElements(WATCHLIST_ARTICLES);

    }
    public void waitForArticleToAppearByTitle(String name_of_article) {
        this.waitForElementPresent(
                getSavedArticleXpathByTitle(name_of_article),
                "Cannot find saved article by title",
                10
        );
    }
}
