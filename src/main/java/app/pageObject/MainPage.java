package app.pageObject;

import app.forms.NotificationArea;
import framework.elements.Button;
import org.openqa.selenium.By;

public class MainPage {
    private NotificationArea notificationArea;
    private By startGameBtnLoc = By.className("battlefield-start-button");
    private By randomEnemyLoc = By.xpath("(//a[contains(@class,'battlefield-start-choose_rival-variant-link')])[1]");
    private By selectRandomPlaceShipLoc = By.cssSelector("li[class*='placeships-variant__randomly'] .placeships-variant-link");

    public MainPage(){
        notificationArea = new NotificationArea();
    }

    public NotificationArea getNotificationArea() {
        return notificationArea;
    }


    public boolean isMainPage() {
        return getStartGameButton().isDisplayed();
    }

    private Button getStartGameButton() {
        return new Button(startGameBtnLoc, "Button to start game");
    }

    public void startGame() {
        getStartGameButton().click();
    }

    private Button getRandomEnemyButton() {
        return new Button(randomEnemyLoc, "Button to choose random enemy");
    }

    public boolean isRandomEnemyEnabled() {
        return getRandomEnemyButton().isEnabled();
    }

    public void chooseRandomEnemy() {
        getRandomEnemyButton().click();
    }

    private Button getRandomPlaceShipButton() {
        return new Button(selectRandomPlaceShipLoc, "Button to place ships randomly");
    }

    public void selectRandomPlaceShip() {
        getRandomPlaceShipButton().click();
    }

}
