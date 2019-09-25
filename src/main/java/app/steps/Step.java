package app.steps;

import app.pageObject.pages.MainPage;
import framework.utils.MathUtils;

public class Step {
    public static void selectRandomPlaceShip(int times) {
        int count = MathUtils.getRandomInt(times);
        for (int i = 0; i < count; i++) {
            new MainPage().selectRandomPlaceShip();
        }
    }
}
