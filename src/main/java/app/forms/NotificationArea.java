package app.forms;

import framework.elements.Label;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static app.enums.EndGameNotification.EndGameNotificationMessages;

public class NotificationArea {
    private By connectionNotificationLoc = By.cssSelector("div[class*='notification__connect-to-server']");
    private By waitingForEnemyNotificationLoc = By.cssSelector("div[class*='notification__waiting-for-rival']");
    private By enemyMoveFirstNotificationLoc = By.cssSelector("div[class*='notification__game-started-move-off']");
    private By enemyMoveNotificationLoc = By.cssSelector("div[class*='notification__move-off']");
    private By youLoseNotificationLoc = By.cssSelector("div[class*='notification__game-over-lose']");
    private By youWinNotificationLoc = By.cssSelector("div[class*='notification__game-over-win']");
    private By enemyLeaveNotificationLoc = By.cssSelector("div[class*='notification__rival-leave']");
    private By serverErrorNotificationLoc = By.cssSelector("div[class*='notification__server-error']");
    private By gameErrorNotificationLoc = By.cssSelector("div[class*='notification__game-error']");

    public String[] getEndGameNotification() {
        if (getLabelYouWin().isDisplayed()) {
            return new String[] {Boolean.TRUE.toString(), EndGameNotificationMessages.WIN.getMessage()};
        } else if (getLabelYouLose().isDisplayed()) {
            return new String[] {Boolean.FALSE.toString(), EndGameNotificationMessages.LOSE.getMessage()};
        } else if (getLabelEnemyLeave().isDisplayed()) {
            return new String[] {Boolean.FALSE.toString(), EndGameNotificationMessages.ENEMY_LEAVE.getMessage()};
        } else if (getLabelServerError().isDisplayed()) {
            return new String[] {Boolean.FALSE.toString(), EndGameNotificationMessages.SERVER_ERROR.getMessage()};
        } else if (getLabelGameError().isDisplayed()) {
            return new String[] {Boolean.FALSE.toString(), EndGameNotificationMessages.GAME_ERROR.getMessage()};
        }
        throw new IllegalArgumentException("Unknown notification");
    }

    public boolean checkForEndGameNotification() {
        try {
            return getLabelYouWin().isDisplayed() || getLabelYouLose().isDisplayed() || getLabelEnemyLeave().isDisplayed() ||
                getLabelServerError().isDisplayed() || getLabelGameError().isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public By getConnectionNotificationLoc() {
        return connectionNotificationLoc;
    }

    public By getWaitingForEnemyNotificationLoc() {
        return waitingForEnemyNotificationLoc;
    }

    public By getEnemyMoveFirstNotificationLoc() {
        return enemyMoveFirstNotificationLoc;
    }

    public By getEnemyMoveNotificationLoc() {
        return enemyMoveNotificationLoc;
    }

    private Label getLabelYouWin() {
        return new Label(youWinNotificationLoc, "Label when you won");
    }

    private Label getLabelYouLose() {
        return new Label(youLoseNotificationLoc, "Label when you lost");
    }

    private Label getLabelEnemyLeave() {
        return new Label(enemyLeaveNotificationLoc, "Label when you enemy leave");
    }

    private Label getLabelServerError() {
        return new Label(serverErrorNotificationLoc, "Label when server error");
    }

    private Label getLabelGameError() {
        return new Label(gameErrorNotificationLoc, "Label when game error");
    }
}
