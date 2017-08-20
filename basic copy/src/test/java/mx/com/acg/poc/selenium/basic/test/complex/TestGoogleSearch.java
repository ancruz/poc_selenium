package mx.com.acg.poc.selenium.basic.test.complex;

import org.testng.Assert;
import org.testng.annotations.Test;

import mx.com.acg.poc.selenium.basic.test.complex.common.GenericTest;
import mx.com.acg.poc.selenium.basic.test.complex.page.GoogleMainPage;
import mx.com.acg.poc.selenium.basic.test.complex.page.SearchResultPage;

/**
 * 
 * @author angelita.cruz.gomez
 * @version 1.0
 *
 */
public class TestGoogleSearch extends GenericTest {
    
    @Test(enabled = false)
    public void verifyGoogleSearch() {
        
        GoogleMainPage googleMainPage = browser.loadPage(GoogleMainPage.class, "https://www.google.com");
        
        Assert.assertNotNull(googleMainPage);
        
        SearchResultPage searchResultPage = googleMainPage.searc("Selenium best Practices");
        
        Assert.assertNotNull(searchResultPage);
    }
}
