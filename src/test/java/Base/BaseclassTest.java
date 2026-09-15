package Base;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseclassTest 
{
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
	public WebDriver getdriver()
	{
		return driver.get();
	}
	public WebDriverWait getWait() {
        return wait.get();
    }
  @BeforeClass
  @Parameters("browser")
  public void setup(@Optional("chrome")String browser) 
  {
	  WebDriver obj;
	 
	  switch(browser.toLowerCase())
	  {
	  case "firefox":
		  WebDriverManager.firefoxdriver().setup();
		  obj=new FirefoxDriver();
		  break;
		  
	  case "chrome":
		  WebDriverManager.chromedriver().setup();
		  ChromeOptions options = new ChromeOptions();
	
		    Map<String, Object> prefs = new HashMap<>();
		    prefs.put("credentials_enable_service", false);
		    prefs.put("profile.password_manager_enabled", false);
		    prefs.put("profile.password_manager_leak_detection", false); // Disables the data breach warning popup
		    prefs.put("autofill.profile_enabled", false);
		    prefs.put("autofill.credit_card_enabled", false);
		    
		    String downloadPath = System.getProperty("user.home") + File.separator + "Downloads";
		    prefs.put("download.default_directory", downloadPath);
		    prefs.put("download.prompt_for_download", false);
		    prefs.put("download.directory_upgrade", true);
		    prefs.put("plugins.always_open_pdf_externally", true); // Prevents Chrome PDF viewer from opening it in-browser
		    
		    options.setExperimentalOption("prefs", prefs);
		 
		    options.addArguments("--disable-features=PasswordLeakToggleMove");
		    options.addArguments("--disable-notifications");
		    options.addArguments("--disable-dev-shm-usage"); // Prevents browser memory overload
		    options.addArguments("--no-sandbox");            // Prevents OS thread lockups
		    options.addArguments("--disable-gpu");           // Prevents rendering hangs
		    obj = new ChromeDriver(options);
		  break;
		  
	  case "edge":
		  WebDriverManager.edgedriver().setup();
		  obj=new EdgeDriver();
		  break;
		  
     default:
			  WebDriverManager.chromedriver().setup();
			  obj= new ChromeDriver();
			  break;
  }
	  driver.set(obj);
	  wait.set(new WebDriverWait(obj, Duration.ofSeconds(10)));
	  getdriver().manage().window().maximize();
     getdriver().get("https://www.automationexercise.com/");
    }
     public void setViewportSize(int width, int height) 
     {
         getdriver().manage().window().setSize(new Dimension(width, height));
     }
     public void maximizeWindow()
     {
         getdriver().manage().window().maximize();
     }
    

  @AfterClass
  public void drop() 
  {
	  if(getdriver()!=null)
	  {
		  getdriver().quit();
	  driver.remove();
      }
	  if (getWait() != null) {
          wait.remove();  
      }
  }


  
  }

