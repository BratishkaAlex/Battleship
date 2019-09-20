package framework.browser;

import framework.utils.PropertyManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.util.HashMap;

class PropertiesForBrowser {
    static MutableCapabilities getOptions(String browser) {
        switch (browser) {
            case "chrome":
                return getPropForChrome();
            case "firefox":
                return getPropsForFirefox();
            default:
                throw new IllegalArgumentException("Wrong browser name!");
        }
    }

    private static ChromeOptions getPropForChrome() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", new File(PropertyManager.getConfigProperty("path")).getAbsolutePath());
        chromePrefs.put("safebrowsing.enabled", "true");
        chromePrefs.put("intl.accept_languages", PropertyManager.getConfigProperty("language"));
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        return options;
    }

    private static FirefoxOptions getPropsForFirefox() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("intl.accept_languages", PropertyManager.getConfigProperty("language"));
        firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-debian-package, application/octet-stream");
        firefoxOptions.addPreference("browser.download.folderList", 2);
        firefoxOptions.addPreference("browser.download.dir", new File(PropertyManager.getConfigProperty("path")).getAbsolutePath());
        return firefoxOptions;
    }
}
