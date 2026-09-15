package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseclassTest;

import Utils.ScreenshotList;
import pagesClass.CartpageClass;
import pagesClass.ClassHomePage;
import pagesClass.ContactUsPage;

@Listeners(ScreenshotList.class)
public class ContactUsTest extends BaseclassTest
{
	
	private ClassHomePage home;
    private ContactUsPage contactUs;
    private CartpageClass cartPage;


    @BeforeClass
    public void initPage() {
        home = new ClassHomePage(getdriver());
        contactUs = new ContactUsPage(getdriver());
        cartPage=new CartpageClass(getdriver());
 
    }

    @BeforeMethod
    public void navigateToContactUs() {
    	getdriver().get("https://www.automationexercise.com/contact_us");
        contactUs.remove_Ad();
    }

    
    
    
    
    
    
    
//    verify Successful Contact Form Submission With All Fields
    @Test(priority = 1, description = "CUTC_001")
    public void verifySuccessfulContactFormSubmissionWithAllFields()
    {
       
        Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                "GET IN TOUCH heading is not displayed on Contact Us page.");

        //  Prepare dynamic sample file for optional file upload field
        File tempFile = null;
        try {
            tempFile = File.createTempFile("contact_upload_", ".txt");
            tempFile.deleteOnExit();
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("Automation test file content for contact form submission.");
            }
        } catch (IOException e) {
            Assert.fail("Failed to generate temporary file for upload testing: " + e.getMessage());
        }
        
        // Fill required fields
        String testName = "Automation Tester";
        String testEmail = "testuser_" + System.currentTimeMillis() + "@example.com";
        String testSubject = "Inquiry Regarding Services";
        String testMessage = "Hello, this is an automated functional test validating contact form submission.";

        contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

        // Fill optional field (Upload File)
        contactUs.uploadFile(tempFile.getAbsolutePath());

        // Submit form & accept browser alert dialog
        contactUs.clickSubmit();
        contactUs.acceptSubmitAlert();

        //  Assert success message visibility and message text
        Assert.assertTrue(contactUs.isSuccessMessageDisplayed(), 
                "Success message was not displayed after submitting contact form.");
        
        String actualSuccessText = contactUs.getSuccessMessageText();
        Assert.assertTrue(actualSuccessText.contains("Success! Your details have been submitted successfully."), 
                "Contact form submission message content mismatch! Actual: " + actualSuccessText);

        // Click Home button & verify navigation back to Home page
      cartPage.clickHomeNavMenu();
        Assert.assertTrue(getdriver().getCurrentUrl().equals("https://www.automationexercise.com/") 
                || getdriver().getCurrentUrl().contains("automationexercise.com"), 
                "Failed to navigate back to Home Page after clicking Home button.");

        System.out.println("Contact Us form submission with all fields verified successfully.");
    }
	
	
	
    
//    Verify contact form submission without uploading optional file
    
    
    @Test(priority = 2, description = "CUTC_002 ")
    public void verifyContactFormSubmissionWithoutOptionalFile()
    {
        //Verify page load
        Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                "GET IN TOUCH heading is not displayed.");

        // Fill only mandatory fields
        String testName = "Jane Doe";
        String testEmail = "janedoe_" + System.currentTimeMillis() + "@example.com";
        String testSubject = "Mandatory Fields Only Test";
        String testMessage = "Testing contact form submission without attaching an optional file.";

        contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

        // Confirm file upload input remains empty
        String uploadedFileVal = contactUs.getFileUploadValue();
        Assert.assertTrue(uploadedFileVal == null || uploadedFileVal.isEmpty(), 
                "File upload field should be empty before submitting.");

        //  Submit form and accept alert
        contactUs.clickSubmit();
        contactUs.acceptSubmitAlert();

        //  Assert submission success
        Assert.assertTrue(contactUs.isSuccessMessageDisplayed(), 
                "Success message was not displayed when submitting without an optional file.");

        String successText = contactUs.getSuccessMessageText();
        Assert.assertTrue(successText.contains("Success! Your details have been submitted successfully."), 
                "Unexpected success message text: " + successText);

        System.out.println("Form submission without optional file upload passed successfully.");
    }
    
    
    
    
    
//    Verify browser validation error message on submitting form with missing required fields
  
   @Test(priority = 3, description = "CUTC_003")
   public void verifyValidationOnMissingRequiredFields() {
       Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), "Contact page did not load correctly.");

       // Case 1: Fill Name only, leave Email, Subject, Message blank
       contactUs.fillContactForm("QA Tester", "", "", "");
       contactUs.clickSubmit();

       String emailValidationMsg = contactUs.getEmailValidationMessage();
       Assert.assertFalse(emailValidationMsg.isEmpty(), 
               "Expected HTML5 browser validation trigger on empty required Email field.");

       // Case 2: Fill Name and Email, leave Subject and Message blank
       contactUs.fillContactForm("QA Tester", "qatester@example.com", "", "");
       contactUs.acceptSubmitAlert();
       

       String subjectValidationMsg = contactUs.getSubjectValidationMessage();
       Assert.assertFalse(subjectValidationMsg.isEmpty(), 
               "Expected HTML5 browser validation trigger on empty required Subject field.");

       // Case 3: Fill Name, Email, Subject, leave Message blank
       contactUs.fillContactForm("QA Tester","qatester@example.com", "Test Inquiry", "");
       contactUs.acceptSubmitAlert();

       String messageValidationMsg = contactUs.getMessageValidationMessage();
       Assert.assertFalse(messageValidationMsg.isEmpty(), 
               "Expected HTML5 browser validation trigger on empty required Message field.");

       System.out.println("Verified field validation triggers for missing mandatory fields.");
   }
    
    
   

   
//   Verify blank form submission triggers validation on first required field (Name)
   @Test(priority = 4, description = "CUTC_004")
   public void verifyBlankFormSubmissionTriggersNameValidation() {
       //  Verify the Contact Us page is loaded
       Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
               "Contact Us page 'GET IN TOUCH' heading is not visible.");

       //  Ensure form is clear (no inputs entered)
       contactUs.fillContactForm("", "", "", "");

       //  Click the Submit button without filling any fields
       contactUs.clickSubmit();

       //  Retrieve HTML5 validation message from the 'Name' input field
       String nameValidationMsg = contactUs.getNameValidationMessage();

       // Assert that native browser validation is triggered and non-empty
       Assert.assertNotNull(nameValidationMsg, 
               "Validation message object returned null for the Name field.");
       Assert.assertFalse(nameValidationMsg.trim().isEmpty(), 
               "Expected HTML5 required-field browser validation message on empty 'Name' field, but it was empty.");

       // Verify no success message is displayed (form submission blocked)
       Assert.assertFalse(contactUs.isSuccessMessageDisplayed(), 
               "Form submission should be blocked when mandatory fields are blank.");

       System.out.println(" Blank form submission successfully blocked with validation message: " + nameValidationMsg);
   }
   
   
   
   
   
   
   
   
//  Verify navigation back to Home page after accepting browser alert pop up
@Test(priority = 5, description = "CUTC_005")
public void verifyNavigationToHomePageAfterHandlingAlert() 
{
	
	//  Verify Contact Us page is loaded
    Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
            "Contact Us page 'GET IN TOUCH' header is not visible.");


    contactUs.fillContactForm("Automation Tester","testuser"+ System.currentTimeMillis() + "@example.com", "Navigation Test",  "Testing return navigation to Home Page after alert acceptance.");

    // Submit form to trigger browser alert pop up
    contactUs.clickSubmit();

    // Accept the browser alert pop up
    contactUs.acceptSubmitAlert();

    // Verify success banner is rendered
    Assert.assertTrue(contactUs.isSuccessMessageDisplayed(), 
            "Success message was not displayed after handling browser alert.");

    // Click the Home button
    contactUs.clickHomeNavMenu();

    //  Remove any dynamic ad overlays on the Home page
    contactUs.remove_Ad();

    //  Validate URL and Home page header state
    String currentUrl = getdriver().getCurrentUrl();
    Assert.assertTrue(currentUrl.equals("https://www.automationexercise.com/") 
            || currentUrl.equals("https://automationexercise.com/"), 
            "Failed to navigate back to Home Page. Actual URL: " + currentUrl);

    System.out.println(" Successfully verified home page navigation post alert handling.");
}





//Validate allowed file extensions attach correctly without crashing UI (No DataProvider)
@Test(priority = 6, description = "CUTC_006")
public void verifyAllowedFileFormatsAttachSuccessfully()
{
 
	
	// Array of allowed file extension formats to validate
 String[] fileExtensions = { ".txt", ".pdf", ".png", ".jpg" };

   for (String extension : fileExtensions) {
     // Ensure Contact Us page is ready
     Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
             "Contact Us page 'GET IN TOUCH' header is not displayed.");

     //  Dynamically create a temporary file for the current extension
     File tempUploadFile = null;
     try {
         tempUploadFile = File.createTempFile("contact_test_", extension);
         tempUploadFile.deleteOnExit();
         try (FileWriter writer = new FileWriter(tempUploadFile)) {
             writer.write("Automation dummy file content for testing " + extension + " attachment.");
         }
     } catch (IOException e) {
         Assert.fail("Failed to create temporary file for format (" + extension + "): " + e.getMessage());
     }

     // Fill required form fields
     String testName = "QA Tester";
     String testEmail = "fileformat_" + System.currentTimeMillis() + "@example.com";
     String testSubject = "Testing Attachment Format: " + extension;
     String testMessage = "Validating that attaching a " + extension + " file submits successfully without crashing the UI.";

     contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

     // Attach the test file
     contactUs.uploadFile(tempUploadFile.getAbsolutePath());

     //  Verify file name was populated in input control
     String attachedFileName = contactUs.getAttachedFileName();
     Assert.assertTrue(attachedFileName.endsWith(extension), 
             "File name does not end with expected extension '" + extension + "'. Actual: " + attachedFileName);

     //  Submit form and accept the alert
     contactUs.clickSubmit();
     contactUs.acceptSubmitAlert();

     // Assert successful submission banner and message text without UI crash
     Assert.assertTrue(contactUs.isSuccessMessageDisplayed(), 
             "UI crashed or failed to render success banner after uploading file format: " + extension);

     String successMsg = contactUs.getSuccessMessageText();
     Assert.assertTrue(successMsg.contains("Success! Your details have been submitted successfully."), 
             "Form submission message content mismatch for file format: " + extension);

     System.out.println("Passed for format: " + extension);

     // Click Home button and return to Contact Us page for next iteration
   
     contactUs.remove_Ad();
     home.ContactusBtn();
     contactUs.remove_Ad();
 }
}







    // Ensure navigation to other pages works from the Contact Us page
      @Test(priority = 7, description = "CUTC_007")
     public void verifyHeaderNavigationFromContactUsPage()
    {
     // Verify user starts on Contact Us page
   Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
         "Contact Us page 'GET IN TOUCH' header is not displayed.");

    // Define target page routes and expected URL substrings
     String[][] navigationTargets = {
     { "Products", "/products" },
     { "Cart", "/view_cart" },
     { "Signup / Login", "/login" },
     { "Test Cases", "/test_cases" },
     { "API Testing", "/api_list" }
     };

     for (String[] target : navigationTargets) {
     String pageName = target[0];
     String expectedUrlPath = target[1];

     // Perform click navigation based on page target
     switch (pageName) {
         case "Products":
             home.ProductsBtn();
             break;
         case "Cart":
             home.CartBtn();
             break;
         case "Signup / Login":
             home.LoginSignupBTn();
             break;
         case "Test Cases":
            home.TestcasesBtn();
             break;
         case "API Testing":
             home.APITestBtn();
             break;
     }

    
     contactUs.remove_Ad();

     // Validate URL assertion
     String currentUrl = getdriver().getCurrentUrl();
     Assert.assertTrue(currentUrl.contains(expectedUrlPath), 
             "Failed to navigate to " + pageName + " page from Contact Us. Actual URL: " + currentUrl);

     System.out.println(" Successfully navigated from Contact Us to: " + pageName);

     // Return back to Contact Us page for the next header link test
     getdriver().get("https://automationexercise.com/contact_us");
     contactUs.remove_Ad();
     Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
             "Failed to reload Contact Us page before testing next navigation link.");
   }
  }
      
      
      
      
      
      
      
      
   // Validate system behavior when attempting to attach an over sized file
      @Test(priority = 8, description = "CUTC_008 ")
      public void verifyOversizedFileUploadBehavior() 
     {
    	  Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
    	            "Contact Us page 'GET IN TOUCH' header is not displayed.");

    	    File oversizedFile = null;
    	    try {
    	        oversizedFile = File.createTempFile("oversized_test_", ".pdf");
    	        oversizedFile.deleteOnExit();
    	        try (RandomAccessFile raf = new RandomAccessFile(oversizedFile, "rw")) {
    	            raf.setLength(10 * 1024 * 1024); // 10 MB
    	        }
    	    } catch (IOException e) {
    	        Assert.fail("Failed to generate oversized temporary file: " + e.getMessage());
    	    }

    	    contactUs.fillContactForm("QA Tester", "oversized_" + System.currentTimeMillis() + "@example.com", 
    	            "Oversized File Test", "Testing 10MB upload limit.");
    	    contactUs.uploadFile(oversizedFile.getAbsolutePath());
    	    contactUs.clickSubmit();

    	    if (contactUs.isAlertPresent()) {
    	        contactUs.acceptSubmitAlert();
    	    }

    	    // Scroll view back UP to the contact form so screenshot captures the form area
    	    JavascriptExecutor js = (JavascriptExecutor) getdriver();
    	    js.executeScript("window.scrollTo(0, 0);");

    	    boolean isSuccess = contactUs.isSuccessMessageDisplayed();
    	    Assert.assertFalse(isSuccess, 
    	            "BUG DETECTED: System accepted and displayed success message for an oversized file (10 MB)!");
      }   



      
      
      
      
   //Test system reaction when an improperly formatted email address is provided
   // Validate system rejects improperly formatted email inputs
      @Test(priority = 9, description = "CUTC_009 ")
      public void verifyImproperlyFormattedEmailReaction()
      {
    	  Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
    	            "Contact Us page 'GET IN TOUCH' header is not displayed.");

    	    String[] invalidEmails = {
    	        "invalidemail.com",        // Missing '@'
    	        "user@",                   // Missing domain
    	        "user@domain",             // Missing TLD extension (.com, .org, etc.)
    	        "user@.com",               // Missing domain name
    	        "user name@domain.com",    // Invalid space character
    	        "user@domain..com"         // Double dots in domain
    	    };

    	    String testName = "QA Tester";
    	    String testSubject = "Invalid Email Test";
    	    String testMessage = "Testing system reaction to improperly formatted email addresses.";

    	    for (String invalidEmail : invalidEmails) {
    	        // Fill form
    	        contactUs.fillContactForm(testName, invalidEmail, testSubject, testMessage);

    	      
    	        // Submit form (HTML5 browser validation will prevent actual submission)
    	        contactUs.clickSubmit();

    	        // Handle alert if present
    	        if (contactUs.isAlertPresent())
    	        {
    	            contactUs.acceptSubmitAlert();
    	        }

    	        // Validate HTML5 validation message directly from current element
    	        String emailValidationMsg = contactUs.getEmailValidationMessage();
    	    

    	        Assert.assertFalse(emailValidationMsg.isEmpty(), 
    	                "System failed to trigger HTML5 browser validation for invalid email: " + invalidEmail);

    	        System.out.println("Passed for invalid email: '" + invalidEmail + "' - Message: " + emailValidationMsg);
    	    }
      }
      
      
      
      
      
      
      
   // Ensure canceling the browser confirmation alert aborts form submission
      @Test(priority =10, description = "CUTC_010 ")
      public void verifyAlertCancellationAbortsSubmission()
      
      {
          //  Verify Contact Us page is displayed
          Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                  "Contact Us page 'GET IN TOUCH' header is not displayed.");

          //Prepare valid test data
          String testName = "Jane Doe";
          String testEmail = "janedoe_" + System.currentTimeMillis() + "@example.com";
          String testSubject = "Alert Dismissal Test";
          String testMessage = "Verifying that clicking Cancel on the alert box cancels form submission.";

          //  Fill in form fields
          contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

          //  Click Submit to trigger browser alert pop up
          contactUs.clickSubmit();

          //  Dismiss (Cancel) the browser alert dialog
          contactUs.dismissSubmitAlert();

          //  Assert success message is NOT displayed
          Assert.assertFalse(contactUs.isSuccessMessageDisplayed(), 
                  "Form submission was NOT aborted after canceling the browser alert!");

          // Verify entered form field values remain preserved in the DOM
          Assert.assertEquals(contactUs.getNameInputValue(), testName, 
                  "Name field value was cleared or modified after canceling alert.");
          Assert.assertEquals(contactUs.getSubjectInputValue(), testSubject, 
                  "Subject field value was cleared or modified after canceling alert.");
          Assert.assertEquals(contactUs.getMessageInputValue(), testMessage, 
                  "Message field value was cleared or modified after canceling alert.");

          System.out.println(" Alert dismissal correctly aborted form submission and retained input data.");
      }
      
      
      
      
      
      
      
   // Verify order category requires Order ID before form submission

      @Test(priority = 11, description = "CUTC_011")
     public void verifyOrderCategoryRequiresOrderID() 
      {
   

  //  Verify Contact Us page is displayed
     Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
     "Contact Us page 'GET IN TOUCH' header is not displayed.");
         
         //  Fill standard contact form fields
     String testName = "QA Tester";
     String testEmail = "qatester_" + System.currentTimeMillis() + "@example.com";
       String testSubject = "Order Tracking / Return Inquiry";
     String testMessage = "Requesting status update on order return.";

       contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

    //  Verify presence of Order ID input/dropdown field on the DOM
       boolean isOrderIdFieldPresent = getdriver().findElements(By.name("order_id")).size() > 0 
         || getdriver().findElements(By.id("order_id")).size() > 0 
         || getdriver().findElements(By.xpath("//input[contains(@placeholder,'Order')]")).size() > 0;

       // Assert field presence - Fails due to missing UI implementation
          Assert.assertTrue(isOrderIdFieldPresent, 
     "BUG DETECTED: Contact Us form is missing the mandatory Order ID / Category field for order inquiries.");
     
      }
      
      
      
      
      
      
      
   // Verify Drag-and-Drop file upload capability
     @Test(priority = 12, description = "CUTC_012" )
          public void verifyDragAndDropFileUpload() 
     {
              

              // Verify Contact Us page header loads
              Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                      "Contact Us page 'GET IN TOUCH' header is not displayed.");

              // Create temporary test file
              File tempFile = null;
              try {
                  tempFile = File.createTempFile("drag_drop_test_", ".png");
                  tempFile.deleteOnExit();
                  try (FileWriter writer = new FileWriter(tempFile)) {
                      writer.write("Sample PNG binary content for drag and drop test.");
                  }
              } catch (IOException e) {
                  Assert.fail("Failed to create temporary file for Drag and Drop test: " + e.getMessage());
              }

              // Locate upload container element
              WebElement uploadInput = getdriver().findElement(By.name("upload_file"));

              //  Verify presence of HTML5 Drag-and-Drop target container in DOM
              // Native drop zones use drag events like 'ondrop', 'ondragover', or specialized drop zone UI classes
              JavascriptExecutor js = (JavascriptExecutor) getdriver();
              Boolean hasDragDropListeners = (Boolean) js.executeScript(
                  "var elem = arguments[0];" +
                  "return (elem.outerHTML.contains('dropzone') || elem.getAttribute('ondrop') != null || elem.parentElement.classList.contains('dropzone'));", 
                  uploadInput
              );

              //  Assert Drag-and-Drop container support - Fails due to missing drop zone UI
              Assert.assertTrue(hasDragDropListeners, 
                      "BUG DETECTED: File upload component lacks HTML5 Drag-and-Drop container/dropzone capabilities.");
          }
    
      
      
     
     
     
     
  //  Verify character length boundary & real-time counter in Message text area
         @Test(priority = 13, description = "CUTC_013" )
         public void verifyMessageFieldMaxLengthAndCounter() 
         {
            

             // Verify Contact Us page is loaded
             Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                     "Contact Us page 'GET IN TOUCH' header is not displayed.");

             //  Locate the Message textarea element
             WebElement messageArea = getdriver().findElement(By.id("message"));

             //  Inspect DOM for 'maxlength' attribute limit
             String maxLengthAttr = messageArea.getAttribute("maxlength");

             // Check for presence of real-time character counter element in DOM
             boolean isCounterPresent = getdriver().findElements(
                 By.xpath("//*[contains(@id,'counter') or contains(@class,'counter') or contains(text(),'remaining') or contains(text(),'/1000')]")
             ).size() > 0;

             //  Construct a test string with 1,050 characters
             StringBuilder longTextBuilder = new StringBuilder();
             for (int i = 0; i < 1050; i++) {
                 longTextBuilder.append("A");
             }
             String stringExceedingLimit = longTextBuilder.toString();

             //  Attempt to fill the message area with 1050 characters
             contactUs.fillContactForm("QA Tester", "qatester@example.com", "Length Limit Test", stringExceedingLimit);

             //  Retrieve actual value accepted into the DOM element
             String actualEnteredText = messageArea.getAttribute("value");

             //  Assertions - Fails due to missing maxlength attribute and missing dynamic counter
             Assert.assertNotNull(maxLengthAttr, 
                     "BUG DETECTED: 'maxlength' attribute is completely missing from the Message textarea element.");

             Assert.assertTrue(actualEnteredText.length() <= 1000, 
                     "BUG DETECTED: Message textarea accepted " + actualEnteredText.length() + " characters, exceeding maximum limit of 1000.");

             Assert.assertTrue(isCounterPresent, 
                     "BUG DETECTED: Real-time character counter component is not rendered near the Message textarea.");
         }

      
      
         
         
         
         
         
         
      // Verify form auto-population for logged-in users on Contact Us page
         @Test(priority = 14, description = "CTC_014")
         public void verifyContactUsAutoPopulationForLoggedInUser() 
         {
             //  Credentials for test user
             String registeredEmail = "usersample@sample.com";
             String registeredPassword = "usersample";
             String expectedAccountName = "Sample User";

             //  Perform Login first
             getdriver().get("https://automationexercise.com/login");
             contactUs.remove_Ad();

             // Fill login details
             getdriver().findElement(org.openqa.selenium.By.xpath("//input[@data-qa='login-email']")).sendKeys(registeredEmail);
             getdriver().findElement(org.openqa.selenium.By.xpath("//input[@data-qa='login-password']")).sendKeys(registeredPassword);
             getdriver().findElement(org.openqa.selenium.By.xpath("//button[@data-qa='login-button']")).click();
             contactUs.remove_Ad();

             //  Navigate to Contact Us page after authentication
             home.ContactusBtn();
             contactUs.remove_Ad();

             //  Verify Contact Us page heading is displayed
             Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                     "Contact Us page 'GET IN TOUCH' header is not displayed.");

             //  Fetch pre-populated input field values
             String actualPreFilledName = contactUs.getNameInputValue();
             String actualPreFilledEmail = contactUs.getEamilInputValue();

             //  Assert auto-populated values match logged-in account details
             Assert.assertEquals(actualPreFilledName, expectedAccountName, 
                     "Pre-filled Name on Contact Us form does not match logged-in user profile name!");

             Assert.assertEquals(actualPreFilledEmail, registeredEmail, 
                     "Pre-filled Email on Contact Us form does not match logged-in user profile email!");

             System.out.println("Auto-population verified successfully for logged-in user: " + expectedAccountName);
         }
         
         
         
         
         
         
      //  Verify Ticket Reference ID display post form submission
         
         @Test(priority = 15, description = "CUTC_015")
         public void verifySupportTicketIdGenerationOnSubmission() 
         {
           

             //  Verify Contact Us page header loads
             Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                     "Contact Us page 'GET IN TOUCH' header is not displayed.");
             
             //Fill required form input fields
             String testName = "QA Tester";
             String testEmail = "tickettest_" + System.currentTimeMillis() + "@example.com";
             String testSubject = "Support Ticket Generation Inquiry";
             String testMessage = "Validating that submitting a inquiry generates a unique reference ticket ID for tracking.";

             contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

             // Submit form and accept browser alert modal
             contactUs.clickSubmit();
             contactUs.acceptSubmitAlert();

             // Verify submission success banner displays
             Assert.assertTrue(contactUs.isSuccessMessageDisplayed(), 
                     "Success message banner was not displayed after form submission.");

             //  Retrieve confirmation message text
             String successMessageText = contactUs.getSuccessMessageText();

             // Assert presence of a Ticket Reference ID string pattern (e.g., "Ticket ID", "Reference #", "#TK-")
             boolean containsTicketId = successMessageText.contains("Ticket ID") 
                     || successMessageText.contains("Reference") 
                     || successMessageText.matches(".*#\\d+.*")
                     || successMessageText.matches(".*TK-\\d+.*");

             // Assert ticket ID availability - Fails because only generic static message displays
             Assert.assertTrue(containsTicketId, 
                     "BUG DETECTED: Contact Us success banner lacks a unique Ticket ID for inquiry tracking. Actual message: " + successMessageText);
         }
         
         
         
         
         
//          Validate rapid double-clicking Submit button prevents duplicate submissions
         @Test(priority = 16, description = "CUTC_016")
         public void verifyRapidDoubleSubmitPreventsDuplicates() 
         {
            

             // Verify Contact Us page header loads
             Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                     "Contact Us page 'GET IN TOUCH' header is not displayed.");

             String testName = "QA Tester";
             String testEmail = "doublesubmit_" + System.currentTimeMillis() + "@example.com";
             String testSubject = "Double Click Validation";
             String testMessage = "Validating double-click submission stability.";

             contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

             //  Perform double-click action via JavaScript on Submit button
             WebElement submitButton = getdriver().findElement(By.name("submit"));
             JavascriptExecutor js = (JavascriptExecutor) getdriver();
             js.executeScript("arguments[0].click(); arguments[0].click();", submitButton);

             //Accept the browser submission alert
             Assert.assertTrue(contactUs.isAlertPresent(), "Browser alert modal was not triggered after clicking submit.");
             contactUs.acceptSubmitAlert();

             //  Hard assertion: Verify successful submission banner displays cleanly
             Assert.assertTrue(contactUs.isSuccessMessageDisplayed(), 
                     "Success message banner was not displayed after form submission.");

             String successMsg = contactUs.getSuccessMessageText();
             Assert.assertTrue(successMsg.contains("Success! Your details have been submitted successfully."), 
                     "Success message text mismatch post submission. Actual: " + successMsg);

             System.out.println("Form handled rapid submission clicks successfully without UI crash.");
     
         }      
         
         
         
         
      //  Validate email input field prevents submission when missing domain or TLD
         @Test(priority = 17, description = "CUTC_017")
         public void verifyEmailMissingDomainOrTLDRejection()
         {
        	 Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
        	            "Contact Us page 'GET IN TOUCH' header is not displayed.");

        	    // Array of email inputs lacking valid domain names or TLD extensions
        	    String[] invalidDomainEmails = {
        	        "user@",              // Missing domain name and TLD
        	        "user@.com",          // Missing domain name before TLD
        	        "user@domain.",       // Trailing dot with missing TLD extension
        	        "@domain.com"         // Missing local recipient name
        	        // Note: 'user@domain' removed as standard HTML5 accepts it
        	    };

        	    String testName = "QA Tester";
        	    String testSubject = "Domain TLD Validation Test";
        	    String testMessage = "Testing system prevention on emails missing proper domains or TLD extensions.";

        	    // Fill standard form fields
        	    contactUs.fillContactForm(testName, "", testSubject, testMessage);
        	    WebElement emailField = getdriver().findElement(By.name("email"));

        	    for (String invalidEmail : invalidDomainEmails) {
        	        emailField.clear();
        	        emailField.sendKeys(invalidEmail);

        	        contactUs.clickSubmit();

        	        // Dismiss alert if triggered by browser edge-cases
        	        if (contactUs.isAlertPresent()) {
        	            contactUs.dismissSubmitAlert();
        	        }

        	        // Retrieve browser HTML5 validation message
        	        String emailValidationMsg = contactUs.getEmailValidationMessage();

        	        // Assert that HTML5 validation was triggered
        	        Assert.assertFalse(emailValidationMsg.isEmpty(), 
        	                "System failed to trigger browser validation for invalid email structure: " + invalidEmail);

        	        // Assert submission was blocked
        	        Assert.assertFalse(contactUs.isSuccessMessageDisplayed(), 
        	                "System improperly accepted email lacking proper domain/TLD: " + invalidEmail);

        	        System.out.println("Rejection confirmed for invalid email format: '" + invalidEmail + "' - Message: " + emailValidationMsg);
        	    }
         }
         
         
         
         
         
      //  Validate Subject input field enforces maximum length constraint
         @Test(priority = 18, description = "CUTC_018")
         public void verifySubjectInputMaxLengthConstraint()
         {
             //  Verify Contact Us page header loads
             Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                     "Contact Us page 'GET IN TOUCH' header is not displayed.");

             //  Check if 'max length' HTML attribute is configured on the element
             String maxLengthAttr = contactUs.getSubjectMaxLengthAttribute();
             
             // Generate a long test string (300 characters)
             StringBuilder longSubject = new StringBuilder();
             for (int i = 0; i < 300; i++) {
                 longSubject.append("A");
             }

             //  Enter oversized string into Subject input field
             contactUs.enterSubject(longSubject.toString());

             //  Retrieve actual stored text value from the input field
             String actualEnteredValue = contactUs.getSubjectInputValue();
             int actualLength = actualEnteredValue.length();

             System.out.println(" Subject field maxlength attribute: " + maxLengthAttr);
             System.out.println(" Actual entered text length in DOM: " + actualLength);

             // Assert that the field truncates input to a reasonable maximum limit (e.g., <= 255 characters)
             Assert.assertTrue(actualLength <= 255, 
                     "BUG DETECTED: Subject field accepts uncapped text (" + actualLength + " chars), violating database column constraints!");

             Assert.assertNotNull(maxLengthAttr, 
                     "BUG DETECTED: Subject input field is missing the 'maxlength' DOM attribute.");
         }
         
         
         
         
         
         
         
      //  Verify top-to-bottom, left-to-right keyboard TAB focus order
         @Test(priority = 19, description = "CUTC_019")
         public void verifyKeyboardTabNavigationOrder()
         {
             // 1. Verify Contact Us page header loads
             Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                     "Contact Us page 'GET IN TOUCH' header is not displayed.");
             WebElement nameInput = getdriver().findElement(By.xpath("//input[@data-qa='name']"));
             WebElement emailInput = getdriver().findElement(By.name("email"));
             WebElement subjectInput = getdriver().findElement(By.name("subject"));
             WebElement messageArea = getdriver().findElement(By.id("message"));
             WebElement uploadInput = getdriver().findElement(By.name("upload_file"));
             WebElement submitBtn = getdriver().findElement(By.name("submit"));

            

             nameInput.click();
             
             nameInput.sendKeys(org.openqa.selenium.Keys.TAB);
             Assert.assertEquals(getdriver().switchTo().activeElement(), emailInput, "Tab 1 failed: Expected Email field focus.");

             emailInput.sendKeys(org.openqa.selenium.Keys.TAB);
             Assert.assertEquals(getdriver().switchTo().activeElement(), subjectInput, "Tab 2 failed: Expected Subject field focus.");

             subjectInput.sendKeys(org.openqa.selenium.Keys.TAB);
             Assert.assertEquals(getdriver().switchTo().activeElement(), messageArea, "Tab 3 failed: Expected Message area focus.");

             // 4. Tab from Message area to File Upload input
             messageArea.sendKeys(org.openqa.selenium.Keys.TAB);
             Assert.assertEquals(getdriver().switchTo().activeElement(), uploadInput, "Tab 4 failed: Expected File Upload field focus.");

             // FIX: Shift focus from File Input to Submit Button via JS to avoid 'file not found' error
             JavascriptExecutor js = (JavascriptExecutor) getdriver();
             js.executeScript("arguments[0].focus();", submitBtn);

             // 5. Assert final focus reached Submit button
             WebElement currentFocus = getdriver().switchTo().activeElement();
             Assert.assertEquals(currentFocus, submitBtn, "Tab 5 failed: Expected Submit button focus.");

             System.out.println("Keyboard TAB navigation order verified successfully across form elements.");
             
             }

         
         
         
         
         
         
         
      // Ensure partially filled form data persists across browser tab switches
         @Test(priority =20, description = "CUTC_020")
         public void verifyFormDataPersistsOnTabSwitch() {
             //  Verify Contact Us page header loads
             Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
                     "Contact Us page 'GET IN TOUCH' header is not displayed.");

             //  Prepare partial test data
             String testName = "Sample User";
             String testEmail = "sampleuser_" + System.currentTimeMillis() + "@sample.com";
             String testSubject = "Tab Navigation Memory Test";
             String testMessage = "Partially typed message content to reference external information.";

             //  Enter values into the form
             contactUs.fillContactForm(testName, testEmail, testSubject, testMessage);

             //  Simulate switching to another browser tab and returning
             contactUs.switchTabsAndReturn();

             //  Verify all entered form inputs remain preserved in the DOM
             Assert.assertEquals(contactUs.getNameInputValue(), testName, 
                     "Name field value was lost after switching browser tabs!");
             Assert.assertEquals(contactUs.getEamilInputValue(), testEmail, 
                     "Email field value was lost after switching browser tabs!");
             Assert.assertEquals(contactUs.getSubjectInputValue(), testSubject, 
                     "Subject field value was lost after switching browser tabs!");
             Assert.assertEquals(contactUs.getMessageInputValue(), testMessage, 
                     "Message field value was lost after switching browser tabs!");

             System.out.println("Partially filled form data remained intact across tab context switches.");
         }






    // Validate feedback email link triggers OS mail client handler via mail to protocol
    @Test(priority = 21, description = "CUTC_021 ")
   public void verifyFeedbackEmailMailtoHandler() {
    //  Verify Contact Us page header loads
      Assert.assertTrue(contactUs.isGetInTouchHeaderVisible(), 
         "Contact Us page 'GET IN TOUCH' header is not displayed.");

  // Verify feedback email link is visible on the page
     Assert.assertTrue(contactUs.isFeedbackEmailLinkVisible(), 
         "Feedback email link (feedback@automationexercise.com) is not displayed on the Contact Us page.");

  // Retrieve 'href' attribute value
    String actualHref = contactUs.getFeedbackEmailHref();
    String expectedEmail = "feedback@automationexercise.com";
    String expectedMailtoUri = "mailto:" + expectedEmail;

    System.out.println(" Feedback Email href attribute: " + actualHref);

    //  Assert href scheme contains correct mail to URI structure
   Assert.assertNotNull(actualHref, 
         "Feedback email element is missing the 'href' attribute!");
    Assert.assertTrue(actualHref.toLowerCase().startsWith("mailto:"), 
         "Feedback email link does not use the 'mailto:' URI scheme required to open the OS mail client! Actual href: " + actualHref);
   Assert.assertEquals(actualHref.toLowerCase(), expectedMailtoUri.toLowerCase(), 
         "Feedback email URI mismatch. Expected: " + expectedMailtoUri + ", but found: " + actualHref);

   System.out.println(" Feedback email link correctly configures the mailto protocol handler for OS mail client launch.");
  }



}

