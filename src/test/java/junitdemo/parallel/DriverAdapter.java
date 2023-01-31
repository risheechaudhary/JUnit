package junitdemo.parallel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DriverAdapter {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private ThreadLocal<WebDriver> driver;
    private ThreadLocal<WebDriverWait> webDriverWait;
    private ThreadLocal<Actions> actions;

    public void start(Browser browser) {
        driver = new ThreadLocal<>();
        webDriverWait = new ThreadLocal<>();
        actions = new ThreadLocal<>();


            var capabilites = new DesiredCapabilities();
            capabilites.setCapability("browserName","Chrome");
            capabilites.setCapability("ChromeVersion", "95.0");

            HashMap<String,Object> ltOptions = new HashMap<String, Object>();

            String username = System.getenv("LT_USERNAME");
            String accessKey = System.getenv("LT_ACCESS_KEY");

            ltOptions.put("username",username);
            ltOptions.put("accessKey",accessKey);
            ltOptions.put("build", "first to be build");

            ltOptions.put("platformName", "Windows 10");
            ltOptions.put("browserName", "Chrome");
            ltOptions.put("browserVersion", 95.0);
            ltOptions.put("network", true);
            ltOptions.put("console", "true");
            ltOptions.put("visual", true);
            ltOptions.put("timezone", "UTC+02:00");
            capabilites.setCapability("LT_Options",ltOptions);

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;
            case SAFARI:
                WebDriverManager.safaridriver().setup();
                driver.set(new SafariDriver());
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            default:
                throw new IllegalArgumentException(browser.name());
        }

            try {
                String url = String.format("https://%s:%s@hub.lambdatest.com/wd/hub", username, accessKey);
                WebDriver remoteWebDriver = new RemoteWebDriver(new URL(url), capabilites);
                driver.set(remoteWebDriver);
            }
            catch (MalformedURLException e){
                System.out.println("Invalid url grid");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        switch (browser) {
            case CHROME:
                capabilites.setCapability("browserName", browser.getName());
                break;
            case EDGE:
                capabilites.setCapability("browserName", browser.getName());
                break;
            case SAFARI:
                capabilites.setCapability("browserName", browser.getName());
                break;
            case FIREFOX:
                capabilites.setCapability("browserName", browser.getName());
                break;
            default:
                throw new IllegalArgumentException(browser.name());
        }
    }

    public void quit(){
        driver.get().quit();
    }

    public void goToUrl(String url){
        driver.get().navigate().to(url);
    }

    void validateInnerTextIs(WebElement resultElement, String expectedText) {
        webDriverWait.get().until(ExpectedConditions.textToBePresentInElement(resultElement, expectedText));
    }

    public WebElement findElement (By locator){
        return webDriverWait.get().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public Actions getActions(){
        return actions.get();
    }

    public void executeScript(String script, Object... args) {
        try {
            ((JavascriptExecutor)driver.get()).executeScript(script, args);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}