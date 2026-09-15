package pagesClass;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import Utils.ActionUtil;

public class CheckoutPage 
{

	private WebDriver driver;
    private ActionUtil actionUtil;
//    private WebDriverWait wait;

    // --- Page Headers & Bread crumbs ---
    @FindBy(xpath = "//li[@class='active' and contains(text(),'Checkout')]")
    private WebElement checkoutBreadcrumb;

    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private WebElement homeBreadcrumbLink;

    // --- Address Details Sections ---
    @FindBy(xpath = "//ul[@id='address_delivery']")
    private WebElement deliveryAddressCard;

    @FindBy(xpath = "//ul[@id='address_invoice']")
    private WebElement billingAddressCard;

    @FindBy(xpath = "//ul[@id='address_delivery']//li[contains(@class,'address_firstname')]")
    private WebElement deliveryName;

    @FindBy(xpath = "//ul[@id='address_delivery']//li[contains(@class,'address_address1')][1]")
    private WebElement deliveryCompany;

    @FindBy(xpath = "//ul[@id='address_delivery']//li[contains(@class,'address_address1')][2]")
    private WebElement deliveryAddress1;

    @FindBy(xpath = "//ul[@id='address_delivery']//li[contains(@class,'address_city')]")
    private WebElement deliveryCityStatePincode;

    @FindBy(xpath = "//ul[@id='address_delivery']//li[contains(@class,'address_country_name')]")
    private WebElement deliveryCountry;

    @FindBy(xpath = "//ul[@id='address_delivery']//li[contains(@class,'address_phone')]")
    private WebElement deliveryPhone;

    @FindBy(xpath = "//ul[@id='address_invoice']//li[contains(@class,'address_firstname')]")
    private WebElement billingName;

    @FindBy(xpath = "//ul[@id='address_invoice']//li[contains(@class,'address_address1')][1]")
    private WebElement billingCompany;

    @FindBy(xpath = "//ul[@id='address_invoice']//li[contains(@class,'address_address1')][2]")
    private WebElement billingAddress1;

    @FindBy(xpath = "//ul[@id='address_invoice']//li[contains(@class,'address_city')]")
    private WebElement billingCityStatePincode;

    @FindBy(xpath = "//ul[@id='address_invoice']//li[contains(@class,'address_country_name')]")
    private WebElement billingCountry;

    @FindBy(xpath = "//ul[@id='address_invoice']//li[contains(@class,'address_phone')]")
    private WebElement billingPhone;

    // --- Order Review Table ---
    @FindBy(id = "cart_info")
    private WebElement orderReviewTable;
    
   

    @FindBy(xpath = "//tr[contains(@id,'product-')]")
    private List<WebElement> reviewItemRows;

    @FindBy(xpath = "//td[contains(@class,'cart_total')]/p[contains(@class,'cart_total_price')]")
    private List<WebElement> itemTotalPrices;

    @FindBy(xpath = "//td[contains(@class,'cart_total_price')]//b | //tr[last()]//p[contains(@class,'cart_total_price')]")
    private WebElement finalTotalAmount;

    // --- Order Commentary Area & Place Order ---
    @FindBy(xpath = "//textarea[@name='message']")
    private WebElement commentTextArea;

    @FindBy(xpath = "//a[contains(@href,'/payment') and contains(@class,'check_out')]")
    private WebElement placeOrderBtn;

    // --- Footer Subscription ---
    @FindBy(id = "susbscribe_email")
    private WebElement footerEmailInput;

    @FindBy(id = "subscribe")
    private WebElement footerSubscribeBtn;

    @FindBy(xpath = "//div[contains(@class,'alert-success') or contains(text(),'successfully subscribed')]")
    private WebElement footerSuccessMsg;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isCheckoutPageLoaded() {
    	  actionUtil.remove_ad();
        return driver.getCurrentUrl().contains("checkout") || actionUtil.isDisplayed(checkoutBreadcrumb);
    }

    public boolean isDeliveryAddressDisplayed() {
    	  actionUtil.remove_ad();
        return actionUtil.isDisplayed(deliveryAddressCard);
    }

    public boolean isBillingAddressDisplayed() {
    	  actionUtil.remove_ad();
        return actionUtil.isDisplayed(billingAddressCard);
    }

    public String getDeliveryAddressText() {
    	  actionUtil.remove_ad();
        return actionUtil.getText(deliveryAddressCard);
    }

    public String getBillingAddressText() {
    	  actionUtil.remove_ad();
        return actionUtil.getText(billingAddressCard);
    }

    public boolean areAddressesMatching() {
    	  actionUtil.remove_ad();
        String delivery = getDeliveryAddressText().replaceAll("(?i)YOUR DELIVERY ADDRESS", "").trim();
        String billing = getBillingAddressText().replaceAll("(?i)YOUR BILLING ADDRESS", "").trim();
        return delivery.equalsIgnoreCase(billing);
    }

    public boolean isOrderReviewTableDisplayed() {
    	  actionUtil.remove_ad();
        return actionUtil.isDisplayed(orderReviewTable);
    }

    public int getOrderReviewItemCount() {
    	  actionUtil.remove_ad();
        return reviewItemRows.size();
    }
    
    
    public boolean isPlaceOrderButtonDisplayed() {
        actionUtil.remove_ad();
        try {
            return placeOrderBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    
    
    public int getSubmittedCommentLength() {
        actionUtil.remove_ad();
        String commentVal = commentTextArea.getAttribute("value");
        return commentVal != null ? commentVal.length() : 0;
    }
    
    
    
    
    public boolean hasCommentTextAreaMaxLength() {
        String maxlength = commentTextArea.getAttribute("maxlength");
        return maxlength != null && !maxlength.isEmpty();
    }
    
    
    
    public boolean isPaymentPageDisplayed() {
        actionUtil.remove_ad();
        return driver.getCurrentUrl().contains("payment");
    }
    
    

    public String getItemTitle(int index) {
        WebElement titleEl = driver.findElement(By.xpath("(//tr[contains(@id,'product-')])[" + index + "]//td[contains(@class,'cart_description')]/h4/a"));
      actionUtil.remove_ad();
        actionUtil.scrollToElement(titleEl);
        return actionUtil.getText(titleEl);
    }
  

    public String getItemPrice(int index) {
        org.openqa.selenium.WebElement priceEl = driver.findElement(
            org.openqa.selenium.By.xpath("(//tr[contains(@id,'product-')])[" + index + "]//td[contains(@class,'cart_price')]/p"));
        actionUtil.scrollToElement(priceEl);
        return actionUtil.getText(priceEl);
    }

    public String getItemQuantity(int index) {
        org.openqa.selenium.WebElement qtyEl = driver.findElement(
            org.openqa.selenium.By.xpath("(//tr[contains(@id,'product-')])[" + index + "]//td[contains(@class,'cart_quantity')]/button"));
        actionUtil.scrollToElement(qtyEl);
        return actionUtil.getText(qtyEl);
    }

    public String getItemTotal(int index) {
        org.openqa.selenium.WebElement totalEl = driver.findElement(
            org.openqa.selenium.By.xpath("(//tr[contains(@id,'product-')])[" + index + "]//td[contains(@class,'cart_total')]/p"));
        actionUtil.scrollToElement(totalEl);
        return actionUtil.getText(totalEl);
    }
    
    public int getFinalTotalAmount() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(finalTotalAmount);
        String totalText = actionUtil.getText(finalTotalAmount);
        return Integer.parseInt(totalText.replaceAll("[^0-9]", ""));
    }
    
    
    public String getItemCategoryText(int index) {
        actionUtil.remove_ad();
        WebElement categoryEl = driver.findElement(By.xpath(
            "(//tr[contains(@id,'product-')])[" + index + "]//td[contains(@class,'cart_description')]/p"));
        actionUtil.scrollToElement(categoryEl);
        return actionUtil.getText(categoryEl);
    }
    
    public void clickCategoryBreadcrumbInReviewTable(String subCategoryName) {
        actionUtil.remove_ad();
        WebElement categoryLink = driver.findElement(By.xpath(
            "//td[contains(@class,'cart_description')]//*[contains(text(),'" + subCategoryName + "')]"));
        actionUtil.scrollToElement(categoryLink);
        actionUtil.clickViaJS(categoryLink);
    }
   
    
    public void enterOrderComment(String comment) {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(commentTextArea);
        commentTextArea.clear();
        commentTextArea.sendKeys(comment);
    }
  

    
    
    public String getEnteredOrderComment() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(commentTextArea);
        // Retrieving value attribute for input/text area elements
        String commentValue = commentTextArea.getAttribute("value");
        return commentValue != null ? commentValue.trim() : "";
    }

    
    
    
    public List<String> getAllRawPriceTexts() {
        actionUtil.remove_ad();
        List<WebElement> priceElements = driver.findElements(By.xpath(
            "//td[contains(@class,'cart_price')]/p | //td[contains(@class,'cart_total')]/p | //p[@class='cart_total_price']"));
        
        List<String> priceTexts = new java.util.ArrayList<>();
        for (WebElement el : priceElements) {
            priceTexts.add(actionUtil.getText(el));
        }
        return priceTexts;
    }
    
    
    
    
    public java.math.BigDecimal calculateExactItemTotalsSum() {
        actionUtil.remove_ad();
        int itemCount = getOrderReviewItemCount();
        java.math.BigDecimal sum = java.math.BigDecimal.ZERO;

        for (int i = 1; i <= itemCount; i++) {
            String itemTotalText = getItemTotal(i).replaceAll("[^0-9.]", "");
            if (!itemTotalText.isEmpty()) {
                java.math.BigDecimal rowTotal = new java.math.BigDecimal(itemTotalText);
                sum = sum.add(rowTotal);
            }
        }
        return sum.setScale(2, java.math.RoundingMode.HALF_UP);
    }
    
    
    
    public java.math.BigDecimal getFinalTotalAmountBigDecimal() {
        actionUtil.remove_ad();
        actionUtil.scrollToElement(finalTotalAmount);
        String totalText = actionUtil.getText(finalTotalAmount).replaceAll("[^0-9.]", "");
        return new java.math.BigDecimal(totalText).setScale(2, java.math.RoundingMode.HALF_UP);
    }
    
    
    

    public void clickPlaceOrder() {
    	  actionUtil.remove_ad();
        actionUtil.scrollToElement(placeOrderBtn);
        actionUtil.clickViaJS(placeOrderBtn);
    }

    
    public void doubleClickPlaceOrder() {
    	  actionUtil.remove_ad();
        actionUtil.scrollToElement(placeOrderBtn);
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.doubleClick(placeOrderBtn).perform();
    }
    
    
    
    public void clickHomeBreadcrumb() {
    	  actionUtil.remove_ad();
        actionUtil.scrollToElement(homeBreadcrumbLink);
        actionUtil.clickViaJS(homeBreadcrumbLink);
    }

    public void subscribeFooterEmail(String email) {
    	  actionUtil.remove_ad();
        actionUtil.scrollToElement(footerEmailInput);
        actionUtil.sendKeys(footerEmailInput, email);
        actionUtil.clickViaJS(footerSubscribeBtn);
    }

    public boolean isFooterSubscriptionSuccess() {
    	  actionUtil.remove_ad();
        return actionUtil.isDisplayed(footerSuccessMsg);
    }
    
    
    
    public String getDeliveryNameText() {
    	  actionUtil.remove_ad();
        return actionUtil.getText(deliveryName);
    }

    public String getDeliveryAddress1Text() {
    	  actionUtil.remove_ad();
        return actionUtil.getText(deliveryAddress1);
    }

    public String getBillingNameText() {
    	  actionUtil.remove_ad();
        return actionUtil.getText(billingName);
    }

    public String getBillingAddress1Text() {
    	  actionUtil.remove_ad();
        return actionUtil.getText(billingAddress1);
    }
    
 
    public void clearCartTableCompletely() {
        try {
            // Step 1: Open cart view
            driver.get("https://www.automationexercise.com/view_cart");
            actionUtil.remove_ad();

            // Step 2: Trigger JS click on all cart item delete buttons currently in the DOM
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            String deleteScript = 
                "var buttons = document.querySelectorAll('.cart_quantity_delete');" +
                "for(var i=0; i<buttons.length; i++){ buttons[i].click(); }";
            js.executeScript(deleteScript);

            // Step 3: Clear session cookies & local storage
            driver.manage().deleteAllCookies();
            js.executeScript("window.localStorage.clear(); window.sessionStorage.clear();");

            // Step 4: Refresh to confirm empty cart state
            driver.navigate().refresh();
            System.out.println("Cart table cleared successfully.");
        } catch (Exception e) {
            System.err.println("Notice: Cart was already empty or reset skipped: " + e.getMessage());
        }
    }
    
   
    public void remove_Ad() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }

	
	
}
