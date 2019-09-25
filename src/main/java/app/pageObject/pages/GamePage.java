package app.pageObject.pages;

import app.pageObject.forms.NotificationArea;
import framework.elements.Button;
import framework.utils.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;

import static app.enums.CellStatus.CellStatuses;
import static app.enums.Direction.Directions;
import static app.enums.Direction.Directions.*;
import static framework.base.BaseElement.countOfSuchElements;
import static framework.elements.elementsAttributes.Attributes.CLASS;
import static framework.utils.LoggerUtil.LOGGER;
import static framework.utils.MathUtils.getRandomInt;

public class GamePage {
    private static final int MIN_FIELD_BORDER = 0;
    private static final int MAX_FIELD_BORDER = 9;
    private final static String EMPTY_RANDOM_CELL_PATTERN = "(//div[contains(@class,'battlefield__rival')]//td[contains(@class,'battlefield-cell__empty')])[%d]";
    private final static String CELL_BY_X_Y_PATTERN = "//div[contains(@class, 'battlefield battlefield__rival')]//div[@data-x='%d' and @data-y='%d']//..";
    private NotificationArea notificationArea;
    private By emptyEnemyCellsLoc = By.cssSelector(".battlefield__rival .battlefield-table .battlefield-cell__empty");

    public GamePage() {
        notificationArea = new NotificationArea();
    }

    public NotificationArea getNotificationArea() {
        return notificationArea;
    }

    public void run() {
        notificationArea.waitForEnemyJoinedTheGame();
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
            notificationArea.waitWhileEnemyMoves();
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            try {
                getEnemyCell(By.xpath(String.format(EMPTY_RANDOM_CELL_PATTERN, getRandomInt(countOfSuchElements(emptyEnemyCellsLoc)) + 1))).click();
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
            notificationArea.waitWhileEnemyMoves();
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
        moveByDirection(RIGHT, x + 1, y);
        moveByDirection(LEFT, x - 1, y);
        moveByDirection(UP, x, y - 1);
        moveByDirection(DOWN, x, y + 1);
    }

    private boolean hitEnemyShip(int x, int y) {
        Button enemyCell = getEnemyCell(By.xpath(String.format(CELL_BY_X_Y_PATTERN, x, y)));
        if (checkForEmptyCell(enemyCell)) {
            enemyCell.click();
            Waiter.waitWhileElementProcessing(enemyCell);
            return checkForHit(enemyCell) && !checkForKill(enemyCell);
        } else {
            return false;
        }
    }

    private void moveByDirection(Directions direction, int x, int y) {
        notificationArea.waitWhileEnemyMoves();
        while (x <= MAX_FIELD_BORDER && x >= MIN_FIELD_BORDER && y <= MAX_FIELD_BORDER && y >= MIN_FIELD_BORDER) {
            if (notificationArea.checkForEndGameNotification()) {
                break;
            }
            if (!hitEnemyShip(x, y)) {
                break;
            }
            switch (direction) {
                case RIGHT:
                    x++;
                    break;
                case LEFT:
                    x--;
                    break;
                case UP:
                    y--;
                    break;
                case DOWN:
                    y++;
                    break;
            }
        }
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
