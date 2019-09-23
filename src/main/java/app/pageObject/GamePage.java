package app.pageObject;

import app.forms.NotificationArea;
import framework.elements.Button;
import framework.utils.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;

import static app.enums.CellStatus.CellStatuses;
import static framework.base.BaseElement.countOfSuchElements;
import static framework.utils.LoggerUtil.LOGGER;
import static framework.utils.MathUtils.getRandomInt;

public class GamePage {
    private final int MIN_FIELD_BORDER = 0;
    private final int MAX_FIELD_BORDER = 9;
    private final String CLASS = "class";
    private NotificationArea notificationArea;
    private String PATTERN_FOR_EMPTY_RANDOM_CELL = "(//div[contains(@class,'battlefield__rival')]//td[contains(@class,'battlefield-cell__empty')])[%d]";
    private String PATTERN_FOR_CELL_BY_X_Y = "//div[contains(@class, 'battlefield battlefield__rival')]//div[@data-x='%d' and @data-y='%d']//..";
    private By emptyEnemyCellsLoc = By.cssSelector(".battlefield__rival .battlefield-table .battlefield-cell__empty");

    public GamePage() {
        notificationArea = new NotificationArea();
    }

    public NotificationArea getNotificationArea() {
        return notificationArea;
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
            Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveNotificationLoc());
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            try {
                getEnemyCell(By.xpath(String.format(PATTERN_FOR_EMPTY_RANDOM_CELL, getRandomInt(countOfSuchElements(emptyEnemyCellsLoc)) + 1))).click();
                LOGGER.info("Random shot");
            } catch (ElementClickInterceptedException e) {
                LOGGER.warn("Prevent ElementClickInterceptedException");
                continue;
            }
        }
    }

    private void runByDiagonal(int x, int y) {
        int border = y + 1;
        for (; x < border; x++, y--) {
            Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveNotificationLoc());
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            try {
                LOGGER.info(String.format("Shot by x=%d y=%d", x, y));
                if (hitEnemyShip(x, y)) {
                    finishEnemyShip(x, y);
                    x++;
                    y--;
                }
            } catch (ElementClickInterceptedException e) {
                LOGGER.warn("Prevent ElementClickInterceptedException");
                x--;
                y++;
            }
        }
    }

    private void finishEnemyShip(int x, int y) {
        moveRight(x, y);
        Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveNotificationLoc());
        moveLeft(x, y);
        Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveNotificationLoc());
        moveUp(x, y);
        Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveNotificationLoc());
        moveDown(x, y);
    }

    private boolean hitEnemyShip(int x, int y) {
        Button enemyCell = getEnemyCell(By.xpath(String.format(PATTERN_FOR_CELL_BY_X_Y, x, y)));
        if (checkForEmptyCell(enemyCell)) {
            enemyCell.click();
            Waiter.waitWhileElementProcessing(enemyCell);
            return checkForHit(enemyCell) && !checkForKill(enemyCell);
        } else {
            return false;
        }
    }

    private void moveRight(int x, int y) {
        while (x < MAX_FIELD_BORDER) {
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            x++;
            if (!hitEnemyShip(x, y)) {
                break;
            }
        }
    }

    private void moveLeft(int x, int y) {
        while (x > MIN_FIELD_BORDER) {
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            x--;
            if (!hitEnemyShip(x, y)) {
                break;
            }
        }
    }

    private void moveUp(int x, int y) {
        while (y > MIN_FIELD_BORDER) {
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            y--;
            if (!hitEnemyShip(x, y)) {
                break;
            }
        }
    }

    private void moveDown(int x, int y) {
        while (y < MAX_FIELD_BORDER) {
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            y++;
            if (!hitEnemyShip(x, y)) {
                break;
            }
        }
    }

    private void waitForEnemy() {
        Waiter.waitUntilElementIsVisible(notificationArea.getConnectionNotificationLoc());
        Waiter.waitUntilElementIsVisible(notificationArea.getWaitingForEnemyNotificationLoc());
        Waiter.waitUntilElementIsVisible(notificationArea.getEnemyMoveFirstNotificationLoc());
    }

    private Button getEnemyCell(By loc) {
        return new Button(loc, "Cell of enemy battleground");
    }

    private boolean checkForKill(Button cell) {
        return cell.getAttribute(CLASS).contains(CellStatuses.DONE.getStatus());
    }

    private boolean checkForEmptyCell(Button cell) {
        return cell.getAttribute(CLASS).contains(CellStatuses.EMPTY.getStatus());
    }

    private boolean checkForHit(Button cell) {
        return cell.getAttribute(CLASS).contains(CellStatuses.HIT.getStatus());
    }
}
