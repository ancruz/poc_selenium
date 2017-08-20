package mx.com.acg.poc.selenium.basic.test.complex.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import mx.com.acg.poc.selenium.basic.test.complex.common.GenericPage;

/**
 * 
 * @author angelita.cruz.gomez
 * @version 1.0
 *
 */
public class GoogleMainPage extends GenericPage {
    
    @FindBy(id = "lst-ib")
    WebElement inputSearch;
    
    @FindBy(name = "btnK")
    WebElement buttonGoogleSearch;
    
    public SearchResultPage searc(String search) {
        if (sendKeys(inputSearch, search) && click(buttonGoogleSearch))
            return browser.loadPage(SearchResultPage.class);
        return null;
    }
    
    @Override
    protected boolean waitForPage() {
        return driver.getCurrentUrl().contains("https://www.google.co");
    }
}
