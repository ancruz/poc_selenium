package mx.com.acg.poc.selenium.basic.test.complex.common;

import org.testng.annotations.AfterSuite;

/**
 * 
 * @author angelita.cruz.gomez
 * @version 1.0
 *
 */
public class GenericTest {
    
    protected Browser browser = Browser.getInstance(Browser.FIREFOX_BROWSER);
    
    @AfterSuite
    public void afterSuit() {
        browser.close();
    }
    
    public static void waiting(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
        }
    }
}