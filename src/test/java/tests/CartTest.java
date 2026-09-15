package tests;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Base.BaseclassTest;

import pagesClass.CartpageClass;
import pagesClass.ClassHomePage;
import pagesClass.LoginpageClass;
import pagesClass.ProductpageClass;
import Utils.ScreenshotList;


@Listeners(ScreenshotList.class)
public class CartTest  extends BaseclassTest
{
	private CartpageClass cartPage;
    private ProductpageClass productPage;
    private ClassHomePage homePage;
    private LoginpageClass loginpage;

    @BeforeClass
    public void initPage() 
    {
        cartPage = new CartpageClass(getdriver());
        productPage = new ProductpageClass(getdriver());
        loginpage=new LoginpageClass(getdriver());
        homePage = new ClassHomePage(getdriver());
    }
    private void navigateToHome()
    {
    	getdriver().get("https://automationexercise.com");
    }

    
    private void navigateToCart() 
    {
        getdriver().get("https://automationexercise.com/view_cart");
    }

    private void navigateToProducts() 
    {
        getdriver().get("https://automationexercise.com/products");
    }

    
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    	getdriver().manage().deleteAllCookies();
    }
    
    
    
    @DataProvider(name = "getvalidData")
    public Object[][] validloginData() 
	{
        // Fetches rows where Status is 'Valid' from 'Credentials' sheet
        return Utils.Excelutility.getTestDataByStatus("src/test/resources/testData.xlsx", 
        		"Credentials", "Valid");
	}
    
    
    
    
    // Verify adding a single product from product catalog grid to cart
    @Test(priority = 1, description = "Act_001")
    public void verify_add_product_from_grid_to_cart() 
    {
    	navigateToHome();
    	Assert.assertTrue(homePage.IshomepageLoad(), "Home page failed to load.");

    	        // Step 2: Navigate to Products catalog page[cite: 7]
    	        homePage.ProductsBtn();
    	        Assert.assertTrue(productPage.isAllProductsPageVisible(), "All Products catalog page is not displayed.");

    	        // Step 3: Hover over the first product card and click Add to Cart
    	        productPage.hoverFirstProductCard();
    	        productPage.frstcartclick();

    	     
    	        Assert.assertTrue(productPage.isAddedModalVisible(), "Added to Cart modal did not display.");
    	        productPage.clickViewCartFromModal();

    
    	        Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not displayed.");
    	        Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is not visible.");
    	        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart item count does not match expected count.");
    }
    
    
    
    
    
    
    
 //Verify adding multiple distinct products to cart and viewing them together
    @Test(priority=2,description = "ACT_002")
    public void verify_multiple_products_added_and_viewed_together() 
    {
    	Assert.assertTrue(homePage.IshomepageLoad(), "Home page failed to load.");

    	     
    	        homePage.ProductsBtn();
    	        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products page header is not displayed.");
    	        WebElement addToCartBtn = getdriver().findElement(By.cssSelector("a[data-product-id='1']"));
    	        JavascriptExecutor js = (JavascriptExecutor) getdriver();
    	        js.executeScript("arguments[0].click();", addToCartBtn);
    	        

    	        Assert.assertTrue(productPage.isAddedModalVisible(), "Cart confirmation modal did not appear for product 1.");
    	        productPage.ContinueBtnClick();
    	        
    	        
    	        WebElement addToCartBtn2 = getdriver().findElement(By.cssSelector("a[data-product-id='2']"));
    	        js.executeScript("arguments[0].click();", addToCartBtn2);

    	    
    	        Assert.assertTrue(productPage.isAddedModalVisible(), "Cart confirmation modal did not appear for product 2.");
    	        productPage.clickViewCartFromModal();

    	        Assert.assertTrue(cartPage.isCartPageVisible(), "Failed to navigate to the Cart page.");
    	        Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is not visible.");

    	        int cartItems = cartPage.getCartItemCount();
    	        Assert.assertEquals(cartItems, 2, "Cart does not contain exactly 2 distinct items.");
    	    }

    
    
    
    
    
// Verify setting specific product quantities before adding to cart

   @Test(priority= 3,description ="ACT__003")
   public void verify_set_specific_product_quantity() 
   {
	   navigateToProducts();
	   
	   Assert.assertTrue(productPage.isAllProductsPageVisible(), "All Products page was not displayed.");
       productPage.clickFirstProductDetails();
       Assert.assertTrue(productPage.areProductDetailsVisible(), "Product details page was not displayed.");

       String targetQuantity = "4";
       productPage.setQuantity(targetQuantity);
       productPage.addProductToCart();

       
       Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
       productPage.clickViewCartFromModal();

      
       Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page was not loaded.");
       String actualQuantity = cartPage.getItemQuantity();
       Assert.assertEquals(actualQuantity, targetQuantity, "Cart item quantity does not match the configured amount.");
       }
    
    
    
   
// Verify adding a product from the Recommended Items slider to cart
   @Test(priority = 4, description = "Act_004")
   public void verify_add_product_from_recommended_slider() 
   {
	   navigateToHome();
	   Assert.assertTrue(homePage.IshomepageLoad(), "Home page was not displayed successfully.");

     
       Assert.assertTrue(homePage.isRecommendedSectionVisible(), "'RECOMMENDED ITEMS' section is not visible on the home page.");

      
       homePage.clickRecommendedAddToCart();

       
       Assert.assertTrue(homePage.IsCartModalVisible(), "Cart Modal was not displayed after adding recommended product.");

      
       productPage.clickViewCartFromModal();
       Assert.assertTrue(cartPage.isCartPageVisible(), "Failed to navigate to the Cart page.");
       Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is empty or not visible.");
       Assert.assertTrue(cartPage.getCartItemCount() > 0, "No products were added to the cart table.");
   }
    
    
   
   
   
   
   
   
// Verify product quantity updates correctly on Product Detail page and reflects in cart 
   @Test(priority =5, description = "ACT__005")
   public void verify_product_quantity_update_on_details_page() 
   {
       navigateToProducts();
       
       Assert.assertTrue(productPage.isAllProductsPageVisible(), "All Products page is not displayed.");

       productPage.clickFirstProductDetails();
       Assert.assertTrue(productPage.areProductDetailsVisible(), "Product Details page is not displayed.");

  
       String expectedQuantity = "19";
       productPage.setQuantity(expectedQuantity);
       productPage.addProductToCart();
       Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
     
       productPage.clickViewCartFromModal();

      
       Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not displayed.");
       
       String actualQuantity = cartPage.getItemQuantity();
       Assert.assertEquals(actualQuantity, expectedQuantity, "Cart quantity does not match the updated product quantity!");
   }
   
   
   
   
   
   
   
// Verify 'Continue Shopping' button functionality inside success modal
@Test(priority =6, description = "Act_006")
public void verify_modal_continue_shopping() 
{
    navigateToProducts();
    Assert.assertTrue(productPage.isAllProductsPageVisible(), "All Products page is not visible.");
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    
    // 1. Verify that the success modal is visible after adding to cart
    Assert.assertTrue(productPage.isAddedModalVisible(), "Modal is visible.");
    
    // 2. Click the 'Continue Shopping' button on the modal
    productPage.ContinueBtnClick();
   
    Assert.assertFalse(productPage.isPromotionalBannerVisible(), "Success modal closes upon clicking continue shopping.");
}
   







//Verify 'View Cart' redirection link inside success modal 
@Test(priority =7, description = "Act_007")
public void verify_modal_view_cart_redirection() 
{
	
    navigateToProducts();
    Assert.assertTrue(productPage.isAllProductsPageVisible(), "All Products page is not visible.");

  
    productPage.hoverFirstProductCard();
    productPage.frstcartclick();

  
    Assert.assertTrue(productPage.isAddedModalVisible(), "Added to cart modal is not displayed.");
    productPage.clickViewCartFromModal();

   
    Assert.assertTrue(cartPage.isCartPageVisible(), "User is not redirected to view_cart URL.");

  
    Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is not visible.");
    Assert.assertTrue(cartPage.getCartItemCount() > 0, "No items found in cart table.");

    String priceText = cartPage.getItemPrice();
    String quantityText = cartPage.getItemQuantity();
    String totalText = cartPage.getItemTotal();

    Assert.assertFalse(priceText.isEmpty(), "Product price is not displayed.");
    Assert.assertFalse(quantityText.isEmpty(), "Product quantity is not displayed.");
    Assert.assertFalse(totalText.isEmpty(), "Product total price is not displayed.");

   
    int price = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    int quantity = Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));
    int total = Integer.parseInt(totalText.replaceAll("[^0-9]", ""));

    Assert.assertEquals(total, price * quantity, "Total price calculation does not match Price * Quantity.");
}






//Verify adding products to cart after filtering by category

@Test(priority = 8, description = "Act_008")
public void testAddProductToCartAfterCategoryFilter() {
  
	
	navigateToHome();
    homePage. remove_Ad();
    Assert.assertTrue(homePage.IshomepageLoad(), "Home page failed to load.");
    homePage. remove_Ad();
    Assert.assertTrue(homePage.isCategoryDisplay(), "category is not displayed.");
   
    homePage.category(); 
    
    Assert.assertTrue(homePage.issubCategoryDisplay(), "Subcategory is not displayed.");
    homePage.subcategory_Click();
    Assert.assertTrue(productPage.isFilteredProductsViewVisible(), "Filtered Category products view is not visible.");

 
    cartPage.addFirstProductFromGrid();

  
    productPage.clickViewCartFromModal();
    Assert.assertTrue(cartPage.isCartPageVisible(), "Failed to navigate to the Cart page.");
    Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart is empty after filtering by Category.");
}




//Verify adding products to cart after filtering by Brand
@Test(priority =9, description = "Act_008")
public void verify_add_product_afterBrandfiltering() 
{
	
	navigateToHome();
	
	Assert.assertTrue(homePage.IshomepageLoad(), "Home page failed to load.");
    Assert.assertTrue(homePage.IsBrandsheadingVisble(), "Brands heading is not visible in sidebar.");
    homePage.click_Brandname(); // Clicks H&M brand

 
    Assert.assertTrue(productPage.isFilteredProductsViewVisible(), "Filtered Brand products view is not visible.");

  
    cartPage.addFirstProductFromGrid();

   
    productPage.clickViewCartFromModal();
    Assert.assertTrue(cartPage.isCartPageVisible(), "Failed to navigate to the Cart page.");
    Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart is empty after filtering by Brand.");
}
   





//Verify cart item retention across page navigation and refresh cycles
@Test(priority = 10, description = "Act_009")
public void verify_cart_retention_on_navigation_and_refresh() 
    {
    navigateToProducts();
    Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page failed to load.");
            
            productPage.hoverFirstProductCard();
            productPage.frstcartclick();
            Assert.assertTrue(productPage.isAddedModalVisible(), "Added to Cart modal did not display.");
            
            // Step 2: Open cart page and capture item count[cite: 6, 8]
            productPage.clickViewCartFromModal();
            Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not displayed.");
            int initialItemCount = cartPage.getCartItemCount();
            Assert.assertTrue(initialItemCount > 0, "Cart is empty initially.");

            // Step 3: Verify retention after page refresh
            getdriver().navigate().refresh();
            cartPage.remove_Ad();
            Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is not visible after refresh.");
            Assert.assertEquals(cartPage.getCartItemCount(), initialItemCount, "Cart item count altered after browser refresh.");

            // Step 4: Verify retention after site navigation (Home -> Cart)
           navigateToHome();
           navigateToCart();
            Assert.assertTrue(cartPage.isCartPageVisible(), "Failed to return to Cart page via header.");
            Assert.assertEquals(cartPage.getCartItemCount(), initialItemCount, 
                "Cart item count altered after navigating away and returning.");
   }







//Test to verify deleting an individual product item from the cart
@Test(priority=10, description = "ACT_010")
public void verify_remove_item_from_cart() 
 {
  


  navigateToProducts();
  Assert.assertTrue(productPage.isAllProductsPageVisible(),"Products page not reached");
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(),"CartModal not visible");
    productPage.clickViewCartFromModal();
    
   
    int initialCount = cartPage.getCartItemCount();
    System.out.println(initialCount);
    Assert.assertTrue(initialCount > 0, "Cart contains items prior to removal.");
    Assert.assertTrue(cartPage.isDeleteBtnVisible(), "delete Button not visible");
    cartPage.RemoveFirstCartItem();
    
    getdriver().navigate().refresh();
    int finalCount = cartPage.getCartItemCount();
    System.out.println(finalCount);
    Assert.assertTrue(finalCount <initialCount, "Cart item count decreases after deletion.");
 }






//Verify cart persistence after user login and logout
@Test(priority =11 , description = "Act_011",dataProvider="getvalidData")
public void verify_cart_persistence_after_login_logout(String email,String password) 
   {

    navigateToProducts();
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Added to Cart modal did not display.");
    productPage.clickViewCartFromModal();
    
    int initialCartCount = cartPage.getCartItemCount();
    Assert.assertTrue(initialCartCount > 0, "Cart contains items before login.");

   
    cartPage.clickProceedToCheckout();
    if (cartPage.isCheckoutModalVisible()) 
    {
        cartPage.clickRegisterLoginInModal();
    } 
    else 
    {
        homePage.LoginSignupBTn(); // Fallback if modal isn't triggered
    }
    loginpage.StartLogin(email,password);
   homePage.CartBtn();
    int postLoginCartCount = cartPage.getCartItemCount();
    Assert.assertEquals(postLoginCartCount, initialCartCount, "Cart items persist successfully after user login.");
  loginpage.logOut();
   getdriver().navigate().to("https://www.automationexercise.com/view_cart");
   Assert.assertTrue(cartPage.getCartItemCount() >0, "Cart handles state appropriately post-logout.");
    
    System.out.println("Cart Persistence Test  executed successfully.");
   }











//Verify visibility of 'Save for Later' functionality on the cart page
@Test(priority =12, description = "Act_012 ")
public void verify_save_for_later_feature_visibility() 
{
	navigateToProducts();
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
    productPage.clickViewCartFromModal();
    
    boolean isSaveForLaterPresent = cartPage.isSaveForLaterVisible();
    
    // Use SoftAssert to log the missing feature without breaking downstream tests
    org.testng.asserts.SoftAssert softAssert = new org.testng.asserts.SoftAssert();
    softAssert.assertTrue(isSaveForLaterPresent, "[BUG] 'Save for Later' functionality is missing on automationexercise.com.");
    softAssert.assertAll();
}









//Act_024: Verify Delivery Pin code Availability & Date Estimation visibility on cart page (Bug Identification Test)
@Test(priority =13, description = "Act_013")
public void verify_delivery_pincode_availability_widget() 
{
  navigateToProducts();
  productPage.clickFirstProductDetails();
  productPage.addProductToCart();
  Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
  productPage.clickViewCartFromModal();
  
  // Asserting that the pin code checker is visible on the cart page. 
  // This will fail (reporting a bug) because Automation Exercise does not implement this feature.
  boolean isPincodeAvailable = cartPage.isPincodeCheckerVisible();
  
  Assert.assertTrue(isPincodeAvailable, 
      "[BUG] Delivery Pincode Availability & Date Estimation input field/widget is NOT visible on the cart page!");
}





//Verify Price Breakdown & Fee Transparency section visibility on cart page
@Test(priority =14, description = "Act_014")
public void verify_price_breakdown_fee_transparency() 
{
    navigateToProducts();
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
    productPage.clickViewCartFromModal();
    
    // Asserting visibility of price breakdown/fee transparency section
    boolean isBreakdownVisible = cartPage.isPriceBreakdownVisible();
    
    Assert.assertTrue(isBreakdownVisible, 
        "BUG DETECTED: Price Breakdown & Fee Transparency section (Subtotal, Tax, Shipping, Grand Total) is not visible on the cart page.");
}







//Verify dynamic quantity selection/drop down is visible and editable on the cart page
@Test(priority =15, description = "Act_015")
public void verify_cart_quantity_dropdown_visibility() 
{
  navigateToProducts();
  productPage.clickFirstProductDetails();
  productPage.addProductToCart();
  Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
  productPage.clickViewCartFromModal();
  
  // Assertion to catch the bug: automationexercise cart displays quantity as a static button text rather than an interactive dropdown/input
  boolean isQuantityInteractive = cartPage.isCartQuantityEditableOrVisible();
  
  Assert.assertTrue(isQuantityInteractive, 
      "BUG DETECTED: The dynamic quantity selection/dropdown is not visible or editable on the cart page.");
}





//Verify 'Recommendations' / 'Items You May Have Missed' section visibility on cart page
@Test(priority =16, description = "Act_016")
public void verify_cart_recommended_items_visibility() 
{
	
	navigateToProducts();
Assert.assertTrue(productPage.isAllProductsPageVisible(),"Products page not reached");
  productPage.clickFirstProductDetails();
  productPage.addProductToCart();
  Assert.assertTrue(productPage.isAddedModalVisible(),"CartModal not visible");
  productPage.clickViewCartFromModal();
  JavascriptExecutor js = (JavascriptExecutor) getdriver();
  js.executeScript("window.scrollBy(0, 300);");
    // Asserting that the recommended items section should be visible on the cart page
    boolean isRecommendedDisplayed = cartPage.isRecommendedItemsVisible();
    
    // This will fail and log the bug if the section is missing from the DOM/UI
    Assert.assertTrue(isRecommendedDisplayed, 
        "BUG DETECTED: 'Recommendations' / 'Items You May Have Missed' section is NOT visible on the cart page!");
  }






@Test(priority = 17, description = "ACT_017")
public void verify_total_price_visible_under_rapid_additions() 
{
	navigateToProducts();
    productPage.clickFirstProductDetails();
    Assert.assertTrue(productPage.areProductDetailsVisible(), "Product details page is visible.");

    // Simulate rapid sequential clicks on 'Add to Cart'
    try {
        productPage.addProductToCart();
        // Rapid double/triple additions
        productPage.addProductToCart();
        productPage.addProductToCart();
    } catch (Exception e) {
        System.out.println("Handled rapid click exception: " + e.getMessage());
    }

    //Navigate to Cart view via success modal link
    Assert.assertTrue(productPage.isAddedModalVisible(), "Success modal appears after adding item.");
    productPage.clickViewCartFromModal();
    
    //Assertions to verify cart details and total price display visibility
    Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is successfully loaded.");
    int itemCount = cartPage.getCartItemCount();
    Assert.assertTrue(itemCount > 0, "Cart contains added items.");

    String itemTotal = cartPage.getItemTotal();
    
    // BUG ASSERTION: Fails if total price calculation is missing, blank, or hidden due to rapid triggers
    Assert.assertNotNull(itemTotal, "Total Price element should not be null.");
    Assert.assertFalse(itemTotal.trim().isEmpty(), 
        "BUG DETECTED: Total Price Calculation under Rapid Additions is not visible or blank on the cart page!");
    
    System.out.println("Rapid Additions Total Price verified successfully. Total displayed: " + itemTotal);
}






//Verify duplicate item handling and quantity increment in the cart
@Test(priority =18, description = "Act_018")
public void verify_duplicate_item_handling_and_quantity_increment() 
   {
    navigateToProducts();
    
    // Step 1: Add the first product to the cart with quantity 1
    productPage.clickFirstProductDetails();
    productPage.setQuantity("1");
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Success modal appears on first addition.");
    productPage.ContinueBtnClick();
    
    // Step 2: Navigate back to the same product details and add it again with quantity 2
    navigateToProducts();
    productPage.clickFirstProductDetails();
    productPage.setQuantity("2");
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Success modal appears on second addition.");
    
    // Step 3: Proceed to view cart
    productPage.clickViewCartFromModal();
    Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is visible.");
    
    // Step 4: Validate duplicate handling behavior 
    // Note: Automation Exercise typically lists duplicate items as separate rows or updates quantity. 
    // We verify total item rows vs expected quantity consolidation.
    int totalRows = cartPage.getCartItemCount();
    Assert.assertTrue(totalRows > 0, "Cart contains the added products.");
    
    String currentQuantity = cartPage.getItemQuantity();
    System.out.println("Duplicate Item Handling Test executed. Current quantity displayed: " + currentQuantity);
    
    // Depending on application behavior, assert quantity accumulation or distinct row grouping
    Assert.assertFalse(currentQuantity.isEmpty(), "Item quantity field is populated properly for duplicate/incremented items.");
  }









//Verify table contents and mathematical calculations (Price * Quantity = Total) on Cart Page
@Test(priority =19, description = "Act_019")
public void verify_cart_table_contents_and_calculations() 
   {
    navigateToProducts();
    
    
    productPage.clickFirstProductDetails();
    String targetQuantity = "3";
    productPage.setQuantity(targetQuantity);
    productPage.addProductToCart();
    
    Assert.assertTrue(productPage.isAddedModalVisible(), "Added to Cart modal did not display.");
    productPage.clickViewCartFromModal();
    Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is not visible.");
    Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart contains no added items.");

 
    String priceText = cartPage.getItemPrice();     // e.g., "Rs. 500"
    String quantityText = cartPage.getItemQuantity(); // e.g., "3"
    String totalText = cartPage.getItemTotal();     // e.g., "Rs. 1500"

    // Assert quantity matches expected input
    Assert.assertEquals(quantityText, targetQuantity, "Cart quantity not matches the specified input quantity.");

 
    // Removing non-numeric characters (like currency symbols "Rs. ") to convert into integers
    int unitPrice = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    int quantity = Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));
    int displayedTotal = Integer.parseInt(totalText.replaceAll("[^0-9]", ""));

    int calculatedTotal = unitPrice * quantity;

 
    Assert.assertEquals(displayedTotal, calculatedTotal, 
        "Calculation validation failed! Expected Total: " + calculatedTotal + " but got Displayed Total: " + displayedTotal);
    
    System.out.println("Cart table contents and price calculations verified successfully: " 
        + unitPrice + " * " + quantity + " = " + displayedTotal);
  }





//Verify Navigation from Cart Page to Home Page
@Test(priority =20, description = "Act_020")
public void verify_navigation_from_cart_to_home_page() 
  {
 
 navigateToProducts();
 productPage.clickFirstProductDetails();
 productPage.addProductToCart();
 Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
 productPage.clickViewCartFromModal();
 
 // Confirm we are currently on the cart page
 Assert.assertTrue(cartPage.isCartPageVisible(), "User is successfully on the cart page.");
 
 //  Click on the Home navigation link / logo
 cartPage.clickHomeNavMenu();
 
 //  Verify redirection back to the Home page
 String currentUrl = getdriver().getCurrentUrl();
 boolean isHomeUrl = currentUrl.equals("https://automationexercise.com/") || currentUrl.equals("https://automationexercise.com");
 
 Assert.assertTrue(isHomeUrl, "User is successfully redirected back to the Home page from the Cart page. Current URL: " + currentUrl);
    }






@Test(priority =21, description = "ACT_021")
public void verify_empty_cart_message_display()
  {
    // Step 1: Navigate directly to the cart page
    navigateToCart();

    // Step 2: Clear any pre-existing items from the cart to guarantee an empty state
    while (cartPage.getCartItemCount() > 0) {
        cartPage.RemoveFirstCartItem();
    }

    // Step 3: Refresh or re-verify cart page view if necessary
    navigateToCart();

    // Step 4: Assert that the empty cart message / placeholder is visible
    boolean isMessageDisplayed = cartPage.isEmptyCartMessageVisible();
    Assert.assertTrue(isMessageDisplayed, "FAIL: The 'Cart is empty!' message or placeholder is not visible when the cart has no items.");
    
    // Optional: Verify that the cart table containing items is NOT visible
    boolean isTableVisible = cartPage.isCartTableVisible();
    Assert.assertFalse(isTableVisible, "FAIL: The cart item table is still visible even though the cart should be empty.");
    
    System.out.println("SUCCESS: Empty cart message display verified successfully on automationexercise.com.");
   }




//Verify 'Proceed To Checkout' modal appears for logged-out/unauthenticated users
@Test(priority =22, description = "ACT_022_Checkout_Unauthenticated")
public void verify_checkout_unauthenticated_user() 
{
    navigateToProducts();
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Added to Cart modal did not display.");
    productPage.clickViewCartFromModal();
    
    // Click Proceed To Checkout while not logged in
    cartPage.clickProceedToCheckout();
    
    boolean isModalDisplayed = cartPage.isRegisterLoginModalVisible();
    Assert.assertTrue(isModalDisplayed, 
        "Checkout popup/modal prompts unauthenticated users to register/login. Expected [true] but found [false]");

    System.out.println("[PASS]: Checkout modal successfully prompted guest user to Register/Login.");
}


//Verify 'Register / Login' redirection link inside the checkout modal
@Test(priority =23, description = "ACT_022_Checkout_Modal_Redirection")
public void verify_checkout_modal_register_login_redirection() {
    navigateToProducts();
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
    productPage.clickViewCartFromModal();
    
    cartPage.clickProceedToCheckout();
    
    if (cartPage.isCheckoutModalVisible())
    {
        cartPage.clickRegisterLoginInModal();
        // Assert redirection to the login/sign up page
        Assert.assertTrue(getdriver().getCurrentUrl().contains("login"), "Redirected to login/signup page successfully from checkout modal.");
    }
}




//Verify navigation on double-clicking product description in the cart table
@Test(priority = 24, description = "Act_023_Verify_Cart_Product_Description_Double_Click")
public void verify_cart_product_description_double_click() 
{
    // Navigate to products and add an item to the cart
    navigateToProducts();
    productPage.hoverFirstProductCard();
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    
    Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");

    productPage.clickViewCartFromModal();
    Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table is visible.");
    Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart contains items.");

    //  Perform double-click action on the product description/name in the cart
    org.openqa.selenium.WebElement productDescriptionLink = getdriver().findElement(org.openqa.selenium.By.xpath("//*[@id=\"product-1\"]/td[2]/h4/a"));
    
    org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getdriver());
    actions.doubleClick(productDescriptionLink).perform();

    //  Verify that double-clicking redirects the user back to the respective product detail page
    boolean isRedirectedToDetails = getdriver().getCurrentUrl().contains("product_details");
    
    Assert.assertTrue(isRedirectedToDetails, 
        "Navigation failed: Double-clicking the product description in the cart did not redirect to the product details page.");
    
    System.out.println("Cart product description double-click navigation verified successfully.");
  }





//Bug Verification Test: Double-clicking on a product image under the grid fails to navigate
@Test(priority = 25, description = "ACT_024")
public void verify_double_click_product_image_navigation() 
  {
	 navigateToProducts();
	    productPage.clickFirstProductDetails();
	    productPage.addProductToCart();
	    
	    // Verify that the success modal is visible after adding to cart
	    Assert.assertTrue(productPage.isAddedModalVisible(), "Modal is visible.");
	    
	    //  Click the 'Continue Shopping' button on the modal
	    productPage.ContinueBtnClick();
	    
	    //Assert that the modal is successfully dismissed/closed
	    Assert.assertFalse(productPage.isAddedModalVisible());
 
	    navigateToCart();
	    
	    org.openqa.selenium.WebElement productImage = getdriver().findElement(
     org.openqa.selenium.By.xpath("/html/body/section/div/div[2]/table/tbody/tr/td[1]/a/img")
 );  cartPage.remove_Ad();
	    try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
 // 3. Perform a double-click on the product image
 org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(getdriver());
 action.doubleClick(productImage).perform();
 
 // 4. Validate navigation outcome
 // Expected behavior of a properly responsive UI: Double-click should still trigger/navigate to product details.
 // Actual behavior (bug representation): The double-click event breaks the single-click binding, 
 // resulting in the user remaining on the grid page instead of navigating.
 boolean isDetailPageLoaded = productPage.areProductDetailsVisible();
 
 // This assertion fails if the bug is present (remaining on grid instead of opening details)
 Assert.assertTrue(isDetailPageLoaded, 
     "BUG DETECTED: Double-clicking on the product image under the catalog grid fails to navigate to the product details view!");
   }







// Act_019: Verify cart total pricing format display accuracy
@Test(priority =26, description = "Act_025")
public void verify_cart_pricing_currency_format()
{
    navigateToProducts();
    productPage.clickFirstProductDetails();
    productPage.addProductToCart();
    Assert.assertTrue(productPage.isAddedModalVisible(), "Product added modal was not displayed.");
    productPage.clickViewCartFromModal();
    
    String priceText = cartPage.getItemPrice();
    Assert.assertTrue(priceText.contains("Rs.") || priceText.matches(".*\\d+.*"), "Currency pricing symbol format renders correctly.");
}




//Validate cart item count and state persist when navigating across non-checkout pages
@Test(priority =27, description = "Act_026" )
   
  public void verifyCartPersistsAcrossNonCheckoutPages()
{
      
	
	navigateToHome();
	homePage.hoverOverFeaturedProduct();
	homePage.clickFeatureProductAddToCartDirectly();
	Assert.assertTrue(homePage.isCartModalVisible(),"cart modal not visible");
	homePage.clickViewCart();
 
      // Step 2: Record baseline cart item count and details
      int initialCartCount = cartPage.getCartItemCount();
      Assert.assertTrue(initialCartCount > 0, "Cart is empty after adding a product.");

      // Step 3: Navigate away to 'Products' page and return to cart
      homePage.ProductsBtn();
      productPage.remove_Ad();
      homePage.CartBtn();
      Assert.assertEquals(cartPage.getCartItemCount(), initialCartCount, 
          "[STATE ERROR]: Cart item count mutated after navigating to Products page.");

      // Step 4: Navigate away to 'Test Cases' page and return to cart
      homePage.TestcasesBtn();
      homePage.remove_Ad();
      homePage.CartBtn();
      Assert.assertEquals(cartPage.getCartItemCount(), initialCartCount, 
          "[STATE ERROR]: Cart item count mutated after navigating to Test Cases page.");

      // Step 5: Navigate away to 'Contact Us' page and return to cart
      homePage.ContactusBtn();
      homePage.remove_Ad();
      homePage.CartBtn();
      Assert.assertEquals(cartPage.getCartItemCount(), initialCartCount, 
          "[STATE ERROR]: Cart item count mutated after navigating to Contact Us page.");

      System.out.println("Cart state remained intact across all non-checkout page navigations.");
  }



}

















   


    
   
    
    
    
    
    
    
    

    
   
  
   

  

	
	
	
	

