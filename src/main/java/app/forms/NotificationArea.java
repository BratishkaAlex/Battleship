package app.forms;

import framework.elements.Label;
import org.openqa.selenium.By;

public class NotificationArea {
    private By connectionNotificationLoc = By.cssSelector("div[class*='notification__connect-to-server']");
    private By waitingForEnemyNotificationLoc = By.cssSelector("div[class*='notification__waiting-for-rival']");
    private By enemyMoveFirstNotificationLoc = By.cssSelector("div[class*='notification__game-started-move-off']");
    private By youLoseNotificationLoc = By.cssSelector("div[class*='notification__game-over-lose']");
    private By youWinNotificationLoc = By.cssSelector("div[class*='notification__game-over-win']");
    private By enemyMoveNotificationLoc = By.cssSelector("div[class*='notification__move-off']");

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

    public boolean youLose() {
        return getLabelYouLose().isDisplayed();
    }

    public boolean youWin() {
        return getLabelYouWin().isDisplayed();
    }

    private Label getLabelYouWin() {
        return new Label(youWinNotificationLoc, "Label when you won");
    }

    private Label getLabelYouLose() {
        return new Label(youLoseNotificationLoc, "Label when you lost");
    }
}
