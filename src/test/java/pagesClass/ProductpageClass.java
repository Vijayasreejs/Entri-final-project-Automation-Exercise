package pagesClass;

import java.time.Duration;

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

public class ProductpageClass

{

	private WebDriver driver;
    private ActionUtil actionUtil;
    private WebDriverWait wait;
	
 // --- Headers & Structural Elements ---
    @FindBy(xpath = "//h2[contains(@class,'title') and contains(text(),'All Products')]")
    private WebElement allProductsHeader;

    @FindBy(xpath = "//h2[contains(@class,'title') and contains(text(),'Searched Products')]")
    private WebElement searchedProductsHeader;

    @FindBy(xpath = "//h2[text()='Category']")
    private WebElement filteredProductsHeader;

    @FindBy(xpath = "//h2[contains(text(),'Brands')]")
    private WebElement brandSidebarHeader;

    @FindBy(xpath = "//div[contains(@class,'carousel') or contains(@class,'banner') or @id='slider']")
    private WebElement promotionalBanner;

    // --- Search Bar ---
    @FindBy(id = "search_product")
    private WebElement searchInput;

    @FindBy(id = "submit_search")
    private WebElement searchBtn;
    
    @FindBy (xpath="//*[@id=\"aswift_1_host\"]/div/div/div[6]/span[1]")
    private WebElement Discoveritem;
    
   
    private final By autoSuggestionDropdown =By.xpath("//ul[contains(@class,'typeahead') or contains(@class,'autocomplete-suggestions')]");
    
    
    
    // --- Product Catalog Grid ---
    @FindBy(xpath = "//div[contains(@class,'product-image-wrapper')]")
    private java.util.List<WebElement> productCards;

    @FindBy(xpath = "//div[contains(@class,'product-image-wrapper')][1]")
    private WebElement firstProductCard;

    @FindBy(xpath = "/html/body/section[2]/div/div/div[2]/div/div[3]/div")
    private WebElement SecProductCard;
    
    @FindBy(xpath = "(//a[contains(@href,'/product_details/')])[1]")
    private WebElement firstProductViewBtn;

    // --- Categories Accordion ---
    @FindBy(xpath = "//a[@href='#Women' or contains(text(),'Women')]")
    private WebElement categoryWomenAccordion;

    @FindBy(xpath = "//div[@id='Women']//a[contains(text(),'Dress')]")
    private WebElement subCategoryWomenDress;

    // --- Brands Side bar ---
    @FindBy(xpath = "(//div[contains(@class,'brands-name')]//a)[1]")
    private WebElement firstBrandLink;

    @FindBy(xpath = "//a[contains(@href,'/brand_products/Polo')]")
    private WebElement brandPoloLink;

    // --- Product Detail Information ---
    @FindBy(xpath = "//div[@class='product-information']")
    private WebElement productDetailsContainer;

    @FindBy(xpath = "//div[@class='product-information']/h2")
    private WebElement productName;

    @FindBy(xpath = "//div[@class='product-information']/span/span")
    private WebElement productPrice;

    @FindBy(xpath = "//div[@class='product-information']/p[b[text()='Availability:']]")
    private WebElement availabilityStatus;

    @FindBy(xpath = "//div[@class='product-information']/p[b[text()='Condition:']]")
    private WebElement conditionStatus;

    @FindBy(xpath = "//div[@class='product-information']/p[b[text()='Brand:']]")
    private WebElement brandSpecification;

    @FindBy(id = "quantity")
    private WebElement quantityInput;

    @FindBy(xpath = "//button[contains(@class,'cart')]")
    private WebElement addToCartBtn;
    
    
    @FindBy(xpath="//button[@class='btn btn-default cart']")
    private WebElement cartButton;
    		
    @FindBy(xpath = "//a[@data-product-id='1']")
    private WebElement firstCart;
   
    
    // --- Cart Modal ---
    @FindBy(id = "cartModal")
    private WebElement addedCartModal;
    
    @FindBy(xpath="//button[text()='Continue Shopping']")
    private WebElement ContinueBtn;
    
//    private By cartModalContent = By.xpath("//div[@id='cartModal']//div[contains(@class,'modal-content')]");
   

    @FindBy(xpath = "//u[contains(text(),'View Cart')] | //a[contains(@href,'/view_cart')]")
    private WebElement viewCartModalLink;

    // --- Review Form ---
    @FindBy(id = "name")
    private WebElement reviewNameInput;

    @FindBy(id = "email")
    private WebElement reviewEmailInput;

    @FindBy(id = "review")
    private WebElement reviewTextArea;

    @FindBy(id = "button-review")
    private WebElement reviewSubmitBtn;

    @FindBy(xpath = "//div[contains(@class,'alert-success') or contains(text(),'Thank you for your review.')]")
    private WebElement reviewSuccessAlert;

    // --- Advanced Filters / Controls (For UI Feature Assertions) ---
    @FindBy(xpath = "//select[@id='sort' or @id='orderby']")
    private java.util.List<WebElement> sortingDropdowns;

    @FindBy(xpath = "//ul[contains(@class,'pagination')]")
    private java.util.List<WebElement> paginationControls;

    @FindBy(xpath = "(//div[contains(@class,'product-image-wrapper')])[1]//input[@id='quantity' or contains(@class,'quantity')]")
    private java.util.List<WebElement> gridQuantitySelectors;

    @FindBy(xpath = "//div[contains(@class,'product-information')]//img[@id='product-hero' or contains(@class,'main-image')] | //div[@class='view-product']/img")
    private WebElement heroImage;

    @FindBy(xpath = "//div[contains(@class,'similar-product')]//img | //div[contains(@class,'thumbnails')]//img")
    private java.util.List<WebElement> productThumbnails;
    
    
    @FindBy(xpath = "//div[contains(@class,'alert-danger') or contains(@class,'error-msg') or contains(text(),'exceeds') or contains(text(),'limit')]")
    private WebElement quantityErrorAlert;
    
    
    @FindBy(xpath = "//ol[contains(@class,'breadcrumb')] | //ul[contains(@class,'breadcrumb')] | //div[contains(@class,'breadcrumb')]")
    private WebElement breadcrumbContainer;

    @FindBy(xpath = "//ol[contains(@class,'breadcrumb')]//a | //ul[contains(@class,'breadcrumb')]//a | //div[contains(@class,'breadcrumb')]//a")
    private java.util.List<WebElement> breadcrumbLinks;
    
    @FindBy(xpath = "//div[contains(@class,'rating-breakdown') or contains(@class,'review-bars')]")
    private java.util.List<WebElement> reviewBreakdownBars;

    @FindBy(xpath = "//button[contains(@class,'star-filter') or contains(text(),'5 Star') or contains(text(),'5-star')]")
    private java.util.List<WebElement> starFilterButtons;

    @FindBy(xpath = "//div[contains(@class,'review-item') or contains(@class,'single-review')]")
    private java.util.List<WebElement> displayedReviews;
    
    @FindBy(xpath = "//div[@class='product-information']")
    private WebElement productInformationBlock;

    @FindBy(xpath = "//div[@class='product-information']/p[b[contains(text(),'Description')]] | //div[@class='product-information']/p[1]")
    private WebElement productDescriptionText;
    
    
 // Constructor
    public ProductpageClass(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this); // Initializes @FindBy elements
    }


   
    public boolean isAllProductsPageVisible() {
        return actionUtil.isDisplayed(allProductsHeader) || driver.getCurrentUrl().contains("/products");
    }

    public boolean isSearchedProductsVisible() {
        return actionUtil.isDisplayed(searchedProductsHeader);
    }

    public boolean isFilteredProductsViewVisible() {
    	actionUtil.remove_ad();
        return actionUtil.isDisplayed(filteredProductsHeader) || driver.getCurrentUrl().contains("category_products");
    }

    public int getProductCount() {
        return productCards.size();
    }

    public boolean areProductsDisplayed() {
        return !productCards.isEmpty();
    }

    public void searchProduct(String keyword) {
       
        actionUtil.sendKeys(searchInput, keyword);
        actionUtil.clickViaJS(searchBtn);
 
    }
    public void typeInSearchBox(String text) 
    {
        actionUtil.sendKeys(searchInput, text);
    }

    public void clickSearchButton()
    {
        actionUtil.click(searchBtn);
    }

    public boolean isAutoSuggestionDropdownDisplayed()
    {
      return actionUtil.isElementPresentAndVisible(autoSuggestionDropdown);
       
    }

   
    public String getValue() {
        return actionUtil.getAttribute(searchInput, "value");
    }
    
    
    

    public void clickWomenCategory() {
        actionUtil.scrollToElement(categoryWomenAccordion);
        actionUtil.clickViaJS(categoryWomenAccordion);
    }

    public boolean isDressSubCategoryVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(subCategoryWomenDress)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickDressSubCategory() {
        actionUtil.scrollToElement(subCategoryWomenDress);
        actionUtil.clickViaJS(subCategoryWomenDress);
    }

    public void selectCategoryWomenDress() {
        clickWomenCategory();
        clickDressSubCategory();
    }


    public boolean isBrandSidebarVisible() {
        actionUtil.scrollToElement(brandSidebarHeader);
        return actionUtil.isDisplayed(brandSidebarHeader);
    }

    public void clickFirstBrand() {
        actionUtil.scrollToElement(firstBrandLink);
        actionUtil.clickViaJS(firstBrandLink);
    }

    public void viewPolo() {
        actionUtil.scrollToElement(brandPoloLink);
    }

    public void selectBrandPolo() {
        actionUtil.scrollToElement(brandPoloLink);
        actionUtil.clickViaJS(brandPoloLink);
    }

    public void clickFirstProductDetails() {
       
    	actionUtil.remove_ad();
        
        // Explicitly wait until the product details link is visible and click able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstProduct = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@href,'/product_details/')])[1]"))
        );
        
        actionUtil.scrollToElement(firstProduct);
        actionUtil.clickViaJS(firstProduct);
       
    }

    public boolean areProductDetailsVisible() {
        return actionUtil.isDisplayed(productDetailsContainer) && actionUtil.isDisplayed(productName);
    }

    public void hoverFirstProductCard() {
        actionUtil.scrollToElement(firstProductCard);
        actionUtil.hoverOver(firstProductCard);
    }
    
    
    
    public void hoverSecProductCard() {
        actionUtil.scrollToElement(SecProductCard);
        actionUtil.hoverOver(SecProductCard);
    }
    
    public void cartClick()
    {
    	actionUtil.scrollToElement(cartButton);
    	  actionUtil.clickViaJS(addToCartBtn);
    }
    
    public void ContinueBtnClick()
    {
    	actionUtil.hoverOver(ContinueBtn);
    	actionUtil.click(ContinueBtn);
    }
    

  
    public void setQuantity(String qty) {
    	
        actionUtil.scrollToElement(quantityInput);
        quantityInput.clear();
        quantityInput.sendKeys(qty);
    }

    public void isAddCartFocused() {
        actionUtil.scrollToElement(addToCartBtn);
    }

    public void addProductToCart() {
    	actionUtil.remove_ad();
        actionUtil.scrollToElement(addToCartBtn);
//        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        actionUtil.click(addToCartBtn);
    }

    public boolean isAddedModalVisible() {
        try {
        	actionUtil.scrollToElement(addedCartModal);
            return wait.until(ExpectedConditions.visibilityOf(addedCartModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void frstcartclick() {
    	 actionUtil.remove_ad();
    	 actionUtil.scrollToElement(firstCart);
    	 
    	 actionUtil.clickViaJS(firstCart);
    }
   

 
    
    
    public void clickViewCartFromModal() {
        try {
        	actionUtil.remove_ad();
            wait.until(ExpectedConditions.elementToBeClickable(viewCartModalLink));
            actionUtil.clickViaJS(viewCartModalLink);
        } catch (Exception e) {
            driver.get("https://automationexercise.com/view_cart");
        }
    }

   
    public void submitReview(String name, String email, String review) {
        actionUtil.scrollToElement(reviewSubmitBtn);
        actionUtil.sendKeys(reviewNameInput, name);
        actionUtil.sendKeys(reviewEmailInput, email);
        actionUtil.sendKeys(reviewTextArea, review);
        actionUtil.clickViaJS(reviewSubmitBtn);
    }

    public boolean isReviewSuccessMessageVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(reviewSuccessAlert)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateReviewFormViaKeyboard(String name, String email, String review) {
        actionUtil.scrollToElement(reviewNameInput);
        reviewNameInput.clear();
        reviewNameInput.sendKeys(name);

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.TAB).sendKeys(email)
               .sendKeys(Keys.TAB).sendKeys(review)
               .sendKeys(Keys.TAB).sendKeys(Keys.ENTER)
               .build().perform();
    }

  
    public boolean isPromotionalBannerVisible() {
        try {
            if (actionUtil.isDisplayed(promotionalBanner)) {
                return true;
            }
            // Fallback check for header advertisement banner dynamic elements on AutomationExercise
            return !driver.findElements(org.openqa.selenium.By.xpath("//div[@id='header-carousel'] | //img[@id='sale_image'] | //div[contains(@class,'carousel-inner')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSortingDropdownPresent() {
        return !sortingDropdowns.isEmpty();
    }

    public boolean isPaginationPresent() {
        return !paginationControls.isEmpty();
    }

    public boolean isGridQuantitySelectorPresent() {
        return !gridQuantitySelectors.isEmpty();
    }

 public String CheckDiscoverItems()
 { 
	 actionUtil.remove_ad();
	 actionUtil.scrollToElement(Discoveritem);
	return actionUtil.getText(Discoveritem);
	 
 }
 
 public boolean areThumbnailsAvailable() {
     return !productThumbnails.isEmpty();
 }

 public String getHeroImageSrc() {
     actionUtil.scrollToElement(heroImage);
     return heroImage.getAttribute("src");
 }

 public void hoverThumbnail(int index) {
     if (index < productThumbnails.size()) {
         WebElement thumb = productThumbnails.get(index);
         actionUtil.scrollToElement(thumb);
         actionUtil.hoverOver(thumb);
     }
 }

 public void clickThumbnail(int index) {
     if (index < productThumbnails.size()) {
         WebElement thumb = productThumbnails.get(index);
         actionUtil.scrollToElement(thumb);
         actionUtil.clickViaJS(thumb);
     }
 }
 
 
 public String getAvailabilityStatusText() {
     actionUtil.scrollToElement(availabilityStatus);
     return availabilityStatus.getText().trim();
 }

 public boolean isAddToCartBtnEnabled() {
     try {
         return addToCartBtn.isEnabled() && !addToCartBtn.getAttribute("class").contains("disabled");
     } catch (Exception e) {
         return false;
     }
 }

 public boolean isQuantityInputDisabled() {
     try {
         String disabledAttr = quantityInput.getAttribute("disabled");
         String readonlyAttr = quantityInput.getAttribute("readonly");
         return disabledAttr != null || "true".equals(readonlyAttr) || !quantityInput.isEnabled();
     } catch (Exception e) {
         return false;
     }
 }
 
 
 
 public boolean isProductDescriptionValid() {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(productInformationBlock));
	        String text = productDescriptionText.getText().trim();

	        // Check for common bug conditions: empty string, 'null', or 'undefined'
	        if (text.isEmpty() || text.equalsIgnoreCase("null") || text.equalsIgnoreCase("undefined")) {
	            System.err.println("[BUG DETECTED] Product description is missing or invalid: '" + text + "'");
	            return false;
	        }
	        return true;
	    } catch (Exception e) {
	        System.err.println("[BUG DETECTED] Product description element not found in DOM.");
	        return false;
	    }
	}
 
 
 

 public void enterExcessiveQuantity(String qty) {
     actionUtil.scrollToElement(quantityInput);
     quantityInput.clear();
     quantityInput.sendKeys(qty);
 }

 public boolean isQuantityErrorDisplayed() {
     try {
         return wait.until(ExpectedConditions.visibilityOf(quantityErrorAlert)).isDisplayed();
     } catch (Exception e) {
         return false;
     }
 }

 public String getQuantityValidationMessage() {
     return quantityInput.getAttribute("validationMessage");
 }
 
 
 
 
 public boolean isBreadcrumbContainerDisplayed() {
     try {
         actionUtil.scrollToElement(breadcrumbContainer);
         return breadcrumbContainer.isDisplayed();
     } catch (Exception e) {
         return false;
     }
 }

 public int getBreadcrumbLinksCount() {
     return breadcrumbLinks.size();
 }

 public void clickBreadcrumbLink(int index) {
     if (index < breadcrumbLinks.size()) {
         WebElement link = breadcrumbLinks.get(index);
         actionUtil.scrollToElement(link);
         actionUtil.clickViaJS(link);
     }
 }

 public String getBreadcrumbLinkText(int index) {
     if (index < breadcrumbLinks.size()) {
         return breadcrumbLinks.get(index).getText().trim();
     }
     return "";
 }
 
 
 
 public boolean isReviewBreakdownDisplayed() {
     return !reviewBreakdownBars.isEmpty() && actionUtil.isDisplayed(reviewBreakdownBars.get(0));
 }

 public boolean areStarFilterButtonsPresent() {
     return !starFilterButtons.isEmpty();
 }

 public void clickStarFilter(String starRating) {
     By filterLocator = By.xpath("//button[contains(text(),'" + starRating + "') or contains(@data-rating,'" + starRating + "')]");
     WebElement filterBtn = wait.until(ExpectedConditions.elementToBeClickable(filterLocator));
     actionUtil.clickViaJS(filterBtn);
 }

 public int getDisplayedReviewCount() {
     return displayedReviews.size();
 }
 
 
 
 public void remove_Ad() {
     try {
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
     } catch (Exception ignored) {}
 }
    
    
    
}
	
	

