package pagesClass;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ActionUtil;

public class ContactUsPage 

{

	private WebDriver driver;
    private ActionUtil actionUtil;
    private WebDriverWait wait;
    
    
    
    private By nameLocator = By.xpath("//input[@data-qa='name']");
    private By emailLocator = By.xpath("//form[@id='contact-us-form']//input[@name='email']");
    private By subjectLocator = By.name("subject");
    private By messageLocator = By.id("message");

    // --- WebElements ---
    @FindBy(xpath = "//h2[contains(text(),'Get In Touch')]")
    private WebElement getInTouchHeading;

    @FindBy(xpath = "//input[@data-qa='name']")
    private WebElement nameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "subject")
    private WebElement subjectInput;

    @FindBy(id = "message")
    private WebElement messageArea;

    @FindBy(name = "upload_file")
    private WebElement fileUploadInput;
    
    @FindBy(xpath = "//a[contains(@href,'mailto:feedback@automationexercise.com')]")
    private WebElement feedbackEmailLink;

    @FindBy(name = "submit")
    private WebElement submitBtn;

    @FindBy(xpath = "//div[contains(@class,'status') and contains(@class,'alert-success')]")
    private WebElement successMsg;

    @FindBy(xpath = "//a[contains(@class,'btn-success') and contains(.,'Home')]")
    private WebElement homeBtn;

    // Constructor
    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // --- Page Actions & Verification Methods ---

    public boolean isGetInTouchHeaderVisible() {
        actionUtil.remove_ad();
        return actionUtil.isDisplayed(getInTouchHeading);
    }
    
    public void enterEmail(String email) {
        actionUtil.dismissGoogleAds();
        WebElement emailElem = wait.until(ExpectedConditions.presenceOfElementLocated(emailLocator));
        actionUtil.scrollToElement(emailElem);
        
        // Clear using standard clear and key combination to handle stale states
        emailElem.clear();
        if (email != null && !email.isEmpty()) {
            emailElem.sendKeys(email);
        }
    }
    public void enterSubject(String subject) {
        actionUtil.sendKeyss(By.name("subject"), subject);
    }
   
    
    public void fillContactForm(String name, String email, String subject, String message)
    { 
    	actionUtil.dismissGoogleAds();

        // Wait for presence in DOM rather than strict visibility to avoid ad overlay timeouts
        WebElement nameElem = wait.until(ExpectedConditions.presenceOfElementLocated(nameLocator));
        WebElement emailElem = wait.until(ExpectedConditions.presenceOfElementLocated(emailLocator));
        WebElement subjectElem = wait.until(ExpectedConditions.presenceOfElementLocated(subjectLocator));
        WebElement messageElem = wait.until(ExpectedConditions.presenceOfElementLocated(messageLocator));

        actionUtil.scrollToElement(nameElem);

        if (name != null) {
            nameElem.clear();
            nameElem.sendKeys(name);
        }
        if (email != null) {
            emailElem.clear();
            emailElem.sendKeys(email);
        }
        if (subject != null) {
            subjectElem.clear();
            subjectElem.sendKeys(subject);
        }
        if (message != null) {
            messageElem.clear();
            messageElem.sendKeys(message);
        }
    }
 
    
  
    
    
    
    public void uploadFile(String absoluteFilePath) {
    	WebElement uploadElem = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("upload_file")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "arguments[0].style.display='block'; arguments[0].style.visibility='visible';", uploadElem);
        uploadElem.sendKeys(absoluteFilePath);
    }
    
    public String getAttachedFileName() {
        String filePath = fileUploadInput.getAttribute("value");
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        return new java.io.File(filePath).getName();
    }
    public void clickHomeNavMenu() {
        actionUtil.clickViaJS(driver.findElement(By.xpath("//a[contains(text(),'Home')]")));
    }


    public void clickSubmit() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(submitBtn);
        actionUtil.clickViaJS(submitBtn);
    }

    
    public String getNameValidationMessage() {
        return actionUtil.getValidationMessage(nameLocator);
    }
    
    
 // Method to retrieve HTML5 validation message safely
    public String getEmailValidationMessage() 
    {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // 1. Clean dynamic ad overlays safely without throwing exceptions
        js.executeScript(
            "var ads = document.querySelectorAll('iframe, .adsbygoogle, #aswift_0_host, #aswift_1_host');" +
            "ads.forEach(ad => ad.remove());"
        );

        try {
            // 2. Explicitly wait until email input is present in DOM
            WebElement emailElem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//form[@id='contact-us-form']//input[@name='email']")
            ));

            // 3. Scroll to element and highlight border
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", emailElem);
            js.executeScript("arguments[0].style.border='3px solid red';", emailElem);

            // 4. Return HTML5 validation message
            return (String) js.executeScript("return arguments[0].validationMessage;", emailElem);
        } catch (Exception e) {
            System.out.println("Could not locate email element: " + e.getMessage());
            return "";
        }
    }
    
    
    
    public String getSubjectValidationMessage() {
        return actionUtil.getValidationMessage(subjectLocator);
    }

    public String getMessageValidationMessage() {
        return actionUtil.getValidationMessage(messageLocator);
    }
    
    
    public String getNameInputValue() {
        return nameInput.getAttribute("value");
    }
    
    public String getEamilInputValue() {
        return emailInput.getAttribute("value");
    }
    
    public String getSubjectInputValue() {
        return subjectInput.getAttribute("value");
    }
    
    
    public String getMessageInputValue() {
        return messageArea.getAttribute("value");
    }
    
    public String getSubjectMaxLengthAttribute() {
        return subjectInput.getAttribute("maxlength");
    }
    
    public void dismissSubmitAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception ignored) {}
    }
    
    
    public void acceptSubmitAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception ignored) {}
    }

    public boolean isSuccessMessageDisplayed() {
    	actionUtil.remove_ad();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
          
            js.executeScript(
                "var ads = document.querySelectorAll('iframe, .adsbygoogle, #aswift_0_host, #aswift_1_host');" +
                "ads.forEach(ad => ad.remove());"
            );

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", getInTouchHeading);
        } catch (Exception ignored) {}

        return actionUtil.isDisplayed(successMsg);
    }

    public String getSuccessMessageText() {
        return actionUtil.getText(successMsg);
    }

    public boolean isAlertPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFileUploadValidationMessage() {
    	try {
            
            return actionUtil.getValidationMessage(By.name("upload_file"));
        } catch (Exception e) {
            return "";
        }
    }
    public String getFileUploadValue() {
    	WebElement uploadElem = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("upload_file")));
        return uploadElem.getAttribute("value");
    }
    
    
    public String[] getFocusedElementDetails()
    {
        // Send TAB key to current focused element
        driver.switchTo().activeElement().sendKeys(org.openqa.selenium.Keys.TAB);
        
        // Switch to active element in DOM focus
        WebElement activeElem = driver.switchTo().activeElement();
        
        String tagName = activeElem.getTagName();
        String name = activeElem.getAttribute("name") != null ? activeElem.getAttribute("name") : "";
        String id = activeElem.getAttribute("id") != null ? activeElem.getAttribute("id") : "";
        String placeholder = activeElem.getAttribute("placeholder") != null ? activeElem.getAttribute("placeholder") : "";
        
        return new String[] { tagName, name, id, placeholder };
    }

    
    
    public void switchTabsAndReturn()
    {
        String originalTab = driver.getWindowHandle();

        // Open a new blank tab and switch focus
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get("https://www.google.com");

        // Switch back to original tab
        driver.switchTo().window(originalTab);
    }
    
    
    public boolean isFeedbackEmailLinkVisible() {
        return actionUtil.isDisplayed(feedbackEmailLink);
    }
    
    
    public String getFeedbackEmailHref() {
        return feedbackEmailLink.getAttribute("href");
    }
    
    public void clickFeedbackEmailLink() {
        actionUtil.clickViaJS(feedbackEmailLink);
    }
    
    
    public void remove_Ad() {
        actionUtil.remove_ad();
    }

	
}
