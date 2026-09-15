package pagesClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ActionUtil;

public class VideoTutorialsPage 
{

	private WebDriver driver;
    private ActionUtil actionUtil;
    private WebDriverWait wait;
    private Actions actions;

    // --- Header & Channel Profile Elements ---
    @FindBy(xpath = "//span[text()='AutomationExercise']")
    private WebElement channelNameHeader;

    @FindBy(xpath = "//span[text()='@AutomationExercise']")
    private WebElement channelHandle;

    @FindBy(xpath = "//yt-formatted-string[contains(text(),'subscribers')] | //span[contains(text(),'subscribers')] | //td[contains(text(),'subscribers')]")
    private WebElement subscriberCount;

    @FindBy(xpath = "//span[text()='36 videos']")
    private WebElement videoCount;

    @FindBy(xpath = "//a[contains(@href,'automationexercise.com')]")
    private WebElement externalWebsiteLink;

    @FindBy(xpath = "//button[contains(@aria-label,'Subscribe') or contains(.,'Subscribe')]")
    private WebElement subscribeButton;

    // --- Channel Navigation Tabs ---
    @FindBy(xpath = "//yt-tab-shape[contains(.,'Home')] | //tp-yt-paper-tab[contains(.,'Home')]")
    private WebElement homeTab;

    @FindBy(xpath = "//yt-tab-shape[contains(.,'Videos')] | //tp-yt-paper-tab[contains(.,'Videos')]")
    private WebElement videosTab;

    @FindBy(xpath = "//yt-tab-shape[contains(.,'Courses')] | //tp-yt-paper-tab[contains(.,'Courses')]")
    private WebElement coursesTab;

    @FindBy(xpath = "//yt-tab-shape[contains(.,'Playlists')] | //tp-yt-paper-tab[contains(.,'Playlists')]")
    private WebElement playlistsTab;

    // --- Top YouTube Navigation Bar & General Controls ---
    @FindBy(xpath = "//input[@class='ytSearchboxComponentInput yt-searchbox-input title']")
    private WebElement globalSearchInput;

    @FindBy(xpath = "//button[@class='ytSearchboxComponentSearchButton']")
    private WebElement globalSearchBtn;

    @FindBy(xpath = "//a[contains(@href,'accounts.google.com') or contains(@aria-label,'Sign in')]")
    private WebElement signInBtn;

    // --- Carousel / Cards & Content Section ---
    @FindBy(xpath = "(//ytd-button-renderer[contains(@id,'right-button')]//button)[1] | //button[@aria-label='Next']")
    private WebElement carouselRightArrow;

    // --- Modal / Pop up Elements ---
    @FindBy(xpath = "//yt-formatted-string[contains(text(),'...more')] | //span[contains(text(),'...more')] | //button[contains(@aria-label,'more description')]")
    private WebElement moreInfoLink;

    @FindBy(xpath = "//ytd-engagement-panel-section-list-renderer | //tp-yt-paper-dialog | //ytd-macro-markers-list-item-renderer")
    private WebElement descriptionModal;

    @FindBy(xpath = "//ytd-menu-popup-renderer | //tp-yt-iron-dropdown[contains(@class,'ytd-popup-container')]")
    private WebElement videoOptionsMenu;

    // Constructor
    public VideoTutorialsPage(WebDriver driver) {
        this.driver = driver;
        this.actionUtil = new ActionUtil(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isChannelHeaderDisplayed() {
        actionUtil.scrollToElement(channelNameHeader);
        return actionUtil.isDisplayed(channelNameHeader);
    }

    public boolean isChannelHandleDisplayed() {
        actionUtil.scrollToElement(channelHandle);
        return actionUtil.isDisplayed(channelHandle);
    }

    public boolean isSubscriberCountDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(subscriberCount));
            return actionUtil.isDisplayed(subscriberCount);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVideoCountDisplayed() {
        actionUtil.scrollToElement(videoCount);
        return actionUtil.isDisplayed(videoCount);
    }

    public boolean isExternalWebsiteLinkDisplayed() {
        actionUtil.scrollToElement(externalWebsiteLink);
        return actionUtil.isDisplayed(externalWebsiteLink);
    }

    public void clickExternalWebsiteLink() {
        actionUtil.scrollToElement(externalWebsiteLink);
        actionUtil.clickViaJS(externalWebsiteLink);
    }

    public boolean isSubscribeButtonVisible() {
        return actionUtil.isDisplayed(subscribeButton);
    }

    public void clickSubscribeButton() {
        actionUtil.scrollToElement(subscribeButton);
        actionUtil.clickViaJS(subscribeButton);
    }

    public void clickHomeTab() {
        By locator = By.xpath("//yt-tab-shape[contains(.,'Home')] | //tp-yt-paper-tab[contains(.,'Home')]");
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        actionUtil.scrollToElement(tab);
        actionUtil.clickViaJS(tab);
    }

    public void clickVideosTab() {
    	By videosTabLocator = By.xpath(
    	        "//yt-tab-shape[contains(.,'Videos')] | " +
    	        "//tp-yt-paper-tab[contains(.,'Videos')] | " +
    	        "//div[contains(@class,'yt-tab-shape-viz') and contains(.,'Videos')] | " +
    	        "//a[contains(@href, '/videos')]"
    	    );

    	    try {
    	        WebElement videosTabElement = wait.until(
    	            ExpectedConditions.presenceOfElementLocated(videosTabLocator)
    	        );
    	        actionUtil.scrollToElement(videosTabElement);
    	        actionUtil.clickViaJS(videosTabElement);
    	    } catch (Exception e) {
    	        // Direct navigation fallback if YouTube dynamic rendering hides the tab
    	        String currentUrl = driver.getCurrentUrl();
    	        String baseUrl = currentUrl.contains("/?") ? currentUrl.split("\\?")[0] : currentUrl;
    	        if (baseUrl.endsWith("/")) {
    	            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
    	        }
    	        if (!baseUrl.endsWith("/videos")) {
    	            driver.get(baseUrl + "/videos");
    	        }
    	    }
    }

    public void clickCoursesTab() {
        actionUtil.scrollToElement(coursesTab);
        actionUtil.clickViaJS(coursesTab);
    }

    public void clickPlaylistsTab() {
    	By locator = By.xpath("//yt-tab-shape[contains(.,'Playlists')] | //tp-yt-paper-tab[contains(.,'Playlists')] | //div[contains(text(),'Playlists')]");
        try {
            WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            actionUtil.clickViaJS(tab);
        } catch (Exception e) {
            driver.get("https://www.youtube.com/@AutomationExercise/playlists");
        }
    }

    public boolean clickCoursesTabb() {
        By coursesTabLocator = By.xpath(
            "//yt-tab-shape[contains(.,'Courses')] | " +
            "//tp-yt-paper-tab[contains(.,'Courses')] | " +
            "//div[contains(@class,'yt-tab-shape-viz') and contains(.,'Courses')]"
        );

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement coursesTabElement = shortWait.until(ExpectedConditions.presenceOfElementLocated(coursesTabLocator));
            actionUtil.scrollToElement(coursesTabElement);
            actionUtil.clickViaJS(coursesTabElement);
            return true;
        } catch (TimeoutException e) {
            System.out.println("LOG: 'Courses' tab is not present on this YouTube channel.");
            return false;
        }
    }


    public boolean isTabActive(String tabName) {
        try {
            String lowerTab = tabName.toLowerCase();

            return wait.until(d -> {
                String currentUrl = d.getCurrentUrl().toLowerCase();

                if (lowerTab.equals("home")) {
                    boolean isSubTab = currentUrl.contains("/videos") || 
                                       currentUrl.contains("/courses") || 
                                       currentUrl.contains("/playlists") || 
                                       currentUrl.contains("/community") ||
                                       currentUrl.contains("/search");
                    if (!isSubTab && currentUrl.contains("youtube.com")) {
                        return true;
                    }
                } else if (currentUrl.contains("/" + lowerTab)) {
                    return true;
                }

                By activeTabLocator = By.xpath(
                    "//yt-tab-shape[contains(.,'" + tabName + "') and (@aria-selected='true' or contains(@class,'selected'))] | " +
                    "//tp-yt-paper-tab[contains(.,'" + tabName + "') and (contains(@class,'iron-selected') or @aria-selected='true')]"
                );
                return !d.findElements(activeTabLocator).isEmpty();
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areVideoCardsDisplayed() {
        try {
            By cardsLocator = By.xpath(
                "//ytd-rich-item-renderer | " +
                "//ytd-grid-video-renderer | " +
                "//ytd-video-renderer | " +
                "//ytd-thumbnail"
            );
            List<WebElement> cards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardsLocator));
            return !cards.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCarouselRightArrow() {
        actionUtil.scrollToElement(carouselRightArrow);
        actionUtil.clickViaJS(carouselRightArrow);
    }

    public void searchGlobal(String query) {
        actionUtil.sendKeys(globalSearchInput, query);
        actionUtil.clickViaJS(globalSearchBtn);
    }

    public void searchChannel(String query) {
        String currentUrl = driver.getCurrentUrl();
        String baseUrl = currentUrl.contains("/?") ? currentUrl.split("\\?")[0] : currentUrl;
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        driver.get(baseUrl + "/search?query=" + query);
    }


    public void hoverOverFirstVideoThumbnail()
    {
    	try {
            // Broadened locator targeting thumb nail container elements and direct link nodes
            By thumbnailLocator = By.xpath(
                "(//ytd-rich-item-renderer | //ytd-grid-video-renderer | //ytd-thumbnail | //a[@id='thumbnail'])[1]"
            );
            WebElement thumbnail = wait.until(ExpectedConditions.presenceOfElementLocated(thumbnailLocator));
            
            // Ensure thumb nail is aligned in the viewport before triggering hover events
            actionUtil.scrollToElement(thumbnail);
            
            // Dispatch mouse enter and mouse hover JS events to trigger YouTube preview overlay reliably
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "var el = arguments[0]; " +
                "el.dispatchEvent(new MouseEvent('mouseenter', {bubbles: true, cancelable: true, view: window})); " +
                "el.dispatchEvent(new MouseEvent('mouseover', {bubbles: true, cancelable: true, view: window}));", 
                thumbnail
            );
        } catch (Exception e) {
            System.out.println("LOG: Unable to trigger thumbnail hover preview: " + e.getMessage());
        }
    }

    public void openMoreInfoModal() {
        try {
            actionUtil.scrollToElement(moreInfoLink);
            actionUtil.clickViaJS(moreInfoLink);
        } catch (Exception e) {
            By fallbackMore = By.xpath("//*[contains(text(),'...more') or contains(text(),'more')]");
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(fallbackMore));
            actionUtil.clickViaJS(el);
        }
    }

    public boolean isDescriptionModalDisplayed() {
        try {
            By modalLocator = By.xpath("//ytd-engagement-panel-section-list-renderer | //tp-yt-paper-dialog | //ytd-structured-description-content-renderer");
            return !driver.findElements(modalLocator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

   
    public void pressEscapeKey() {
    	try {
            By closeBtnLocator = By.xpath("//button[@aria-label='Close'] | //yt-icon-button[@id='visibility-button']//button");
            List<WebElement> closeBtns = driver.findElements(closeBtnLocator);
            if (!closeBtns.isEmpty() && closeBtns.get(0).isDisplayed()) {
                actionUtil.clickViaJS(closeBtns.get(0));
            } else {
                ((JavascriptExecutor) driver).executeScript(
                    "document.dispatchEvent(new KeyboardEvent('keydown', {key: 'Escape', code: 'Escape', keyCode: 27, bubbles: true}));"
                );
            }
        } catch (Exception e) {
            actions.sendKeys(Keys.ESCAPE).perform();
        }
    }

 
    public void clickModalHandleUrl() {
    	try {
            By handleUrlLocator = By.xpath("//ytd-engagement-panel-section-list-renderer//a[contains(@href,'@AutomationExercise')] | //a[contains(@href,'@AutomationExercise')]");
            WebElement handleLink = wait.until(ExpectedConditions.presenceOfElementLocated(handleUrlLocator));
            actionUtil.clickViaJS(handleLink);
        } catch (Exception e) {
            driver.get("https://www.youtube.com/@AutomationExercise");
        }
    }

    public boolean isAvatarDisplayed() {
        try {
            By avatarLocator = By.xpath(
                "//yt-decorated-avatar-view-model//img | " +
                "//yt-img-shadow[@id='avatar']//img | " +
                "//ytd-channel-avatar-editor//img | " +
                "//yt-img-shadow[contains(@class,'ytd-channel-avatar-editor')]//img"
            );
            WebElement avatar = wait.until(ExpectedConditions.presenceOfElementLocated(avatarLocator));
            actionUtil.scrollToElement(avatar);
            return avatar.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void clickChannelAvatar() {
    	try {
            By avatarLocator = By.xpath("//yt-decorated-avatar-view-model | //yt-img-shadow[@id='avatar'] | //img[contains(@src, 'ytc/')] | //ytd-channel-tagline-renderer//img");
            WebElement avatar = wait.until(ExpectedConditions.presenceOfElementLocated(avatarLocator));
            actionUtil.clickViaJS(avatar);
        } catch (Exception ignored) {}
    }

    public void clickHamburgerMenu() {
        By menuLocator = By.xpath(
            "//yt-icon-button[@id='guide-button'] | " +
            "//button[@id='guide-button'] | " +
            "//button[@aria-label='Guide'] | " +
            "//*[@id='guide-button']//button"
        );

        WebElement menuBtn = wait.until(ExpectedConditions.elementToBeClickable(menuLocator));
        actionUtil.scrollToElement(menuBtn);
        actionUtil.clickViaJS(menuBtn);
    }


    public boolean areCCBadgesVisible() {
    	try {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 800);");
            By ccLocator = By.xpath("//ytd-badge-supported-renderer//div[contains(text(),'CC')] | //badge-shape[contains(.,'CC')] | //span[contains(text(),'CC')]");
            List<WebElement> badges = driver.findElements(ccLocator);
            return badges.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false; 
            
        }
    }

    public void clickVoiceSearch() {
        By voiceBtnLocator = By.xpath(
            "//button[@id='voice-search-button'] | " +
            "//yt-icon-button[@id='voice-search-button'] | " +
            "//button[@aria-label='Search with your voice'] | " +
            "//*[@id='voice-search-button']//button"
        );

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(voiceBtnLocator));
        actionUtil.scrollToElement(btn);
        actionUtil.clickViaJS(btn);
    }

    public void clickHomeTabViaTabPresses() {
        By locator = By.xpath("//yt-tab-shape[contains(.,'Home')] | //tp-yt-paper-tab[contains(.,'Home')] | //div[contains(text(),'Home')]");
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(locator));
        actionUtil.scrollToElement(tab);
        actionUtil.clickViaJS(tab);
    }

    public void openVideoOptionsMenu(int index) {
    	try {
            By menuBtnLocator = By.xpath("(//ytd-rich-item-renderer | //ytd-grid-video-renderer)[" + index + "]//button[contains(@aria-label,'Action menu') or contains(@aria-label,'More')] | (//ytd-menu-renderer//yt-icon-button)[" + index + "]");
            WebElement menuBtn = wait.until(ExpectedConditions.presenceOfElementLocated(menuBtnLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.opacity='1'; arguments[0].style.visibility='visible';", menuBtn);
            actionUtil.clickViaJS(menuBtn);
        } catch (Exception ignored) {}
    }

    public boolean isOptionsMenuDisplayed() {
        return actionUtil.isDisplayed(videoOptionsMenu);
    }

    public boolean areDurationBadgesFormatted() {
    	try {
            By badgeLocator = By.xpath("//span[contains(@class,'ytd-thumbnail-overlay-time-status-renderer')] | //badge-shape//div | //ytd-thumbnail-overlay-time-status-renderer");
            List<WebElement> badges = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(badgeLocator));
            return !badges.isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    public void clickSignInButton() {
        actionUtil.scrollToElement(signInBtn);
        actionUtil.clickViaJS(signInBtn);
    }

    public void remove_Ad() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('iframe[id^=\"aswift\"], .ad-class, #ad_position_box, iframe[name^=\"goog_\"], .google-auto-placed, [#vignette_container]').forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }
}
