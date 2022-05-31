package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    public void testPassThroughWelcome()
    {
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            return;
        }

        WelcomePageObject Welcome = new WelcomePageObject(driver);

        Welcome.waitForLearnMoreLink();
        Welcome.clickNextButton();

        Welcome.waitForNewWayToExploreText();
        Welcome.clickNextButton();

        Welcome.waitForAddOrEditPreferredLangText();
        Welcome.clickNextButton();

        Welcome.waitForLearnMoreAboutDataCollectedText();
        Welcome.clickGetStartedButton();
    }
}
