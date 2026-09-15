package pagesClass;

import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utils.ActionUtil;

public class APIClass 

{
	
	private WebDriver driver;
    private ActionUtil actionUtil;
//    private WebDriverWait wait;

    // --- Header & Navigation Elements ---
    @FindBy(xpath = "//a[contains(text(),'API Testing')]")
    private WebElement apiTestingHeaderLink;

    @FindBy(xpath = "//b[text()='APIs List for practice']")
    private WebElement apisListHeading;

    // --- Accordion Panes / API List Elements ---
    @FindBy(xpath = "//div[contains(@class,'panel-group')]//div[contains(@class,'panel')]")
    private List<WebElement> apiPanelsList;

    @FindBy(xpath = "//a[contains(@data-toggle,'collapse')]")
    private List<WebElement> apiAccordionHeaders;

    // Locators for accordion elements inside individual panels
//   private By accordionHeaderBy = By.xpath(".//a[contains(@data-toggle,'collapse')]");
//    private By accordionBodyBy = By.xpath(".//div[contains(@class,'panel-collapse')]");

    @FindBy(xpath = "//a[contains(@data-toggle,'collapse')]")
    private List<WebElement> allApiHeaders;

    // "Response Code" text element inside all expanded panel lists
    @FindBy(xpath = "//div[contains(@class,'panel-collapse')]//li[contains(.,'Response Code')]")
    private List<WebElement> allResponseCodeElements;
    
    @FindBy(xpath = "//u[contains(text(),'API 1: Get All Products List')]")
    private WebElement api1Header;

    @FindBy(xpath = "//a[contains(@href, 'https://automationexercise.com/api/productsList')]")
    private WebElement api1UrlLink;

    @FindBy(xpath = "//body[contains(text(), '\"responseCode\": 200')]")
    private WebElement api1ResponseBody;
    
    @FindBy(xpath = "//a[contains(@data-toggle,'collapse')]//u")
    private List<WebElement> apiTitleUTags; 
    
    @FindBy(xpath="//*[@id=\"collapse1\"]/ul/li[3]/text()")
    private WebElement api1Bodytext;
    
    // --- Scroll to Top Button ---
    @FindBy(xpath = "//a[@id='scrollUp']")
    private WebElement scrollUpArrow;
    
    
    @FindBy(xpath = "//h2[text()='Subscription']")
    private WebElement subscriptionHeading;

    @FindBy(id = "susbscribe_email")
    private WebElement subscribeEmailInput;

    @FindBy(id = "subscribe")
    private WebElement subscribeButton;

    @FindBy(xpath = "//div[contains(@class,'alert-success')]")
    private WebElement successAlertMessage;
    
    @FindBy(xpath = "//a[contains(@href, 'mailto:feedback@automationexercise.com')]")
    private WebElement feedbackEmailLink;
    

    // --- Constructor ---
    public APIClass(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void removeAd() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }

    public void navigateToApiPageDirectly() {
        driver.get("https://www.automationexercise.com/api_list");
        removeAd();
    }

    public boolean isApiPageLoaded() {
        return driver.getCurrentUrl().contains("api_list") || actionUtil.isDisplayed(apisListHeading);
    }

    public String getApisListHeadingText() {
    	removeAd();
        return actionUtil.getText(apisListHeading);
    }

  
    public int getApiPanelsCount() {
        return apiTitleUTags.size();
    }


    public void clickApi1Header() {
        removeAd();
        actionUtil.scrollToElement(api1Header);
        actionUtil.clickViaJS(api1Header);
    }

    public void clickApi1UrlLink() {
        removeAd();
        actionUtil.scrollToElement(api1UrlLink);
        actionUtil.clickViaJS(api1UrlLink);
    }

    public String getApi1ResponseText() {
        return actionUtil.getText(api1ResponseBody);
    }

 
    public String getApi1ResponseBodyText() {
        // Fetches the entire visible body text displayed on the screen
        return actionUtil.getText(api1Bodytext);
    }
    
    public String getResponseCodeByIndex(int index) {
        removeAd();
        if (index >= 0 && index < allResponseCodeElements.size()) {
            WebElement element = allResponseCodeElements.get(index);
            actionUtil.scrollToElement(element);
            return actionUtil.getText(element);
        }
        throw new IllegalArgumentException("Invalid API index: " + index);
    }
   
   public void clickApiHeaderByIndex(int index) {
       removeAd();
       if (index >= 0 && index < allApiHeaders.size()) {
           WebElement header = allApiHeaders.get(index);
           actionUtil.scrollToElement(header);
           actionUtil.clickViaJS(header);
       } else {
           throw new IllegalArgumentException("Invalid API index: " + index);
       }
   }
    
   public void scrollToFooter() {
	    actionUtil.scrollToBottom();
	}

	public boolean isSubscriptionHeadingDisplayed() {
	    actionUtil.scrollToElement(subscriptionHeading);
	    return actionUtil.isDisplayed(subscriptionHeading);
	}

	public void enterSubscriptionEmail(String email) {
	    actionUtil.scrollToElement(subscribeEmailInput);
	    subscribeEmailInput.clear();
	    subscribeEmailInput.sendKeys(email);
	}

	public void clickSubscribeButton() {
	    actionUtil.clickViaJS(subscribeButton);
	}

	public String getSuccessAlertText() {
	    actionUtil.scrollToElement(successAlertMessage);
	    return actionUtil.getText(successAlertMessage);
	}
   
	public String getFeedbackEmailHref() {
	    removeAd();
	    actionUtil.scrollToElement(feedbackEmailLink);
	    return feedbackEmailLink.getAttribute("href");
	}
	
	
}
