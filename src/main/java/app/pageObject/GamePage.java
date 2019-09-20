package app.pageObject;

import app.appElements.Cell;
import app.forms.NotificationArea;
import framework.browser.Browser;
import framework.utils.MathUtils;
import framework.utils.PropertyManager;
import framework.utils.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static framework.utils.LoggerUtil.LOGGER;

public class GamePage {
    private NotificationArea notificationArea;
    private By emptyEnemyCellsLoc = By.cssSelector(".battlefield__rival .battlefield-table .battlefield-cell__empty");
    private By hitEnemyCellsLoc = By.cssSelector(".battlefield__rival .battlefield-table .battlefield-cell__hit");
    private String PATT = "//div[contains(@class, 'battlefield battlefield__rival')]//td[contains(@class, 'battlefield-cell__empty')]//div[@data-y='%d' and @data-x='%d']";


    public GamePage() {
        notificationArea = new NotificationArea();
    }

    public NotificationArea getNotificationArea() {
        return notificationArea;
    }

    public void play() {
        notificationArea.waitForYourMove();
        List<WebElement> emptyCells = Browser.getDriver().findElements(emptyEnemyCellsLoc);
        emptyCells.get(MathUtils.getRandomInt(emptyCells.size())).click();
    }

    private Cell getEnemyCell(By loc) {
        return new Cell(loc, "Cell of enemy battleground");
    }

    private void runByDiagonal(int x, int y) {
        int border = y + 1;
        for (; x < border; x++, y--) {
            notificationArea.waitForYourMove();
            try {
                getEnemyCell(By.xpath(String.format(PATT, y, x))).click();
                LOGGER.info(String.format("Shot by cell x=%d y=%d", x, y));
            } catch (NoSuchElementException e) {
                continue;
            }
        }
    }

    public void run() {
        runByDiagonal(0, 3);
        runByDiagonal(0, 7);
        runByDiagonal(2, 9);
        runByDiagonal(6, 9);
        runByDiagonal(0, 1);
        runByDiagonal(0, 5);
        runByDiagonal(0, 9);
        runByDiagonal(4, 9);
        runByDiagonal(8, 9);
        for (int i = 0; i < 40; i++) {
            play();
        }
    }
}
