package app.pageObject;

import app.forms.NotificationArea;
import framework.elements.Button;
import org.openqa.selenium.By;

public class MainPage {
    private NotificationArea notificationArea;
    private By startGameBtnLoc = By.className("battlefield-start-button");
    private By randomEnemyLoc = By.xpath("(//a[contains(@class,'battlefield-start-choose_rival-variant-link')])[1]");
    private By selectRandomPlaceShipLoc = By.cssSelector("li[class*='placeships-variant__randomly'] .placeships-variant-link");

    public MainPage() {
        notificationArea = new NotificationArea();
    }

    public boolean isMainPage() {
        return getStartGameButton().isDisplayed();
    }

    public void startGame() {
        getStartGameButton().click();
    }

    public boolean isRandomEnemyEnabled() {
        return getRandomEnemyButton().isEnabled();
    }

    public void chooseRandomEnemy() {
        getRandomEnemyButton().click();
    }

    public void selectRandomPlaceShip() {
        getRandomPlaceShipButton().click();
    }

    private Button getRandomPlaceShipButton() {
        return new Button(selectRandomPlaceShipLoc, "Button to place ships randomly");
    }

    private Button getStartGameButton() {
        return new Button(startGameBtnLoc, "Button to start game");
    }

    private Button getRandomEnemyButton() {
        return new Button(randomEnemyLoc, "Button to choose random enemy");
    }
}
