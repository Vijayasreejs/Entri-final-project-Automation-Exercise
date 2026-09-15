package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseclassTest;
import pagesClass.CartpageClass;
import pagesClass.CheckoutPage;
import pagesClass.ClassHomePage;
import pagesClass.LoginpageClass;
import pagesClass.ProductpageClass;
import Utils.ScreenshotList;

@Listeners(ScreenshotList.class)
public class CheckoutTest  extends BaseclassTest
{

	
	private ClassHomePage home;
    private LoginpageClass loginPage;
    private CartpageClass cartPage;
    private CheckoutPage checkoutPage;
    private ProductpageClass productPage;

    @BeforeClass
    public void initPages() {
        home = new ClassHomePage(getdriver());
        loginPage = new LoginpageClass(getdriver());
        cartPage = new CartpageClass(getdriver());
        checkoutPage = new CheckoutPage(getdriver());
        productPage = new ProductpageClass(getdriver());
    }

    @BeforeMethod
    public void setupTestState() {
        getdriver().get("https://www.automationexercise.com/");
        home.remove_Ad();
    }
    
    
    @AfterMethod
    public void clearCartAfterTest() {
        if (checkoutPage != null) {
            checkoutPage.clearCartTableCompletely();
        }
    }

    
  

    private void performUserLogin(String email, String password) {
        if (!loginPage.IsloggedIn()) {
            home.LoginSignupBTn();
            loginPage.StartLogin(email, password);
        }
    }

    private void addProductAndNavigateToCheckout() {
        home.ProductsBtn();
        productPage.remove_Ad();
        productPage.clickFirstProductDetails();
        productPage.addProductToCart();
        productPage.isAddedModalVisible();
        productPage.clickViewCartFromModal();
        productPage.remove_Ad();
        Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page failed to display.");
        cartPage.clickProceedToCheckout();
    }
    
    

	@DataProvider(name = "getvalidData")
    public Object[][] validloginData() 
	{
        // Fetches rows where Status is 'Valid' from 'Credentials' sheet
        return Utils.Excelutility.getTestDataByStatus("src/test/resources/testData.xlsx", 
        		"Credentials", "Valid");
	}

    //Verify display and consistency of Delivery Address and Billing Address details
    @Test(priority = 1, description = "CKTC_001",dataProvider="getvalidData")
    public void verifyDeliveryAndBillingAddressDetails(String email, String password)
    {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();

        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to navigate to Checkout page.");
        Assert.assertTrue(checkoutPage.isDeliveryAddressDisplayed(), "Delivery address panel is not visible.");
        Assert.assertTrue(checkoutPage.isBillingAddressDisplayed(), "Billing address panel is not visible.");
        Assert.assertTrue(checkoutPage.areAddressesMatching(), "Delivery and Billing address details do not match.");
        System.out.println("Delivery and Billing Address consistency verified.");
    }

    
    
    
    
    // Verify item descriptions, individual unit prices, and quantities under "Review Your Order"
    @Test(priority = 2, description = "CKTC_002",dataProvider="getvalidData")
    public void verifyReviewYourOrderDetails(String email, String password)
    {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();

        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");
        Assert.assertTrue(checkoutPage.isOrderReviewTableDisplayed(), "Order Review section is not visible.");

        // 4. Iterate over items in 'Review Your Order' table and verify details
        int totalItems = checkoutPage.getOrderReviewItemCount();
        Assert.assertTrue(totalItems > 0, "No items found in 'Review Your Order' table.");

        for (int i = 1; i <= totalItems; i++) {
            // Fetch dynamic attributes per item row
            String description = checkoutPage.getItemTitle(i);
            String unitPriceText = checkoutPage.getItemPrice(i);
            String quantityText = checkoutPage.getItemQuantity(i);
            String totalPriceText = checkoutPage.getItemTotal(i);

            // Assertions for item visibility and accuracy
            Assert.assertFalse(description.isEmpty(), "Item description is missing for row " + i);
            Assert.assertTrue(unitPriceText.contains("Rs."), "Unit price format invalid for row " + i + ": " + unitPriceText);
            
            int quantity = Integer.parseInt(quantityText.trim());
            Assert.assertTrue(quantity > 0, "Quantity must be greater than zero for row " + i);

            // Price calculation validation: Unit Price * Quantity == Total Price
            int unitPrice = Integer.parseInt(unitPriceText.replaceAll("[^0-9]", ""));
            int totalPrice = Integer.parseInt(totalPriceText.replaceAll("[^0-9]", ""));
            
            Assert.assertEquals(unitPrice * quantity, totalPrice, 
                "Calculated total does not match displayed item total for row " + i);

            System.out.println("Row " + i + " Verified -> Item: " + description + 
                               " | Unit Price: " + unitPriceText + 
                               " | Quantity: " + quantity + 
                               " | Total: " + totalPriceText);
        }
    }

    
    
    
    
    

    // Verify mathematical calculation of row item totals and overall Total Amount
    @Test(priority = 3, description = "CKTC_003",dataProvider="getvalidData")
    public void verifyMathematicalCalculationsInOrderReview(String email, String password) 
    {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();
    
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");
        Assert.assertTrue(checkoutPage.isOrderReviewTableDisplayed(), "Order Review section is not visible.");

        int itemCount = checkoutPage.getOrderReviewItemCount();
        Assert.assertTrue(itemCount > 0, "No items found in Order Review table.");

        int calculatedGrandTotal = 0;

        // Step 1: Verify row item total calculations (Unit Price * Quantity == Item Total)
        for (int i = 1; i <= itemCount; i++) {
            String unitPriceText = checkoutPage.getItemPrice(i);
            String quantityText = checkoutPage.getItemQuantity(i);
            String itemTotalText = checkoutPage.getItemTotal(i);

            int unitPrice = Integer.parseInt(unitPriceText.replaceAll("[^0-9]", ""));
            int quantity = Integer.parseInt(quantityText.trim());
            int displayedItemTotal = Integer.parseInt(itemTotalText.replaceAll("[^0-9]", ""));

            int expectedItemTotal = unitPrice * quantity;

            // Row-level assertion
            Assert.assertEquals(displayedItemTotal, expectedItemTotal, 
                "Row " + i + " mismatch: Unit Price (" + unitPrice + ") * Quantity (" + quantity + ") does not equal Displayed Total (" + displayedItemTotal + ")");

            calculatedGrandTotal += displayedItemTotal;
        }

        // Step 2: Verify overall Total Amount matches the sum of all row totals
        int displayedGrandTotal = checkoutPage.getFinalTotalAmount();

        Assert.assertEquals(calculatedGrandTotal, displayedGrandTotal, 
            "Grand Total mismatch: Calculated sum (" + calculatedGrandTotal + ") does not match displayed Total Amount (" + displayedGrandTotal + ")");

        System.out.println("Mathematical calculation test passed: Sum of row totals (" 
            + calculatedGrandTotal + ") matches overall Total Amount (" + displayedGrandTotal + ").");
    	
    	
    }

    
    
    
    
 // Verify navigation when clicking on category links/bread crumbs in the product review table
    @Test(priority = 4, description = "CKTC_004", dataProvider = "getvalidData")
    public void verifyCategoryBreadcrumbNavigationInReviewTable(String email, String password)
    {
       
     	performUserLogin(email,password);
        addProductAndNavigateToCheckout();
    	
    	
    	
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");
        Assert.assertTrue(checkoutPage.isOrderReviewTableDisplayed(), "Order Review section is not visible.");

        // Step 1: Get category text from the first product row in Order Review
        String categoryText = checkoutPage.getItemCategoryText(1);
        Assert.assertFalse(categoryText.isEmpty(), "Category/Breadcrumb text is empty for item row 1.");

        // Extract sub category name (e.g. from "Women > Dress" extract "Dress")
        String subCategoryName = categoryText.contains(">") 
            ? categoryText.substring(categoryText.lastIndexOf(">") + 1).trim() 
            : categoryText.trim();

        // Step 2: Click the category/breadcrumb link within the review table
        checkoutPage.clickCategoryBreadcrumbInReviewTable(subCategoryName);

        // Step 3: Assert page redirected to category/products listing page
        boolean isNavigated = getdriver().getCurrentUrl().contains("category_products") 
                           || productPage.isFilteredProductsViewVisible();

        Assert.assertTrue(isNavigated, 
            "Failed to navigate to category listing page upon clicking breadcrumb link: " + subCategoryName);

        System.out.println("Category breadcrumb navigation verified successfully for: " + subCategoryName);
    	
    		
    	
    }
    
    
    
    
    
    
    
    
    
    
    // Enter order comments/notes before placing the order
    @Test(priority = 5, description = "CKTC_005",dataProvider="getvalidData")
    public void verifyOrderCommentEntryAndProgression(String email, String password)
    {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();

        String testComment = "Please leave the package at the front desk. Handle with care.";
        checkoutPage.enterOrderComment(testComment);
        Assert.assertEquals(checkoutPage.getEnteredOrderComment(), testComment, "Order comment input did not retain typed text.");

        checkoutPage.clickPlaceOrder();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("payment"), "Failed to proceed to Payment page after entering comment.");
        System.out.println(" Order comment entry and progression to payment page verified.");
    }

    
    
    
    //Proceed to payment without entering an order comment
    @Test(priority = 6, description = "CKTC_006",dataProvider="getvalidData")
    public void verifyProceedToPaymentWithBlankComment(String email, String password) 
    {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();

        checkoutPage.enterOrderComment("");
        checkoutPage.clickPlaceOrder();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("payment"), "Failed to proceed to Payment page when comment area is left blank.");
        System.out.println(" Blank order comment verification passed.");
    }

    
    
    
    
    //  Verify newsletter subscription from the Checkout page footer
    @Test(priority = 7, description = "CKTC_007",dataProvider="getvalidData")
    public void verifyCheckoutFooterSubscription(String email, String password) {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();

        String Semail = "subscriber_" + System.currentTimeMillis() + "@demo.com";
        checkoutPage.subscribeFooterEmail(Semail);
        Assert.assertTrue(checkoutPage.isFooterSubscriptionSuccess(), "Footer subscription success alert not displayed on Checkout page.");
        System.out.println("Footer subscription from Checkout page verified.");
    }

    // Verify navigation links in header on the Checkout page
    @Test(priority = 8, description = "CKTC_008",dataProvider="getvalidData")
    public void verifyHeaderNavigationLinksFromCheckout(String email, String password) {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to land on Checkout page.");
        cartPage.clickHomeNavMenu();
        Assert.assertEquals(getdriver().getCurrentUrl(), "https://www.automationexercise.com/", 
            "Home header link failed to navigate away from Checkout page.");
        getdriver().navigate().back();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to navigate back to Checkout page.");

      
        home.ProductsBtn();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("products"), 
            "Products header link failed to navigate away from Checkout page.");

        getdriver().navigate().back();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to navigate back to Checkout page.");

  
        home.CartBtn();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("view_cart"), 
            "Cart header link failed to navigate away from Checkout page.");

        
        getdriver().navigate().back();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to navigate back to Checkout page.");

        loginPage.logOut();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("login"), 
            "Logout header link failed to log out and redirect away from Checkout page.");

        System.out.println("Header navigation links (Home, Products, Cart, Logout) successfully verified from Checkout page.");
    }

    
    
    
    // Verify bread crumb trail links on the Checkout page
    @Test(priority = 9, description = "CKTC_009",dataProvider="getvalidData")
    public void verifyBreadcrumbTrailNavigation(String email, String password) {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();

        checkoutPage.clickHomeBreadcrumb();
        Assert.assertEquals(getdriver().getCurrentUrl(), "https://www.automationexercise.com/", "Breadcrumb Home link failed to navigate to homepage.");
        System.out.println("Breadcrumb trail navigation verified.");
    }

    
    
    
    
    
//    verify Individual And Overall Checkout Totals
    @Test(priority = 10, description = "CKTC_10",dataProvider="getvalidData")
    public void verifyIndividualAndOverallCheckoutTotals(String email, String password) {
        // Authenticate user
    	performUserLogin(email,password);

    	 addProductAndNavigateToCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");
        Assert.assertTrue(checkoutPage.isOrderReviewTableDisplayed(), "Order Review section is not visible.");

        int itemCount = checkoutPage.getOrderReviewItemCount();
        Assert.assertTrue(itemCount > 0, "No items found in Order Review table.");

        int calculatedGrandTotal = 0;

        //Iterate through each row to verify individual item totals (Price * Quantity = Total)
        for (int i = 1; i <= itemCount; i++) {
            String unitPriceText = checkoutPage.getItemPrice(i);
            String quantityText = checkoutPage.getItemQuantity(i);
            String itemTotalText = checkoutPage.getItemTotal(i);

            int unitPrice = Integer.parseInt(unitPriceText.replaceAll("[^0-9]", ""));
            int quantity = Integer.parseInt(quantityText.trim());
            int displayedItemTotal = Integer.parseInt(itemTotalText.replaceAll("[^0-9]", ""));

            int expectedItemTotal = unitPrice * quantity;

            // Assert row-level math accuracy
            Assert.assertEquals(displayedItemTotal, expectedItemTotal, 
                "Row " + i + " mismatch: Unit Price (" + unitPrice + ") * Quantity (" + quantity + 
                ") does not equal Displayed Item Total (" + displayedItemTotal + ")");

            calculatedGrandTotal += displayedItemTotal;
        }

        //  Verify the overall final grand total against calculated sum of all items
        int displayedGrandTotal = checkoutPage.getFinalTotalAmount();

        Assert.assertEquals(calculatedGrandTotal, displayedGrandTotal, 
            "Grand Total mismatch: Calculated sum of items (" + calculatedGrandTotal + 
            ") does not match displayed Total Amount (" + displayedGrandTotal + ")");

        System.out.println("Individual and Overall Checkout Totals successfully verified! Sum: " 
            + calculatedGrandTotal + " | Displayed Total: " + displayedGrandTotal);
    	
    	
       
    }
    
    
    
    
    // Special Characters and High Length in Order Comment
    @Test(priority = 11, description = "CKTC_011",dataProvider="getvalidData")
    public void verifySpecialCharactersAndLongTextInComment(String email, String password)
		
   {
    	performUserLogin(email,password);
        addProductAndNavigateToCheckout();

        String specialCharComment = "Testing!@#$%^&*()_+-=[]{}|;':\",./<>?~` Special characters & " + "A".repeat(500);
        checkoutPage.enterOrderComment(specialCharComment);
        checkoutPage.clickPlaceOrder();

        Assert.assertTrue(getdriver().getCurrentUrl().contains("payment"), "System crashed or failed to proceed with special characters/long text in comment.");
        System.out.println("Special characters and long comment string verified successfully.");
    }
    
    
    
    
    

//    verify Guest User Checkout Prompt Modal
    
    @Test(priority = 12, description = "CKTC_012")
    public void verifyGuestUserCheckoutPromptModal() {
        // Ensure user is logged out (guest user state)
        if (loginPage.IsloggedIn()) {
            loginPage.logOut();
        }

        // Navigate to Products page and add an item to cart
        home.ProductsBtn();
        productPage.remove_Ad();
        productPage.clickFirstProductDetails();
        productPage.addProductToCart();
        Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
        //  Go to View Cart page
        productPage.clickViewCartFromModal();
        Assert.assertTrue(cartPage.isCartPageVisible(), "Failed to navigate to Cart page.");

        //  Click 'Proceed To Checkout' as a guest user
        cartPage.clickProceedToCheckout();

        // Assert that Checkout Modal prompting Login/Register is displayed
        Assert.assertTrue(cartPage.isCheckoutModalVisible(), 
            "Guest user was not prompted with the Login/Register modal upon clicking Proceed To Checkout.");

        // Assert that guest user CANNOT access checkout page details directly
        Assert.assertFalse(checkoutPage.isCheckoutPageLoaded(), 
            "Guest user was able to access the checkout page without logging in or registering.");

        // Click 'Register / Login' on modal and verify redirection to login page
        cartPage.clickRegisterLoginInModal();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("/login"), 
            "Clicking 'Register / Login' in modal did not navigate to the login/signup page.");

        System.out.println("[PASS] Guest user checkout modal prompt and redirection verified successfully.");
    }
    
    
    
    
    // Direct URL Access to Checkout without Items in Cart
    @Test(priority = 13, description = "CKTC_013",dataProvider="getvalidData")
    public void verifyDirectCheckoutUrlAccessWithoutItems(String email, String password) {
    	performUserLogin(email,password);
   
        // Clear cart or navigate directly when cart is empty
        getdriver().get("https://www.automationexercise.com/checkout");
        checkoutPage.remove_Ad();

        boolean redirectedOrEmpty = getdriver().getCurrentUrl().contains("view_cart") || cartPage.isEmptyCartMessageVisible() || !checkoutPage.isOrderReviewTableDisplayed();
        Assert.assertTrue(redirectedOrEmpty, "Direct access to /checkout with an empty cart was not gracefully handled.");
        System.out.println("Direct URL access to checkout with empty cart verified.");
    }	
    
    
    
    
    
 // Verify updating address fields in account updates Delivery and Billing blocks on Checkout page
    @Test(priority = 14, description = "CKTC_014", dataProvider = "getvalidData")
    public void verifyAccountAddressUpdateReflectsOnCheckout(String email, String password) 
    {
        //  Log in and navigate to Account Settings / Address creation state
        performUserLogin(email, password);

        //  Add product to cart and proceed to Checkout page to check initial address
        addProductAndNavigateToCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to reach Checkout page.");
        
        getdriver().get("https://www.automationexercise.com/account_settings"); 
        Assert.assertFalse(getdriver().getCurrentUrl().contains("https://www.automationexercise.com"),"There is no settings options for the website"); 
        System.out.println("Account address couldnt update successfully reflected on Checkout Delivery and Billing blocks.");
    }
    
	
    
    
    
 // Ensure that going back to the Cart, modifying quantities, and returning to Checkout updates the Order Review section correctly.
    @Test(priority = 15, description = "CKTC_015", dataProvider = "getvalidData")
    public void verifyQuantityModificationInCartUpdatesOrderReview(String email, String password)
    {
    
      
    
       performUserLogin(email, password);
        addProductAndNavigateToCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");

        int initialQuantity = Integer.parseInt(checkoutPage.getItemQuantity(1).trim());
        int addedQuantity = 2; // We will add 2 more items
        int expectedTotalQuantity = initialQuantity + addedQuantity; // Expected: 1 + 2 = 3

        // 2. Navigate to product details and add 2 more items to accumulate
        getdriver().get("https://www.automationexercise.com/product_details/1");
        productPage.remove_Ad();
        
        productPage.setQuantity(String.valueOf(addedQuantity));
        productPage.addProductToCart();
        productPage.clickViewCartFromModal();

        // 3. Proceed back to Checkout
        cartPage.clickProceedToCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to re-enter Checkout page.");

        // 4. Verify updated total quantity equals 3
        int updatedQuantity = Integer.parseInt(checkoutPage.getItemQuantity(1).trim());
        Assert.assertEquals(updatedQuantity, expectedTotalQuantity, 
            "Quantity did not update correctly in Order Review.");
                          
    
    }
	
    
    
    
    
    
    
 // Verify session timeout gracefully restricts order submission and prompts user authentication
    @Test(priority = 16, description = "CKTC_016", dataProvider = "getvalidData")
    public void verifySessionTimeoutRestrictsOrderSubmission(String email, String password) {
        // Log in, populate cart, and proceed to the Checkout page
        performUserLogin(email, password);
        addProductAndNavigateToCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "User failed to land on Checkout page.");

        // Enter an optional order comment
        checkoutPage.enterOrderComment("Session timeout validation order comment.");

        // Simulate session timeout by clearing browser session cookies and refreshing context
        getdriver().manage().deleteAllCookies();
        getdriver().navigate().refresh();

        // Attempt to submit the order post session expiration
        checkoutPage.clickPlaceOrder();

        // Assert that direct order submission is blocked and user is redirected to the authentication page
        boolean isRedirectedToAuth = getdriver().getCurrentUrl().contains("login") || loginPage.IsLoginVisible();
        Assert.assertTrue(isRedirectedToAuth, 
            "Security Failure: Expired session allowed order progression instead of redirecting to login page.");

        System.out.println("Session timeout verification passed: Order submission restricted and authentication prompted successfully.");
    }
    
    
    
    
    
 // Verify system behavior when rapidly double-clicking the "Place Order" button
    @Test(priority = 17, description = "CKTC_017", dataProvider = "getvalidData")
    public void verifyRapidDoubleClickPlaceOrder(String email, String password) {
        performUserLogin(email, password);
        addProductAndNavigateToCheckout();

        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Failed to reach Checkout page.");

        // Action: Perform rapid double-click on "Place Order"
        checkoutPage.doubleClickPlaceOrder();

        // Assertion 1: Ensure user successfully lands on the Payment page without system exception
        Assert.assertTrue(getdriver().getCurrentUrl().contains("payment"), 
            "System failed to navigate to the Payment page following double-click.");

        // Assertion 2: Verify page loaded cleanly and no duplicate processing occurred
        boolean doubleClickHandled = !getdriver().getCurrentUrl().contains("checkout");
        Assert.assertTrue(doubleClickHandled, 
            "System got stuck on Checkout page or created duplicate order requests.");

        System.out.println("Rapid double-clicking on Place Order handled gracefully.");
    }
    
    
    
    
    
    
 // Verify Checkout page behavior when switching browser tabs / multiple browser windows
    @Test(priority = 18, description = "CKTC_018", dataProvider = "getvalidData")
    public void verifyCheckoutPageBehaviorOnTabSwitch(String email, String password) {
     
        performUserLogin(email, password);
        addProductAndNavigateToCheckout();        // (Tab 1)
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Primary Tab: Failed to reach Checkout page.");

        // Store Primary Tab Handle
        String originalTab = getdriver().getWindowHandle();

        //  Open a new tab (Tab 2) and switch control to it
        getdriver().switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        getdriver().get("https://www.automationexercise.com/");
        
        // Assert second tab is active
        Assert.assertNotEquals(getdriver().getWindowHandle(), originalTab, "Failed to switch context to the new tab.");

        // Perform actions in Tab 2 (e.g., Navigate to Cart / Logout to test session sync)
        home.CartBtn();
        Assert.assertTrue(cartPage.isCartPageVisible(), "Secondary Tab: Cart page failed to load.");

        // Switch back to the original Checkout tab (Tab 1)
        getdriver().switchTo().window(originalTab);

        // Verify primary tab integrity and state persistence on focus
        Assert.assertEquals(getdriver().getWindowHandle(), originalTab, "Failed to return context to the primary tab.");
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page URL state lost after switching tabs back.");
        Assert.assertTrue(checkoutPage.isOrderReviewTableDisplayed(), "Order review table missing after returning to primary tab.");

        //  Verify page interactions remain active after tab switch
        String tabTestComment = "Validating order submission after tab switching.";
        checkoutPage.enterOrderComment(tabTestComment);
        Assert.assertEquals(checkoutPage.getEnteredOrderComment(), tabTestComment, "Form input failed after switching back from secondary tab.");

        checkoutPage.clickPlaceOrder();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("payment"), 
            "Failed to proceed to Payment page after returning from secondary tab.");

        System.out.println("Checkout page state, form input, and order progression successfully validated across multi-tab session.");
    }
    
    
    
    
    
    
    
    
 // Verify preservation of entered order comment after navigating back and returning to Checkout
    @Test(priority = 19, description = "CKTC_019", dataProvider = "getvalidData")
    public void verifyPreservationOfOrderCommentOnNavigation(String email, String password) {
        performUserLogin(email, password);
        addProductAndNavigateToCheckout();

        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");

      
        String expectedComment = "Special Delivery Instructions - Test " + System.currentTimeMillis();
        checkoutPage.enterOrderComment(expectedComment);

        // Verify the comment is entered in the text area
        String initialEnteredComment = checkoutPage.getEnteredOrderComment();
        Assert.assertEquals(initialEnteredComment, expectedComment, "Comment was not entered correctly into the textarea.");

        // Navigate away (e.g., navigate back to Cart or previous page)
        getdriver().navigate().back();

        //  Navigate forward back to the Checkout page
        getdriver().navigate().forward();

        // Verify the entered order comment is preserved
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to reload after forward navigation.");
        
        String preservedComment = checkoutPage.getEnteredOrderComment();
        Assert.assertEquals(preservedComment, expectedComment, 
            "Order comment was lost or modified after navigating back and returning to Checkout page.");

        System.out.println("Order comment preservation verified successfully. Comment: " + preservedComment);
    }
    
    
    
    
    
    
    
 // Verify entering an over sized string (5000+ characters) in order comments 
    // does not break page rendering, crash database transaction, or truncate silently without warning
    @Test(priority = 20, description = "CKTC_020", dataProvider = "getvalidData")
    public void verifyOversizedOrderCommentHandling(String email, String password) {
        performUserLogin(email, password);
        addProductAndNavigateToCheckout();

        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");

        // Generate a 5000+ character string
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            builder.append("Testing 5000+ character order comment payload block ");
        }
        String oversizedComment = builder.toString(); // Length > 5000 chars

        // Enter over sized string into order comments area
        checkoutPage.enterOrderComment(oversizedComment);

        // Step 3: Verify page rendering remains intact (no layout breaking or HTTP 500/502 errors)
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Page layout broke or crashed after typing oversized comment.");
        Assert.assertTrue(checkoutPage.isPlaceOrderButtonDisplayed(), "Place Order button became hidden or unrenderable.");

        //  Click 'Place Order' to submit transaction
        checkoutPage.clickPlaceOrder();

        //  Verify database transaction completed without back end error/crash
        boolean isPaymentPageVisible = checkoutPage.isPaymentPageDisplayed() ;
        Assert.assertTrue(isPaymentPageVisible, "Database transaction crashed or submission failed due to oversized string payload.");

        // Verify length validation handling (either accepts full length, alerts user, or truncates intentionally)
        int actualSubmittedLength = checkoutPage.getSubmittedCommentLength();
        System.out.println("Submitted comment character length: " + actualSubmittedLength);

        if (actualSubmittedLength < oversizedComment.length()) {
            // If truncated, verify character limit attribute or visual indicator is configured
            boolean hasMaxlength = checkoutPage.hasCommentTextAreaMaxLength();
            Assert.assertTrue(hasMaxlength, "Comment was truncated silently without a 'maxlength' HTML validation attribute or user warning.");
        }

        System.out.println("Oversized order comment validation test passed successfully.");
    }
    
    
    
    
    
    
    
 // Validate that calculating total amounts for multiple items does not introduce floating-point 
    // precision rounding errors (e.g., displaying Rs. 500.0000000000001 or mismatched totals)
    @Test(priority = 21, description = "CKTC_021", dataProvider = "getvalidData")
    public void verifyFloatingPointPrecisionInTotals(String email, String password) {
        performUserLogin(email, password);
        addProductAndNavigateToCheckout();

        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), "Checkout page failed to load.");

     // Step 1: Verify raw DOM price strings do not contain unwanted floating-point representations (e.g., 500.0000001)
        List<String> rawPriceTexts = checkoutPage.getAllRawPriceTexts();
        for (String priceText : rawPriceTexts) {
            Assert.assertFalse(priceText.matches(".*\\.\\d{3,}.*"), 
                "Floating-point precision defect detected in displayed DOM text: " + priceText);
        }

        // Step 2: Sum exact item totals dynamically as safe long integers
        int itemCount = checkoutPage.getOrderReviewItemCount();
        long calculatedSum = 0;

        for (int i = 1; i <= itemCount; i++) {
            String itemTotalText = checkoutPage.getItemTotal(i).replaceAll("[^0-9]", "");
            if (!itemTotalText.isEmpty()) {
                calculatedSum += Long.parseLong(itemTotalText);
            }
        }

        // Step 3: Extract displayed grand total as integer
        long displayedGrandTotal = (long) checkoutPage.getFinalTotalAmount();

        // Step 4: Validate exact precision match without IEEE 754 floating-point drift
        Assert.assertEquals(calculatedSum, displayedGrandTotal, 
            "Price precision mismatch! Calculated sum (" + calculatedSum + ") does not equal displayed total (" + displayedGrandTotal + ")");

        System.out.println("Whole-number precision validation passed cleanly. Calculated Sum: " + calculatedSum + " | Displayed Total: " + displayedGrandTotal);
    }
    
    
    
    
    
    
    
    
    
}



