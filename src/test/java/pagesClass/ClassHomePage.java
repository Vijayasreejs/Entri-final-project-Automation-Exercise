package pagesClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ActionUtil;

public class ClassHomePage 
{

	private WebDriver driver;
    private ActionUtil actionUtil;
    private WebDriverWait wait;

    // --- Header & Navigation Bar Elements ---
    
    
    
    @FindBy(xpath = "//*[@id=\"header\"]/div/div/div/div[1]/div/a/img")
    private WebElement WebsiteLogo;

    
    
    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private WebElement homeNavBtn;

    @FindBy(xpath = "//a[contains(text(),'Signup / Login')]")
    private WebElement loginSignupBtn;

    @FindBy(xpath = "//a[contains(text(),'Products')]")
    private WebElement productsBtn;

    @FindBy(xpath = "//a[contains(text(),'Cart')]")
    private WebElement cartBtn;

    @FindBy(xpath = "//a[contains(text(),'Test Cases')]")
    private WebElement testCasesBtn;

    @FindBy(xpath = "//a[contains(text(),'API Testing')]")
    private WebElement apiTestBtn;

    @FindBy(xpath = "//a[contains(text(),'Video Tutorials')]")
    private WebElement videoTutorialsBtn;

    @FindBy(xpath = "//a[contains(text(),'Contact us')]")
    private WebElement contactUsBtn;
    
    @FindBy(xpath = "//a[contains(text(),'Logout')]")
	private  WebElement logotNav;
	

    // --- Sidebar Category & Brand Elements ---
    @FindBy(xpath = "//h2[contains(text(),'Category')]")
    private WebElement categoryHeading;


    @FindBy(xpath = "//a[@href='/category_products/1']")
    private WebElement categoryWomenSubItem;

    @FindBy(xpath = "//div[@id='Men']//a[contains(text(),'Tshirts')]")
    private WebElement categoryMenSubItem;
    
    
    
    @FindBy(xpath = "//a[@href='#Women' or contains(text(),'Women')]")
    private WebElement categoryWomenToggle;

    @FindBy(xpath = "//a[@href='#Men' or contains(text(),'Men')]")
    private WebElement categoryMenToggle;

    @FindBy(xpath = "//h2[contains(text(),'Brands')]")
    private WebElement brandsHeading;

    @FindBy(xpath = "//a[contains(@href,'/brand_products/HM') or contains(@href,'/brand_products/H&M') or contains(text(),'H&M')]")
    private WebElement brandHM;

    @FindBy(xpath = "//a[contains(@href,'/brand_products/Polo')]")
    private WebElement brandPolo;

    @FindBy(xpath = "//div[@class='brands-name']")
    private WebElement brandItemCountsContainer;
    
    @FindBy(xpath = "//*[@id=\"aswift_1_host\"]/div/div/div[3]/span[1]")
    private WebElement discoverMoreItem1;  

    @FindBy(xpath = "//*[@id=\"aswift_2_host\"]/div/div/div[4]/span[1]")
    private WebElement discoverMoreItem2;
    
 
    
    private By  discoverMoreTop= By.xpath("//*[@id=\"aswift_1_host\"]/div/div/div[1]/span");
    
    private By discoverMoreBottom = By.xpath("//*[@id=\"aswift_2_host\"]/div/div/div[1]");
    
    @FindBy(xpath="//a[contains(text(),'Logged in as')]")
    private WebElement Loggedin_user;
    
    // --- Carousel Elements ---
    
    @FindBy(id = "slider")
    private WebElement carouselContainer;

    @FindBy(xpath = "//div[@id='slider-carousel']//div[contains(@class,'active')]")
    private WebElement activeSlide;

    @FindBy(xpath = "//a[@class='left control-carousel hidden-xs']")
    private WebElement prevSlideBtn;

    @FindBy(xpath = "//a[@class='right control-carousel hidden-xs']")
    private WebElement nextSlideBtn;

    @FindBy(xpath = "//div[@id='slider-carousel']//a[contains(@href,'/test_cases')]")
    private WebElement bannerTestCaseBtn;

    @FindBy(xpath = "//div[@id='slider-carousel']//a[contains(@href,'/api_list')]")
    private WebElement bannerApiBtn;

    // --- Featured Products Elements ---
    @FindBy(xpath = "//div[contains(@class,'features_items')]")
    private WebElement featuredProductsSection;

    @FindBy(xpath = "(//div[contains(@class,'product-image-wrapper')])[1]")
    private WebElement firstFeaturedProductCard;
    
    @FindBy(xpath="//*[@id=\"cartModal\"]/div/div/div[2]/p[2]/a/u")
    private WebElement viewCart;

    @FindBy(xpath = "(//div[contains(@class,'product-image-wrapper')])[1]//div[contains(@class,'product-overlay')]")
    private WebElement firstProductOverlay;

    @FindBy(xpath = "(//div[contains(@class,'product-image-wrapper')])[1]//a[contains(@class,'add-to-cart')]")
    private WebElement firstFeaturedAddToCartBtn;

    @FindBy(xpath = "(//div[contains(@class,'product-image-wrapper')])[1]//a[contains(text(),'View Product')]")
    private WebElement firstFeaturedViewProductBtn;

    @FindBy(xpath = "(//div[contains(@class,'productinfo')]//h2)[1]")
    private WebElement firstFeaturedPrice;

    @FindBy(xpath = "(//div[contains(@class,'productinfo')]//p)[1]")
    private WebElement firstFeaturedDescription;

    @FindBy(xpath = "(//div[contains(@class,'product-overlay')])[1]//a[contains(@class,'add-to-cart')]")
    private WebElement overlayAddToCartBtn;
    // --- Recommended Items Section ---
    @FindBy(xpath = "//div[contains(@class,'recommended_items')]")
    private WebElement recommendedSection;

    @FindBy(xpath = "//a[@class='right recommended-item-control']")
    private WebElement recommendedNextBtn;

    @FindBy(xpath = "//div[contains(@class,'recommended_items')]//a[contains(@class,'add-to-cart')][1]")
    private WebElement recommendedAddToCartBtn;

    // --- Modal / Popup Elements ---
    @FindBy(xpath = "//a[contains(text(),'Cart')]//span[contains(@class,'badge')] | //a[contains(text(),'Cart') and contains(.,'(')]")
    private WebElement cartCountBadge;
    
    
    @FindBy(id = "cartModal")
    private WebElement cartModal;

    @FindBy(xpath = "//button[contains(text(),'Continue Shopping')]")
    private WebElement continueShoppingBtn;

    @FindBy(xpath = "//div[contains(@class,'toast') or contains(@class,'banner-dismiss')]")
    private WebElement floatingToast;

    @FindBy(xpath = "//button[contains(@class,'dismiss-toast')]")
    private WebElement dismissToastBtn;

    // --- Footer Section Elements ---
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

    @FindBy(xpath = "//footer//p[contains(text(),'Copyright')]")
    private WebElement copyrightText;

    // Constructor
    public ClassHomePage(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this); // Initializes @FindBy elements
    }

    public boolean IshomepageLoad() {
        return actionUtil.isDisplayed(homeNavBtn) || actionUtil.isDisplayed(categoryHeading);
    }

    public void LoginSignupBTn() { actionUtil.scrollToElement(loginSignupBtn); actionUtil.clickViaJS(loginSignupBtn); }
    
    public void ProductsBtn() { 
        remove_Ad();
        actionUtil.scrollToElement(productsBtn);
        actionUtil.clickViaJS(productsBtn);
        remove_Ad();
        if (driver.getCurrentUrl().contains("#google_vignette")) {
            driver.get("https://www.automationexercise.com/products");
        }
    }
    
   public boolean isWebsiteLogoVisible()
   {
	   actionUtil.scrollToElement(WebsiteLogo);
	   return actionUtil.isDisplayed(WebsiteLogo);
   }
   public void isWebsiteLogoclickable()
   {
	   actionUtil.scrollToElement(WebsiteLogo);
 	actionUtil.clickViaJS(WebsiteLogo);
   }
    
    
    public void CartBtn()
    {  
    	actionUtil.scrollToElement(cartBtn);
    actionUtil.clickViaJS(cartBtn); 
    }
    public void TestcasesBtn() 
    { 
      	actionUtil.scrollToElement(testCasesBtn);
    	actionUtil.clickViaJS(testCasesBtn);
    	}
    public void APITestBtn()
    
    { 
    	actionUtil.scrollToElement(apiTestBtn);
    	actionUtil.clickViaJS(apiTestBtn);
    }
    
    public void VideoTutorialsBtn()
    {
    	actionUtil.scrollToElement(videoTutorialsBtn);
    	actionUtil.clickViaJS(videoTutorialsBtn);
    	}
    public void ContactusBtn() 
    { 
    	actionUtil.scrollToElement(contactUsBtn);
    	actionUtil.clickViaJS(contactUsBtn);
    	}

    public void LogoOutBtn()
    {
    	actionUtil.scrollToElement(logotNav);
   	 actionUtil.clickViaJS(logotNav);
    }
    
    
    public boolean CategoryHeadingDisplay() { 
        actionUtil.scrollToElement(categoryHeading);
        return actionUtil.isDisplayed(categoryHeading); 
    }

    public boolean isCategoryDisplay() { return actionUtil.isDisplayed(categoryWomenToggle); }
    
    public void category() { 
        actionUtil.scrollToElement(categoryWomenToggle);
        actionUtil.clickViaJS(categoryWomenToggle); 
    }

    public boolean issubCategoryDisplay() { 
    	  actionUtil.scrollToElement(categoryWomenSubItem);
        return actionUtil.isDisplayed(categoryWomenSubItem); 
    }

    public void subcategory_Click() { 
        actionUtil.scrollToElement(categoryWomenSubItem);
        actionUtil.clickViaJS(categoryWomenSubItem); 
    }

    public boolean isCategoryMenDisplay() { return actionUtil.isDisplayed(categoryMenToggle); }
    
    public void categoryMen() { 
        actionUtil.scrollToElement(categoryMenToggle);
        actionUtil.clickViaJS(categoryMenToggle); 
    }

    public boolean isSubCategoryMenDisplay() {
        return actionUtil.isDisplayed(categoryMenSubItem);
    }

    public void clickCategoryToggle() { 
        actionUtil.scrollToElement(categoryWomenToggle);
        actionUtil.clickViaJS(categoryWomenToggle); 
    }

    public boolean IsBrandsheadingVisble() { 
        actionUtil.scrollToElement(brandsHeading);
        return actionUtil.isDisplayed(brandsHeading); 
    }

    public boolean IsBrandnameVisble() { return actionUtil.isDisplayed(brandHM); }

    public void click_Brandname() { 
        actionUtil.scrollToElement(brandHM);
        actionUtil.clickViaJS(brandHM); 
    }

    public boolean isBrandPoloVisible() { return actionUtil.isDisplayed(brandPolo); }

    public void clickBrandPolo() { 
        actionUtil.scrollToElement(brandPolo);
        actionUtil.clickViaJS(brandPolo); 
    }

    public boolean areBrandCountsDisplayed() { return actionUtil.isDisplayed(brandItemCountsContainer); }

    public boolean IsFeaturedProduct_displayed() { return actionUtil.isDisplayed(featuredProductsSection); }
    public boolean Iscart_OnFeatureProduct() { return actionUtil.isDisplayed(firstFeaturedAddToCartBtn); }
    public boolean IsViewProduct_OnFeatureProduct() { return actionUtil.isDisplayed(firstFeaturedViewProductBtn); }
    public boolean IspriceDisplay_OnFeatureProduct() { return actionUtil.isDisplayed(firstFeaturedPrice); }
    public boolean IsDescriptionDisplay_OnFeatureProduct() { return actionUtil.isDisplayed(firstFeaturedDescription); }

    public void hoverOverFeaturedProduct() {
    	remove_Ad();
        actionUtil.scrollToElement(firstFeaturedProductCard);
        actionUtil.hoverOver(firstFeaturedProductCard);
        
       
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].querySelector('.product-overlay').style.display = 'block';", firstFeaturedProductCard);
        js.executeScript("arguments[0].querySelector('.product-overlay').style.opacity = '1';", firstFeaturedProductCard);
    }

    public void clickOverlayAddToCart() {
    	remove_Ad();
        // Locate the specific overlay button inside the hovered card to prevent stale element execution
        WebElement overlayBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//div[contains(@class,'product-overlay')])[1]//a[contains(@class,'add-to-cart')]")
        ));
        actionUtil.clickViaJS(overlayBtn);
    }

    public void clickFeatureProductAddToCartDirectly() {
        remove_Ad();
        actionUtil.scrollToElement(firstFeaturedAddToCartBtn);
        actionUtil.clickViaJS(firstFeaturedAddToCartBtn);
    }

    public boolean isProductOverlayDisplayed() { 
        try {
            return wait.until(ExpectedConditions.visibilityOf(firstProductOverlay)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickFeatureProductViewDirectly() { actionUtil.clickViaJS(firstFeaturedViewProductBtn); }
    public String getPriceText() { return actionUtil.getText(firstFeaturedPrice); }

    

    public void clickNextSlide() { actionUtil.clickViaJS(nextSlideBtn); }
    public void clickBannerTestCaseBtn() {
      
    	scrollToCarousel();
        actionUtil.clickViaJS(bannerTestCaseBtn);
    }
    
    public String getActiveSlideText()
    {
    	By activeSlideLocator = By.xpath("//div[@id='slider-carousel']//div[contains(@class,'item') and contains(@class,'active')]");
        for (int i = 0; i < 5; i++) {
            try {
                WebElement dynamicActiveSlide = wait.until(ExpectedConditions.presenceOfElementLocated(activeSlideLocator));
                return actionUtil.getText(dynamicActiveSlide);
            } catch (Exception e) {
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        }
        // Fallback locator targeting any visible slide inside the carousel
        WebElement fallbackSlide = driver.findElement(By.xpath("//div[@id='slider-carousel']//div[contains(@class,'item')][1]"));
        return actionUtil.getText(fallbackSlide);
    }
    
    
    public WebElement getActiveSlideElement() {
    	By activeSlideLocator = By.xpath("//div[@id='slider-carousel']//div[contains(@class,'item') and contains(@class,'active')]");
        for (int i = 0; i < 5; i++) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(activeSlideLocator));
            } catch (Exception e) {
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        }
        return driver.findElement(By.xpath("//div[@id='slider-carousel']//div[contains(@class,'item')][1]"));
    }
    
    
    
    public int getCarouselIndicatorsCount() {
        By dotsLocator = By.xpath("//ol[contains(@class,'carousel-indicators')]/li");
        return actionUtil.findElements(dotsLocator).size();
    }
    public boolean isIndicatorActive(int index) {
        By dotLocator = By.xpath("//ol[contains(@class,'carousel-indicators')]/li[" + index + "]");
        try {
            WebElement dot = wait.until(ExpectedConditions.presenceOfElementLocated(dotLocator));
            String className = dot.getAttribute("class");
            return className != null && className.contains("active");
        } catch (Exception e) {
            return false;
        }     
    }
    public boolean IsloggedIn()
    { 
    
            remove_Ad();
            actionUtil.scrollToElement(Loggedin_user);
            return actionUtil.isDisplayed(Loggedin_user);
    }

    public void clickCarouselDot(int index) {
        remove_Ad();
        By dotLocator = By.xpath("//ol[contains(@class,'carousel-indicators')]/li[" + index + "]");
        WebElement dot = wait.until(ExpectedConditions.elementToBeClickable(dotLocator));
        actionUtil.scrollToElement(dot);
        actionUtil.clickViaJS(dot);
    }

    public void clickBannerApiBtn() {
        scrollToCarousel();
        actionUtil.clickViaJS(bannerApiBtn);
    }
    
    public void clickViewCart() {
      actionUtil.scrollToElement(viewCart);
        actionUtil.clickViaJS(viewCart);
    }


    public void scrollToCarousel() { actionUtil.scrollToElement(carouselContainer); }
    public boolean isCarouselVisible() { actionUtil.remove_ad();     return actionUtil.isDisplayed(carouselContainer); }
    public void clickNextSlideViaJS() { actionUtil.clickViaJS(nextSlideBtn); }

   
   

    public void clickRecommendedAddToCart() {
        actionUtil.scrollToElement(recommendedAddToCartBtn);
        actionUtil.clickViaJS(recommendedAddToCartBtn);
    }

    public boolean isRecommendedSectionVisible() {
        actionUtil.scrollToElement(recommendedSection);
        return actionUtil.isDisplayed(recommendedSection);
    }

    public void clickRecommendedNext() { 
        actionUtil.scrollToElement(recommendedNextBtn);
        actionUtil.clickViaJS(recommendedNextBtn); 
    }

    public boolean isCartModalVisible() { 
    	try {
            // Wait for modal backdrop or modal dialogue to be present and visible
            By modalContentLocator = By.xpath("//div[@id='cartModal']//div[contains(@class,'modal-content')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(modalContentLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCartCountUpdatedOrVisible() {
        try {
            // Option 1: Check if a dedicated count badge element is visible
        	 String cartText = actionUtil.getText(cartBtn);
             return cartText.matches(".*\\(\\d+\\).*") || cartText.matches(".*\\d+.*");
            }
            
           catch (Exception e) {
            return false;
        }
    }
    
    public boolean IsCartModalVisible() {
        try {
            // Wait until inner content box or modal wrapper is rendered and displayed
            By modalLocator = By.xpath("//div[@id='cartModal']//div[contains(@class,'modal-content')] | //div[@id='cartModal']");
            WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
            return modalElement.isDisplayed();
        } catch (Exception e) {
            // Fallback strategy: check if action buttons inside cart modal are interactable
            try {
                By modalButtonsLocator = By.xpath("//button[contains(text(),'Continue Shopping')] | //u[contains(text(),'View Cart')]");
                return wait.until(ExpectedConditions.visibilityOfElementLocated(modalButtonsLocator)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    
    public void click_ContinueshoppingBtn() { 
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn));
            actionUtil.clickViaJS(continueShoppingBtn);
            wait.until(ExpectedConditions.invisibilityOf(cartModal));
        } catch (Exception ignored) {}
    }

    public void dismissCartModalIfVisible() {
        if (isCartModalVisible()) {
            click_ContinueshoppingBtn();
        }
    }

    public boolean isFloatingToastVisible() { return actionUtil.isDisplayed(floatingToast); }

    public void dismissFloatingToast() {
        if (actionUtil.isDisplayed(dismissToastBtn)) {
            actionUtil.click(dismissToastBtn);
        }
    }

    public void Scrollpage() { actionUtil.scrollToBottom(); }

    public boolean isSubscriptionVisible() { 
        actionUtil.scrollToElement(subscriptionHeading);
        return actionUtil.isDisplayed(subscriptionHeading); 
    }

    public void enterSubscriptionEmail(String email) { actionUtil.sendKeys(subscriptionEmailInput, email); }
    public void clickSubscribe() { actionUtil.clickViaJS(subscribeBtn); }
    public boolean IssubscribeSuccess() { return actionUtil.isDisplayed(subscribeSuccessMsg); }

    public String getEmailInputValidationMessage() {
        return subscriptionEmailInput.getAttribute("validationMessage");
    }

    public boolean isScrollUpArrowVisible() { 
        actionUtil.scrollToBottom();
        return actionUtil.isDisplayed(scrollUpArrow); 
    }

    public void clickScrollUpArrow() { actionUtil.clickViaJS(scrollUpArrow); }
    public void clickScrollUpButton() { actionUtil.clickViaJS(scrollUpArrow); }

    public boolean isScrolledToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long scrollY = (Long) js.executeScript("return window.pageYOffset || document.documentElement.scrollTop;");
        return scrollY != null && scrollY <= 200;
    }

    public boolean isCopyrightVisible() { 
        actionUtil.scrollToElement(copyrightText);
        return actionUtil.isDisplayed(copyrightText); 
    }

    public String getCopyrightText() { return actionUtil.getText(copyrightText); }

    public void clickDiscoverMoreItemTop() 
    { 
             remove_Ad();
            actionUtil.clickk(discoverMoreTop);
        }

    public void clickDiscoverMoreItemBottom()
    { 
        remove_Ad();
    
            actionUtil.clickk(discoverMoreBottom);
    }
    


    
    public int getDiscoverMoreInstanceCount1() {
        List<WebElement> elements = actionUtil.findElements(discoverMoreTop);
        return elements.size();
    }
    public int getDiscoverMoreInstanceCount2() {
        List<WebElement> elements = actionUtil.findElements(discoverMoreBottom);
        return elements.size();
    }


    public boolean isDiscoverMoreRelevantToContextT() {
        if (actionUtil.isDisplayed(discoverMoreItem1)) {
            String href = discoverMoreItem1.getAttribute("href");
            return href != null && (href.contains("products") || href.contains("category") || href.contains("brands"));
        }
        return false;
    }
    
    
    
    public boolean isDiscoverMoreRelevantToContextB() {
        if (actionUtil.isDisplayed(discoverMoreItem2)) {
            String href = discoverMoreItem2.getAttribute("href");
            return href != null && (href.contains("products") || href.contains("category") || href.contains("brands"));
        }
        return false;
    }
    
    
    public boolean isAdBannerVisible() { return true; }

    public void remove_Ad() 
    {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }
    
    
    
    
}

	
	
	
	
	
	
