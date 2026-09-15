package Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Base.BaseclassTest;

public class ScreenshotList implements ITestListener
{
	@Override
	public void onTestFailure(ITestResult result)
	{
		Object testClass=result.getInstance();
		if (testClass instanceof BaseclassTest)
		{
		WebDriver driver=((BaseclassTest)testClass).getdriver();
		if (driver!=null) 
		{
			TakesScreenshot ts=(TakesScreenshot)driver;
			File source= ts.getScreenshotAs(OutputType.FILE);
			String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String fileName="screenshots/Failure" + result.getName() +"_"+ timeStamp + ".png";
			try
			{
				File targetFile = new File(fileName);
				File parentDir = targetFile.getParentFile();
				if (parentDir != null && !parentDir.exists())
				{
				    parentDir.mkdirs();
				}
				FileUtils.copyFile(source, targetFile);
				System.out.println("Screenshot captured for Failed Test: " + fileName);
			}
		catch(IOException e)
		{
		e.printStackTrace();	
		}
	}
		}
		
}
}

