package app.pageObject;

import app.forms.NotificationArea;
import framework.elements.Button;
import framework.utils.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;

import static framework.base.BaseElement.countOfSuchElements;
import static framework.utils.LoggerUtil.LOGGER;
import static framework.utils.MathUtils.getRandomInt;

public class GamePage {
    private NotificationArea notificationArea;
    private String PATTERN_FOR_EMPTY_CELL = "(//div[contains(@class,'battlefield__rival')]//td[contains(@class,'battlefield-cell__empty')])[%d]";
    private By emptyEnemyCellsLoc = By.cssSelector(".battlefield__rival .battlefield-table .battlefield-cell__empty");
    private By hitEnemyCellsLoc = By.cssSelector(".battlefield__rival .battlefield-table .battlefield-cell__hit");
    private String PATT = "//div[contains(@class, 'battlefield battlefield__rival')]//div[@data-y='%d' and @data-x='%d']//..";

    public GamePage() {
        notificationArea = new NotificationArea();
    }

    public void run() {
        waitForEnemy();
        runByDiagonal(0, 3);
        runByDiagonal(0, 7);
        runByDiagonal(2, 9);
        runByDiagonal(6, 9);
        runByDiagonal(0, 1);
        runByDiagonal(0, 5);
        runByDiagonal(0, 9);
        runByDiagonal(4, 9);
        runByDiagonal(8, 9);
        randomShots();
    }

    private void randomShots() {
        while (countOfSuchElements(emptyEnemyCellsLoc) > 0) {
            if (notificationArea.youLose()) {
                System.out.println("You lose");
                break;
            } else if (notificationArea.youWin()) {
                System.out.println("You win");
                break;
            }
            try {
                Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveNotificationLoc());
                if (notificationArea.youLose()) {
                    break;
                }
                getEnemyCell(By.xpath(String.format(PATTERN_FOR_EMPTY_CELL, getRandomInt(countOfSuchElements(emptyEnemyCellsLoc)) + 1))).click();
                LOGGER.info("Random shot");
            } catch (ElementClickInterceptedException e) {
                continue;
            }
        }
    }

    private Button getEnemyCell(By loc) {
        return new Button(loc, "Cell of enemy battleground");
    }

    private boolean checkForHit(Button cell) {
        return cell.getAttribute("class").contains("hit");
    }

    private boolean checkForKill(Button cell) {
        return cell.getAttribute("class").contains("done");
    }

    private void runByDiagonal(int x, int y) {
        int border = y + 1;
        for (; x < border; x++, y--) {
            try {
                Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveNotificationLoc());
                if (notificationArea.youLose()) {
                    System.out.println("You lose");
                    break;
                } else if (notificationArea.youWin()) {
                    System.out.println("You win");
                    break;
                }
                Button enemyCell = getEnemyCell(By.xpath(String.format(PATT, y, x)));
                LOGGER.info(String.format("Shot by cell x=%d y=%d", x, y));
                enemyCell.click();
                Waiter.waitWhileElementProcessing(enemyCell);
                if (checkForHit(enemyCell)) {
                    LOGGER.info("Enemy ship hit");
                    x++;
                    y--;
                    if (checkForKill(enemyCell)) {
                        System.out.println("Kill small enemy ship");
                    }
                }
            } catch (ElementClickInterceptedException e) {
                x--;
                y++;
            }
        }
    }

    private void waitForEnemy() {
        Waiter.waitUntilElementIsVisible(notificationArea.getConnectionNotificationLoc());
        Waiter.waitUntilElementIsVisible(notificationArea.getWaitingForEnemyNotificationLoc());
        Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveFirstNotificationLoc());
    }
}
