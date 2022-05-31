package tests;

import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;

public class SearchTests extends CoreTestCase {
    @Test
    public void testFindStringBySearch() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.waitForSearchResultByDescription("bject-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.waitForCancelButtonToAppear();
        Search.clickCancelButton(); // Clear search input
        Search.clickCancelButton(); // Close searching window
        Search.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNonEmptySearch() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        String search_line = "Linkin Park Diskography";
        Search.typeSearchLine(search_line);
        int amount_of_search_results = Search.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found too few result!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        String search_line = "Jgtjhntb";
        Search.typeSearchLine(search_line);
        Search.waitForEmptyResultsLabel();
        Search.assertThereIsNoResultOfSearch();
    }
}
