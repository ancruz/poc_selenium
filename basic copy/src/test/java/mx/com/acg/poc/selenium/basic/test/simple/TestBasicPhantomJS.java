package mx.com.acg.poc.selenium.basic.test.simple;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * @author angelita.cruz.gomez
 * @version 1.0
 *
 */
public class TestBasicPhantomJS {
    
    @Test(enabled = true)
    public void verifyGoogleSearch() {
        
        Path path = FileSystems.getDefault().getPath("src/main/resources/phantomjs_win.exe");
        
        System.setProperty("phantomjs.binary.path", path.toString());
        
        WebDriver driver = new PhantomJSDriver();
        
        String desiredUrl = "https://www.google.com";
        
        driver.get(desiredUrl);
        
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            
        }
        
        WebElement element = driver.findElement(By.name("q"));
        
        element.sendKeys("INE ");
        
        element.submit();
        
        String actualUrl = driver.getCurrentUrl();
        
        driver.quit();
        
        System.out.println("--- The Url is: " + actualUrl);
        
        Assert.assertTrue(actualUrl.startsWith("https://www.google"));
    }
}
