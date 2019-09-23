import app.pageObject.GamePage;
import app.pageObject.MainPage;
import app.steps.Step;
import framework.base.BaseTest;
import framework.browser.Browser;
import framework.utils.PropertyManager;
import org.testng.annotations.Test;

import static framework.utils.LoggerUtil.LOGGER;
import static framework.utils.LoggerUtil.step;
import static org.testng.Assert.assertTrue;

public class BattleShipTest extends BaseTest {

    private final int COUNT_OF_REPLACE_SHIPS = 16;
    private int counter = 1;

    @Test
    public void BattleShip() {
        step("Go to http://ru.battleship-game.org", counter++);
        Browser.enterUrl(PropertyManager.getConfigProperty("url"));

        MainPage mainPage = new MainPage();
        LOGGER.info("Checking that main page was opened");
        assertTrue(mainPage.isMainPage(), "This is not the main page");

        step("Choose random enemy", counter++);
        mainPage.chooseRandomEnemy();
        LOGGER.info("Checking that random enemy chosen");
        assertTrue(mainPage.isRandomEnemyEnabled(), "Random enemy didn't choose");

        step("Click on random place ship", counter++);
        Step.selectRandomPlaceShip(COUNT_OF_REPLACE_SHIPS);

        step("Click on start game", counter++);
        mainPage.startGame();

        step("Wait for enemy", counter++);
        GamePage gamePage = new GamePage();

        step("Try to win", counter++);
        gamePage.run();
        LOGGER.info("Checking the result of game");
        assertTrue(Boolean.parseBoolean(gamePage.getNotificationArea().getEndGameNotification()[0]), gamePage.getNotificationArea().getEndGameNotification()[1]);
    }
}
