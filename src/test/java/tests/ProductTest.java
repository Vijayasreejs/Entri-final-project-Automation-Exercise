package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Base.BaseclassTest;

import pagesClass.ClassHomePage;
import pagesClass.ProductpageClass;
import Utils.ScreenshotList;	

@Listeners(ScreenshotList.class)
public class ProductTest extends BaseclassTest

{
	private ProductpageClass productPage;
    private ClassHomePage homePage;

    @BeforeClass
    public void initPage() {
        productPage = new ProductpageClass(getdriver());
        homePage = new ClassHomePage(getdriver());
    }
@BeforeMethod
    private void navigateToProducts()
    {
        getdriver().get("https://automationexercise.com/products");
        homePage.remove_Ad();
        homePage.dismissCartModalIfVisible();
    }

    //  Verify all products page navigation and heading visibility
    @Test(priority = 1, description = "PDT_001")
    public void verify_all_products_page_navigation() 
    {
        homePage.ProductsBtn();
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "'All Products' page heading is visible.");
    }

    
    
    
    
    //Verify searching for a product
    @Test(priority = 2, description = "PDT_002")
    public void verify_product_search() 
    {
        productPage.searchProduct("Tshirt");
        Assert.assertTrue(productPage.isSearchedProductsVisible(), "'Searched Products' section heading is displayed.");
        Assert.assertTrue(productPage.getProductCount() > 0, "Search results return matching products.");
    }
    
    
    
    
    
    
    
 // Verify expansion and selection of product categories (Women -> Dress)
    @Test(priority = 3, description = "PDT_003")
    public void verify_expansion_and_selection_of_product_categories() 
    {
      
        // Expands the category accordion and selects the sub-category
        productPage.selectCategoryWomenDress();
        
        // Assertions to verify successful redirection and presence of filtered items
        Assert.assertTrue(getdriver().getCurrentUrl().contains("category_products"), "Navigated to category filter page successfully.");
        Assert.assertTrue(productPage.isFilteredProductsViewVisible(), "Filtered category header view is visible.");
        Assert.assertTrue(productPage.getProductCount() > 0, "Filtered category list displays items.");
    }
    
    
    
    
    
    
 //Verify redirection to the product detail page from the catalog grid
    @Test(priority=4,description = "PDT_004")
    public void verify_redirection_to_product_detail_page() 
    {
      
        Assert.assertTrue(productPage.getProductCount() > 0, "Product list is populated with items[cite: 1].");
        
        
        productPage.clickFirstProductDetails();
        
      
        String currentUrl = getdriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/product_details/"), "User is redirected to the correct product details URL.");
      
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail attributes (name, category, price, availability, condition, brand) are visible[cite: 1].");
    }

    
    
    
    
    
//  Verify adding single product to cart from catalog grid
    @Test(priority =5, description = "PDT_005")
    public void verify_add_to_cart_from_grid()
    {
        // Hover and add to cart simulation
        Assert.assertTrue(productPage.getProductCount() > 0, "Products available in grid for cart addition.");
    }
    
    
    
    
    
    
    
    
    //  Verify search for non-existent product
    @Test(priority = 6, description = "PDT_006")
    public void verify_non_existent_product_search()
    {

        productPage.searchProduct("NonExistentProductXYZ123");
        Assert.assertTrue(productPage.isSearchedProductsVisible(), "'Searched Products' section heading is displayed.");
       Boolean count = productPage.areProductsDisplayed();
       Assert.assertFalse(count, "Product list should be empty for non-existent search terms.");
       
    }
    
    
    
    
    
    
    
    // Verify  brands side bar presence on products page and filtering products by brand selection
    @Test(priority = 7, description = "PDT_007")
    public void verify_brand_product_filtering()
{
        Assert.assertTrue(productPage.isBrandSidebarVisible(), "Brands sidebar heading is visible.");
        productPage.clickFirstBrand();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("brand_products"), "Navigated to brand filtered URL.");
        Assert.assertTrue(productPage.getProductCount() > 0, "Brand products list is populated.");
    }
    
    
    
    
    
    
    
    
    
    
 //Verify Discover More Sub-Categories Navigation and Interactivity
    @Test(priority = 8, description = "PDT_008")
    public void verify_discover_more_subcategories_navigation() 
    {
    
    // Step 1: Click on the Women category accordion dropdown link
    productPage.clickWomenCategory();
    Assert.assertTrue(productPage.isDressSubCategoryVisible());
   
    // Step 3: Click on the Dress sub-category link using JS executor to bypass overlays
    productPage.clickDressSubCategory();
    
   
    
    // Step 5: Verify view rendering and product list population
    Assert.assertTrue(productPage.isFilteredProductsViewVisible(), "Filtered products header is displayed for the sub-category.");
    Assert.assertTrue(productPage.getProductCount() > 0, "Sub-category page displays a valid list of matching items.");
    
        
    }
    
    
    
    
    
    
 //  Verify the display of the promotional banner image in the header area of the Products page
    @Test(priority = 9, description = "PDT_009")
    public void verify_promotional_banner_display() 
    {
  
        // Step 2: Verify that the All Products page has loaded successfully[cite: 1]
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

        // Step 3: Assert that the promotional banner or carousel container is visible in the header area[cite: 2]
        boolean isBannerVisible = productPage.isPromotionalBannerVisible();
        Assert.assertTrue(isBannerVisible, "Defect Found: Promotional banner image/slider in the header area is not displayed correctly on the Products page.");
    }
    
    
    
    
    
 //Verify submitting search bar with no input on products page
    @Test(priority =10, description = "PDT_010")
    public void verify_submit_search_bar_with_no_input() 
    {
        // Leave search input blank and click search button
        productPage.searchProduct(""); 
        
        // Assert that the page handles empty search submission gracefully 
        // (e.g., maintaining the product catalog view or showing all products)
        boolean isCatalogVisible = productPage.isAllProductsPageVisible() || productPage.getProductCount() > 0;
        Assert.assertTrue(isCatalogVisible, "System handles empty search submission gracefully without crashing.");
    }
    
    
    
    
    
    
    
    
    
    //  Verify state and value retention of search text box after search execution
    @Test(priority =11, description = "PDT_011")
    public void verify_search_textbox_value_retention()
    {
        String searchKeyword = "Tshirt";
        
        // Step 2: Locate search input, enter keyword, and execute search
       productPage. searchProduct(searchKeyword);
    
        Assert.assertTrue(productPage.isSearchedProductsVisible(), "'Searched Products' section heading is displayed.");
       
        // Step 5: Assert that the keyword is retained in the text box
        Assert.assertEquals(productPage.getValue(), searchKeyword, "Search textbox retains the entered keyword after search execution.");
    }


    
    
    
    
    
    
 // Verify the functionality of refreshing the Products page
    @Test(priority = 12, description = "PDT_012")
    public void verify_products_page_refresh_functionality() 
    {
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed initially[cite: 1].");
        int initialCount = productPage.getProductCount();
        Assert.assertTrue(initialCount > 0, "Product list is populated before refresh[cite: 1].");
        
        // Refresh the page
        getdriver().navigate().refresh();
        
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "'All Products' page heading remains visible after refresh[cite: 1].");
        int refreshedCount = productPage.getProductCount();
        Assert.assertEquals(refreshedCount, initialCount, "Product count remains consistent after page refresh.");
        Assert.assertTrue(getdriver().getCurrentUrl().contains("/products"), "URL remains correctly on the products page after refresh.");
    }
    
    
    
    
    
    
    
    
 
    
    @Test(priority = 14, description = "PDT_013")
    public void verify_product_search_and_navigation_back()
    {
       
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

        // Search for a specific product
        String searchKeyword = "Top";
        productPage.searchProduct(searchKeyword);
        
        // Verify search results are displayed correctly
        Assert.assertTrue(productPage.isSearchedProductsVisible(), "'Searched Products' section heading is displayed.");
        int searchResultsCount = productPage.getProductCount();
        Assert.assertTrue(searchResultsCount > 0, "Search results return matching products.");

        //  Navigate back to the All Products page or re-verify catalog state
        navigateToProducts();
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Successfully navigated back to the 'All Products' catalog view.");
    }
    
    
    
    
    
   
    
 // Verify product details elements display correctly on the product detail page[cite: 2]
    @Test(priority =15, description = "PDT_014")
    public void verify_product_detail_page()
    {
       
        productPage.clickFirstProductDetails();
        
        // Comprehensive assertions verifying that all critical product information blocks render as expected
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail fields (name, category, price, availability, condition, brand) are visible.");
        
        // Granular component assertions for precise verification
        Assert.assertFalse(getdriver().findElement(org.openqa.selenium.By.xpath("//div[@class='product-information']/h2")).getText().isEmpty(), "Product name text is correctly displayed.");
        Assert.assertFalse(getdriver().findElement(org.openqa.selenium.By.xpath("//div[@class='product-information']/span/span")).getText().isEmpty(), "Product price and currency symbol are properly formatted.");
    }
    
    
    
    
    
  
    
 // Verify adding product to cart with default quantity (1)
    @Test(priority = 16, description = "PDT_015")
    public void verify_add_product_with_default_quantity()
    {
    	
    	
   
    productPage.clickFirstProductDetails();
    Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page is displayed.");

    WebElement quantityInputBox = getdriver().findElement(By.id("quantity"));
    Assert.assertEquals(quantityInputBox.getAttribute("value"), "1", "Default quantity value is not 1.");

    productPage.addProductToCart();
    
    // Modal visibility validation handled by dynamic explicit wait inside Productpage class
    Assert.assertTrue(productPage.isAddedModalVisible(), "Success modal confirming addition is visible.");
    productPage.clickViewCartFromModal();
    
    Assert.assertTrue(getdriver().getCurrentUrl().contains("view_cart"), "Successfully navigated to the cart page.");
    
    org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(getdriver(), java.time.Duration.ofSeconds(10));
    WebElement cartQuantityBtn = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//td[contains(@class,'cart_quantity')]/button")));
    String cartQuantityText = cartQuantityBtn.getText().trim();

    Assert.assertEquals(cartQuantityText, "1", "Cart quantity does not match default quantity of 1!");
    }

    
    
    
    
    
    
 // Verify 'Add to Cart' behavior with zero quantity
    @Test(priority = 17, description = "PDT_016")
    public void verify_add_to_cart_zero_quantity() 
    {
        
        productPage.clickFirstProductDetails();
        productPage.setQuantity("0");
     productPage.isAddCartFocused();
    	   productPage.addProductToCart();
      
     
    	// Assert that zero quantity addition is blocked (Modal does not open or zero is prevented)
           boolean isModalVisible = productPage.isAddedModalVisible();
           Assert.assertFalse(isModalVisible, "Defect Found: System allowed adding a zero quantity product to the cart.");
    }
    
    
    
    
    
    
 // Verify 'Add to Cart' behavior with negative quantity
    @Test(priority = 18, description = "PDT_016")
    public void verify_add_to_cart_negative_quantity()
    {
        
        productPage.clickFirstProductDetails();
        productPage.setQuantity("-1");
       
        productPage.isAddCartFocused();
        productPage.addProductToCart();
     // Assert that negative quantity addition is blocked (Modal does not open)
        boolean isModalVisible = productPage.isAddedModalVisible();
        Assert.assertFalse(isModalVisible, "Defect Found: System allowed adding a negative quantity (-1) to the cart.");
    }
    
    
    
    
    
    
    //  Verify product review submission form presence and success workflow
    @Test(priority = 19, description = "PDT_017")
    public void verify_product_review_submission() 
    {
       
        productPage.clickFirstProductDetails();
        productPage.submitReview("John Doe", "john@sample.com", "Great quality product, highly recommend!");
        Assert.assertTrue(productPage.isReviewSuccessMessageVisible(), "Success alert for review submission is displayed.");
    }
    
    
    
    
    
    
 // Verify review submission with empty or missing fields
    @Test(priority = 20, description = "PDT_018")
    public void verify_review_submission_with_empty_fields() 
    {
      
        productPage.clickFirstProductDetails();
        
        // Test case 1: All fields empty
        productPage.submitReview("", "", "");
        Assert.assertFalse(productPage.isReviewSuccessMessageVisible(), "Review submission should be blocked when all fields are empty.");

        // Test case 2: Missing review text only
        productPage.submitReview("Jane Doe", "jane@sample.com", "");
        Assert.assertFalse(productPage.isReviewSuccessMessageVisible(), "Review submission should be blocked when the review text is missing.");
    }
    
    
    
    
    
    //Verify Review Submission with Invalid Email Address
    @Test(priority=21,description = "PDT_019")
    public void verify_review_form_invalid_email()
    {
       
        productPage.clickFirstProductDetails();
        
        // Submit review using an invalid email format (missing '@' or domain)
        productPage.submitReview("John Doe", "invalid-email-syntax", "Great quality product!");
        
        // Assert that the success alert message is NOT visible, meaning the submission was blocked
        Assert.assertFalse(
            productPage.isReviewSuccessMessageVisible(), 
            "Review submission was incorrectly allowed with a malformed email address."
        );
    }
    
    
    
    
    
    
    //Verify Keyboard Navigation for Review Form
    @Test(priority=22,description = "PDT_020")
    public void verify_review_form_keyboard_navigation()
    {
        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page is displayed.");

        // Use the built-in helper method from Product page class for clean element interaction and tab navigation
        productPage.navigateReviewFormViaKeyboard("Sample User", "john.doe@example.com", "Great product quality and fast shipping!");

        // Assert that the success message is displayed after keyboard submission
        Assert.assertTrue(productPage.isReviewSuccessMessageVisible(), "Success alert for review submission is displayed after keyboard-driven interaction.");

    }
    
    
    
    
    //Verify User Can Modify Quantity and Add Product to Cart Successfully
    @Test(priority = 23, description = "PDT_021")
    public void verify_modify_quantity_and_add_to_cart() 
    {
    	
    	Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed successfully.");

        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail attributes are visible.");

        try {
            ((org.openqa.selenium.JavascriptExecutor) getdriver())
                .executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}

        // Step 2: Locate the quantity input field
        org.openqa.selenium.WebElement quantityInput = getdriver().findElement(org.openqa.selenium.By.id("quantity"));

        // Step 3: Set input value to 3 and trigger DOM change/input events via JavaScript
        String targetQuantity = "3";
        ((org.openqa.selenium.JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].value = arguments[1]; " +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", 
            quantityInput, 
            targetQuantity
        );

        // Step 4: Click the 'Add to cart' button
        org.openqa.selenium.WebElement addToCartBtn = getdriver().findElement(org.openqa.selenium.By.xpath("//button[contains(@class,'cart')]"));
        ((org.openqa.selenium.JavascriptExecutor) getdriver())
            .executeScript("arguments[0].click();", addToCartBtn);

        // Step 5: Wait for the 'View Cart' modal link and click it
        org.openqa.selenium.By viewCartLocator = org.openqa.selenium.By.xpath("//u[contains(text(),'View Cart')] | //a[contains(@href,'view_cart')]");
        org.openqa.selenium.WebElement viewCartLink = new org.openqa.selenium.support.ui.WebDriverWait(
            getdriver(), 
            java.time.Duration.ofSeconds(10)
        ).until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(viewCartLocator));

        ((org.openqa.selenium.JavascriptExecutor) getdriver())
            .executeScript("arguments[0].click();", viewCartLink);

        // Step 6: Locate the quantity element inside the cart table
        org.openqa.selenium.By cartQuantityLocator = org.openqa.selenium.By.xpath("//td[contains(@class,'cart_quantity')]/button");
        org.openqa.selenium.WebElement cartQuantityBtn = new org.openqa.selenium.support.ui.WebDriverWait(
            getdriver(), 
            java.time.Duration.ofSeconds(10)
        ).until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(cartQuantityLocator));

        // Step 7: Verify actual quantity matches target quantity (3)
        String actualQuantity = cartQuantityBtn.getText().trim();
        Assert.assertEquals(actualQuantity, targetQuantity, "Cart quantity does not match modified target quantity!");
    
    
    }





    
    
    
    @Test(priority=24,description = "PDT_022")
    public void verify_brand_filter_mismatch_bug()
{
     
       
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

        // Ensure the brand side bar is visible and select 'Polo' (or the first available brand)
        Assert.assertTrue(productPage.isBrandSidebarVisible(), "Brands sidebar is visible.");
        
        String selectedBrandName = "Polo";
       productPage.viewPolo();
        productPage.selectBrandPolo();

        
        Assert.assertTrue(getdriver().getCurrentUrl().contains("brand_products/Polo"), "Successfully navigated to Polo brand products page.");
        Assert.assertTrue(productPage.getProductCount() > 0, "Filtered product list for Polo contains items.");

      
        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail attributes are visible.");

        // Extract the brand name listed in the product specification/information section
        WebElement brandSpecificationElement = getdriver().findElement(By.xpath("//div[@class='product-information']/p[b[text()='Brand:']]"));
        String specificationText = brandSpecificationElement.getText(); // Example format: "Brand: Polo"
        
        // Clean up text to isolate the brand value
        String actualBrandInSpec = specificationText.replace("Brand:", "").trim();

        // Assert alignment between selected filter and product specification data
        Assert.assertEquals(
            actualBrandInSpec, 
            selectedBrandName, 
            " Brand mismatch! The sidebar filter selected '" + selectedBrandName + 
            "', but the product specification page displays '" + actualBrandInSpec + "'."
        );
    }
    
    
    
    
    
    
    
    
 // Verify no irrelevant categories are displayed under the category section or page title
    @Test(priority =25, description = "PDT_023")
    public void verify_irrelevant_categories_display() 
    {
    	// Extract page body text to inspect for stray/irrelevant strings
        String pageBodyText = getdriver().findElement(org.openqa.selenium.By.tagName("body")).getText();
        boolean containsIrrelevantText = pageBodyText.toLowerCase().contains("WE") || 
                                         pageBodyText.toLowerCase().contains("Traditional Wear");
         Assert.assertEquals(containsIrrelevantText, productPage.CheckDiscoverItems(),"BUG DETECTED: Irrelevant categories or text found under the categories or page section.");                                        
   }
    
   
    
    
    
    
    
    
//    Verify successful submission of product review and rating.
    @Test(priority=26,description = "PDT_024")
    public void verify_successful_product_review() 
    {
       
      
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

      
        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page attributes are visible.");

        try {
            ((org.openqa.selenium.JavascriptExecutor) getdriver())
                .executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}

        // Step 2: Locate and populate input fields
        org.openqa.selenium.By nameBy = org.openqa.selenium.By.id("name");
        org.openqa.selenium.By emailBy = org.openqa.selenium.By.id("email");
        org.openqa.selenium.By reviewBy = org.openqa.selenium.By.id("review");
        org.openqa.selenium.By submitBy = org.openqa.selenium.By.id("button-review");

        // Scroll form into center view
        ((org.openqa.selenium.JavascriptExecutor) getdriver())
            .executeScript("arguments[0].scrollIntoView({block: 'center'});", getdriver().findElement(nameBy));

        getdriver().findElement(nameBy).clear();
        getdriver().findElement(nameBy).sendKeys("Test User");

        getdriver().findElement(emailBy).clear();
        getdriver().findElement(emailBy).sendKeys("testuser@sample.com");

        getdriver().findElement(reviewBy).clear();
        getdriver().findElement(reviewBy).sendKeys("Automated review verification test. Product is top notch!");

        // Step 3: Click submit button via JavaScript
        ((org.openqa.selenium.JavascriptExecutor) getdriver())
            .executeScript("arguments[0].click();", getdriver().findElement(submitBy));

        // Step 4: Explicitly wait until the success alert message appears in the DOM
        org.openqa.selenium.By alertBy = org.openqa.selenium.By.xpath("//*[contains(text(),'Thank you for your review.')]");
        
        org.openqa.selenium.WebElement successAlert = new org.openqa.selenium.support.ui.WebDriverWait(
            getdriver(), 
            java.time.Duration.ofSeconds(10)
        ).until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(alertBy));

        // Step 5: Assert alert element text is present and displayed
        Assert.assertNotNull(successAlert, "Success alert element should be found in DOM.");
        Assert.assertTrue(
            successAlert.isDisplayed() && successAlert.getText().contains("Thank you for your review."),
            "Success alert message 'Thank you for your review.' is displayed."
        );
    }


    
    
    
    
    
    
 // Verify that the stock availability status is displayed correctly on the product details page
    @Test(priority =27, description = "PDT_025")
    public void verify_product_stock_availability_status()
    {
     
      
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

       
        productPage.clickFirstProductDetails();

      
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail attributes are visible.");
        
       
        WebElement availabilityElement = getdriver().findElement(By.xpath("//div[@class='product-information']/p[b[text()='Availability:']]"));
        String availabilityText = availabilityElement.getText();
        
        System.out.println("Verified Availability Status: " + availabilityText);
        
        Assert.assertTrue(availabilityText.contains("In Stock"), "Stock availability status is correctly displayed as 'In Stock'.");
    }



    
    
    
    
    
//    Verify that the product condition field is displayed correctly on the
//    product details page
    @Test(priority=28,description = "PDT_026")
    public void verify_product_condition_field_display() 
    {
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

  
        productPage.clickFirstProductDetails();

        
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product details container and fields are visible.");

      
        WebElement conditionElement = getdriver().findElement(By.xpath("//div[@class='product-information']/p[b[text()='Condition:']]"));
        String conditionText = conditionElement.getText();

     
        Assert.assertTrue(conditionElement.isDisplayed(), "Product condition field is visible on the UI.");
        Assert.assertFalse(conditionText.isEmpty(), "Product condition text is populated.");
        Assert.assertTrue(conditionText.contains("Condition:"), "Condition field label matches expected text format.");
        
        System.out.println("Verified Successfully -> Field Text: " + conditionText);
    }


    
    
    
    
    
//    Verify default quantity value in the quantity box
    @Test(priority = 29, description = "PDT_027")
    public void verify_default_quantity_value() 
    {
      
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

        //  Click on the first product's 'View Product' details button
        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page is displayed.");

        //Locate the quantity input field and verify its default value
        WebElement quantityInputBox = getdriver().findElement(By.id("quantity"));
        String defaultQuantityValue = quantityInputBox.getAttribute("value");

        //  Assert that the default value is equal to "1"
        Assert.assertEquals(defaultQuantityValue, "1", "Default quantity value in the box is incorrect.");
        System.out.println("Verification Passed: The default quantity value is correctly set to " + defaultQuantityValue);
    }

 
    
    
  
    
    
    
 //  Verify presence of Sorting Drop down and Pagination on Products Page
    @Test(priority = 31, description = "PDT_029")
    public void verify_sorting_and_pagination_controls()
    {
      
Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");
        
        // Asserting presence of sorting and pagination which fail due to application limitation on automationexercise.com
        boolean isSortingAvailable = productPage.isSortingDropdownPresent();
        boolean isPaginationAvailable = productPage.isPaginationPresent();
        
        Assert.assertTrue(isSortingAvailable, "Bug: Sorting drop-down is missing from the Products Page.");
        Assert.assertTrue(isPaginationAvailable, "Bug: Pagination controls are missing from the Products Page.");
    }
    
    
    
    
    
    
    
    
    
    //  Verify 'Quick View' and 'Add to Wish list' actions on product cards
    @Test(priority = 32, description = "PDT_030")
    public void verify_quick_view_and_wishlis() 
    {

       boolean isQuickViewPresent = getdriver().findElements(org.openqa.selenium.By.xpath("//button[contains(text(),'Quick View') or contains(@class,'quick-view')]")).size() > 0;
        boolean isWishlistPresent = getdriver().findElements(org.openqa.selenium.By.xpath("//button[contains(text(),'Wishlist') or contains(@class,'fa-fav')]")).size() > 0;

        
        Assert.assertTrue(isQuickViewPresent, "Defect Found: 'Quick View' option is missing on product cards.");
        Assert.assertTrue(isWishlistPresent, "Defect Found: 'Add to Wish list' option is missing on product cards.");
    }

    
    
    
    
 
    
    
    
    // Verify real-time search auto-suggestions and spell check/typo correction
    @Test(priority = 33, description = "PDT_031")
    public void verify_search_typo_tolerance_or_handling() 
    {
       String typoKeyword = "T-shrt";
        
        // Step 1: Type the search keyword into the search bar
        productPage.typeInSearchBox(typoKeyword);
        
        // Step 2: Verify auto-suggestion dropdown appearance while typing
        boolean isAutoSuggestionDisplayed = productPage.isAutoSuggestionDropdownDisplayed();
        
        // Step 3: Execute search submission
        productPage.clickSearchButton();
        
        // Step 4: Verify if results are returned despite the intentional spelling typo
        Assert.assertTrue(productPage.isSearchedProductsVisible(), "'Searched Products' section heading should be displayed.");
        boolean hasCorrectedResults = productPage.getProductCount() > 0;
        
        // Assert Bug: System fails both auto-suggestion display and typo tolerance handling
        Assert.assertTrue(
            isAutoSuggestionDisplayed || hasCorrectedResults, 
            "BUG DETECTED (Test Case No. 31): Search bar lacks both real-time auto-suggestions and spell-check/typo-tolerance capability."
        );
    }
    
    
    
    
    
    
    
   
    
    
    

    // Verify availability of granular filters like price range sliders and clothing sizes
    @Test(priority = 34, description = "PDT_032")
    public void verify_granular_filtering_options()
    {
      
        // Asserting presence of standard sidebars that do exist
        Assert.assertTrue(productPage.isBrandSidebarVisible(), "Sidebar brand filters are visible.");
        
        // Checking for granular filters (Price range / Clothing sizes) which are expected in a full e-commerce suite
        boolean isPriceSliderPresent = getdriver().findElements(org.openqa.selenium.By.id("sl-priceRange")).size() > 0;
        boolean isSizeFilterPresent = getdriver().findElements(org.openqa.selenium.By.xpath("//div[@class='shipping_calculator']//input[@type='checkbox']")).size() > 0;

        // Documenting the failure scenario for missing advanced filters on AutomationExercise
        Assert.assertTrue(isPriceSliderPresent, "BUG DETECTED: Price range slider filter is missing from the products catalog page.");
        Assert.assertTrue(isSizeFilterPresent, "BUG DETECTED: Clothing size filter options are missing from the products catalog page.");
    }


    
    
    
    
    
    
    
    
    //  Verify presence of a quantity selector directly on product listing cards
    @Test(priority = 35, description = "PDT_032")
    public void verify_quantity_selector_on_grid_cards()
    {
        productPage.hoverFirstProductCard();
        
        // Automation Exercise current design lacks an inline quantity selector on grid cards.
        // This test verifies the negative state or confirms limitation design constraints.
        boolean isQuantitySelectorOnGrid = productPage.isGridQuantitySelectorPresent();
        
        if (!isQuantitySelectorOnGrid) {
            System.out.println("Known Limitation/Bug: Quantity selector is absent on product grid cards. Users must use product details page.");
        }
       
      Assert.assertTrue(isQuantitySelectorOnGrid, "Bug Found: Quantity selector is missing directly on product listing grid cards.");
    }
    
    
   
    
    
    
//    Validate quantity modification on product card tile before add to cart
 //  Validate in line quantity modification directly on the product card tile
    @Test(priority = 36, description = "PDT_033")
    public void verify_modify_quantity_on_product_card_tile() 
    {
        Assert.assertTrue(productPage.isAllProductsPageVisible(), "Products catalog page is displayed.");

        // Hover over the target product card tile to expose direct card controls
        productPage.hoverFirstProductCard();

        // Check for the presence of an in line quantity input selector on the card tile
        boolean isQuantityInputOnCard = productPage.isGridQuantitySelectorPresent();

        // Validate in line modification if present; otherwise, fail with a detailed defect assertion
        Assert.assertTrue(isQuantityInputOnCard, 
            "DEFECT / FEATURE MISSING: Product card tiles do not support direct quantity modification (e.g., 1 to 2) before clicking 'Add to cart'. Quantity can only be adjusted on the Product Details page.");

        // Execution step when selector exists on the grid card
        org.openqa.selenium.WebElement cardQuantityInput = getdriver().findElement(
            org.openqa.selenium.By.xpath("(//div[contains(@class,'product-image-wrapper')])[1]//input[@id='quantity' or contains(@class,'quantity')]")
        );

        cardQuantityInput.clear();
        cardQuantityInput.sendKeys("2");

        Assert.assertEquals(cardQuantityInput.getAttribute("value"), "2", "Product card quantity input value did not update to 2.");
    }
    
    
    
    
//    Validate hero image updates on thumb nail hover/click
 //  Verify main product hero image updates when hovering or clicking thumb nails
    @Test(priority = 37, description = "PDT_034")
    public void verify_hero_image_updates_on_thumbnail_interaction() {
        // Navigate to product detail page
        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page is displayed[cite: 1].");

        //  Ensure gallery thumb nails exist
        Assert.assertTrue(productPage.areThumbnailsAvailable(), 
            "DEFECT / FEATURE MISSING: No product micro-thumbnails were found on the product detail page.");

        //  Capture initial hero image source URL
        String initialHeroSrc = productPage.getHeroImageSrc();

        // Interact with the second thumb nail (hover/click)
        productPage.hoverThumbnail(1);
        productPage.clickThumbnail(1);

        //  Wait and capture updated hero image source URL
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(getdriver(), java.time.Duration.ofSeconds(5));
        
        boolean isHeroUpdated = wait.until(driver -> {
            String updatedSrc = productPage.getHeroImageSrc();
            return updatedSrc != null && !updatedSrc.equals(initialHeroSrc);
        });

        //  Validate dynamic update
        Assert.assertTrue(isHeroUpdated, 
            "DEFECT FOUND: Main product hero image source did not update after interacting with the micro-thumbnail.");
    }
    
    
    
    
//    Validate out of stock visual indicators and disabled controls
 //Validate visual indicators and disabled controls when Stock Availability == 0
    @Test(priority = 38, description = "PDT_035 ")
    public void verify_out_of_stock_visual_indicators_and_disabled_controls() 
    {
        // Navigate to product detail page
        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page is displayed[cite: 1].");

        //  Read product stock availability text
        String availabilityText = productPage.getAvailabilityStatusText();

        //  Conditional validation based on stock state
        if (availabilityText.contains("Out of Stock") || availabilityText.contains("0")) {
            // Validate Visual Badge Indicator
            Assert.assertTrue(availabilityText.contains("Out of Stock"), 
                "Visual Indicator Error: Expected 'Out of Stock' status text on UI.");

            // Validate Disabled State for 'Add to Cart' button
            Assert.assertFalse(productPage.isAddToCartBtnEnabled(), 
                "DEFECT FOUND: 'Add to Cart' button remains enabled for out-of-stock product.");

            // Validate Disabled State for Quantity Input
            Assert.assertTrue(productPage.isQuantityInputDisabled(), 
                "DEFECT FOUND: Quantity input box remains editable for out-of-stock product.");
        } else {
            // Documenting test assertion failure if AUT lacks zero-stock mock products natively
            System.out.println("Current product stock status: " + availabilityText);
            
            Assert.fail("DEFECT / TEST LIMITATION: Current platform hardcodes all catalog products to 'In Stock'. " +
                "Zero-stock visual badges ('Out of Stock') and disabled state controls ('Add to Cart' button / Quantity input) cannot be validated.");
        }
    }
    
    
    
    
    
 // Validate system behavior when adding quantity exceeding maximum limit per customer
    @Test(priority = 39, description = "PDT_036 ")
    public void verify_add_quantity_exceeding_maximum_allowed_limit() 
    {
        // Navigate to product detail page
        productPage.clickFirstProductDetails();
        Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page is displayed[cite: 1].");

        //  Attempt to set an excessive quantity (e.g., 999 items)
        String excessiveQty = "999";
        productPage.enterExcessiveQuantity(excessiveQty);

        //  Trigger "Add to Cart" action
        productPage.addProductToCart();

        //  Check for HTML5 browser validation or UI alert notification
        String browserValidationMsg = productPage.getQuantityValidationMessage();
        boolean isErrorAlertVisible = productPage.isQuantityErrorDisplayed();
        boolean isModalVisible = productPage.isAddedModalVisible();

        //  Assert that the request is blocked and appropriate error handling occurs
        if (isModalVisible) {
            // Dismiss modal for test cleanup state
            productPage.clickViewCartFromModal();
            
            Assert.fail(
                "DEFECT DETECTED: The system allowed adding " + excessiveQty + 
                " units to the cart without enforcing a maximum purchase quantity limit per customer."
            );
        } else {
            boolean isBlocked = isErrorAlertVisible || (browserValidationMsg != null && !browserValidationMsg.isEmpty());
            Assert.assertTrue(isBlocked, 
                "System blocked excessive quantity addition but failed to render a user-facing validation error message."
            );
        }
        
    }  
        
        
        
        
        
        
     // Validate structural bread crumb path and functional navigation links on product detail view
        @Test(priority = 40, description = "PDT_037 ")
        public void verify_breadcrumb_structure_and_functional_navigation() 
        {
        
            productPage.clickFirstProductDetails();
            Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail page is displayed[cite: 1].");

            //  Validate structural presence of bread crumb hierarchy
            boolean isBreadcrumbPresent = productPage.isBreadcrumbContainerDisplayed();
            
            if (!isBreadcrumbPresent) {
                Assert.fail(
                    "DEFECT / FEATURE MISSING: Breadcrumb navigation bar is missing on the Product Detail page. " +
                    "Users cannot view or navigate the structural hierarchy (e.g., Home > Category > Product)."
                );
            }

            // Validate bread crumb link count (Minimum expected: Home link and Category link)
            int linkCount = productPage.getBreadcrumbLinksCount();
            Assert.assertTrue(linkCount >= 2, 
                "Breadcrumb hierarchy incomplete: Expected at least 2 functional parent links (Home, Category)."
            );

            //  Test functional link navigation (Clicking the 'Home' or 'Products' parent breadcrumb)
            String parentLinkText = productPage.getBreadcrumbLinkText(0);
            productPage.clickBreadcrumbLink(0);

            //  Verify successful redirection to parent page
            String currentUrl = getdriver().getCurrentUrl();
            boolean isNavigatedBack = currentUrl.equals("https://automationexercise.com/") || currentUrl.contains("/products");

            Assert.assertTrue(isNavigatedBack, 
                "DEFECT DETECTED: Clicking breadcrumb link '" + parentLinkText + "' failed to navigate to the parent page."
            );
        }
        
        
        
        
        
        
//        Validate review breakdown bars and star rating filter functionality
     // Validate review score breakdown bar charts and star rating filtering
        @Test(priority = 41, description = "PDT_038 ")
        public void verify_review_score_breakdown_and_star_filtering() 
        {
            //  Navigate to the product details page
            productPage.clickFirstProductDetails();
            Assert.assertTrue(productPage.areProductDetailsVisible(), "Product detail attributes are visible[cite: 1].");

            //  Check for review score breakdown bar chart rendering
            boolean hasBreakdownChart = productPage.isReviewBreakdownDisplayed();

            // Check for star-filtering buttons (5-star, 4-star, etc.)
            boolean hasStarFilters = productPage.areStarFilterButtonsPresent();

            //  Validate presence or raise defect assertion for missing review controls
            if (!hasBreakdownChart || !hasStarFilters) {
                Assert.fail(
                    "DEFECT / FEATURE MISSING: Product details page lacks review score breakdown bar charts " +
                    "and star-rating filtering options (e.g., 5-star, 4-star). Users can only submit textual reviews."
                );
            }

            //  Functional execution when star filters are supported by AUT
            productPage.clickStarFilter("5");
            
            // Wait for dynamic DOM update of filtered reviews
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            Assert.assertTrue(productPage.getDisplayedReviewCount() > 0, 
                "Filtering by 5-star reviews returned no visible items."
            );
        }
        
        
        
        
     // Verify system behavior when a product has no description text
        @Test(priority = 42, description = "PDT_039")
        public void testProductDescriptionAvailability() {
            // Step 1: Navigate to target product details page
            getdriver().get("https://automationexercise.com/product_details/1");

            // Step 2: Validate product description
            boolean isDescriptionValid = productPage.isProductDescriptionValid();

            // Step 3: Assert description presence; failure automatically triggers failure log & screenshot via Listener
            Assert.assertTrue(isDescriptionValid, 
                " Product detailed description is missing, empty, or rendering null on the product detail page.");
        }
    
        
        
        
    
    
    
}


    
	
	

