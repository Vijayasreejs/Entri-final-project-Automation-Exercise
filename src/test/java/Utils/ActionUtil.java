package Utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionUtil 
{

	private WebDriver driver;
    private WebDriverWait wait;

    public ActionUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    
    public void click(WebElement element) {
    
        waitForVisibility(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    
    }

    public void clickk(By locator) {
        try {
           
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (Exception e) {
          
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
        }
    }
    
    public static void setWindowsize(WebDriver driver, int width, int height) 
    {
    	try {
            // Force window out of maximized state prior to resizing
            driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
        } catch (Exception e) {
            // Fallback Java script execution for window dimension changes
            ((JavascriptExecutor) driver).executeScript(
                String.format("window.resizeTo(%d, %d);", width, height)
            );
        }
    }
    
    public void clickViaJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    
    
    public void sendKeys(WebElement element, String text) 
    {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    public void sendKeyss(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        element.clear();
        element.sendKeys(text);
    }
    
    public String getText(WebElement element) 
    {
        return element.getText().trim();
    }
    
    public String getAttributee(By locator, String attributeName)
    {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getAttribute(attributeName);
    }
    

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isDisplayedd(By locator) 
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }
    
   
    public boolean isElementFocused(By locator) 
    {
        WebElement element = driver.findElement(locator);
        WebElement activeElement = (WebElement) ((JavascriptExecutor) driver)
            .executeScript("return document.activeElement;");
        return element.equals(activeElement);
   
    }
    
    public boolean isElementPresentAndVisible(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public void pressTabKey() {
        new Actions(driver).sendKeys(org.openqa.selenium.Keys.TAB).perform();
    }
    
    
    
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    } 
    
    
    
    
    public void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
        } catch (Exception ignored) {}
    }
    
    
    public void scrollToTop()
    {
    	JavascriptExecutor js=(JavascriptExecutor)driver;
    	  js.executeScript("window.scrollTo(0, 0);");
    }
    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    
    public WebElement getElement(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
            .ignoring(StaleElementReferenceException.class)
            .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    
    
    public void hoverOver(WebElement element) {
        waitForVisibility(element);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    
    
    public void waitForVisibility(WebElement element) {
   
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    

    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }
	
	
    public void dismissGoogleAds() {
    	try {
            // Handle Google vignette ads or iframe overlays
            List<WebElement> closeButtons = driver.findElements(By.xpath("//ins[@class='adsbygoogle']//a[text()='Close'] | //div[contains(@id, 'dismiss-button')] | //iframe[contains(@src, 'google_vignette')]"));
            if (!closeButtons.isEmpty()) {
                WebElement closeBtn = closeButtons.get(0);
                if (closeBtn.isDisplayed()) {
                    closeBtn.click();
                }
            }
            
            // Switch back to default content if trapped in an ad iframe
            driver.switchTo().defaultContent();
            
            // Handle URL injected google_vignette hash if it blocks navigation
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("#google_vignette")) {
                driver.get(currentUrl.split("#")[0]);
            }
        } catch (Exception e) {
            // Suppress exceptions if no ad is currently rendering
        }
    }
    
   
    public void remove_ad() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }
	
    
    public String getValidationMessage(By locator)
    {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", element);
    }
	
	
	
}
