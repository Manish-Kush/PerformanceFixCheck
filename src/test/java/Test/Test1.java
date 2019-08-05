package Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Generic.AutomationData;

public class Test1 implements AutomationData{
	
	public static WebDriver driver;
	 
	 static
	 {
		// System.setProperty(CHROME_KEY, CHROME_VALUE);
		 System.setProperty(FIREFOX_KEY, FIREFOX_VALUE);
	 }
	 
	public static void Initlization()
	{
		//driver  = new ChromeDriver();
		driver  = new FirefoxDriver(); //This starts Web Browser
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(ITO, TimeUnit.SECONDS);
	
		driver.get(URL);
	}
	
	public static void main(String[] args) throws InterruptedException {
	
		System.setProperty("webdriver.gecko.driver", "/home/manish/Desktop/Driver/geckodriver");
		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("http://10.8.55.122/instanmc/loginForm.do");
				
		driver.findElement(By.name("userId")).clear();
		driver.findElement(By.name("userId")).sendKeys("Doc001");
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("Insta@123");
		
		driver.findElement(By.name("button")).click();
		
		driver.get("http://10.8.55.122/instanmc/emr/EMRMainDisplay.do?_method=list&mr_no=UA1300000035638");
		
		driver.findElement(By.linkText("ExpandAll")).click();
		driver.findElement(By.linkText("Us Kub Region - (22-06-2017)")).click();

		driver.switchTo().frame(0);
		
		System.out.println("=====================> "+ driver.getTitle());
		Thread.sleep(5000);
		
		String src = driver.getPageSource();
		
		System.out.println(src);
		
		if(src.contains("Exception")) {
			System.out.println("There is a Exception in the link ");
		}
		else System.out.println("There is no exception in the link");
		
		driver.close();

	}
}
