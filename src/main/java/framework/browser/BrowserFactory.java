package framework.browser;

import framework.utils.PropertyManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static framework.utils.LoggerUtil.LOGGER;

class BrowserFactory {
    static WebDriver getDriver() {
        WebDriver driver;
        String browser = PropertyManager.getConfigProperty("browser");
        switch (browser) {
            case "chrome":
                LOGGER.info("Creating instance of ChromeDriver");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(PropertiesForBrowser.getOptions(browser));
                break;
            case "firefox":
                LOGGER.info("Creating instance of FirefoxDriver");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(PropertiesForBrowser.getOptions(browser));
                break;
            default:
                throw new IllegalArgumentException("Wrong browser name!");
        }
        return driver;
    }
}
