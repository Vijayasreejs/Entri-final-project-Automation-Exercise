package pagesClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ActionUtil;

public class CartpageClass 
{

	private WebDriver driver;
    private ActionUtil actionUtil;
    private WebDriverWait wait;

    @FindBy(xpath = "(//a[contains(@class,'add-to-cart')])[1]")
    private WebElement firstGridAddToCartBtn;

    @FindBy(xpath = "//button[contains(text(),'Continue Shopping')]")
    private WebElement continueShoppingBtn;

    @FindBy(id = "cart_info_table")
    private WebElement cartTable;

    @FindBy(xpath = "//td[contains(@class,'cart_quantity')]/button")
    private WebElement cartQuantityBtn;

    @FindBy(xpath = "//td[contains(@class,'cart_price')]/p")
    private WebElement cartPrice;

    @FindBy(xpath = "//td[contains(@class,'cart_total')]/p")
    private WebElement cartTotal;

    @FindBy(xpath = "(//a[@class='cart_quantity_delete'])[1]")
    private WebElement deleteItemBtn;

    @FindBy(xpath = "//a[contains(text(),'Proceed To Checkout')]")
    private WebElement proceedToCheckoutBtn;

    @FindBy(id = "checkoutModal")
    private WebElement checkoutModal;

    @FindBy(xpath = "//u[contains(text(),'Register / Login')]")
    private WebElement registerLoginModalBtn;

    @FindBy(xpath = "//b[contains(text(),'Cart is empty!')]")
    private WebElement emptyCartMsg;
    
    @FindBy(xpath = "//a[@data-product-id='1']")
    private WebElement firstproduct;
    
    @FindBy(xpath="//div[@id='cartModal']")
    private WebElement cartModal;
    
    @FindBy(xpath="//tr[contains(@id, 'product-')]")
    
    private final By cartRows = By.xpath("//tr[contains(@id, 'product-')]");


    public CartpageClass(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void addFirstProductFromGrid() 
    {
    	actionUtil.clickViaJS(firstGridAddToCartBtn);
        actionUtil.waitForVisibility(cartModal);
    }

    public boolean isCartTableVisible() {
        return actionUtil.isDisplayed(cartTable);
    }
    
    public boolean isDeleteBtnVisible() {
        return actionUtil.isDisplayed(deleteItemBtn);
    }

    public boolean isCartPageVisible() {
        return driver.getCurrentUrl().contains("view_cart");
    }

    public int getCartItemCount() {
    	try {
            List<WebElement> items = actionUtil.findElements(cartRows);
            return items == null ? 0 : items.size();
        } catch (Exception e) 
        {
            return 0;
        }
    }

    public String getItemQuantity() {
        return actionUtil.getText(cartQuantityBtn);
    }

    public String getItemPrice() {
        return actionUtil.getText(cartPrice);
    }

    public String getItemTotal() {
        return actionUtil.getText(cartTotal);
    }

    public void RemoveFirstCartItem() 
    {  
    	actionUtil.scrollToElement(deleteItemBtn);
        actionUtil.clickViaJS(deleteItemBtn);
    }
    
    
   

    public void clickProceedToCheckout() {
    	
        remove_Ad();
        
        By proceedBtnLocator = By.xpath("//a[contains(text(),'Proceed To Checkout')]");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(proceedBtnLocator));
        actionUtil.scrollToElement(btn);
        
        
        actionUtil.clickViaJS(btn);
        
    
        try {
            wait.until(ExpectedConditions.urlContains("checkout"));
        } catch (org.openqa.selenium.TimeoutException e) {
         
            remove_Ad();
            driver.get("https://www.automationexercise.com/checkout");
        }
    }
    
    
    
    public boolean isCheckoutModalVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(checkoutModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRegisterLoginModalVisible() {
        try {
            // Wait for the modal content to fully execute CSS transitions and become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By modalLinkLocator = By.xpath("//div[@id='checkoutModal']//u[contains(text(),'Register / Login')] | //div[@id='checkoutModal']//a[contains(@href,'/login')]");
            
            return wait.until(ExpectedConditions.visibilityOfElementLocated(modalLinkLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void clickRegisterLoginInModal() {
        actionUtil.clickViaJS(registerLoginModalBtn);
    }

    public boolean isEmptyCartMessageVisible() {
        return actionUtil.isDisplayed(emptyCartMsg);
    }

    public boolean isSaveForLaterVisible() {
        return driver.findElements(By.xpath("//*[contains(text(),'Save for Later')]")).size() > 0;
    }

    public boolean isPincodeCheckerVisible() {
        return driver.findElements(By.id("pincode")).size() > 0;
    }

    public boolean isPriceBreakdownVisible() {
        return driver.findElements(By.id("price-breakdown")).size() > 0;
    }

    public boolean isCartQuantityEditableOrVisible() {
        return driver.findElements(By.xpath("//input[contains(@class,'cart_quantity_input')]")).size() > 0;
    }

    public boolean isRecommendedItemsVisible() {
        return driver.findElements(By.xpath("//div[contains(@class,'recommended_items')]")).size() > 0;
    }

    public void clickHomeNavMenu() {
        actionUtil.clickViaJS(driver.findElement(By.xpath("//a[contains(text(),'Home')]")));
    }

    public void remove_Ad() {
        actionUtil.remove_ad();
    }
	
}
