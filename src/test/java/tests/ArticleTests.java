package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value="Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open Article and make sure the title is expected")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstringByDescription("bject-oriented programming language");

        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        String article_title = Article.getArticleTitle();
        // Article.takeScreenshot("article_page");
        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value="Article")})
    @DisplayName("Swipe Article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("Java");
        Search.clickByArticleWithSubstringByDescription("bject-oriented programming language");

        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        Article.waitForTitleElement();
        Article.swipeToFooter();
    }

    @Test
    @DisplayName("Article is not present after clear")
    @Features(value = {@Feature(value = "Search"),@Feature(value="Article"),@Feature(value="Clear")})
    @Description("Check Article not visibility after clear")
    @Step("Starting test testVisibilityArticlesAfterSearchAndClear")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testVisibilityArticlesAfterSearchAndClear() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        Search.waitForSearchResultByDescription("bject-oriented programming language");

        Assert.assertTrue("No one articles after search",Article.getAmountOfArticles() > 0);

        Search.clickCancelButton();
        Search.waitForSearchResultContainsText("Search and read the free encyclopedia in your language");
    }

    @Test
    @DisplayName("Article is not present after clear")
    @Features(value = {@Feature(value = "Search"),@Feature(value="Article")})
    @Description("Check searched articles contains search word by assert contains search word on article name")
    @Step("Starting test testCheckArticlesContainsSearchWord")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckArticlesContainsSearchWord() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        String search_line = "java";
        Search.typeSearchLine(search_line);
        for(String name_of_articles : Search.getTitleListOfArticlesContainsSearchWord()) {
            Assert.assertTrue(
                    name_of_articles + " attribute doesn't contain '" + search_line + "'",
                    name_of_articles.contains(search_line));
        }
    }

    @Test
    @DisplayName("Check article has title element")
    @Features(value = {@Feature(value = "Search"),@Feature(value="Article")})
    @Description("Check searched articles contains search word by assert contains search word on article name")
    @Step("Starting test testCheckArticlesContainsSearchWord")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckArticleTitleElement() {

        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstringByDescription("bject-oriented programming language");
        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        Article.waitForTitleElement();
    }
}
