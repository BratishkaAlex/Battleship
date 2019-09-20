package app.steps;

import app.pageObject.MainPage;
import framework.utils.MathUtils;

public class Step {
    public static void clickOnSelectRandomPlaceShip(int times) {
        int count = MathUtils.getRandomInt(times);
        for (int i = 0; i < count; i++) {
            new MainPage().selectRandomPlaceShip();
        }
    }
}
