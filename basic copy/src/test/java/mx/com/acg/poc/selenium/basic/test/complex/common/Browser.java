package mx.com.acg.poc.selenium.basic.test.complex.common;

import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

/**
 * 
 * @author angelita.cruz.gomez
 * @version 1.0
 *
 */
public final class Browser {
    
    protected static WebDriver driver;
    protected static Browser   instance;
    private static Object      mutex;
    
    static {
        instance = null;
        mutex = new Object();
    }
    
    public static final int FIREFOX_BROWSER   = 0;
    public static final int PHANTOMJS_BROWSER = 1;
    
    private Browser(int browser) {
        switch (browser) {
            case FIREFOX_BROWSER:
                initFirefoxDriver();
                break;
            case PHANTOMJS_BROWSER:
                initPhantomJsDriver();
                break;
        }
    }
    
    public static Browser getInstance(int browser) {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null)
                    instance = new Browser(browser);
            }
        }
        return instance;
    }
    
    public WebDriver getDriver() {
        return driver;
    }
    
    public void loadUrl(String url) {
        driver.get(url);
    }
    
    public <T extends GenericPage> T loadPage(Class<T> type, String url) {
        driver.get(url);
        return loadPage(type);
    }
    
    public <T extends GenericPage> T loadPage(Class<T> type) {
        T instance;
        try {
            instance = type.getConstructor().newInstance();
            Method method = type.getMethod("initPage", new Class[] { Browser.class });
            method.invoke(instance, this);
            if (waitForPage(instance)) {
                PageFactory.initElements(driver, instance);
                return instance;
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    public void close() {
        try {
            driver.quit();
            driver.close();
        } catch (Exception e) {
        }
    }
    
    private void initFirefoxDriver() {
        try {
            Path path = FileSystems.getDefault().getPath("src/main/resources/geckodriver_win.exe");
            System.setProperty("webdriver.gecko.driver", path.toString());
            driver = new FirefoxDriver();
        } catch (Exception e) {
            driver = null;
        }
    }
    
    private void initPhantomJsDriver() {
        try {
            Path path = FileSystems.getDefault().getPath("src/main/resources/phantomjs_win.exe");
            System.setProperty("phantomjs.binary.path", path.toString());
            driver = new PhantomJSDriver();
        } catch (Exception e) {
            driver = null;
        }
    }
    
    private boolean waitForPage(GenericPage page) {
        return waitForPage(page, 150, 8);
    }
    
    private boolean waitForPage(GenericPage page, int poolingTime, int maxTime) {
        try {
            new FluentWait<GenericPage>(page).ignoring(Exception.class).withTimeout(maxTime, TimeUnit.SECONDS).pollingEvery(poolingTime, TimeUnit.MILLISECONDS).until(waitForPageCondition());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private Function<GenericPage, Boolean> waitForPageCondition() {
        return new Function<GenericPage, Boolean>() {
            public Boolean apply(GenericPage page) {
                return page.waitForPage();
            }
        };
    }
}