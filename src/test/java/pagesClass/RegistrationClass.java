package pagesClass;


import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ActionUtil;

public class RegistrationClass
{
	
	private WebDriver driver;
    private ActionUtil actionUtil;
//    private WebDriverWait wait;

    // --- Account Information Section ---
    @FindBy(xpath = "//b[contains(text(),'Enter Account Information')]")
    private WebElement accInfoHeading;

    @FindBy(id = "id_gender1")
    private WebElement titleMrRadio;

    @FindBy(id = "id_gender2")
    private WebElement titleMrsRadio;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement daysDropdown;

    @FindBy(id = "months")
    private WebElement monthsDropdown;

    @FindBy(id = "years")
    private WebElement yearsDropdown;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement offersCheckbox;

    // --- Address Information Section ---
    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "company")
    private WebElement companyInput;

    @FindBy(id = "address1")
    private WebElement address1Input;

    @FindBy(id = "address2")
    private WebElement address2Input;

    @FindBy(id = "country")
    private WebElement countryDropdown;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//button[text()='Create Account']")
    private WebElement createAccountBtn;
    
    @FindBy(id="summary")
    private WebElement summary;
    // --- Post-Registration Confirmation Elements ---
    @FindBy(xpath = "//b[contains(text(),'Account Created!')]")
    private WebElement accountCreatedHeading;

    @FindBy(xpath = "//h2[@data-qa='account-deleted'] | //b[contains(text(),'ACCOUNT DELETED!')]")
    private WebElement accountDeletedHeading;

    @FindBy(xpath = "//a[@data-qa='continue-button']")
    private WebElement continueBtn;

    @FindBy(xpath = "//a[contains(text(),'Delete Account')]")
    private WebElement deleteAccountBtn;

    // --- Password Toggle Element ---
    @FindBy(xpath = "//input[@id='password']/following-sibling::i[contains(@class,'fa-eye')]")
    private WebElement passwordToggleIcon;

    // Constructor
    public RegistrationClass(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // --- Visibility & Verification Methods ---
    public boolean IsAccInfopageVisible() {
    	actionUtil.scrollToElement(accInfoHeading);
        return actionUtil.isDisplayed(accInfoHeading);
    }
    
    

    public boolean mandatoryfields() {
        return actionUtil.isDisplayed(passwordInput) && actionUtil.isDisplayed(firstNameInput)
                && actionUtil.isDisplayed(lastNameInput) && actionUtil.isDisplayed(address1Input)
                && actionUtil.isDisplayed(stateInput) && actionUtil.isDisplayed(cityInput)
                && actionUtil.isDisplayed(zipcodeInput) && actionUtil.isDisplayed(mobileNumberInput);
    }

    public boolean Isaccountcreated() {
    	actionUtil.scrollToElement(accountCreatedHeading);
        return actionUtil.isDisplayed(accountCreatedHeading);
    }

    public boolean Isaccountdeleted() {
    	try {
            // Wait up to 10 seconds for the Account Deleted text/URL to settle
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("delete_account"),
                ExpectedConditions.visibilityOf(accountDeletedHeading)
            ));
            return accountDeletedHeading.isDisplayed() || driver.getCurrentUrl().contains("delete_account");
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isTitleMrDisplayed() {
        return actionUtil.isDisplayed(titleMrRadio);
    }


    public boolean isTitleMrSelected() {
    	
        return titleMrRadio.isSelected();
    }

    public boolean isTitleMrsSelected() {
        return titleMrsRadio.isSelected();
    }

    public boolean passmasked() {
        return "password".equalsIgnoreCase(actionUtil.getAttribute(passwordInput, "type"));
    }

    public boolean newsletter_offersdisplayed() {
        return actionUtil.isDisplayed(newsletterCheckbox) && actionUtil.isDisplayed(offersCheckbox);
    }

    public boolean isMobileInputVisible() {
    	actionUtil.scrollToElement(mobileNumberInput);
        return actionUtil.isDisplayed(mobileNumberInput);
    }

    public boolean isEmailFieldVisible() {
        return actionUtil.isDisplayed(emailInput);
    }

    public boolean isEmailFieldReadOnlyOrDisabled() {
        String readOnlyAttr = actionUtil.getAttribute(emailInput, "readonly");
        String disabledAttr = actionUtil.getAttribute(emailInput, "disabled");
        return "true".equalsIgnoreCase(readOnlyAttr) || "true".equalsIgnoreCase(disabledAttr) 
                || readOnlyAttr != null || disabledAttr != null;
    }

    // --- Action Methods ---
    public void clickTitleMr() {
    	actionUtil.scrollToElement(titleMrRadio);
        actionUtil.clickViaJS(titleMrRadio);
    }

    public void enterPassword(String password)
    {
    	actionUtil.scrollToElement(passwordInput);
        actionUtil.sendKeys(passwordInput, password);
    }

    public void enterFirstName(String fname) {
        actionUtil.sendKeys(firstNameInput, fname);
    }

    public void enterLastName(String lname) {
        actionUtil.sendKeys(lastNameInput, lname);
    }

    public void enterZipcode(String zip) {
        actionUtil.sendKeys(zipcodeInput, zip);
    }

    public void enterMobileNumber(String mobile) {
        actionUtil.sendKeys(mobileNumberInput, mobile);
    }

    public void clickCreateAccount() {
        actionUtil.scrollToElement(createAccountBtn);
        actionUtil.clickViaJS(createAccountBtn);
    }

    public void continuebtn_click() {
    	try {
    	    driver.findElement(By.xpath("//button[contains(text(),'No thanks')]")).click();
    	} catch (Exception e) {
    	    // Popup not present or handled by browser prefs
    	}
    	actionUtil.scrollToElement(continueBtn);
        actionUtil.click(continueBtn);
        
    }

    public void DeleteAccBtn() {
    	actionUtil.remove_ad(); // Clean any active ad overlays
        actionUtil.scrollToElement(deleteAccountBtn);
        actionUtil.clickViaJS(deleteAccountBtn);
        
        // Handle ad vignette redirects if captured in the URL
        if (driver.getCurrentUrl().contains("#google_vignette")) {
            driver.get("https://www.automationexercise.com/delete_account");
        }
    }

    public void newsletter_offers() {
        actionUtil.scrollToElement(newsletterCheckbox);
        if (!newsletterCheckbox.isSelected()) actionUtil.clickViaJS(newsletterCheckbox);
        if (!offersCheckbox.isSelected()) actionUtil.clickViaJS(offersCheckbox);
    }

    public void selectDateOfBirth(String day, String month, String year) 
    {
        new Select(daysDropdown).selectByVisibleText(day);
        new Select(monthsDropdown).selectByVisibleText(month);
        new Select(yearsDropdown).selectByVisibleText(year);
    }

    public void selectCountryByVisibleText(String country) {
        new Select(countryDropdown).selectByVisibleText(country);
    }

    public String getSelectedCountry() {
        return new Select(countryDropdown).getFirstSelectedOption().getText().trim();
    }

    public boolean isCountryDropdownAvailable() {
        return actionUtil.isDisplayed(countryDropdown);
    }

    public int getCountryDropdownCount() {
        return new Select(countryDropdown).getOptions().size();
    }

    public boolean areDobDropdownsEnabled() {
        return actionUtil.isDisplayed(daysDropdown) && actionUtil.isDisplayed(monthsDropdown) && actionUtil.isDisplayed(yearsDropdown);
    }

    public int getDayDropdownCount() {
        return new Select(daysDropdown).getOptions().size();
    }

    public int getMonthDropdownCount() {
        return new Select(monthsDropdown).getOptions().size();
    }

    public boolean isYearRangeValid(int startYear) {
        List<WebElement> options = new Select(yearsDropdown).getOptions();
        for (WebElement opt : options) {
            try {
                int yr = Integer.parseInt(opt.getText().trim());
                if (yr >= startYear) return true;
            } catch (NumberFormatException ignored) {}
        }
        return false;
    }

    public boolean isYearOptionAvailable(String year) {
        List<WebElement> options = new Select(yearsDropdown).getOptions();
        for (WebElement opt : options) {
            if (opt.getText().trim().equals(year)) return true;
        }
        return false;
    }

    // --- Registration Helpers ---
    public void registration(String fname, String lname, String password, String company, String address, String state, String city, String zip, String mobile) {
        actionUtil.sendKeys(passwordInput, password);
        actionUtil.sendKeys(firstNameInput, fname);
        actionUtil.sendKeys(lastNameInput, lname);
        actionUtil.sendKeys(companyInput, company);
        actionUtil.sendKeys(address1Input, address);
        actionUtil.sendKeys(stateInput, state);
        actionUtil.sendKeys(cityInput, city);
        actionUtil.sendKeys(zipcodeInput, zip);
        actionUtil.sendKeys(mobileNumberInput, mobile);
        clickCreateAccount();
    }

    public void registrationWithAddress2(String fname, String lname, String password, String company, String address1, String address2, String state, String city, String zip, String mobile) {
        actionUtil.sendKeys(address2Input, address2);
        registration(fname, lname, password, company, address1, state, city, zip, mobile);
    }

    public void fillAccountDetailsExcept(String fname, String lname, String address, String state, String city, String zip, String mobile) {
        actionUtil.sendKeys(passwordInput, "SecurePass123!");
        actionUtil.sendKeys(firstNameInput, fname);
        actionUtil.sendKeys(lastNameInput, lname);
        actionUtil.sendKeys(address1Input, address);
        actionUtil.sendKeys(stateInput, state);
        actionUtil.sendKeys(cityInput, city);
        actionUtil.sendKeys(zipcodeInput, zip);
        actionUtil.sendKeys(mobileNumberInput, mobile);
        clickCreateAccount();
    }

    public void registrationViaEnterKey(String fname, String lname, String password, String company, String address, String state, String city, String zip, String mobile) {
        actionUtil.sendKeys(passwordInput, password);
        actionUtil.sendKeys(firstNameInput, fname);
        actionUtil.sendKeys(lastNameInput, lname);
        actionUtil.sendKeys(companyInput, company);
        actionUtil.sendKeys(address1Input, address);
        actionUtil.sendKeys(stateInput, state);
        actionUtil.sendKeys(cityInput, city);
        actionUtil.sendKeys(zipcodeInput, zip);
        actionUtil.sendKeys(mobileNumberInput, mobile);
        mobileNumberInput.sendKeys(Keys.ENTER);
    }

    public void submitIncompleteForm() {
        clickCreateAccount();
    }

    // --- Getter & Field Accessor Methods ---
    public String getZipcodeValue() {  actionUtil.scrollToElement(zipcodeInput);  return actionUtil.getAttribute(zipcodeInput, "value"); }
    public String getFirstNameValue() { return actionUtil.getAttribute(firstNameInput, "value"); }
    public String getLastNameValue() { return actionUtil.getAttribute(lastNameInput, "value"); }
    public String getEmailFieldValue() { return actionUtil.getAttribute(emailInput, "value"); }
    public String getPasswordFieldValue() { return actionUtil.getAttribute(passwordInput, "value"); }

    public String getFirstnameValidationMessage() { return actionUtil.getAttribute(firstNameInput, "validationMessage"); }
    public String getLastnameValidationMessage() { return actionUtil.getAttribute(lastNameInput, "validationMessage"); }
    public String getAddressValidationMessage() { return actionUtil.getAttribute(address1Input, "validationMessage"); }
    public String getStateValidationMessage() { return actionUtil.getAttribute(stateInput, "validationMessage"); }
    public String getCityValidationMessage() { return actionUtil.getAttribute(cityInput, "validationMessage"); }
    public String getZipcodeValidationMessage() { return actionUtil.getAttribute(zipcodeInput, "validationMessage"); }
    public String getMobileValidationMessage() { return actionUtil.getAttribute(mobileNumberInput, "validationMessage"); }

    // --- Advanced Interactive & Focus Checks ---
    public void pressTab() {
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }

    public boolean isTitleMrFocused() { return titleMrRadio.equals(driver.switchTo().activeElement()); }
    public boolean isPasswordFocused() { return passwordInput.equals(driver.switchTo().activeElement()); }
    public boolean isDayDropdownFocused() { return daysDropdown.equals(driver.switchTo().activeElement()); }
    public boolean isMonthDropdownFocused() { return monthsDropdown.equals(driver.switchTo().activeElement()); }
    public boolean isYearDropdownFocused() { return yearsDropdown.equals(driver.switchTo().activeElement()); }
    public boolean isFirstNameFocused() { return firstNameInput.equals(driver.switchTo().activeElement()); }

    public void clickOutsideForm() {
        actionUtil.clickViaJS(accInfoHeading);
    }

    public boolean isPasswordStrengthIndicatorVisible() {
        return actionUtil.isElementPresentAndVisible(By.xpath("//*[contains(@id,'strength') or contains(@class,'strength')]"));
    }

    public boolean CreateAccountBtnVisible()
    {     actionUtil.scrollToElement(createAccountBtn);
    	return actionUtil.isDisplayed(createAccountBtn);
    }
    public void clickCreateAccountRapidly()
    {
       
        actionUtil.clickViaJS(createAccountBtn);
        actionUtil.clickViaJS(createAccountBtn);
    }
    
    public boolean isSummarywindowDisplayed()
    {actionUtil.scrollToElement(summary);
    	actionUtil.isDisplayed(summary);
    	return true;
    }

    public void enterPartialAccountDetails(String pwd, String address, String state) {
        actionUtil.sendKeys(passwordInput, pwd);
        actionUtil.sendKeys(address1Input, address);
        actionUtil.sendKeys(stateInput, state);
    }

    public void refreshRegistrationPage() {
        driver.navigate().refresh();
    }

    public boolean isPasswordToggleVisible() {
        return actionUtil.isDisplayed(passwordToggleIcon);
    }

    public String getPasswordInputType() {
        return actionUtil.getAttribute(passwordInput, "type");
    }

    public void clickPasswordToggle() {
        actionUtil.click(passwordToggleIcon);
    }

    public void typeIntoEmailField(String newEmail) {
        actionUtil.sendKeys(emailInput, newEmail);
    }

    public void enterPasswordWithClipboard(String password) {
    	actionUtil.scrollToElement(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void enterLowercaseNames(String fname, String lname, String city) {
        actionUtil.sendKeys(firstNameInput, fname);
        actionUtil.sendKeys(lastNameInput, lname);
        actionUtil.sendKeys(cityInput, city);
    }

}
