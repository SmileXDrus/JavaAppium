package tests;

import lib.Platform;
import lib.ui.AuthPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;

public class MyListsTests extends CoreTestCase {

    private static final String
            name_of_folder = "Learning programming",
            search_line = "java",
            login = "Testfortests",
            password = "fortests00"
    ;

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstringByDescription("bject-oriented programming language");

        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        Article.waitForTitleElement();
        String name_of_article = Article.getArticleTitle();

        if(Platform.getInstance().isAndroid()) {
            Article.addArticleToMyList(name_of_folder);
        } else {
            Article.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW()){
            this.auth();
            Assert.assertEquals("We back not to the same page after login.",
                    name_of_article,
                    Article.getArticleTitle()
            );
            Article.addArticlesToMySaved();
        }
        Article.closeArticle();

        NavigationUI Navigation = NavigationUIFactory.get(driver);
        Navigation.clickMyLists();

        MyListsPageObject MyList = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyList.openFolderByName(name_of_folder);
        }
        MyList.swipeByArticleToDelete(name_of_article);
    }

    @Test
    public void testSaveSeveralArticleToMyListAndCheckRemoval() {
        String
                article_description = "bject-oriented programming language",
                article_description_for_delete = "igh-level programming language";

        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine(search_line);
        Search.clickByArticleWithSubstringByDescription(article_description);

        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        String name_of_article_before_save_in_my_list = Article.getArticleTitle();
        if(Platform.getInstance().isAndroid()) {
            Article.addArticleToMyList(name_of_folder);
        } else {
            Article.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW()){
            this.auth();
            Assert.assertEquals("We back not to the same page after login.",
                    name_of_article_before_save_in_my_list,
                    Article.getArticleTitle()
            );
            Article.addArticlesToMySaved();
        }
        Article.closeArticle();

        Search.initSearchInput();
        Search.typeSearchLine(search_line);
        Search.clickByArticleWithSubstringByDescription(article_description_for_delete);;
        if (Platform.getInstance().isAndroid()) {
            Article.addArticleToMyListInExistingFolder(name_of_folder);
        } else {
            Article.addArticlesToMySaved();
        }
        String name_of_article_for_delete = Article.getArticleTitle();
        Article.closeArticle();

        NavigationUI Navigation = NavigationUIFactory.get(driver);
        Navigation.clickMyLists();

        MyListsPageObject MyList = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyList.openFolderByName(name_of_folder);
        }
        MyList.swipeByArticleToDelete(name_of_article_for_delete);
        int count_of_articles = MyList.getCountOfArticlesInWatchlist();
        MyList.waitForArticleToAppearByTitle(name_of_article_before_save_in_my_list);
        Search.clickByArticleWithSubstringByDescription(article_description);
        String title = Article.getArticleTitle();

        Assert.assertTrue("Article with title '" +title+ "' not in watchlist ", Article.checkArticleSavedInWatchlist());
        Assert.assertEquals(
                "Text attribute " + title + " not equal " + name_of_article_before_save_in_my_list,
                name_of_article_before_save_in_my_list,
                title);
        Assert.assertTrue("More articles ("+count_of_articles+") than expected ",count_of_articles<2);
    }

    private void auth()
    {
        AuthPageObject Auth = new AuthPageObject(driver);
        Auth.clickAuthButton();
        Auth.enterLogInData(login, password);
        Auth.submitForm();
    }
}
