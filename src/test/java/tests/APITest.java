package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseclassTest;
import pagesClass.APIClass;
import Utils.ScreenshotList;

@Listeners(ScreenshotList.class)
public class APITest extends BaseclassTest
{

	private APIClass apiPage;
	

    @BeforeClass
    public void initPage() {
        apiPage = new APIClass(getdriver());
      
    }

    @BeforeMethod
    public void ensureApiPage() {
        if (!getdriver().getCurrentUrl().contains("api_list")) {
            apiPage.navigateToApiPageDirectly();
        }
        apiPage.removeAd();
      
    }

    // TCAP_001: Verify API List page access and URL verification
    @Test(priority = 1, description = "TCAP_001")
    public void test_ApiPageLoads() {
    	
        Assert.assertTrue(apiPage.isApiPageLoaded(), "API List Page failed to load correctly.");
        System.out.println("[TCAP_001] API Testing page URL and load assertion passed.");
    }

    // TCAP_002: Verify API List Page Main Heading Display
    @Test(priority = 2, description = "TCAP_002")
    public void test_ApiPageHeadingDisplay() {
    	String heading = apiPage.getApisListHeadingText().replaceAll("\\s+", " ").trim();
        Assert.assertTrue(heading.equalsIgnoreCase("APIs List for practice"), 
                "Main heading 'APIs List' is missing or incorrect. Actual text: '" + heading + "'");
        System.out.println("[TCAP_002] Main Heading display assertion passed.");
    }

    // TCAP_003: Verify total count of API end points listed on the page
    @Test(priority = 3, description = "TCAP_003")
    public void test_ApiPanelsCount() {
        int count = apiPage.getApiPanelsCount();
        Assert.assertTrue(count > 0, "No API panels found on the API Testing page.");
        System.out.println("[TCAP_003] Total API list panels count verified: " + count);
    }

    //Verify API 1 (Get All Products List) accordion expand and response contents
    @Test(priority = 4, description = "TCAP_004")
    public void test_VerifyApi1GetAllProductsList() {
       
        int apiIndex = 0; // 0 represents API 1
        
    
        apiPage.clickApiHeaderByIndex(apiIndex);
        
        
        String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);
        
     
        Assert.assertTrue(responseCodeText.contains("200"), 
                "Expected status code 200 not found in: " + responseCodeText);
    }
	
    
    
    //Verify API 2 Verify that sending a POST request to the products list endpoint
//    returns an unsupported/405 error code.
    @Test(priority = 5, description = "TCAP_005 ")
    public void test_PostToAllProductsList() {
       
       int apiIndex = 1; // 1 represents API 2 (POST To All Products List)
        
       
        apiPage.clickApiHeaderByIndex(apiIndex);
        
       
        String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);
        
       
        Assert.assertTrue(responseCodeText.contains("200"), 
                "BUG FOUND: Expected status code 200, but found: " + responseCodeText);
    }
  
    
    
    //Verify API 3 Verify fetching all brands via GET request.
    @Test(priority = 6, description = "TCAP_006")
    public void test_VerifyAllBrandsList() {
       
        int apiIndex = 2; // 2 represents API 3
   
        apiPage.clickApiHeaderByIndex(apiIndex);
        
        String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);
        
 
        Assert.assertTrue(responseCodeText.contains("200"), 
                "Expected status code  not found.Got Response code: " + responseCodeText);
    }
    
    
    
    
    
    
    
    //Verify API 4 Verify Put to all brand llist 
  @Test(priority = 7, description = "TCAP_007 ")
  public void test_PutToALLBrandList() {
     
	  int apiIndex = 3; // 3 represents API 4 
      
 
      apiPage.clickApiHeaderByIndex(apiIndex);
      
   
      String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);
      
      Assert.assertTrue(responseCodeText.contains("200"), 
              "BUG FOUND: Expected status code 200, but found: " + responseCodeText);
  }

  
  
  
  
  
  
  //Verify API 5 Verify Post to Search Product with parameter 
@Test(priority = 8, description = "TCAP_008 ")
public void test_searchProduct() {
   
   int apiIndex = 4; // 4 represents API 5
    

    apiPage.clickApiHeaderByIndex(apiIndex);
    
 
    String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);
    
    Assert.assertTrue(responseCodeText.contains("405"), 
            "BUG FOUND: Expected status code 405, but found: " + responseCodeText);
}

	




//Verify API 6 Verify Post to Search Product without parameter
@Test(priority = 9, description = "TCAP_009 ")
public void test_searchProductwithoutParameter() {
 
 int apiIndex = 5; // 5 represents API 6 
  

  apiPage.clickApiHeaderByIndex(apiIndex);
  

  String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);
  
  Assert.assertTrue(responseCodeText.contains("405"), 
          "BUG FOUND: Expected status code 405, but found: " + responseCodeText);
}




//Verify API 7 Verify Login with valid credentials
@Test(priority = 10, description = "TCAP_010 ")
public void test_LoginValid() {

int apiIndex = 6; // 6 represents API 7 


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("405"), 
        "BUG FOUND: Expected status code 405, but found: " + responseCodeText);
}




//Verify API 8 Verify login without email parameter
@Test(priority =11, description = "TCAP_011 ")
public void test_LoginwithoutEmail() {

int apiIndex = 7; // 7 represents API 8


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("405"), 
      "BUG FOUND: Expected response code is not found.Got Code: " + responseCodeText);
}



//Verify API 9 Delete to verify login
@Test(priority = 12, description = "TCAP_012 ")
public void test_DeleteLogin() {

int apiIndex = 8; // 8 represents API 9 


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("405"), 
    "BUG FOUND: Expected response code 405, but found: " + responseCodeText);
}






//Verify API 10 Verify Login with invalid credentials
@Test(priority = 13, description = "TCAP_013 ")
public void test_PostLoginInvalid() {

int apiIndex = 9; // 9 represents API 10


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("405"), 
  "BUG FOUND: Expected response code 405, but found: " + responseCodeText);
}




//Verify API 11 create new user 
@Test(priority = 14, description = "TCAP_014 ")
public void test_NewUserCreate() {

int apiIndex = 10; // 10 represents API 11


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("405"), 
"BUG FOUND: Expected response code 405, but found: " + responseCodeText);
}




//Verify API 12 Delete new user 
@Test(priority =15, description = "TCAP_015 ")
public void test_deleteUserReg() {

int apiIndex = 11; // 11 represents API 12


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("405"), 
"BUG FOUND: Expected response code 405 , but found: " + responseCodeText);
}



//Verify API 13 PUT method to update user 
@Test(priority =16, description = "TCAP_016 ")
public void test_UpdateUser() {

int apiIndex = 12; // 12 represents API 13


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("405"), 
"BUG FOUND: Expected response code 405 , but found: " + responseCodeText);
}






//Verify API 14 Get user account by details 
@Test(priority = 17, description = "TCAP_017 ")
public void test_GetUserAcc() {

int apiIndex = 13; // 13 represents API 14


apiPage.clickApiHeaderByIndex(apiIndex);


String responseCodeText = apiPage.getResponseCodeByIndex(apiIndex);

Assert.assertTrue(responseCodeText.contains("400"), 
"BUG FOUND: Expected response code 400 , but found: " + responseCodeText);
}





//Verify footer subscription with valid email

@Test(priority = 18, description = "TCAP_018")
public void test_VerifyFooterSubscriptionWithValidEmail() {
    String validEmail = "testuser_" + System.currentTimeMillis() + "@example.com";

    // 1. Scroll down to footer
    apiPage.scrollToFooter();

    // 2. Verify 'SUBSCRIPTION' header is displayed
    Assert.assertTrue(apiPage.isSubscriptionHeadingDisplayed(), 
            "Subscription heading is not displayed in the footer.");

    // 3. Enter valid email address and click subscribe button
    apiPage.enterSubscriptionEmail(validEmail);
    apiPage.clickSubscribeButton();

    // 4. Verify success message 'You have been successfully subscribed!' is visible
    String successMessage = apiPage.getSuccessAlertText();
    Assert.assertTrue(successMessage.contains("You have been successfully subscribed!"), 
            "Subscription success message not displayed or incorrect. Actual text: " + successMessage);

    System.out.println("[TC_Subscription] Footer subscription verified successfully with email: " + validEmail);
}





//Verify clicking the feedback email link points to the mailto scheme
@Test(priority = 19, description = "TCAP_019")
public void test_FeedbackEmailLinkProtocol() {
 String expectedHref = "mailto:feedback@automationexercise.com";
 String actualHref = apiPage.getFeedbackEmailHref();

 Assert.assertNotNull(actualHref, "Feedback email link href attribute is missing.");
 Assert.assertTrue(actualHref.equalsIgnoreCase(expectedHref), 
         "Expected href to be '" + expectedHref + "', but found: '" + actualHref + "'");

 System.out.println(" Feedback email link mailto validation passed.");
}


}
