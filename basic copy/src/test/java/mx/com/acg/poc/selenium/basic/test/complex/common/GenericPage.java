package mx.com.acg.poc.selenium.basic.test.complex.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Function;

/**
 * 
 * @author angelita.cruz.gomez
 * @version 1.0
 *
 */
public abstract class GenericPage {
    
    protected WebDriver driver;
    protected Browser   browser;
    
    public void initPage(Browser browser) {
        this.browser = browser;
        driver = browser.getDriver();
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    protected String getText(WebElement element) {
        return doAction(element, 250, 5);
    }
    
    protected boolean isTextPresent(WebElement element, String text) {
        return doAction(element, text, 250, 5);
    }
    
    protected boolean click(WebElement element) {
        if (doAction(element, waitBeDisplayedCondition(), 250, 5)) {
            element.click();
            ;
            return true;
        }
        return false;
    }
    
    protected boolean pressEnter(WebElement element) {
        if (doAction(element, waitBeDisplayedCondition(), 250, 5)) {
            element.sendKeys(Keys.ENTER);
            return true;
        }
        return false;
    }
    
    protected boolean sendKeys(WebElement element, String keys) {
        if (doAction(element, waitBeDisplayedCondition(), 250, 5)) {
            element.clear();
            element.sendKeys(keys);
            return true;
        }
        return false;
    }
    
    protected boolean selectValue(WebElement element, String value) {
        return doAction(element, selectValueCondition(value), 250, 5);
    }
    
    private boolean doAction(WebElement element, Function<WebElement, Boolean> waitForActionCondition, int poolingTime, int maxTime) {
        try {
            new FluentWait<WebElement>(element).ignoring(Exception.class).withTimeout(maxTime, TimeUnit.SECONDS).pollingEvery(poolingTime, TimeUnit.MILLISECONDS).until(waitForActionCondition);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private Function<WebElement, Boolean> waitBeDisplayedCondition() {
        return new Function<WebElement, Boolean>() {
            public Boolean apply(WebElement element) {
                return element.isDisplayed();
            }
        };
    }
    
    private Function<WebElement, Boolean> selectValueCondition(final String value) {
        return new Function<WebElement, Boolean>() {
            public Boolean apply(WebElement element) {
                try {
                    new Select(element).selectByVisibleText(value);
                    return true;
                } catch (Exception e) {
                }
                return false;
            }
        };
    }
    
    private String doAction(WebElement element, int poolingTime, int maxTime) {
        try {
            return new FluentWait<WebElement>(element).ignoring(Exception.class).withTimeout(maxTime, TimeUnit.SECONDS).pollingEvery(poolingTime, TimeUnit.MILLISECONDS).until(waitForTextCondition());
        } catch (Exception e) {
            return null;
        }
    }
    
    private Function<WebElement, String> waitForTextCondition() {
        return new Function<WebElement, String>() {
            
            public String apply(WebElement element) {
                if (element.isDisplayed() && element.getText() != null)
                    return element.getText();
                return null;
            }
        };
    }
    
    private boolean doAction(WebElement element, String text, int poolingTime, int maxTime) {
        try {
            new FluentWait<WebElement>(element).ignoring(Exception.class).withTimeout(maxTime, TimeUnit.SECONDS).pollingEvery(poolingTime, TimeUnit.MILLISECONDS).until(textPresentCondition(text));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private Function<WebElement, Boolean> textPresentCondition(final String text) {
        return new Function<WebElement, Boolean>() {
            public Boolean apply(WebElement element) {
                if (element.isDisplayed() && element.getText() != null)
                    return element.getText().equals(text);
                return false;
            }
        };
    }
    
    protected abstract boolean waitForPage();
}