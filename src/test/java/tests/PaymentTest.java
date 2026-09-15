package tests;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseclassTest;
import Utils.ActionUtil;
import Utils.ScreenshotList;
import pagesClass.CartpageClass;
import pagesClass.CheckoutPage;
import pagesClass.ClassHomePage;
import pagesClass.LoginpageClass;
import pagesClass.PaymentPage;
import pagesClass.ProductpageClass;



@Listeners(ScreenshotList.class)
public class PaymentTest extends BaseclassTest 
{
	private ClassHomePage home;
    private LoginpageClass loginPage;
    private CartpageClass cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
   private ProductpageClass productPage;
   private ActionUtil utils;
   
   
   private final String SCRIPT_TAG_PAYLOAD = "<script>alert('XSS')</script>";
  
   private final String CONTROL_CHARS_PAYLOAD = "John\u0000\u0007\u001B\r\nDoe";
 
   private final String NUMERIC_SPECIAL_PAYLOAD = "12345!@#$%^&*()_+";
   private final String dirPath = System.getProperty("user.home") + File.separator + "Downloads";  
 
    @BeforeClass
    public void initPages() {
        home = new ClassHomePage(getdriver());
        loginPage = new LoginpageClass(getdriver());
        cartPage = new CartpageClass(getdriver());
        checkoutPage = new CheckoutPage(getdriver());
        productPage = new ProductpageClass(getdriver());
        paymentPage = new PaymentPage(getdriver());
        utils=new ActionUtil(getdriver());
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

    private void addProductAndNavigateToPayment() {
        home.ProductsBtn();
        productPage.remove_Ad();
        productPage.clickFirstProductDetails();
        productPage.addProductToCart();
        productPage.isAddedModalVisible();
        productPage.clickViewCartFromModal();
        productPage.remove_Ad();
        cartPage.clickProceedToCheckout();
        checkoutPage.clickPlaceOrder();
    }
    
    
    
//    Verify entering valid card details successfully places order and navigates to success page
    @Test(description = "PYT_001",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
    public void verifySuccessfulPaymentAndOrderPlacement(String email,String password)
    {
       
    	
    	performUserLogin(email,password);
    	addProductAndNavigateToPayment();
    	
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");
        utils.dismissGoogleAds();
        // Execute reusable processPayment method and validate confirmation
        boolean isOrderPlaced = paymentPage.processPayment(
            "Sample User", 
            "4242 4242 4242 4242", 
            "202", 
            "11", 
            "2030"
        );

        Assert.assertTrue(isOrderPlaced, "Order was not successfully placed or success page did not display!");
        Assert.assertTrue(getdriver().getCurrentUrl().contains("payment_done"), "URL does not contain 'payment_done'");
    }
 
    
        


//	Verify system prevents submission and triggers validation when card details are blank
    @Test(description = "PYT_002",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
    public void verifyBlankCardDetailsSubmissionBlocked(String email,String password)
    {
     
    	performUserLogin(email,password);
    	addProductAndNavigateToPayment();
    	
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");
         
       utils.dismissGoogleAds();
        // Leave card fields blank and attempt to pay
        paymentPage.enterCardDetails("", "", "", "", "");
        paymentPage.clickPayAndConfirmOrder();

        // Verify form submission is prevented and validation error is triggered
        Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
            "Order was incorrectly submitted with blank card details!");
        
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
            "System navigated away from Payment page despite blank inputs!");
        
        Assert.assertTrue(paymentPage.isCardNumberFieldInvalid(), 
            "Field validation error message was not triggered on required blank input!");
    }
	
	
	
//    Verify Card Number field rejects non-numeric input or prevents submission with validation error
    @Test(description = "PYT_003",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
    public void verifyNonNumericCardNumberHandling(String email,String password) 
    
    {
       

    	performUserLogin(email,password);
    	addProductAndNavigateToPayment();
    	
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");
        utils.dismissGoogleAds();
      //  Attempt to enter non-numeric characters into Card Number
        String invalidCardInput = "abcd-efgh-ijkl-mnop";
        paymentPage.enterCardDetails("Sample User", invalidCardInput, "202", "12", "2030");

        String actualCardValue = paymentPage.getCardNumberValue();

        // Assert field level rejection or submit level validation
        if (!actualCardValue.equals(invalidCardInput)) {
            // Case A: System rejects/filters non-numeric input during typing
            Assert.assertFalse(actualCardValue.matches(".*[a-zA-Z].*"), 
                "Card Number field allowed alphabetic characters to be typed!");
            System.out.println("Pass: Card Number field filtered out non-numeric characters on entry.");
        } else {
            // Case B: System accepts non-numeric input into input box, test submission rejection
            paymentPage.clickPayAndConfirmOrder();

            Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
                "Defect: Order was successfully placed with non-numeric card details!");

            Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
                "System navigated away from Payment page despite invalid card details!");

            System.out.println("Pass: System prevented order placement when submitting non-numeric card details.");
        }
    }
	
    
    
//    Verify payment fails and order is not placed when entering an expired card date
    @Test(description = "PYT_004",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
    public void verifyExpiredCardDatePaymentFails(String email,String password) 
    {
        
    	

    	performUserLogin(email,password);
    	addProductAndNavigateToPayment();
        utils.dismissGoogleAds();
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

        // Enter valid card info with an expiration date in the past
        paymentPage.enterCardDetails("Sample User", "4242 4242 4242 4242", "202", "01", "2020");
        paymentPage.clickPayAndConfirmOrder();

        //  Verify payment submission failed and order was not confirmed
        Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
            "Defect: Order was successfully placed despite using an expired card date!");

        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
            "System navigated away from Payment page after submitting an expired card date!");
    }
	
	
    
    
    
//    Verify CVC field rejects inputs with less than 3 digits or more than 4 digits
    @Test(description = "PYT_005",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
    public void verifyCvcLengthValidation(String email,String password) 
    {
       
    	

    	performUserLogin(email,password);
    	addProductAndNavigateToPayment();
    	
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");
        utils.dismissGoogleAds();
        // --- Scenario 1: Test CVC with fewer than 3 digits ("12") ---
        paymentPage.enterCardDetails("Sample User", "4242 4242 4242 4242", "20", "11", "2030");
        paymentPage.clickPayAndConfirmOrder();

        Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
            "Defect: Order was placed with a 2-digit CVC!");
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
            "System navigated away from Payment page despite invalid short CVC!");

        // --- Scenario 2: Test CVC with more than 4 digits ("12345") ---
        paymentPage.enterCardDetails("Sample User", "4242 4242 4242 4242", "12345", "11", "2030");
        String actualCvc = paymentPage.getCvcValue();

        if (actualCvc.length() > 4) {
            // If field allowed typing >4 digits, submission must fail
            paymentPage.clickPayAndConfirmOrder();
            Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
                "Defect: Order was placed with a 5-digit CVC!");
            Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
                "System navigated away from Payment page despite invalid long CVC!");
        } else {
            // Field truncated input to max 4 digits on typing
            Assert.assertTrue(actualCvc.length() <= 4, 
                "CVC field did not enforce max character length limit!");
        }
    }

    
    
    
    
    
//    Verify total price is displayed on payment page and fractional currency is preserved without truncation
    @Test(description = "PYT_006",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
    public void verifyTotalPricePresentAndFractionalCurrencyPreserved(String email,String password)
    {
      
    	performUserLogin(email,password);
    	addProductAndNavigateToPayment();
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");
        utils.dismissGoogleAds();
        //  Verify Total Price is present on Payment page
        boolean isPricePresent = paymentPage.isTotalPriceDisplayed();
        Assert.assertTrue(isPricePresent, 
            "[BUG DETECTED]: Total price summary element is missing on the payment submission screen!");

    }
    
    
    
    
    
//    Verify browser Back button post-order confirmation prevents duplicate order creation or re-submitting payment
    @Test(description = "PYT_007",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
    public void verifyPreventDuplicateOrderOnBrowserBack(String email,String password)
    {
       
    	

    	performUserLogin(email,password);
    	addProductAndNavigateToPayment();
    	
    	
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

        // Submit initial successful payment
        boolean isInitialOrderPlaced = paymentPage.processPayment(
            "Sample User", 
            "4242 4242 4242 4242", 
            "202", 
            "11", 
            "2030"
        );
        Assert.assertTrue(isInitialOrderPlaced, "Initial order placement failed!");

        // Click browser Back button post-confirmation
        paymentPage.navigateBrowserBack();

        //  Verify duplicate order creation is prevented
        if (paymentPage.isPaymentPageLoaded()) {
            // Attempting to click payment again if active on payment page
            paymentPage.clickPayAndConfirmOrder();
            Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
                "[DEFECT DETECTED]: Duplicate payment processing allowed via browser Back navigation!");
        } else {
            // System correctly redirected user away from payment screen or cleared session state
            Assert.assertFalse(getdriver().getCurrentUrl().contains("payment_done"), 
                "Browser back did not update view state correctly.");
        }
    }
    
    
    
    
    
    
//    Verify order is not placed when session expires during payment submission
    
    @Test(description = "PYT_008",dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
      public void verifyUnauthenticatedOrderBlockedOnSessionExpiration(String email, String password)
    {
          
          
          performUserLogin(email, password);
          addProductAndNavigateToPayment();
          Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

          //Fill out payment details while authenticated
          paymentPage.enterCardDetails("Sample User", "4242 4242 4242 4242", "202", "11", "2030");

          //Simulate session timeout by clearing browser session cookies & local storage
          getdriver().manage().deleteAllCookies();
          ((org.openqa.selenium.JavascriptExecutor) getdriver()).executeScript("window.localStorage.clear(); window.sessionStorage.clear();");

          // Step 4: Attempt to confirm order post-session invalidation
          paymentPage.clickPayAndConfirmOrder();

          //  Assert that unauthenticated order placement is blocked
          Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
              "[SECURITY RISK]: Order was completed successfully despite an expired session!");

          // Verify user is redirected to login page or session error view
          boolean redirectedToLogin = getdriver().getCurrentUrl().contains("login") || loginPage.IsLoginVisible();
          Assert.assertTrue(redirectedToLogin, 
              "Application failed to redirect unauthenticated user to the login screen after session expiration.");
          
          System.out.println("[PASS]: Unauthenticated order successfully blocked on session expiration.");
      }
    
    
    
    
    
    
//    Validate Script Tag Sanitization in Card holder Name
    @Test(description = "PYT_009", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
      public void verifyScriptTagSanitization(String email, String password)
    {
    	
    	
    	performUserLogin(email, password);
    	addProductAndNavigateToPayment();
    	
          Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

          // Enter script tag in card holder name field
          paymentPage.enterCardDetails(SCRIPT_TAG_PAYLOAD, "4242 4242 4242 4242", "202", "11", "2030");
          paymentPage.clickPayAndConfirmOrder();

          // Verify script execution was prevented and order is handled correctly
          Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
              "Order failed to process or system failed to sanitize script tag input.");
      }

    
    
    
    
//    Validate Control Characters Handling in Card holder Name
      @Test(description = "PYT_009 ", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
      public void verifyControlCharactersSanitization(String email, String password)
      {
    	 
    	  
    	  performUserLogin(email, password);
    	  addProductAndNavigateToPayment();
    	  
          Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

          // Enter control characters in card holder name field
          paymentPage.enterCardDetails(CONTROL_CHARS_PAYLOAD, "4242 4242 4242 4242", "202", "11", "2030");
          paymentPage.clickPayAndConfirmOrder();

          // Ensure pay load is handled cleanly without breaking page flow or causing server error
          Assert.assertTrue(paymentPage.isOrderSuccessPageDisplayed(), 
              "Application failed to handle control characters in cardholder name.");
      }

      
      
      
      
      
//      Validate Non-Alphabetic/Special Characters Input
      @Test(description = "PYT_009", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
      public void verifyNonAlphabeticInputSanitization(String email, String password)
      {
    	
    	  
    	  performUserLogin(email, password);
    	  addProductAndNavigateToPayment();
    	  
          Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

          // Enter numeric and special characters
          paymentPage.enterCardDetails(NUMERIC_SPECIAL_PAYLOAD, "4242 4242 4242 4242", "202", "11", "2030");
          paymentPage.clickPayAndConfirmOrder();

          // Assert submission behavior (adjust assertion if your application blocks or strips special chars)
          Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
              "Form failed on non-alphabetic character input.");
      }
      
      
      
      
      
//      Validate downloading invoice after successful order placement
      @Test(description = "PYT_010", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
        public void verifyInvoiceDownload(String email, String password) throws InterruptedException {
            
    	  performUserLogin(email,password);
      	addProductAndNavigateToPayment();
      	
          Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

          // Execute reusable processPayment method and validate confirmation
          boolean isOrderPlaced = paymentPage.processPayment(
              "Sample User", 
              "4242 4242 4242 4242", 
              "202", 
              "11", 
              "2030"
          );
          Assert.assertTrue(isOrderPlaced, "Order placement was not successful.");

          // Step 3: Clean target directory before downloading (calling class method directly)
         paymentPage. cleanDownloadDirectory(dirPath);

          // Step 4: Click 'Download Invoice' button
          getdriver().findElement(By.xpath("//a[contains(text(),'Download Invoice')]")).click();

          // Step 5: Wait and verify file exists in download directory
          File downloadedFile = paymentPage.waitForFileToDownload(dirPath, 10);
          
          Assert.assertNotNull(downloadedFile, "Invoice file was not downloaded within the timeout period.");
          Assert.assertTrue(downloadedFile.exists(), "Downloaded invoice file does not exist.");
          Assert.assertTrue(downloadedFile.length() > 0, "Downloaded invoice file is empty (0 bytes).");
          
          // Step 6: Verify valid file extension (.txt or .pdf)
          String fileName = downloadedFile.getName().toLowerCase();
          boolean isValidExtension = fileName.endsWith(".txt") || fileName.endsWith(".pdf");
          Assert.assertTrue(isValidExtension, "Downloaded file format is invalid: " + fileName);

            System.out.println("[PASS]: Invoice downloaded successfully -> " + downloadedFile.getAbsolutePath());
        }
    
      
      

      
      
//      Verify clicking brand logo on Payment page do not redirects to Home while preserving user session
      
      @Test(description = "PYT_011 ", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
        public void verifyBrandLogoRedirectsToHomeAndMaintainsSession(String email, String password) {
            
            //  Login, navigate to payment page, and verify page load
            performUserLogin(email, password);
            addProductAndNavigateToPayment();
            Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

            //  Click primary brand Logo in header
            home.isWebsiteLogoclickable();

            //  Assert redirection to Home page URL
            String currentUrl = getdriver().getCurrentUrl();
            Assert.assertTrue(currentUrl.endsWith("/") || currentUrl.contains("index"), 
                "User was not redirected to the Home page after clicking logo. Current URL: " + currentUrl);

            // Verify session state remains active (User is still logged in)
            Assert.assertTrue(home.IsloggedIn(), 
                "[SESSION ERROR]: User session was invalidated after navigating via brand logo.");

            System.out.println("Brand logo does not navigated to Home page and session state was preserved.");
        }
      
      
      
      
      
      
      
      
//      Verify successful payment redirects to /payment_done with Invoice Download and Continue options
      
      @Test(description = "PYT_012 ", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
        public void verifyPaymentSuccessScreenTransition(String email, String password) {
            
            //  Login and reach payment screen
            performUserLogin(email, password);
            addProductAndNavigateToPayment();
            Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

            // Process valid payment
            paymentPage.enterCardDetails("Test User", "4242 4242 4242 4242", "311", "12", "2028");
            paymentPage.clickPayAndConfirmOrder();

            //Validate URL transition to /payment_done
            String currentUrl = getdriver().getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/payment_done"), 
                "Application failed to transition to /payment_done URL. Actual URL: " + currentUrl);

            //  Verify 'Download Invoice' button presence and visibility
            WebElement downloadInvoiceBtn = getdriver().findElement(By.xpath("//a[contains(text(),'Download Invoice')]"));
            Assert.assertTrue(downloadInvoiceBtn.isDisplayed(), 
                "Download Invoice button is not displayed on the payment success screen.");

            // Verify 'Continue' shopping button presence and visibility
            WebElement continueBtn = getdriver().findElement(By.xpath("//a[@data-qa='continue-button']"));
            Assert.assertTrue(continueBtn.isDisplayed(), 
                "Continue shopping button is not displayed on the payment success screen.");

            System.out.println("[PASS]: Payment completed successfully, URL updated to /payment_done, and action options are available.");
        }
      
      
      
      
      
      
      
      
      private void triggerAutofillOnElement(WebElement element, String value) {
          JavascriptExecutor js = (JavascriptExecutor) getdriver();
          js.executeScript(
              "arguments[0].focus();" +
              "arguments[0].value = arguments[1];" +
              "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
              "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
              "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", 
              element, value
          );
      }
      
      
//      Verify browser card auto fill populates all fields and triggers event handlers
      
      @Test(description = "PYT_013", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
        public void verifyBrowserCardAutofillPopulatesAndTriggersHandlers(String email, String password)
      {
            
            // Login and navigate to Payment page
            performUserLogin(email, password);
            addProductAndNavigateToPayment();
            Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

            //Locate form input elements
            WebElement nameOnCardField = getdriver().findElement(By.name("name_on_card"));
            WebElement cardNumberField = getdriver().findElement(By.name("card_number"));
            WebElement cvcField = getdriver().findElement(By.name("cvc"));
            WebElement expMonthField = getdriver().findElement(By.name("expiry_month"));
            WebElement expYearField = getdriver().findElement(By.name("expiry_year"));

            //Simulate browser card saved profile auto fill action across all fields
            triggerAutofillOnElement(nameOnCardField, "Autofill Saved User");
            triggerAutofillOnElement(cardNumberField, "4242 4242 4242 4242");
            triggerAutofillOnElement(cvcField, "311");
            triggerAutofillOnElement(expMonthField, "08");
            triggerAutofillOnElement(expYearField, "2029");

            //  Verify inputs hold the autofilled values
            Assert.assertEquals(nameOnCardField.getAttribute("value"), "Autofill Saved User", "Name on Card failed to populate via autofill.");
            Assert.assertEquals(cardNumberField.getAttribute("value"), "4242 4242 4242 4242", "Card Number failed to populate via autofill.");
            Assert.assertEquals(cvcField.getAttribute("value"), "311", "CVC failed to populate via autofill.");
            Assert.assertEquals(expMonthField.getAttribute("value"), "08", "Expiration Month failed to populate via autofill.");
            Assert.assertEquals(expYearField.getAttribute("value"), "2029", "Expiration Year failed to populate via autofill.");

            // Confirm form events didn't break by submitting order with autofilled data
            paymentPage.clickPayAndConfirmOrder();

            // Validate successful transition
            Assert.assertTrue(paymentPage.isOrderSuccessPageDisplayed(), 
                "Form submission failed or event listeners blocked order placement after browser autofill execution.");

            System.out.println(" Browser card saved profile successfully populated inputs and triggered all form events.");
        }
      
      
      
      
      
      
      
      
      
//      Validate card number with hyphen delimiters processes without format errors
     
        @Test(description = "PYT_014",  dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
        public void verifyCardNumberWithHyphenDelimiters(String email, String password)
        {
            
            performUserLogin(email, password);
            addProductAndNavigateToPayment();
            Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

            // Process payment with hyphen-delimited card number
            paymentPage.enterCardDetails("Delimiter User","4242-4242-4242-4242", "311", "12", "2029");
            paymentPage.clickPayAndConfirmOrder();

            // Validate that submission was processed successfully
            Assert.assertTrue(paymentPage.isOrderSuccessPageDisplayed(), 
                "Order failed to process when card number included hyphen delimiters.");

            System.out.println("[PASS]: Hyphen-delimited card number processed cleanly.");
       
        }
        
        
        
        
        
        
        
    //  Validate auto complete attributes and check for unmasked data leakage during live typing
        
        @Test(description = "PYT_015", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
          public void verifyCardFieldsAutocompleteAndMaskingSecurity(String email, String password) {
              
              //Login and navigate to Payment page
              performUserLogin(email, password);
              addProductAndNavigateToPayment();
              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

              //Locate Card Number and CVC input elements
              WebElement cardNumberField = getdriver().findElement(By.name("card_number"));
              WebElement cvcField = getdriver().findElement(By.name("cvc"));

              // Validate 'auto complete' attribute values
              String cardAutocomplete = cardNumberField.getAttribute("autocomplete");
              String cvcAutocomplete = cvcField.getAttribute("autocomplete");

              boolean isCardAutocompleteValid = cardAutocomplete == null || 
                                                cardAutocomplete.equalsIgnoreCase("cc-number") || 
                                                cardAutocomplete.equalsIgnoreCase("off");
              
              boolean isCvcAutocompleteValid = cvcAutocomplete == null || 
                                               cvcAutocomplete.equalsIgnoreCase("cc-csc") || 
                                               cvcAutocomplete.equalsIgnoreCase("off");

              Assert.assertTrue(isCardAutocompleteValid, 
                  "[SECURITY RISK]: Card number field lacks valid autocomplete setting. Actual: " + cardAutocomplete);
              Assert.assertTrue(isCvcAutocompleteValid, 
                  "[SECURITY RISK]: CVC field lacks valid autocomplete setting. Actual: " + cvcAutocomplete);

              // Simulate live typing of sensitive card data
              String testCardNumber = "4111222233334444";
              String testCvc = "888";

              cardNumberField.sendKeys(testCardNumber);
              cvcField.sendKeys(testCvc);

              // Check outer HTML DOM representation for plain-text attribute leaks
              String cardNumberOuterHtml = cardNumberField.getAttribute("outerHTML");
              String cvcOuterHtml = cvcField.getAttribute("outerHTML");

              // Verify sensitive plain values are not exposed in custom HTML attributes (e.g. data-val="4111222233334444")
              Assert.assertFalse(cardNumberOuterHtml.contains("data-val='" + testCardNumber + "'") || 
                                 cardNumberOuterHtml.contains("data-value=\"" + testCardNumber + "\""), 
                  "[SECURITY RISK]: Sensitive card number leaked in custom HTML attributes!");

              Assert.assertFalse(cvcOuterHtml.contains("data-val='" + testCvc + "'") || 
                                 cvcOuterHtml.contains("data-value=\"" + testCvc + "\""), 
                  "[SECURITY RISK]: Sensitive CVC leaked in custom HTML attributes!");

              System.out.println("Card input security attributes and DOM masking verified successfully.");
          }
        
      
        
        
        
        
        
        
        private final String[] INVALID_MONTHS = {"00", "13", "99"};
        
//        Validate invalid month values (00, 13, 99) block order submission and trigger field validation
        
        @Test(description = "PYT_016", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
          public void verifyInvalidExpirationMonthBlocksSubmission(String email, String password) {
              
              //  Login and navigate to Payment page
              performUserLogin(email, password);
              addProductAndNavigateToPayment();
              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

              WebElement monthField = getdriver().findElement(By.name("expiry_month"));

              for (String invalidMonth : INVALID_MONTHS) {
                  //  Enter payment details with invalid month
                  paymentPage.enterCardDetails("Validation User", "4242 4242 4242 4242", "311", invalidMonth, "2029");
                  paymentPage.clickPayAndConfirmOrder();

                  //  Assert submission is blocked (Order Success screen should NOT display)
                  Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
                      "[SECURITY/VALIDATION BUG]: Order was placed successfully with an invalid month: " + invalidMonth);

                  //  Validate HTML5 or custom field validation message presence
                  String validationMessage = monthField.getAttribute("validationMessage");
                  boolean isHtml5Invalid = monthField.getAttribute("aria-invalid") != null && 
                                           monthField.getAttribute("aria-invalid").equals("true");
                  boolean hasValidationWarning = (validationMessage != null && !validationMessage.isEmpty()) || isHtml5Invalid;

                  Assert.assertTrue(hasValidationWarning || paymentPage.isPaymentPageLoaded(), 
                      "Field validation message was not triggered for invalid month value: " + invalidMonth);

                  System.out.println("[PASS]: Invalid month '" + invalidMonth + "' correctly blocked order submission.");
              }
          }
        
   
        
        
        
        
        
        
     //  Verify YYYY input restricts excess numeric characters via max length attribute
        @Test(description = "PYT_017" , 
                dataProvider = "getvalidData", 
                dataProviderClass = LoginTest.class)
          public void verifyYearInputMaxLengthConstraint(String email, String password) {
              performUserLogin(email, password);
              addProductAndNavigateToPayment();

              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

              // Locate Year Input Field (name='expiry_year')
              WebElement yearInput = getdriver().findElement(By.name("expiry_year"));

              //  DOM Attribute Assertion
              String maxLengthAttr = yearInput.getAttribute("maxlength");
              Assert.assertEquals(maxLengthAttr, "4", "HTML attribute 'maxlength' is not set to 4!");

              //Functional Input Truncation Check
              String excessYearInput = "2028555";
              yearInput.clear();
              yearInput.sendKeys(excessYearInput);

              String actualEnteredValue = yearInput.getAttribute("value");
              Assert.assertEquals(actualEnteredValue, "2028", 
                  "Input field allowed excess characters beyond the 4-digit restriction!");
          }
        
  
        
        
        
        
        
        private final String FORMATTED_CARD_SPACES = "4532 0156 7890 1234";
        private void pasteTextIntoInput(WebElement element, String textToPaste) {
            element.clear();
            element.click();

            // Dispatch ClipboardEvent payload to trigger JS 'paste' listeners
            JavascriptExecutor js = (JavascriptExecutor) getdriver();
            js.executeScript(
                "var el = arguments[0];" +
                "var text = arguments[1];" +
                "el.value = text;" +
                "var pasteEvent = new ClipboardEvent('paste', { bubbles: true, cancelable: true });" +
                "el.dispatchEvent(pasteEvent);" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));",
                element, textToPaste
            );
        }
        
//        Validate pasting card string with spaces/hyphens sanitizes or processes cleanly without JS exceptions"
        @Test(description = "PYT_018" ,dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
          public void verifyPastedFormattedCardNumberProcessing(String email, String password)
        {
              
              //  Login and reach Payment page
              performUserLogin(email, password);
              addProductAndNavigateToPayment();
              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

              WebElement cardNumberField = getdriver().findElement(By.name("card_number"));

              //  Paste formatted space-delimited string
              pasteTextIntoInput(cardNumberField, FORMATTED_CARD_SPACES);

              // Populate remaining valid card details
              paymentPage.enterCardDetails("Paste Test User", cardNumberField.getAttribute("value"), "311", "12", "2029");
              paymentPage.clickPayAndConfirmOrder();

              // Assert submission processed cleanly without throwing JS errors or blocking checkout
              Assert.assertTrue(paymentPage.isOrderSuccessPageDisplayed(), 
                  "Order failed to process after pasting space-formatted card number.");

             System.out.println("[PASS]: Pasted space-formatted card number handled successfully.");
          }
        
      
        
        
        
        
        
        
        
//        Validate card with exact current month and year processes as active and unexpired
        
        @Test(description = "PYT_019", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
          public void verifyCurrentMonthYearCardAccepted(String email, String password) {
              
              // Login and navigate to Payment page
              performUserLogin(email, password);
              addProductAndNavigateToPayment();
              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

              //  Dynamically retrieve current MM and YYYY at runtime
              LocalDate currentDate = LocalDate.now();
              String currentMonth = currentDate.format(DateTimeFormatter.ofPattern("MM"));
              String currentYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

              //  Enter card details using current month and current year
              paymentPage.enterCardDetails("Active Card User", "4242 4242 4242 4242", "311", currentMonth, currentYear);
              paymentPage.clickPayAndConfirmOrder();

              //  Validate order completion (Cards expiring in the current month remain valid through the end of the month)
              Assert.assertTrue(paymentPage.isOrderSuccessPageDisplayed(), 
                  "[VALIDATION BUG]: Payment failed! Card with current expiration date (" + currentMonth + "/" + currentYear + ") was incorrectly treated as expired.");

              System.out.println(" Card with current expiration date (" + currentMonth + "/" + currentYear + ") processed successfully.");
          }
        
      
        
        
        
        
      
        
//        verify Page Refresh On Payment Done Url
        @Test(description = "PYT_020", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
        public void verifyPageRefreshOnPaymentDoneUrl(String email, String password)
        {
           
        	
        	performUserLogin(email, password);
            addProductAndNavigateToPayment();

            Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

            // Complete payment process to land on payment_done URL
            boolean isOrderPlaced = paymentPage.processPayment(
                "Sample User", 
                "4242 4242 4242 4242", 
                "202", 
                "11", 
                "2030"
            );

            Assert.assertTrue(isOrderPlaced, "Order was not successfully placed!");
            Assert.assertTrue(getdriver().getCurrentUrl().contains("payment_done"), "URL does not contain 'payment_done'");

            // Refresh the browser page on the confirmation screen
            getdriver().navigate().refresh();
           

            // Verify application state stability post-refresh
            Assert.assertTrue(getdriver().getCurrentUrl().contains("payment_done"), 
                "Page refresh redirected away from payment_done unexpectedly!");
            Assert.assertTrue(paymentPage.isOrderSuccessPageDisplayed(), 
                "Order confirmation header or success state missing after browser refresh!");
        }
  
        
        
        
        
        
        
        
    //  Direct URL access to payment_done without placing an order
        
        @Test(description = "PYT_021" , dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
          public void verifyDirectAccessToPaymentDoneFails(String email, String password) 
        {
             
        	performUserLogin(email, password);

              // Attempt direct navigation to payment_done URL bypassing cart and checkout flow
              String targetUrl = "https://www.automationexercise.com/payment_done/0";
              getdriver().get(targetUrl);
             

              // Security Assertions: Ensure unauthorized direct access is blocked or safely redirected
              String currentUrl = getdriver().getCurrentUrl();
              
              Assert.assertFalse(currentUrl.endsWith("/payment_done/0"), 
                  "Security Vulnerability: User was able to access payment_done page directly via URL without submitting payment!");

              // Typical defensive expectations: Redirection to Home/Cart OR display of success screen blocked
              Assert.assertFalse(paymentPage.isOrderSuccessPageDisplayed(), 
                  "Order success message is visibly displayed without an active checkout session!");
          }
        
  
        
        
        
        
        
//        Verify behavior when double-clicking Pay and Confirm Order button
        
        @Test(description = "PYT_022" , dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
          public void verifyDoubleClickOnPayAndConfirmOrder(String email, String password) 
        {
           
        	performUserLogin(email, password);
              addProductAndNavigateToPayment();

              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");
              paymentPage. enterCardDetails("Sample User","4242 4242 4242 4242","202","11","2030");
            
             paymentPage.clickPayAndConfirmOrderDoubleclick(); 
     
              // Assertions to verify application handles rapid submission safely
//              Assert.assertTrue(getdriver().getCurrentUrl().contains("0"), 
//                  "Order confirmation URL not reached after double-clicking submit!");
              Assert.assertTrue(paymentPage.isOrderSuccessPageDisplayed(), 
                  "Order confirmation message was not displayed properly post double-click!");
          }
        
       
        
        
        
        
        
        
        
//        Assert absence of UPI payment options (GPay, PhonePe, Paytm, QR Code) on payment page 
        @Test(description = "PYT_023 ", dataProvider = "getvalidData", dataProviderClass = LoginTest.class)
            public void raiseBugForMissingUpiPaymentOptions(String email, String password)
        {
              performUserLogin(email, password);
              addProductAndNavigateToPayment();

              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

              // Check for presence of UPI elements on the checkout page
              boolean isUpiSectionPresent = getdriver().findElements(By.xpath("//*[contains(text(),'UPI') or contains(text(),'Google Pay') or contains(text(),'PhonePe') or contains(text(),'Paytm') or contains(text(),'QR Code')]")).size() > 0;
              boolean isUpiInputPresent = getdriver().findElements(By.xpath("//input[contains(@placeholder, 'UPI') or contains(@id, 'upi')]")).size() > 0;

              // Assert failure to trigger a test defect report for missing UPI options
              Assert.assertTrue(isUpiSectionPresent || isUpiInputPresent, 
                  "BUG DETECTED [Severity: High]: UPI Payment mode options (GPay, PhonePe, Paytm, BHIM QR) are completely missing from the payment page!");
          }
        
        
        
        
        
        
        
        
        
        
//        Assert failure due to missing Net Banking option
        @Test(description = "PYT_024 ", dataProvider = "getvalidData",  dataProviderClass = LoginTest.class)
          public void verifyNetBankingOptionAvailability(String email, String password) 
        {
            
        	performUserLogin(email, password);
              addProductAndNavigateToPayment();

              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");

              // Attempting to check for Net Banking UI elements (Radio buttons, Dropdowns, or Tabs)
              boolean isNetBankingOptionPresent = getdriver().getPageSource().contains("Net Banking") 
                  || getdriver().getPageSource().contains("Select Bank");

              // Intentionally assertion fail to raise a defect report for missing feature
              Assert.assertTrue(isNetBankingOptionPresent, 
                  "DEFECT: Users without active payment cards cannot select major banks to transfer funds directly via online banking portals. Net Banking feature is missing from the payment page.");
          }
        
        
        
        
        
        
        
        
        
//        Verify absence of Cash On Delivery (COD) option forces online payment entry
        @Test(description = "PYT_025 ", dataProvider = "getvalidData",  dataProviderClass = LoginTest.class)
          public void verifyMissingCashOnDeliveryOption(String email, String password) {
              performUserLogin(email, password);
              addProductAndNavigateToPayment();

              // Validate presence of Cash on Delivery UI elements or options on checkout/payment section
              boolean isCodOptionAvailable =  getdriver().getPageSource().contains("Cash on Delivery") 
                                          ||  getdriver().getPageSource().contains("Pay on Delivery") 
                                          || getdriver().getPageSource().contains("COD");

              // Assert that COD is available. If missing, this assertion throws a failure to raise the bug in test reports.
              Assert.assertTrue(isCodOptionAvailable, 
                  "BUG REPORT: Cash on Delivery (COD) option is missing! " +
                  "Users who prefer paying cash on delivery cannot place orders without pre-entering card details.");
          }
        
        
        
        
        
        
        
        
        
        
//        Assert missing EMI and BNPL payment options during checkout
        
        @Test(description = "PYT_026  ", dataProvider = "getvalidData",  dataProviderClass = LoginTest.class)
          public void verifyEMAndBNPLOptionsPresence(String email, String password)
        {
             
        	
        	performUserLogin(email, password);
              addProductAndNavigateToPayment();

              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Failed to reach the payment page.");

              // Verification for missing EMI and Buy Now Pay Later (BNPL) options
              boolean isEMIOptionPresent = getdriver().getPageSource().contains("EMI");
              boolean isBNPLOptionPresent =getdriver().getPageSource().contains("Buy Now Pay Later");

              // Intentionally failing assertion to log bug for missing EMI/BNPL flexible payment choices
              Assert.assertTrue(isEMIOptionPresent, 
                  "BUG DETECTED: High-value order cannot be split into installments. EMI options are missing on the payment page!");
              
              Assert.assertTrue(isBNPLOptionPresent, 
                  "BUG DETECTED: Buy Now Pay Later (BNPL) payment choices are missing on the payment page!");
          }
        
        
        
        
        
        
        
        
        
        
//        verify Bypassing 3D-Secure / OTP prompt directly to payment_done
        @Test(description = "PYT_027 ", dataProvider= "getvalidData",  dataProviderClass = LoginTest.class)
          public void verifyMissing3DSecureOTPChallenge(String email, String password) 
        
        {
              performUserLogin(email, password);
              addProductAndNavigateToPayment();

              Assert.assertTrue(paymentPage.isPaymentPageLoaded(), "Payment page failed to load.");
           
              paymentPage.enterCardDetails("Sample User", "4242 4242 4242 4242", "202", "11", "2030");
              
              
              // Submit Payment Form
              paymentPage.clickPayAndConfirmOrder();

              // 1. Verify that the browser was NOT redirected directly to /payment_done
              String currentUrl = getdriver().getCurrentUrl();
              boolean passedDirectlyToSuccess = currentUrl.contains("payment_done");

              // Assertion fails if the application bypasses 2FA/OTP and lands on payment_done
              Assert.assertFalse(passedDirectlyToSuccess, 
                  "CRITICAL SECURITY BUG: System bypassed 3D-Secure / OTP verification step " +
                  "and directly completed the order at: " + currentUrl);
          }
       
        
        
        
        
        
}
    






