package mx.com.agmc.poc.selenium.initial;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
* @author angelita.cruz.gomez version 1.0
**/
public class TestCaseUno {
    
    @Test(enabled = true)
    public void verifyGoogleSearch() {
        
    	WebDriver driver = getDriver("src/main/resources/phantomjs_linux");
        driver.get("https://www.google.com");
        tryToWait(5000);
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("INE");
        element.submit();
        tryToWait(5000);
        String actualUrl = driver.getCurrentUrl();
        driver.quit();
        Assert.assertTrue(actualUrl.startsWith("https://www.google"));
    }

    public WebDriver getDriver(String driver){
    	Path path = FileSystems.getDefault().getPath(driver);
    	System.setProperty("phantomjs.binary.path", path.toString());
        
        return new PhantomJSDriver();
    }
    
    public void tryToWait(int time){
    	try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }
}