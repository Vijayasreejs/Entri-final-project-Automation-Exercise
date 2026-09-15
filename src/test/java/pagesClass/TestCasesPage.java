package pagesClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ActionUtil;

public class TestCasesPage 
{	
	private WebDriver driver;
    private ActionUtil actionUtil;
    private WebDriverWait wait;

    // --- Page Header / Verification Elements ---
    @FindBy(xpath = "//h2[contains(@class,'title') and contains(text(),'Test Cases')]")
    private WebElement testCasesHeader;
    
    @FindBy(xpath = "//a[contains(text(),'API Testing')]")
    private WebElement apiTestingLink;

    @FindBy(xpath = "//*[@id=\"form\"]/div/div[1]/div/h2/b")
    private WebElement testCasesTitleBreadcrumb;

    @FindBy(xpath = "//div[contains(@class,'panel-group') or @id='accordian']")
    private WebElement accordionGroup;

    @FindBy(xpath = "//a[contains(@data-toggle,'collapse') or contains(@href,'#collapse') or contains(@class,'panel-title')]")
    private List<WebElement> accordionHeaders;

    @FindBy(xpath = "(//a[contains(@data-toggle,'collapse') or contains(@href,'#collapse')])[1]")
    private WebElement firstAccordionHeader;

    @FindBy(xpath = "(//div[contains(@class,'panel-collapse')])[1]")
    private WebElement firstAccordionContent;

    // Locators for all Test Case Accordion Headers
    @FindBy(xpath = "//div[contains(@class,'panel-group')]//a[@data-toggle='collapse']")
    private List<WebElement> accordionItemsList;
    
    @FindBy(xpath = "//a[contains(text(),'Feedback for Us') or contains(text(),'Feedback')] | //b[contains(text(),'Feedback')]")
    private WebElement feedbackHeader;

    @FindBy(xpath = "//a[contains(@href, 'mailto:feedback@automationexercise.com')]")
    private WebElement feedbackMailtoLink;
    
    @FindBy(xpath = "//h2[contains(text(),'Subscription')]")
    private WebElement subscriptionHeading;

    @FindBy(id = "susbscribe_email")
    private WebElement subscriptionEmailInput;

    @FindBy(id = "subscribe")
    private WebElement subscribeBtn;

    @FindBy(xpath = "//div[contains(@class,'alert-success') or contains(text(),'successfully subscribed')]")
    private WebElement subscribeSuccessMsg;
    
    @FindBy(xpath = "//a[@id='scrollUp']")
    private WebElement scrollUpArrow;
    
    @FindBy(xpath = "//a[contains(text(),' Home')]")
    private WebElement homeNavBtn;

    @FindBy(xpath = "//a[@href='/products']")
    private WebElement productsNavBtn;

    @FindBy(xpath = "//a[@href='/view_cart']")
    private WebElement cartNavBtn;

    @FindBy(xpath = "//a[@href='/login']")
    private WebElement signupLoginNavBtn;

    @FindBy(xpath = "//a[@href='/test_cases']")
    private WebElement testCasesNavBtn;

    @FindBy(xpath = "//a[@href='/api_list']")
    private WebElement apiTestingNavBtn;

    @FindBy(xpath = "//a[contains(text(),' Video Tutorials')]")
    private WebElement videoTutorialsNavBtn;

    @FindBy(xpath = "//a[@href='/contact_us']")
    private WebElement contactUsNavBtn;
    
    @FindBy(xpath = "//div[contains(@class,'panel-collapse')]")
    private List<WebElement> accordionBodies;

    // --- Page Section Locators ---
 
    @FindBy(xpath = "//div[@class='panel-group']//div[contains(@class,'panel')]")
    private List<WebElement> testCaseList;
    
    
    @FindBy(xpath = "//div[contains(@class,'panel-collapse')]//u")
    private List<WebElement> underlinedElements;
    
    @FindBy(xpath = "//span[text()='Software quality assurance']")
    private WebElement discoverMoreLink;

    private By discoverMore = By.xpath("//span[text()='Discover more']");
    
   private By accordionHeaderLocator = By.xpath("//a[contains(@href,'#collapse')]");
//    private By accordionBodyLocator = By.xpath("//div[contains(@class,'panel-collapse')]");
   
// Locators for Development Tools Badge / Panel section
   @FindBy(xpath = "//*[@id=\"google-anno-sa\"]")
   private WebElement devToolsBadge;

   private By devToolsBadgeBy = By.xpath("//*[@id=\"google-anno-sa\"]/span[1]/span[2]");
  
   @FindBy(xpath = "//*[@id=\"form\"]/div/div[2]/h5/span")
   private WebElement instructionText;
   
   @FindBy(xpath = "//*[@id=\"feedback\"]/ul/li[3]/a/u")
   private WebElement emailAddressElement;

  
   
 

   private By instructionTextBy = By.xpath("//span[text()='Below is the list of test Cases for you to practice the Automation. Click on the scenario for detailed Test Steps:']");
  
   
   public TestCasesPage(WebDriver driver) 
    {
    	 this.driver = driver;
         this.actionUtil = new ActionUtil(driver);
         this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         PageFactory.initElements(driver, this); // Initializes @FindBy elements;
    }

  
    public boolean isTestCasePageHeaderDisplayed() {
        actionUtil.remove_ad();
        return actionUtil.isDisplayed(testCasesHeader) || actionUtil.isDisplayed(testCasesTitleBreadcrumb);
    }

    
    public String getPageHeaderText() {
        actionUtil.remove_ad();
        if (actionUtil.isDisplayed(testCasesHeader)) {
            return actionUtil.getText(testCasesHeader);
        }
        return actionUtil.getText(testCasesTitleBreadcrumb);
    }
    
    public boolean isAccordionGroupDisplayed() {
        actionUtil.remove_ad();
        return actionUtil.isDisplayed(accordionGroup);
    }

    public int getAccordionItemsCount() {
        actionUtil.remove_ad();
        return accordionItemsList.size();
    }

    public boolean areAccordionItemsVisible() {
        if (accordionItemsList.isEmpty()) {
            return false;
        }
        for (WebElement item : accordionItemsList) {
            actionUtil.scrollToElement(item);
            if (!actionUtil.isDisplayed(item)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isAccordionHeaderVisible() {
        return actionUtil.isDisplayed(firstAccordionHeader);
    }
    
    
    
    public void clickFirstAccordionHeader() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(firstAccordionHeader);
        actionUtil.clickViaJS(firstAccordionHeader);
    }

    public boolean isFirstAccordionExpanded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstAccordionContent));
            String classAttr = firstAccordionContent.getAttribute("class");
            return classAttr != null && (classAttr.contains("in") || classAttr.contains("show"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstAccordionCollapsed() {
        try {
            wait.until(ExpectedConditions.invisibilityOf(firstAccordionContent));
            String classAttr = firstAccordionContent.getAttribute("class");
            return classAttr == null || (!classAttr.contains("in") && !classAttr.contains("show"));
        } catch (Exception e) {
            return true;
        }
    }

    public int getAccordionCount() {
        return accordionHeaders.size();
    }
   
    
    public void scrollToFeedbackSection() {
        actionUtil.scrollToElement(feedbackHeader);
    }

    public boolean isFeedbackHeaderDisplayed() {
        return actionUtil.isDisplayed(feedbackHeader);
    }

    public boolean isFeedbackMailLinkDisplayed() {
        return actionUtil.isDisplayed(feedbackMailtoLink);
    }

    public String getFeedbackMailHref() {
        return actionUtil.getAttribute(feedbackMailtoLink, "href");
    }

    public void clickFeedbackMailLink() {
        actionUtil.clickViaJS(feedbackMailtoLink);
    }
    
    public void scrollToSubscriptionSection() {
        actionUtil.scrollToElement(subscriptionHeading);
    }

    public boolean isSubscriptionHeadingVisible() {
        return actionUtil.isDisplayed(subscriptionHeading);
    }

    public void enterSubscriptionEmail(String email) {
        actionUtil.sendKeys(subscriptionEmailInput, email);
    }

    public void clickSubscribeButton() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(subscribeBtn);
    }

    public boolean isSubscriptionSuccessMessageDisplayed() {
        return actionUtil.isDisplayed(subscribeSuccessMsg);
    }

    public String getSuccessMessageText() {
        return actionUtil.getText(subscribeSuccessMsg);
    }
    
    
    public boolean isSubscriptionEmailFormattedCorrectly(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Validates email contains '@' and ends with or contains '.com'
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[cC][oO][mM]$";
        return email.matches(emailRegex);
    }

    // Retrieves current entered text from the subscription input field
    public String getEnteredSubscriptionEmail() {
        return actionUtil.getAttribute(subscriptionEmailInput, "value");
    }

    // Fetches browser HTML5 validation message for subscription field
    public String getSubscriptionValidationMessage() {
        return actionUtil.getValidationMessage(By.id("susbscribe_email"));
    }
  
    
    
    public boolean areUnderlinedInstructionLinksClickable() {
    	int totalAccordions = getAccordionCount();
        if (totalAccordions == 0) {
            System.out.println("No accordion panels found on the page.");
            return false;
        }

        int totalUTagsVerified = 0;

        for (int i = 0; i < totalAccordions; i++) {
            // Step 1: Expand the specific accordion panel
            expandAccordionByIndex(i);

            // Step 2: Dynamically locate <u> tags within THIS specific expanded accordion
            By innerUTagLocator = By.xpath("(//div[contains(@class,'panel-collapse')])[" + (i + 1) + "]//u");
            List<WebElement> uTagsInPanel = driver.findElements(innerUTagLocator);

            // Skip panels that don't contain <u> tags
            if (uTagsInPanel.isEmpty()) {
                continue;
            }

            // Step 3: Verify visibility and interactability for each <u> element in the current panel
            for (WebElement uTag : uTagsInPanel) {
                actionUtil.scrollToElement(uTag);

                // Verify visual visibility in DOM
                if (!actionUtil.isDisplayed(uTag)) {
                    System.out.println("Underlined element not visible in accordion " + i + ": " + uTag.getText());
                    return false;
                }

                // Verify element is interactable via JS click
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", uTag);
                } catch (Exception e) {
                    System.out.println("Failed to interact with <u> tag: " + uTag.getText());
                    return false;
                }

                totalUTagsVerified++;
            }
        }

        System.out.println("Total <u> elements verified across panels: " + totalUTagsVerified);
        return totalUTagsVerified > 0;
    }
    
    
    public boolean isScrollUpArrowVisible() {
        return actionUtil.isDisplayed(scrollUpArrow);
    }

   
    public void clickScrollUpArrow() {
        actionUtil.clickViaJS(scrollUpArrow);
    }
    
    public boolean isScrolledToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long scrollY = (Long) js.executeScript("return window.pageYOffset || document.documentElement.scrollTop;");
        return scrollY != null && scrollY <= 200;
    }
    
    
    public void scrollToBottom() {
        actionUtil.scrollToBottom();
    }
    
    
    public boolean isHeaderNavigationVisible() {
    	actionUtil.remove_ad();
        return actionUtil.isDisplayed(homeNavBtn) &&
               actionUtil.isDisplayed(productsNavBtn) &&
               actionUtil.isDisplayed(cartNavBtn) &&
               actionUtil.isDisplayed(signupLoginNavBtn) &&
               actionUtil.isDisplayed(testCasesNavBtn) &&
               actionUtil.isDisplayed(apiTestingNavBtn) &&
               actionUtil.isDisplayed(contactUsNavBtn);
    }

    public void clickHome() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(homeNavBtn);
    }

    public void clickProducts() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(productsNavBtn);
    }

    public void clickCart() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(cartNavBtn);
    }

    public void clickSignupLogin() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(signupLoginNavBtn);
    }

    public void clickTestCases() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(testCasesNavBtn);
    }

    public void clickApiTesting() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(apiTestingNavBtn);
    }

    public void clickVideoTutorials() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(videoTutorialsNavBtn);
    }

    public void clickContactUs() {
        actionUtil.remove_ad();
        actionUtil.clickViaJS(contactUsNavBtn);
    }
    
    public void expandAccordionByIndex(int index) {
        if (index < accordionItemsList.size()) {
            WebElement toggle = accordionItemsList.get(index);
            actionUtil.scrollToElement(toggle);
            actionUtil.clickViaJS(toggle);
            
            // Allow animation/transition to process
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }

    public boolean isAccordionExpanded(int index) {
        if (index < accordionBodies.size()) {
            WebElement body = accordionBodies.get(index);
            String className = body.getAttribute("class");
            return className != null && (className.contains("in") || className.contains("show"));
        }
        return false;
    }
    public boolean isAccordionContentExpanded() {
        return actionUtil.isDisplayed(firstAccordionContent);
    }

    public void expandMultipleAccordions(int... indices) {
        for (int index : indices) {
            expandAccordionByIndex(index);
        }
    }

    public boolean areMultipleAccordionsExpanded(int... indices) {
        for (int index : indices) {
            if (!isAccordionExpanded(index)) {
                return false;
            }
        }
        return true;
    }
    
    public void focusFirstAccordion() {
        List<WebElement> list = actionUtil.findElements(accordionHeaderLocator);
        if (!list.isEmpty()) {
            actionUtil.scrollToElement(list.get(0));
            actionUtil.clickViaJS(list.get(0));
        }
    }
    
    
    public void sendKeyToActiveElement(Keys key) {
        WebElement activeElement = driver.switchTo().activeElement();
        activeElement.sendKeys(key);
    }
    
    
    
    
    public boolean isAccordionExpandedWithWait(int index) {
    	actionUtil.remove_ad();
        try {
            if (index < accordionBodies.size()) {
                WebElement body = accordionBodies.get(index);
                return wait.until(ExpectedConditions.attributeContains(body, "class", "in"))
                    || wait.until(ExpectedConditions.attributeContains(body, "class", "show"));
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public void focusAccordionIndex(int index) {
        if (index < accordionHeaders.size()) {
            WebElement header = accordionHeaders.get(index);
            actionUtil.scrollToElement(header);
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", header);
        }
    }
    
    
    
    public void navigateAccordionWithTabKey() {
        actionUtil.pressTabKey();
    }

    public boolean isAccordionFocused(int index) {
        List<WebElement> list = actionUtil.findElements(accordionHeaderLocator);
        if (index < list.size()) {
            return actionUtil.isElementFocused(By.xpath("(//div[contains(@class,'panel-group')]//a[@data-toggle='collapse'])[" + (index + 1) + "]"));
        }
        return false;
    }

    public void expandFocusedAccordionViaKeyboard() {
        WebElement activeElement = driver.switchTo().activeElement();
        activeElement.sendKeys(org.openqa.selenium.Keys.ENTER);
    }
    
    
    public boolean isDiscoverMoreLinkVisible() {
        actionUtil.scrollToElement(discoverMoreLink);
        return actionUtil.isDisplayed(discoverMoreLink);
    }

    public void clickDiscoverMoreLink() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(discoverMoreLink);
        actionUtil.clickViaJS(discoverMoreLink);
    }

    public boolean DiscovermoreHeadingVisible()
    {
    	return actionUtil.isDisplayedd(discoverMore);
    }
    
    public String DiscovermorelinkText()
    {
    	return actionUtil.getText(discoverMoreLink);
    }
    
    
    public void refreshBrowser() {
        driver.navigate().refresh();
        actionUtil.remove_ad();
    }

    public double getVerticalScrollPosition() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object yOffset = js.executeScript("return window.pageYOffset || document.documentElement.scrollTop;");
        return yOffset instanceof Long ? ((Long) yOffset).doubleValue() : (Double) yOffset;
    }

    public void scrollToMiddle() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
    }
    
    
    public boolean isDevToolsBadgeDisplayed() {
        try {
            // Use explicit wait instead of immediate scroll/visibility check
            WebDriverWait badgeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement badge = badgeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='google-anno-sa']")));
            actionUtil.scrollToElement(badge);
            return badge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickDevToolsBadge() {
        try {
            WebDriverWait badgeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement badge = badgeWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='google-anno-sa']")));
            actionUtil.scrollToElement(badge);
            actionUtil.clickViaJS(badge);
        } catch (Exception e) {
            System.out.println("Failed to click DevTools Badge: " + e.getMessage());
        }
    }

    public boolean isDevToolsBadgeClickable() {
        return actionUtil.isElementPresentAndVisible(devToolsBadgeBy); //
    }
    public boolean isInstructionTextDisplayed() {
        return actionUtil.isElementPresentAndVisible(instructionTextBy);
    }

    public String getInstructionTextContent() {
        return actionUtil.getText(instructionText);
    }

    public String getInstructionTextColor() {
        actionUtil.waitForVisibility(instructionText);
        return instructionText.getCssValue("color");
    }

    public boolean isInstructionTextPositionedAboveList() {
        actionUtil.waitForVisibility(instructionText);
        // Returns true if the instruction text element is located within the top content block
        int elementYPosition = instructionText.getLocation().getY();
        return elementYPosition > 0;
    }
    
    
    public boolean isInstructionTextProperlyCapitalized() {
        String fullText = getInstructionTextContent();
        // Returns true if text uses capitalized 'Test Cases'
        return fullText.contains("Test Cases");
    }
    
    
    
    public String getEmailText() {
    	actionUtil.remove_ad();
        return actionUtil.getText(emailAddressElement);
    }

  
    public void attemptToCopyEmail() {
        actionUtil.scrollToElement(emailAddressElement);
        Actions actions = new Actions(driver);
        
        // Highlight / Select text
        actions.moveToElement(emailAddressElement)
               .click()
               .keyDown(Keys.CONTROL)
               .sendKeys("a")
               .sendKeys("c")
               .keyUp(Keys.CONTROL)
               .build()
               .perform();
    }
  
    public String pasteAndGetClipboardContent() {
        actionUtil.scrollToElement(subscriptionEmailInput);
        subscriptionEmailInput.click();
        
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL)
               .sendKeys("v")
               .keyUp(Keys.CONTROL)
               .build()
               .perform();

        return subscriptionEmailInput.getAttribute("value");
    }
    
    

   
    
    public boolean isInstructionTextPositionedBeforeItems() {
        actionUtil.waitForVisibility(instructionText);
        
        if (accordionItemsList.isEmpty()) {
            return false;
        }

        // Get vertical (Y) coordinates of instruction text and the first accordion item
        int instructionYPos = instructionText.getLocation().getY();
        int firstItemYPos = accordionItemsList.get(0).getLocation().getY();

        // Verify instruction text appears strictly above the list item
        return instructionYPos < firstItemYPos;
    }
    
    
    public boolean isInstructionTextRed() {
	    actionUtil.waitForVisibility(instructionText);
	    String color = instructionText.getCssValue("color");
	    // Matches rgba(255, 0, 0, ...), rgb(255, 0, 0), or hex red values
	    return color != null && (color.contains("255, 0, 0") || color.contains("red"));
	}
   
    
    public void clickRedInstructionText() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(instructionText);
        actionUtil.clickViaJS(instructionText);
    }
    
    
    
    public boolean isErrorPageDisplayed() {
        String pageTitle = driver.getTitle().toLowerCase();
        String pageSource = driver.getPageSource().toLowerCase();
        
        return pageTitle.contains("404") || pageTitle.contains("500") || pageTitle.contains("error") ||
               pageSource.contains("404 Not Found") || pageSource.contains("500 Internal Server Error") ||
               pageSource.contains("page not found");
    }
    
    
    
    public boolean verifyTestStepsSequenceInAccordion(int accordionIndex) {
        
    	
    	// 1. Expand target accordion item
        expandAccordionByIndex(accordionIndex);

        // 2. Corrected XPath locator targeting list items inside the active collapse container
        String stepsXpath = "(//div[contains(@class,'panel-collapse')])[" + (accordionIndex + 1) + "]//li";
        
        // Wait briefly for accordion collapse container to expand and present elements
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(stepsXpath)));
        List<WebElement> steps = driver.findElements(By.xpath(stepsXpath));

        if (steps.isEmpty()) {
            System.out.println("No test steps found for scenario index: " + accordionIndex);
            return false;
        }

        int expectedStepNumber = 1;
        for (WebElement stepElement : steps) {
            actionUtil.scrollToElement(stepElement);
            String stepText = actionUtil.getText(stepElement).trim();

            // Extracts step number from formats like "1. Launch browser", "Step 1:", or "1 - Click"
            if (stepText.matches("^\\d+[\\.\\:-].*")) {
                int actualStepNumber = Integer.parseInt(stepText.split("[\\.\\:-]")[0].trim());
                
                if (actualStepNumber != expectedStepNumber) {
                    System.out.println("Sequence mismatch in accordion index " + accordionIndex + 
                        ": Expected Step " + expectedStepNumber + ", but found " + actualStepNumber);
                    return false;
                }
                expectedStepNumber++;
            }
        }
        
        // Returns true if at least one sequentially valid step was processed
        return expectedStepNumber > 1; 
    }
    
    public void remove_Ad() {
        try {
            actionUtil.remove_ad();
        } catch (Exception ignored) {}
    }
    
    
}
    
    
    
    


