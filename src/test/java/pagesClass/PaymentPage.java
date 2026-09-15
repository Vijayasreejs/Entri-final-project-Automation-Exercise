package pagesClass;

import java.io.File;
import java.time.Duration;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ActionUtil;

public class PaymentPage
{

	
	private WebDriver driver;
    private ActionUtil actionUtil;
    private WebDriverWait wait;

 // --- Card Input Locators ---
    @FindBy(xpath = "//input[@data-qa='name-on-card']")
    private WebElement nameOnCardInput;

    @FindBy(xpath = "//input[@data-qa='card-number']")
    private WebElement cardNumberInput;

    @FindBy(xpath = "//input[@data-qa='cvc']")
    private WebElement cvcInput;

    @FindBy(xpath = "//input[@data-qa='expiry-month']")
    private WebElement expiryMonthInput;

    @FindBy(xpath = "//input[@data-qa='expiry-year']")
    private WebElement expiryYearInput;

    @FindBy(xpath = "//button[@data-qa='pay-button']")
    private WebElement payAndConfirmBtn;

    // --- Order Success Confirmation Locators ---
    @FindBy(xpath = "//h2[@data-qa='order-placed'] | //b[contains(text(),'Order Placed!')]")
    private WebElement orderPlacedHeader;

    @FindBy(xpath = "//p[contains(text(),'Congratulations! Your order has been confirmed!')]")
    private WebElement successMessage;

    @FindBy(xpath = "//a[contains(@href,'/download_invoice')]")
    private WebElement downloadInvoiceBtn;

    @FindBy(xpath = "//a[@data-qa='continue-button']")
    private WebElement continueBtn;
  
    @FindBy(xpath = "//tr[contains(@class,'cart_total')]//p | //span[contains(@class,'total_price')] | //*[contains(text(),'Rs.')]")
    private WebElement totalAmountElement;
   
    
    
    
    
    
    // Constructor
    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    
    
    
    public boolean isPaymentPageLoaded() {
        actionUtil.remove_ad();
        return driver.getCurrentUrl().contains("payment") || actionUtil.isDisplayed(payAndConfirmBtn);
    }

    public void enterCardDetails(String nameOnCard, String cardNumber, String cvc, String expMonth, String expYear) {
        actionUtil.remove_ad();
        actionUtil.sendKeys(nameOnCardInput, nameOnCard);
        actionUtil.sendKeys(cardNumberInput, cardNumber);
        actionUtil.sendKeys(cvcInput, cvc);
        actionUtil.sendKeys(expiryMonthInput, expMonth);
        actionUtil.sendKeys(expiryYearInput, expYear);
    }
    
    public String getCardNumberValue() {
        actionUtil.remove_ad();
        return cardNumberInput.getAttribute("value");
    }
    
   
    public boolean processPayment(String nameOnCard, String cardNumber, String cvc, String expMonth, String expYear) {
        enterCardDetails(nameOnCard, cardNumber, cvc, expMonth, expYear);
        clickPayAndConfirmOrder();
        return isOrderSuccessPageDisplayed();
    }

    public boolean isOrderSuccessPageDisplayed() {
        actionUtil.remove_ad();
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("payment_done"),
                ExpectedConditions.visibilityOf(orderPlacedHeader)
            ));
            return actionUtil.isDisplayed(orderPlacedHeader) || driver.getCurrentUrl().contains("payment_done");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickPayAndConfirmOrder() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(payAndConfirmBtn);
        actionUtil.clickViaJS(payAndConfirmBtn);
    }
    
    
    public void clickPayAndConfirmOrderDoubleclick() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(payAndConfirmBtn);
        Actions actions = new Actions(driver);
       actions. doubleClick(payAndConfirmBtn).perform();
     
    }
    
    
    public boolean isCardNumberFieldInvalid() {
        actionUtil.remove_ad();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isInvalid = (Boolean) js.executeScript("return !arguments[0].checkValidity();", cardNumberInput);
        String validationMsg = cardNumberInput.getAttribute("validationMessage");
        return Boolean.TRUE.equals(isInvalid) || (validationMsg != null && !validationMsg.isEmpty());
    }
    
    
    public boolean isExpiryYearFieldInvalid() {
        actionUtil.remove_ad();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isInvalid = (Boolean) js.executeScript("return !arguments[0].checkValidity();", expiryYearInput);
        String validationMsg = expiryYearInput.getAttribute("validationMessage");
        return Boolean.TRUE.equals(isInvalid) || (validationMsg != null && !validationMsg.isEmpty());
    }
    
    public String getCvcValue() {
        actionUtil.remove_ad();
        return cvcInput.getAttribute("value");
    }
    
    
    
    public String getOrderSuccessMessage() {
        actionUtil.remove_ad();
        return actionUtil.getText(successMessage);
    }

    public boolean isDownloadInvoiceButtonVisible() {
        actionUtil.remove_ad();
        return actionUtil.isDisplayed(downloadInvoiceBtn);
    }

    public void clickContinue() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(continueBtn);
    }
    
    public boolean isLinkContextuallyRelevant(String href) {
        if (href == null || href.trim().isEmpty() || href.startsWith("javascript")) {
            return false;
        }
        String lowerHref = href.toLowerCase();
        return lowerHref.contains("privacy") || lowerHref.contains("terms") 
            || lowerHref.contains("contact") || lowerHref.contains("about") 
            || lowerHref.contains("help") || lowerHref.contains("policy") 
            || lowerHref.contains("automationexercise") || lowerHref.contains("checkout");
    }
    
    
    public boolean isTotalPriceDisplayed() {
        actionUtil.remove_ad();
        return actionUtil.isDisplayed(totalAmountElement);
    }
    
    
    public void navigateBrowserBack()
    {
    	driver.navigate().back();
        actionUtil.remove_ad();
        // Refresh or redirect if stuck on payment screen after back navigation
        if (driver.getCurrentUrl().contains("payment")) {
            driver.get("https://www.automationexercise.com/view_cart");
        }
    }
    
    
   public void cleanDownloadDirectory(String dirPath) 
    {
    	
    	File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

   public File waitForFileToDownload(String downloadDir, int timeOutInSeconds) throws InterruptedException {
	   File dir = new File(downloadDir);
	    for (int i = 0; i < timeOutInSeconds * 2; i++) { // Poll every 500ms
	        File[] files = dir.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (!file.getName().endsWith(".crdownload") && 
	                    !file.getName().endsWith(".tmp") && 
	                    file.length() > 0) {
	                    return file;
	                }
	            }
	        }
	        Thread.sleep(500);
	    }
	    return null;
    }
   
   
   
   
}
