import app.pageObject.GamePage;
import app.pageObject.MainPage;
import app.steps.Step;
import framework.base.BaseTest;
import framework.browser.Browser;
import framework.utils.PropertyManager;
import org.testng.annotations.Test;

import static framework.utils.LoggerUtil.step;
import static org.testng.Assert.assertTrue;

public class BattleShipTest extends BaseTest {

    private int counter = 1;
    private final int COUNT_OF_REPLACE_SHIPS = 15;

    @Test
    public void BattleShip() {
        step("Go to http://ru.battleship-game.org", counter++);
        Browser.enterUrl(PropertyManager.getConfigProperty("url"));

        MainPage mainPage = new MainPage();
        assertTrue(mainPage.isMainPage(), "This is not the main page");
        step("Choose random enemy", counter++);
        mainPage.chooseRandomEnemy();
        assertTrue(mainPage.isRandomEnemyEnabled(), "Random enemy didn't choose");
        step("Click on random place ship", counter++);
        Step.clickOnSelectRandomPlaceShip(COUNT_OF_REPLACE_SHIPS + 1);
        step("Click on start game", counter++);
        mainPage.startGame();
        step("Wait for enemy", counter++);
        mainPage.getNotificationArea().waitForEnemy();

        GamePage gamePage = new GamePage();
        gamePage.getNotificationArea().waitForYourFirstMove();
        step("Try to win", counter++);

        gamePage.run();
    }
}
