package app.forms;

import framework.utils.Waiter;
import org.openqa.selenium.By;

public class NotificationArea {
    private By waitForConnectionLoc = By.cssSelector("div[class*='notification__connect-to-server']");
    private By waitForAnotherGamerLoc = By.cssSelector("div[class*='notification__waiting-for-rival']");
    private By waitForFirstMoveLoc = By.cssSelector("div[class*='notification__game-started-move-off']");
    private By waitForMoveLoc = By.cssSelector("div[class*='notification__move-off']");

    public void waitForEnemy() {
        Waiter.waitUntilElementIsVisible(waitForConnectionLoc);
        Waiter.waitUntilElementIsVisible(waitForAnotherGamerLoc);
    }

    public void waitForYourMove(){
        Waiter.waitUntilElementIsVisible(waitForMoveLoc);
    }

    public void waitForYourFirstMove(){
        Waiter.waitUntilElementIsVisible(waitForFirstMoveLoc);
    }
}
