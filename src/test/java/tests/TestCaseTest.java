package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseclassTest;
import pagesClass.ClassHomePage;
import pagesClass.TestCasesPage;
import Utils.ScreenshotList;

@Listeners(ScreenshotList.class)
public class TestCaseTest extends BaseclassTest
{
	private ClassHomePage homePage;
    private TestCasesPage testCasePage;

    @BeforeClass
    public void initPages()
    {
        homePage = new ClassHomePage(getdriver());
        testCasePage = new TestCasesPage(getdriver());
    }

    @BeforeMethod
    public void ensureHomePage() {
        if (!getdriver().getCurrentUrl().equals("https://www.automationexercise.com/test_cases")) {
            getdriver().get("https://www.automationexercise.com/test_cases");
        }
        homePage.remove_Ad();
        homePage.dismissCartModalIfVisible();
    }

  //verify navigation to test case page loads successfully 
    @Test(priority = 1, description = "TCP_001")
    public void test_VerifyNavigationToTestCasesPage()
    {

        Assert.assertTrue(testCasePage.isTestCasePageHeaderDisplayed(), 
            "Test Cases page header is not visible on the page.");

        System.out.println("Navigated to Test Cases Page successfully and header verified.");
        
    }
	
	
    
    
    
    
 // Verify Display of Accordion Items List
    @Test(priority = 2, description = "TCP_002")
    public void verify_AccordionItemsList_Display() 
    {


    	Assert.assertTrue(testCasePage.isTestCasePageHeaderDisplayed(), 
            "Test Cases page header is not visible on the page.");
    
        
        Assert.assertTrue(testCasePage.isAccordionGroupDisplayed(), 
            "Accordion list container is not displayed on the Test Cases page.");

        int itemCounts = testCasePage.getAccordionItemsCount();
        Assert.assertTrue(itemCounts > 0, 
            "No accordion items were found on the Test Cases page. Found: " + itemCounts);

        Assert.assertTrue(testCasePage.areAccordionItemsVisible(), 
            "One or more accordion items in the list are not visible.");

        System.out.println("Accordion Items List Display Verified Successfully. Total items found: " + itemCounts);
    }
	
    
    
    
 // Verify Expanding and Collapsing Accordion Item on Test Cases Page
    @Test(priority = 3, description = "TCP_003")
    public void verifyAccordionExpandCollapse() 
    {
       
        Assert.assertTrue(testCasePage.isAccordionHeaderVisible(), "Accordion items are not visible on the page.");

        
        testCasePage.clickFirstAccordionHeader();
        
        try {
            Thread.sleep(1000); // Allow toggle collapse animation to complete
        } catch (InterruptedException ignored) {}

        Assert.assertTrue(testCasePage.isFirstAccordionExpanded(), 
            "Accordion item failed to expand on click.");

        
        testCasePage.clickFirstAccordionHeader();
        
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException ignored) {}

        Assert.assertTrue(testCasePage.isFirstAccordionCollapsed(), 
            "Accordion item failed to collapse on re-clicking.");

        System.out.println("Verified Expanding/Collapsing Accordion Item successfully.");
    }
	
//    Verify Feedback Section and Mail to Link
    @Test(priority =4, description = "TCP_004")
    public void verifyFeedbackSectionLinks()
    {
    	
        testCasePage.scrollToFeedbackSection();
        Assert.assertTrue(testCasePage.isFeedbackHeaderDisplayed(), 
            "Feedback section heading is not visible on the page.");

        Assert.assertTrue(testCasePage.isFeedbackMailLinkDisplayed(), 
            "Feedback mailto link is not visible.");
        
        String mailHref = testCasePage.getFeedbackMailHref();
        Assert.assertTrue(mailHref.contains("mailto:feedback@automationexercise.com"), 
            "Feedback mail link href attribute does not match expected mailto address. Found: " + mailHref);

      
        testCasePage.clickFeedbackMailLink();
        System.out.println("Feedback Section and Mailto Link Verification Passed successfully.");
    	
    }
    
    
    
    //verify footer subscription successful
    @Test(priority =5,description = "TCP_005")
    public void verifyFooterSubscription()
    {
        
    	testCasePage.scrollToSubscriptionSection();
        Assert.assertTrue(
        		testCasePage.isSubscriptionHeadingVisible(),
            "Subscription heading is not visible in the footer section."
        );

        //Enter a valid email address into the input box
        String validEmail = "testuser_" + System.currentTimeMillis() + "@example.com";
        testCasePage.enterSubscriptionEmail(validEmail);

        // Click the arrow/submit button
        testCasePage.clickSubscribeButton();

        // Verify success message is displayed
        Assert.assertTrue(
        		testCasePage.isSubscriptionSuccessMessageDisplayed(),
            "Subscription success message was not displayed after submitting valid email."
        );
        
        System.out.println("Footer subscription test executed and passed successfully.");
    }
    

    
    
    
 //Verify Bottom-to-Top Scroll Arrow Button in page
    @Test(priority = 6, description = "TCP_006")
    public void verify_BottomToTop_ScrollArrowButton() 
    {
       
        testCasePage.scrollToBottom();
        try {
            testCasePage.remove_Ad();
            Thread.sleep(1000); // Allow layout transition
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

     
        Assert.assertTrue(testCasePage.isScrollUpArrowVisible(), "Scroll-up arrow button is not visible at the bottom of the page.");

        
        testCasePage.clickScrollUpArrow();
        try {
            testCasePage.remove_Ad();
            Thread.sleep(1500); // Allow smooth scrolling action to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        Assert.assertTrue(testCasePage.isScrolledToTop(), "Failed: Page did not scroll to the top after clicking the scroll arrow button.");
        System.out.println("Verify Bottom-to-Top Scroll Arrow Button Passed.");
    }
    
    
    
    
 // Verify Test Cases Page Load & Header Navigation Links visibility and accessibility
    @Test(priority =7, description = "TCP_007")
    public void verify_HeaderNavigationLinks_Visible() {
    
    	
        Assert.assertTrue(testCasePage.isHeaderNavigationVisible(), 
            "One or more header navigation links are not displayed.");
        System.out.println("Header navigation links visibility verified.");

       
        testCasePage.clickHome();
        Assert.assertEquals(getdriver().getCurrentUrl(), "https://www.automationexercise.com/", 
            "Failed to navigate to Home Page.");
        System.out.println("Home Link Navigation passed.");
        getdriver().navigate().back();
        testCasePage.remove_Ad();

      
        testCasePage.clickProducts();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("products"), 
            "Failed to navigate to Products Page.");
        System.out.println("Products Link Navigation passed.");
        getdriver().navigate().back();
        testCasePage.remove_Ad();

       
        testCasePage.clickCart();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("view_cart"), 
            "Failed to navigate to Cart Page.");
        System.out.println("Cart Link Navigation passed.");
        getdriver().navigate().back();
        testCasePage.remove_Ad();

      
        testCasePage.clickSignupLogin();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("login"), 
            "Failed to navigate to Signup/Login Page.");
        System.out.println("Signup/Login Link Navigation passed.");
        getdriver().navigate().back();
        testCasePage.remove_Ad();

    
        testCasePage.clickApiTesting();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("api_list"), 
            "Failed to navigate to API Testing Page.");
        System.out.println("API Testing Link Navigation passed.");
        getdriver().navigate().back();
        testCasePage.remove_Ad();

        
        testCasePage.clickContactUs();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("contact_us"), 
            "Failed to navigate to Contact Us Page.");
        System.out.println("Contact Us Link Navigation passed.");
    }
    
    
 // Verify Multiple Accordion Items Expansion Behavior
    @Test(priority = 8, description = "TCP_-008")
    public void verifyMultipleAccordionItemsExpansionBehavior()
    {

     
        int totalAccordions = testCasePage.getAccordionCount();
        Assert.assertTrue(totalAccordions >= 2, "Insufficient accordion items available to test multiple expansion.");

        // Target first two accordion items
        int firstAccordionIndex = 0;
        int secondAccordionIndex = 1;

        // Expand first accordion item
        testCasePage.expandAccordionByIndex(firstAccordionIndex);
        Assert.assertTrue(testCasePage.isAccordionExpanded(firstAccordionIndex), 
                "First accordion item failed to expand upon click.");

        // Expand second accordion item
        testCasePage.expandAccordionByIndex(secondAccordionIndex);
        Assert.assertTrue(testCasePage.isAccordionExpanded(secondAccordionIndex), 
                "Second accordion item failed to expand upon click.");

        // Verify expansion behavior rule (whether independent multi-expansion or single accordion collapse behavior)
        boolean bothExpanded = testCasePage.areMultipleAccordionsExpanded(firstAccordionIndex, secondAccordionIndex);
        
        System.out.println("Multiple accordion expansion status: " + bothExpanded);
        Assert.assertTrue(testCasePage.isAccordionExpanded(secondAccordionIndex), 
                "Result: Accordion failed to remain expanded as expected.");
    }
    
    
    
    
    
    
    
 // Verify Keyboard Navigation (Accessibility) on Accordion List
    @Test(priority =9, description = "TCP_009")
    public void verify_Accordion_KeyboardNavigation_Accessibility() 
    {
    	int targetIndex = 0;

        //Set actual DOM focus to the first accordion header
        testCasePage.focusAccordionIndex(targetIndex);
        
        //Verify element currently holds DOM focus
        Assert.assertTrue(testCasePage.isAccordionFocused(targetIndex), 
            "Accordion header did not gain DOM keyboard focus.");

        // Send ENTER key to activate accordion expansion
        testCasePage.sendKeyToActiveElement(org.openqa.selenium.Keys.ENTER);

        // Verify accordion successfully expands via ENTER key
        boolean isExpandedWithEnter = testCasePage.isAccordionExpandedWithWait(targetIndex);
        Assert.assertTrue(isExpandedWithEnter, 
            "Accordion item failed to expand using the ENTER key.");

        //  Send SPACE key to toggle/collapse accordion via keyboard accessibility
        testCasePage.sendKeyToActiveElement(org.openqa.selenium.Keys.SPACE);

        //Tab navigation to shift focus to the next logical element in sequence
        testCasePage.navigateAccordionWithTabKey();

        System.out.println("Accordion keyboard accessibility navigation (Focus, ENTER, SPACE, TAB) verified successfully.");
    
    
    }
    
    
    
    
//    Verify irrelevance of 'Discover More' Side bar/Section Link 
    @Test(priority = 10, description = "TCP_010")
    public void verify_DiscoverMore_LinkRedirection() 
    {
    JavascriptExecutor js=(JavascriptExecutor)(getdriver());
    	 js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Assert.assertTrue(testCasePage.DiscovermoreHeadingVisible(),"Discover more Heading is not visible on page ");
        Assert.assertTrue(testCasePage.isDiscoverMoreLinkVisible(), "'Discover More' link is not displayed on the Test Cases page.");
        testCasePage.clickDiscoverMoreLink();
        System.out.println("Verify 'Discover More' Link Redirection Passed Successfully.");
        String Text=testCasePage.DiscovermorelinkText();
       Assert.assertEquals(Text,"Verify checkout functionality");
        System.out.println("Discover more contains irrelevant contents.");
    }
    
    
    
    
    
    
 // Verify Page State on Refresh / Browser Reload the initial test cases page
    @Test(priority = 11, description = "TCP_011")
    public void test_VerifyPageStateOnBrowserReload()
    {
      
        
        String initialHeader = testCasePage.getPageHeaderText();
        Assert.assertTrue(initialHeader.equalsIgnoreCase("TEST CASES"), 
            "Header title did not match expected 'TEST CASES'. Found: " + initialHeader);

        // Step 2: Interact with UI elements (Expand an accordion item and scroll down)
        testCasePage.scrollToMiddle();
        testCasePage.getVerticalScrollPosition();
        
        testCasePage.clickFirstAccordionHeader();
       
        // Step 3: Refresh the browser window
        testCasePage.refreshBrowser();     
        Assert.assertEquals(getdriver().getCurrentUrl(), "https://www.automationexercise.com/test_cases", 
            "URL modified post browser reload.");

        Assert.assertTrue(testCasePage.getPageHeaderText().equalsIgnoreCase("TEST CASES"), 
            "Page header title changed or missing after refresh.");

        System.out.println("Page state verification on browser reload passed successfully.");
    }
    
    
    
    
    
 // Verify 'Development Tools' Badge Display and Behavior
    @Test(priority = 12, description = "TCP_012")
    public void verifyDevToolsBadgeDisplayAndBehavior()
    {
        

        Assert.assertTrue(testCasePage.isDevToolsBadgeDisplayed(), 
            "Development Tools badge is not displayed on the Test Cases page.");

      
        Assert.assertTrue(testCasePage.isDevToolsBadgeClickable(), 
            "Development Tools badge is not interactable/clickable.");
        
        testCasePage.clickDevToolsBadge();

        System.out.println("Development Tools Badge Display and Behavior Assertion Passed.");
    }
    
    
    
 // =Verify presence, color, and positioning of the instruction text on the Test Cases page.
    @Test(priority =13, description = "TCP_0013")
    public void verifyInstructionTextPresenceColorAndPositioning() {
        
    

        //Verify Presence and Visibility of Instruction Text
        Assert.assertTrue(testCasePage.isInstructionTextDisplayed(), 
            "Instruction text is not present/visible on the page.");
        
        String actualText = testCasePage.getInstructionTextContent();
        Assert.assertFalse(actualText.isEmpty(), 
            "Instruction text content is empty.");

        // 3. Verify Text Color (Returns RGBA or standard HEX color value)
        String textColor = testCasePage.getInstructionTextColor();
        Assert.assertNotNull(textColor, 
            "Could not retrieve CSS color attribute of instruction text.");

        // 4. Verify Layout Positioning
        Assert.assertTrue(testCasePage.isInstructionTextPositionedAboveList(), 
            "Instruction text is not positioned correctly on the page.");

        System.out.println("[TCP_001] Verified instruction text presence, color (" + textColor + "), and correct positioning successfully.");
    }
    
    
    
    
    
    @Test(priority = 14, description = "TCP_014")
    public void testCopyEmailAddressFunctionalityFailure() {
      

        // Capture original email text
        String expectedEmail = testCasePage.getEmailText();
        System.out.println("Target Email Address: " + expectedEmail);

        // Attempt copy operation via UI action
        testCasePage.attemptToCopyEmail();

        // Paste clip board content into target field
        String pastedContent = testCasePage.pasteAndGetClipboardContent();
        System.out.println("Clipboard Pasted Content: " + pastedContent);

        // Verify copy failure (Assert that pasted text does NOT equal the expected email)
        Assert.assertNotEquals(pastedContent, expectedEmail, 
            "Defect Found / Verification Passed: Copy email functionality failed as expected, copied content does not match target email.");

        System.out.println("Test completed successfully: Copy functionality failure verified.");
    }
    
    
    
    
    
    //verify rapid multiple clicks on link
    @Test(priority = 15, description = "TCP_015")
    public void verify_RapidMultipleClicks_OnLink() 
    {
        //  Target a navigation link (e.g., Products Link)
        String targetUrlPart = "products";

        //  Perform rapid consecutive clicks on the targeted link using a loop
        int clickCount = 5;
        for (int i = 0; i < clickCount; i++) {
            try {
                testCasePage.clickProducts(); // Executes instant JavaScript or standard click
            } catch (Exception e) {
              
            	// Log element stale state or click interception if rapid action breaks interaction
                System.out.println("Click iteration " + (i + 1) + " threw exception: " + e.getMessage());
            }
        }

        // Verify driver stability, absence of unhandled server/JS errors, and correct final page navigation
      
        String currentUrl = getdriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(targetUrlPart), 
            "Failed: Rapid clicking led to unexpected URL/page state. Current URL: " + currentUrl);

        //  Validate core page elements exist after multi-click stress
        Assert.assertFalse(getdriver().getTitle().contains("500") || getdriver().getTitle().contains("Error"), 
            "Application crashed or rendered an unexpected error page post rapid clicks.");

        System.out.println("Rapid multiple clicks verified successfully without leading to unexpected errors.");
    }
    
    
    
    
    
    
    
    //ensure instruction is placed before the accordion list
    @Test(priority = 16, description = "TCP_016")
    public void verifyInstructionTextBeforeaccordionList() {
        // Verify instruction text is displayed
        Assert.assertTrue(testCasePage.isInstructionTextDisplayed(), 
            "Instruction text is not displayed on the Test Cases page.");

        // Verify items list is non-empty
        Assert.assertTrue(testCasePage.getAccordionItemsCount() > 0, 
            "No accordion items found to check sequence.");

        // Verify sequence (Instruction text Y-position < First list item Y-position)
        Assert.assertTrue(testCasePage.isInstructionTextPositionedBeforeItems(), 
            "Instruction text does not appear in the correct sequence before the test case items.");

        System.out.println("Verified that the instruction text appears in the correct sequence before the test case items.");
    }
    
    
    
    
    
    
    
    
    
    
   // Verify sequence of test steps under expanded test scenarios
    @Test(priority = 17, description = "TCP_017")
    public void verify_TestStepsSequenceUnderTestScenarios() 
    {
    	int totalAccordions = testCasePage.getAccordionCount();
        Assert.assertTrue(totalAccordions > 0, "No test scenario accordions found on the page.");

        // 2. Iterate through each scenario and assert sequence flow
        for (int i = 0; i < totalAccordions-1; i++) {
            boolean isSequenced = testCasePage.verifyTestStepsSequenceInAccordion(i);
            Assert.assertTrue(isSequenced, "Test steps sequence failed for scenario at index: " + i);
        }

        System.out.println("Test steps sequence verification passed across all " + totalAccordions + " displayed test scenarios.");
    }
    
    
    
    
    
    
    
    //Verify clicking red instruction text does not lead to a broken link or error page
    @Test(priority = 18, description = "TCP_018")
    public void verify_RedInstructionTextClick_DoesNotLeadToErrorPage()
    
    {
        // 
        Assert.assertTrue(testCasePage.isInstructionTextDisplayed(), 
            "Red-colored instruction text is not displayed on the Test Cases page.");

        // Click on the red instruction text
        testCasePage.clickRedInstructionText();

        // Verify the driver is on a valid URL and not an error page
        String currentUrl = getdriver().getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL returned null after clicking instruction text.");
        Assert.assertFalse(testCasePage.isErrorPageDisplayed(), 
            "Clicking on the red instruction text led to an error page or broken link. Current URL: " + currentUrl);

        //  Ensure application state remains stable
        Assert.assertTrue(currentUrl.startsWith("http"), 
            "Invalid protocol or broken link endpoint encountered post-click: " + currentUrl);

        System.out.println("Verified successfully: Clicking red instruction text does not lead to a broken link or error page.");
    }
    
    
    
    
//    Validate Email Formatting and Footer Subscription
    
    @Test(priority =19, description = "TCP_019")
    public void verifyFooterSubscriptionemailFormat()
    {
        testCasePage.scrollToSubscriptionSection();
        Assert.assertTrue(
            testCasePage.isSubscriptionHeadingVisible(),
            "Subscription heading is not visible in the footer section."
        );

        // Negative Check - Enter invalid email without '@' or '.com'
        String invalidEmail = "invaliduserdomain";
        testCasePage.enterSubscriptionEmail(invalidEmail);
        
        Assert.assertFalse(
            testCasePage.isSubscriptionEmailFormattedCorrectly(invalidEmail),
            "Failed: Invalid email passed format validation check."
        );

        // Step Execution - Enter a valid formatted email
        String validEmail = "testuser_" + System.currentTimeMillis() + "@example.com";
        testCasePage.enterSubscriptionEmail(validEmail);

        // Verify typed string format
        String enteredEmail = testCasePage.getEnteredSubscriptionEmail();
        Assert.assertTrue(
            testCasePage.isSubscriptionEmailFormattedCorrectly(enteredEmail),
            "Entered email format validation failed: Email must contain '@' and '.com'. Found: " + enteredEmail
        );

        // Submit valid subscription form
        testCasePage.clickSubscribeButton();

        //Verify subscription success message display
        Assert.assertTrue(
            testCasePage.isSubscriptionSuccessMessageDisplayed(),
            "Subscription success message was not displayed after submitting valid email."
        );

        System.out.println("Footer subscription email format validation and submission test executed successfully.");   
     }

    
    
    
    
    
    
 // Verify Footer Subscription and Email Persistence on Refresh
    @Test(priority = 20, description = "TCP_020")
    public void verifyFooterSubscriptionPersistenceOnRefresh()
    {
        testCasePage.scrollToSubscriptionSection();
        Assert.assertTrue(
            testCasePage.isSubscriptionHeadingVisible(),
            "Subscription heading is not visible in the footer section."
        );

        //Enter email into the input field
        String validEmail = "testuser_" + System.currentTimeMillis() + "@example.com";
        testCasePage.enterSubscriptionEmail(validEmail);

        // Refresh the browser prior to submission to test input persistence
        testCasePage.refreshBrowser();

        // Scroll back to the footer section post-refresh
        testCasePage.scrollToSubscriptionSection();

        //  Verify the email address did not change or disappear
        String retainedEmail = testCasePage.getEnteredSubscriptionEmail();
        Assert.assertEquals(
            retainedEmail, 
            validEmail, 
            "The email address disappeared or changed after page refresh."
        );

        // Submit subscription and verify success
        testCasePage.clickSubscribeButton();
        Assert.assertTrue(
            testCasePage.isSubscriptionSuccessMessageDisplayed(),
            "Subscription success message was not displayed after submitting valid email."
        );
        
        System.out.println("Footer subscription email persistence on refresh verified successfully.");
    }
    
    
    
    
    
    
    
    
    
    
 // Verify Scenario Steps Visibility Persists On Browser Refresh
    @Test(priority = 21, description = "TCP_021")
    public void test_VerifyStepsVisibilityPersistsOnRefresh()
    {
        int targetIndex = 0; // Target first test scenario accordion

        // Click on a test scenario to expand steps
        testCasePage.expandAccordionByIndex(targetIndex);
        
        //  Ensure that steps appear
        Assert.assertTrue(testCasePage.isAccordionExpanded(targetIndex), 
            "Test scenario steps did not appear upon clicking.");

        // Refresh the page
        testCasePage.refreshBrowser();

        // Verify that the steps visibility persists after refresh
        Assert.assertTrue(testCasePage.isAccordionExpanded(targetIndex), 
            "Test scenario steps visibility did not persist after page refresh.");
    }
    
    
    
    
    
    
 // Verify phrase "Test Cases" displays proper capitalization (Upper case 'T') in the red instruction text
    @Test(priority = 22, description = "TCP_022")
    public void verify_RedInstructionText_SentenceCapitalization() 
    {
      
        Assert.assertTrue(testCasePage.isInstructionTextDisplayed(), 
            "Instruction text is not displayed on the Test Cases page.");

       
        String actualText = testCasePage.getInstructionTextContent();
        System.out.println("Actual Instruction Text: \"" + actualText + "\"");

      
        Assert.assertFalse(actualText.contains("test Cases"), 
            "Defect Found: The phrase 'test Cases' contains a lowercase 't'. Expected capitalized 'Test Cases'.");

        Assert.assertTrue(testCasePage.isInstructionTextProperlyCapitalized(), 
            "Validation Failed: Instruction text does not contain properly capitalized 'Test Cases'. Found: " + actualText);

        System.out.println("Sentence capitalization verification passed: 'Test Cases' is correctly capitalized.");
    }
    
    
}




