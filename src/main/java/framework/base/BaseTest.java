package framework.base;

import framework.browser.Browser;
import framework.utils.PropertyManager;
import framework.utils.Waiter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    @BeforeTest
    public void setUpBrowser() {
        Browser.maximize();
        Waiter.implicitWait(Integer.parseInt(PropertyManager.getConfigProperty("timeout")));
    }

    @AfterTest
    public void closeBrowser() {
        Browser.closeBrowser();
    }
}
