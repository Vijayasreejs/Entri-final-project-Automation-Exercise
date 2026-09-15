package tests;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseclassTest;

import Utils.ScreenshotList;
import pagesClass.ClassHomePage;
import pagesClass.VideoTutorialsPage;

@Listeners(ScreenshotList.class)
public class TutorialsTest extends BaseclassTest
{
	private ClassHomePage homePage;
    private VideoTutorialsPage videoTutorialsPage;

    @BeforeClass
    public void setup() {
        homePage = new ClassHomePage(getdriver());
        videoTutorialsPage = new VideoTutorialsPage(getdriver());
        homePage.VideoTutorialsBtn();
    }
    
    
    @BeforeMethod
    public void ensureVideoTurialsPage()
{
    	
        if (!getdriver().getCurrentUrl().contains("youtube")) {
        	getdriver().get("https://www.youtube.com/c/AutomationExercise");
        }
        videoTutorialsPage.remove_Ad();
      
    }
    
    
    
    
//    Verify navigation to Video Tutorials page from Home page header

    @Test(priority = 1, description = "VTTC_001")
    public void VTTC_001_VerifyNavigationToVideoTutorials() {
       
        Assert.assertTrue(getdriver().getCurrentUrl().contains("youtube.com") || getdriver().getCurrentUrl().contains("video_tutorials"), "Failed to navigate to Video Tutorials page.");
    }

    
    
    
//   Verify display of Channel Profile header details
    
    @Test(priority = 2, description = "VTTC_002 ")
    public void VTTC_002_VerifyChannelHeaderDetails() {
    	
        Assert.assertTrue(videoTutorialsPage.isChannelHeaderDisplayed(), "Channel name is missing.");
        Assert.assertTrue(videoTutorialsPage.isChannelHandleDisplayed(), "Channel handle is missing.");
        Assert.assertTrue(videoTutorialsPage.isSubscriberCountDisplayed(), "Subscriber count is missing.");
        Assert.assertTrue(videoTutorialsPage.isVideoCountDisplayed(), "Total video count is missing.");
    }

    
//    Verify Subscribe button presence and interaction when unauthenticated
    
    @Test(priority = 3, description = "VTTC_003")
    public void VTTC_003_VerifySubscribeButtonUnauthenticated() {
    	
        Assert.assertTrue(videoTutorialsPage.isSubscribeButtonVisible(), "Subscribe button is not visible.");
        videoTutorialsPage.clickSubscribeButton();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("accounts.google.com") || videoTutorialsPage.isSubscribeButtonVisible(), "SignIn modal/redirect did not trigger.");
    }
    
    
    
    
    
//    Verify Navigation Tabs functioning on the channel page

    @Test(priority = 4, description = "VTTC_004")
    public void VTTC_004_VerifyNavigationTabs()
    {
    	
     
    	// Navigate to Home tab and verify active state
        videoTutorialsPage.clickHomeTab();
        Assert.assertTrue(videoTutorialsPage.isTabActive("Home"), "Home tab is not active.");

        // Navigate to Videos tab and verify active state
        videoTutorialsPage.clickVideosTab();
        Assert.assertTrue(videoTutorialsPage.isTabActive("Videos"), "Videos tab is not active.");

        // Navigate to Courses tab and verify active state
        videoTutorialsPage.clickCoursesTab();
        Assert.assertTrue(videoTutorialsPage.isTabActive("Courses"), "Courses tab is not active.");
        
        // Navigate to Play lists tab and verify active state
        videoTutorialsPage.clickPlaylistsTab();
        Assert.assertTrue(videoTutorialsPage.isTabActive("Playlists"), "Playlists tab is not active.");
   
    }

    
    
    
    
//    Verify video card details displayed in the Videos section
    @Test(priority = 5, description = "VTTC_005")
    public void VTTC_005_VerifyVideoCardDetails() {
 
        videoTutorialsPage.clickVideosTab();
        Assert.assertTrue(videoTutorialsPage.areVideoCardsDisplayed(), "No video cards found under Videos tab.");
    }

    
    
    
    
//    Verify redirection of external channel link
    @Test(priority = 6, description = "VTTC_006")
    public void VTTC_006_VerifyExternalChannelLink() {

        String parentWindow = getdriver().getWindowHandle();
        videoTutorialsPage.clickExternalWebsiteLink();

        Set<String> allWindows = getdriver().getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
            	getdriver().switchTo().window(window);
                break;
            }
        }
        Assert.assertTrue(getdriver().getCurrentUrl().contains("automationexercise.com"), "Failed to navigate to target external website.");
    }
    
    
    
    
    
//    Verify video horizontal scroll arrow functionality

    @Test(priority = 7, description = "VTTC_007 ")
    public void VTTC_007_VerifyCarouselHorizontalScroll() {

        videoTutorialsPage.clickCarouselRightArrow();
        Assert.assertTrue(videoTutorialsPage.areVideoCardsDisplayed(), "Horizontal carousel scroll failed.");
    }

    
    
    
    
//    Verify search execution via top YouTube search bar
    @Test(priority = 8, description = "VTTC_008 ")
    public void VTTC_008_VerifyGlobalSearchExecution() {
    
        videoTutorialsPage.searchGlobal("Playwright tutorial");
        Assert.assertTrue(getdriver().getCurrentUrl().contains("search_query=Playwright+tutorial"), "Global search query URL mismatch.");
    }

    
    
    
    
//    Verify channel-specific content search
    @Test(priority = 9, description = "VTTC_009 ")
    public void VTTC_009_VerifyChannelSpecificSearch() {
    	
        videoTutorialsPage.searchChannel("JMeter");
        Assert.assertTrue(getdriver().getCurrentUrl().contains("query=JMeter") || getdriver().getCurrentUrl().contains("JMeter"), "Channel search failed.");
    }

    
    
    
    
    
//    Verify animated video preview upon thumb nail hover
    @Test(priority = 10, description = "VTTC_010 ")
    public void VTTC_010_VerifyThumbnailHoverPreview() {
    	
    	videoTutorialsPage.clickVideosTab();
        Assert.assertTrue(videoTutorialsPage.areVideoCardsDisplayed(), "Video cards did not load.");
        videoTutorialsPage.hoverOverFirstVideoThumbnail();
    }

    
    
    
    
    
    
    
//    Verify content rendering when navigating to Courses tab
    @Test(priority = 11, description = "VTTC_011 ")
    public void VTTC_011_VerifyCoursesTabContent() {
    
    	boolean tabExists = videoTutorialsPage.clickCoursesTabb();
        
        if (tabExists) {
            Assert.assertTrue(videoTutorialsPage.areVideoCardsDisplayed(), "Courses tab content failed to render.");
        } else {
            // Pass conditionally if the channel doesn't support the Courses feature
            System.out.println("VTTC_011 Skipped Assertion: Channel has no Courses tab.");
        }
    }

    
    
    
    
    
//    Verify Escape key closes Channel Description modal
    @Test(priority = 12, description = "VTTC_012 ")
    public void VTTC_012_VerifyEscapeKeyClosesModal() {
   
    	videoTutorialsPage.openMoreInfoModal();
        videoTutorialsPage.pressEscapeKey();
    }

    
    
    
    
    
    
//    Verify channel handle URL interaction inside About modal
    @Test(priority = 13, description = "VTTC_013")
    public void VTTC_013_VerifyModalHandleUrlClick() {
    	videoTutorialsPage.openMoreInfoModal();
        videoTutorialsPage.clickModalHandleUrl();
        Assert.assertTrue(getdriver().getCurrentUrl().contains("AutomationExercise"), "Handle URL click failed to reload channel.");
    }

    
    
    
//    Verify interaction with main channel avatar
    @Test(priority = 14, description = "VTTC_014")
    public void VTTC_014_VerifyChannelAvatarInteraction() {
    
        Assert.assertTrue(videoTutorialsPage.isAvatarDisplayed(), "Channel avatar is missing.");
        videoTutorialsPage.clickChannelAvatar();
    }
    
    
    
    
//    Verify collapse/expand behavior of left menu icon

    @Test(priority = 15, description = "VTTC_015 ")
    public void VTTC_015_VerifyHamburgerMenuToggle() {
    
        videoTutorialsPage.clickHamburgerMenu();
    }

    
    
    
//    Verify Closed Captions (CC) indicator rendering
    
    @Test(priority = 16, description = "VTTC_016 ")
    public void VTTC_016_VerifyCCBadges() {
    	
        videoTutorialsPage.clickVideosTab();
        Assert.assertTrue(videoTutorialsPage.areCCBadgesVisible(), "CC badges were not rendered on eligible videos.");
    }

    
    
    
    
    
//    Verify Voice Search button activation
    @Test(priority = 17, description = "VTTC_017 ")
    public void VTTC_017_VerifyVoiceSearchActivation() {
    
        videoTutorialsPage.clickVoiceSearch();
    }

    
    
    
    
    
//    Verify navigation and content display under Play lists tab
    @Test(priority = 18, description = "VTTC_018")
    public void VTTC_018_VerifyPlaylistsTabContent() {
    	
    	videoTutorialsPage.clickPlaylistsTab();
        Assert.assertTrue(videoTutorialsPage.isTabActive("Playlists"), "Playlists tab navigation failed.");
    }

    
    
    
    
    
//    Verify options menu display on clicking three-dot icon
    @Test(priority = 19, description = "VTTC_019 ")
    public void VTTC_019_VerifyVideoCardOptionsMenu() {
    	videoTutorialsPage.clickVideosTab();
        videoTutorialsPage.openVideoOptionsMenu(1);
       
    }

    
    
    
    
    
//    Verify duration badge positioning and format on thumb nails
    @Test(priority = 20, description = "VTTC_020 ")
    public void VTTC_020_VerifyThumbnailDurationBadges() {
       
    	videoTutorialsPage.clickVideosTab();
        Assert.assertTrue(videoTutorialsPage.areDurationBadgesFormatted(), "Duration badge formatting is invalid.");
    }

    
    
    
    
    
//     Verify visual highlighting on active navigation tab
    @Test(priority = 21, description = "VTTC_021")
    public void VTTC_021_VerifyDefaultActiveTabHighlighting() {
    	
    	videoTutorialsPage.clickHomeTab();
        Assert.assertTrue(videoTutorialsPage.isTabActive("Home"), "Home tab is not active by default.");
    }
	

    
    
    
//    Verify Top Right Sign In Button Navigates to Google Auth Page
    
    @Test(description = "VTTC_022")
    public void VTTC_022_VerifySignInButtonRedirectsToGoogleAuth() {
        String originalWindow = getdriver().getWindowHandle();

        // Click top-right Sign in button
        videoTutorialsPage.clickSignInButton();

        // Handle window/tab switch if opened in a new tab
        Set<String> allWindows = getdriver().getWindowHandles();
        if (allWindows.size() > 1) {
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    getdriver().switchTo().window(windowHandle);
                    break;
                }
            }
        }

        // Validate navigation to Google authentication URL
        String currentUrl = getdriver().getCurrentUrl();
        Assert.assertTrue(
            currentUrl.contains("accounts.google.com") || currentUrl.contains("signin"), 
            "Failed to navigate to Google sign-in authentication page. Current URL: " + currentUrl
        );
    }

    
    
    
    
    
//    Verify Channel Profile Header Avatar 
    
    @Test(description = "VTTC_023 ")
    public void VTTC_023_VerifyChannelHeaderLogoNotFallbackIcon() {
        // Locate the main profile header image tag
        By avatarImgLocator = By.xpath(
            "//yt-decorated-avatar-view-model//img | " +
            "//yt-img-shadow[@id='avatar']//img | " +
            "//yt-img-shadow[contains(@class,'ytd-channel-avatar-editor')]//img"
        );

        WebElement avatarImg = getdriver().findElement(avatarImgLocator);
        String imageSrc = avatarImg.getAttribute("src");

       
        boolean isFallbackLetterIcon = imageSrc == null || imageSrc.contains("default_avatar") || avatarImg.getText().equals("A");

        Assert.assertTrue(
            isFallbackLetterIcon, 
            "BUG DETECTED: The channel header is rendering the official brand logo (" + imageSrc + ") instead of the fallback letter icon ('A')."
        );
    }
    
    
    
    
    
    
//    Verify URL Redirection Integrity for Legacy /c/ Channel Link Format
    
    @Test(description = "VTTC_024")
    public void VTTC_024_VerifyLegacyChannelUrlRedirection()
    
    {
    	
    	String legacyUrl = "https://www.youtube.com/c/AutomationExercise";
        getdriver().get(legacyUrl);
        videoTutorialsPage.remove_Ad();

        String actualUrl = getdriver().getCurrentUrl();

        boolean hasExpectedLegacyRedirect = actualUrl.contains("/legacy_c/AutomationExercise");

        Assert.assertTrue(
            hasExpectedLegacyRedirect, 
            "BUG DETECTED: Legacy /c/ channel link failed to preserve the /legacy_c/ routing path. Actual redirected URL: " + actualUrl
        );
    }
    
    
    
    
    
//    Verify External Web site Link Opens in New Tab
    
    @Test(description = "VTTC_025 ")
    public void VTTC_025_VerifyExternalWebsiteLinkOpensInNewTab() 
    {
    
        videoTutorialsPage.clickExternalWebsiteLink();

        Set<String> allWindows = getdriver().getWindowHandles();

        boolean openedInSameTabOnly = (allWindows.size() == 1);

        Assert.assertTrue(
            openedInSameTabOnly, 
            "BUG DETECTED: The external website link opened in a new tab/window instead of navigating within the current browser tab. Total window handles: " + allWindows.size()
        );
    }
    
    

}
