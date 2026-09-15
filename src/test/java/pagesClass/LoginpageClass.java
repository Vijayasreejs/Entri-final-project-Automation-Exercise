package pagesClass;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import Utils.ActionUtil;

public class LoginpageClass 
{

//	private WebDriverWait wait;
	private WebDriver driver;
    private ActionUtil actionUtil;
  

    // --- Locators ---
    @FindBy(xpath = "//div[@class='login-form']/h2")
    private WebElement loginHeader;

    @FindBy(xpath = "//div[@class='signup-form']/h2")
    private WebElement signupHeader;

    @FindBy(xpath = "//input[@data-qa='login-email']")
    private WebElement loginEmailInput;

    @FindBy(xpath = "//input[@data-qa='login-password']")
    private WebElement loginPasswordInput;

    @FindBy(xpath = "//button[@data-qa='login-button']")
    private WebElement loginBtn;
    
    @FindBy(xpath = "//a[contains(text(),'Delete Account')]")
    private WebElement deleteAccountBtn;

    @FindBy(xpath = "//*[@id=\"form\"]/div/div/div[2]/h2")
    private WebElement orSeparator;

    private final By signUp_name=By.xpath("//input[@data-qa='signup-name']");
    private final By signUp_email=By.xpath("//input[@data-qa='signup-email']");
    private final By signUp_button=By.xpath("//button[@data-qa='signup-button']");
    private final By duplicate_errormsg=By.xpath("//p[text()='Email Address already exist!']");
    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    private WebElement logoutBtn;

    @FindBy(xpath = "//a[contains(text(),'Logged in as')]")
    private WebElement loggedInAsText;

    @FindBy(xpath = "//p[contains(text(),'your email or password is wrong') or contains(text(),'incorrect')]")
    private WebElement loginErrorMsg;

    @FindBy(xpath = "//a[contains(text(),'Signup / Login')]")
    private WebElement headerLoginSignupBtn;

    @FindBy(xpath = "//a[contains(text(),'Products')]")
    private WebElement headerProductsBtn;

    @FindBy(xpath = "//h2[contains(text(),'Subscription')]")
    private WebElement footerSubscriptionHeading;

    @FindBy(xpath = "//h2[contains(text(),'Subscription')]/following-sibling::p | //form[contains(@class,'searchform')]/p")
    private WebElement footerSubscriptionSubtext;

    @FindBy(xpath = "//div[contains(@class,'ads') or contains(@id,'ad_position_box') or contains(@class,'google-auto-placed')]")
    private WebElement adBanner;

    @FindBy(xpath = "//button[contains(@class,'password-toggle') or @id='togglePassword']")
    private WebElement passwordToggleBtn;

    @FindBy(xpath = "//div[contains(text(),'locked') or contains(text(),'too many attempts')]")
    private WebElement accountLockedMsg;
    
    private By headerLinks = By.cssSelector("header nav a");

    // Constructor
    public LoginpageClass(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // --- Core Action & Assertion Methods ---

    public boolean IsLoginVisible() {
        return actionUtil.isDisplayed(loginHeader);
    }

    public boolean IsSignupVisible() {
        return actionUtil.isDisplayed(signupHeader);
    }

    public boolean areLoginfieldsDisplayed() {
        return actionUtil.isDisplayed(loginEmailInput) && 
               actionUtil.isDisplayed(loginPasswordInput) && 
               actionUtil.isDisplayed(loginBtn);
    }

    public String getEmailPlaceholder() {
        return actionUtil.getAttribute(loginEmailInput, "placeholder");
    }

    public String getPasswordPlaceholder() {
        return actionUtil.getAttribute(loginPasswordInput, "placeholder");
    }

    public boolean isOrSeparatorVisible() {
    	actionUtil.scrollToElement(orSeparator);
        return actionUtil.isDisplayed(orSeparator);
    }

    public void StartLogin(String email, String password) {
        remove_Ad();
        actionUtil.sendKeys(loginEmailInput, email);
        actionUtil.sendKeys(loginPasswordInput, password);
        actionUtil.clickViaJS(loginBtn);
    }

    public boolean IsloggedIn() {
    	try {
            remove_Ad();
            return actionUtil.isDisplayed(loggedInAsText) 
                || actionUtil.isDisplayed(logoutBtn) 
                || actionUtil.isDisplayed(deleteAccountBtn);
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public void StartSignup(String name,String email)
    {   
    	actionUtil.sendKeyss(signUp_name, name);
    	actionUtil.sendKeyss(signUp_email, email);
    	actionUtil.clickk(signUp_button);
    }
    
    public void enterSignupName(String name) 
    {
    	
    	actionUtil.sendKeyss(signUp_name, name);
    }

    public void enterSignupEmail(String email) 
    {
    	
    	actionUtil.sendKeyss(signUp_email, email);
    }

    public boolean isSignupNameErrorDisplayed() {
    	
	    try {
	    	
	        // Checks if the field is invalid via HTML5 validation or custom error states
	        String validationMsg = actionUtil.getValidationMessage(signUp_name);
	        return !validationMsg.isEmpty() || actionUtil.isElementPresentAndVisible(duplicate_errormsg);
	    } catch (Exception e) {
	        return false;
	    }
	}
public boolean areSignupfieldsDisplayed()
{
	
	return actionUtil.isDisplayedd(signUp_name) 
            && actionUtil.isDisplayedd(signUp_email) 
            && actionUtil.isDisplayedd(signUp_button);
	
}


public void startSignupWithBlankName(String email)
{
	actionUtil.sendKeyss(signUp_name, "");
	actionUtil.sendKeyss(signUp_email, email);
	actionUtil.clickk(signUp_button);
}

public String getSignupNameValidationMessage() 
{
    return actionUtil.getValidationMessage(signUp_name);
}



    public String getSignupNameValue() 
   {
    return actionUtil.getAttributee(signUp_name, "value");
   }


    public boolean logOutButtonVisible() {
        return actionUtil.isDisplayed(logoutBtn);
    }
    public void clickSignupNameField()
    {

    	actionUtil.clickk(signUp_name);
    }
    public boolean isSignupNameFocused() 
    {
        return actionUtil.isElementFocused(signUp_name);
    }
    public void clickSignUpEmailField()
    {

    	actionUtil.clickk(signUp_email);
    }
    public boolean isSignUpEmailFocused() 
    {
        return actionUtil.isElementFocused(signUp_email);
    }
    public void clickSignupButton()
    {

    	actionUtil.clickk(signUp_button);
    }
    public boolean isSignupButtonFocused() {
        return actionUtil.isElementFocused(signUp_button);
    }
    
    public boolean isDuplicateErrorVisible()
    {
    	
    	return actionUtil.isDisplayedd(duplicate_errormsg);
    }
   
    public void logOut() {
        remove_Ad();
        actionUtil.clickViaJS(logoutBtn);
    }

    public void enterCredentialsAndSubmitWithEnter(String email, String password) {
        remove_Ad();
        actionUtil.sendKeys(loginEmailInput, email);
        actionUtil.sendKeys(loginPasswordInput, password);
        loginPasswordInput.sendKeys(Keys.ENTER);
    }

    public void enterEmailAndSubmitWithEnter(String email, String password) {
        remove_Ad();
        actionUtil.sendKeys(loginEmailInput, email);
        actionUtil.sendKeys(loginPasswordInput, password);
        loginEmailInput.sendKeys(Keys.ENTER);
    }

    public void loginWithTrimmedEmail(String rawEmail, String password) {
        remove_Ad();
        actionUtil.sendKeys(loginEmailInput, rawEmail.trim());
        actionUtil.sendKeys(loginPasswordInput, password);
        actionUtil.clickViaJS(loginBtn);
    }

    public boolean IsLogInErrorVisible()
    {    remove_Ad();
        return actionUtil.isDisplayed(loginErrorMsg);
    }

    public void checkemptylogin() {
        remove_Ad();
        actionUtil.clickViaJS(loginBtn);
    }

    public String getPasswordValidationMessage() {
        return actionUtil.getAttribute(loginPasswordInput, "validationMessage");
    }

    public String getEmailValidationMessage() {
        return actionUtil.getAttribute(loginEmailInput, "validationMessage");
    }

    public boolean isAccountLockedMessageDisplayed() {
        return actionUtil.isDisplayed(accountLockedMsg);
    }

    public boolean isEmailFieldHighlightedWithError() {
        String classAttr = actionUtil.getAttribute(loginEmailInput, "class");
        String styleAttr = actionUtil.getAttribute(loginEmailInput, "style");
        return (classAttr != null && classAttr.contains("error")) || 
               (styleAttr != null && (styleAttr.contains("red") || styleAttr.contains("border")));
    }

    public void clickHeaderLoginSignup() {
        remove_Ad();
        actionUtil.clickViaJS(headerLoginSignupBtn);
    }

    public void clearAndTypeEmail(String newEmail) {
        actionUtil.sendKeys(loginEmailInput, newEmail);
    }

    public void clickLogin() {
        actionUtil.clickViaJS(loginBtn);
    }

    public String getEmailValue() {
        return actionUtil.getAttribute(loginEmailInput, "value");
    }

    public void duplicateTab() {
        String currentUrl = driver.getCurrentUrl();
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get(currentUrl);
    }

    public void PressTab() {
    	actionUtil.pressTabKey();
    }

    public String getpasswordType() {
        return actionUtil.getAttribute(loginPasswordInput, "type");
    }

    public boolean isPasswordToggleAvailable() {
        return actionUtil.isDisplayed(passwordToggleBtn);
    }

    public void typePassword(String password) {
        actionUtil.sendKeys(loginPasswordInput, password);
    }

    public void clickPasswordToggle() {
        actionUtil.clickViaJS(passwordToggleBtn);
    }

    public void pastePasswordIntoField(String password) {
        actionUtil.sendKeys(loginPasswordInput, password);
    }

    public void clickEmailField() {
        actionUtil.click(loginEmailInput);
    }

    public void clickPasswordField() {
        actionUtil.click(loginPasswordInput);
    }

    public boolean verifyElementFocused() {
        return driver.switchTo().activeElement().equals(loginEmailInput);
    }

    public boolean verifypassElementFocused() {
        return driver.switchTo().activeElement().equals(loginPasswordInput);
    }

    public void clickOutsideForm() {
        actionUtil.clickViaJS(loginHeader);
    }

    
    public void clickHeaderProducts() {
        remove_Ad();
        actionUtil.clickViaJS(headerProductsBtn);
    }

    public boolean isAdBannerDisplayed() {
        return actionUtil.isDisplayed(adBanner);
    }

    public void scrollToFooter() {
        actionUtil.scrollToBottom();
    }

    public String getFooterSubscriptionHeading() {
        actionUtil.scrollToElement(footerSubscriptionHeading);
        return actionUtil.getText(footerSubscriptionHeading);
    }

    public String getFooterSubscriptionSubtext() {
        actionUtil.scrollToElement(footerSubscriptionSubtext);
        return actionUtil.getText(footerSubscriptionSubtext);
    }

    public void setZoom(double zoomFactor) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='" + zoomFactor + "'");
    }

    public boolean areLoginFormElementsVisibleAtZoom() {
        return actionUtil.isDisplayed(loginEmailInput) && actionUtil.isDisplayed(loginPasswordInput);
    }
    
    public List<WebElement> getHeaderLinks() {
        return driver.findElements(headerLinks);
    }

    public void remove_Ad() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }
    
   
}
	
	
	

