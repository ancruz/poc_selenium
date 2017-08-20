package mx.com.acg.poc.selenium.basic.test.complex.page;

import mx.com.acg.poc.selenium.basic.test.complex.common.GenericPage;

/**
 * 
 * @author angelita.cruz.gomez
 * @version 1.0
 *
 */
public class SearchResultPage extends GenericPage {
    
    @Override
    protected boolean waitForPage() {
        return driver.getCurrentUrl().contains("/search");
    }
}
