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

    public void run() {
        for (int x = 0, y = 3; x < 4; x++, y--) {
            notificationArea.waitForYourMove();
            Waiter.implicitWait(1);
            try {
                getEnemyCell(By.xpath(String.format(PATT, y, x))).click();
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        for (int x = 0, y = 7; x < 8; x++, y--) {
            notificationArea.waitForYourMove();
            try {
                getEnemyCell(By.xpath(String.format(PATT, y, x))).click();
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        for (int x = 2, y = 9; x < 10; x++, y--) {
            notificationArea.waitForYourMove();
            try {
                getEnemyCell(By.xpath(String.format(PATT, y, x))).click();
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        for (int x = 6, y = 9; x < 10; x++, y--) {
            notificationArea.waitForYourMove();
            try {
                getEnemyCell(By.xpath(String.format(PATT, y, x))).click();
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        Waiter.implicitWait(Integer.parseInt(PropertyManager.getConfigProperty("timeout")));
    }
}
