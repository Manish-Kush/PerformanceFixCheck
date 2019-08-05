package TestActions;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Generic.BaseClass;
import Pages.HomePage;
import Pages.PatientEMR;

public class CommonActions extends BaseClass{
	
	HomePage homePage;
	PatientEMR patientEMR;

	WebDriverWait wait;
	
	public CommonActions(String userName,String passWord, String shouldInitlization){
		
		LoginActions testLoginPage = new LoginActions(userName,passWord,shouldInitlization);
		homePage = new HomePage(driver);
		patientEMR = new PatientEMR(driver);
		wait = new WebDriverWait(driver, 90);
	}

//Test Case 1
	public List<String> Test(String Mr_No) throws InterruptedException{
		
		List<String> defectLinks = new ArrayList<String>();
		homePage.clickOnMedicalRecordsDropDown();
		homePage.clickOnPatientEMRlink();
		patientEMR.enterMrNumberOfPatient(Mr_No);//MR No.should take from data provider
		patientEMR.clickOnGetDetailsButton();
		
		
		patientEMR.clickOnExpandAllLink();
		
		List<WebElement> links = new ArrayList<WebElement>();
		links = driver.findElements(By.tagName("a"));
		
		Iterator<WebElement> a = links.iterator();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int m = 0;
		while(a.hasNext()) {
			
			WebElement link = a.next();
			String linkText="";
			try {
			linkText = link.getText();
			}
			catch (NoSuchElementException e) {
				e.printStackTrace();
			}
			if(!(linkText.equals("")|linkText.equals(" ")|linkText.equals("My Notifications (0)")|linkText.equals("My Page")|linkText.equals("Search")|linkText.equals("Registration")|linkText.equals("Registration")|linkText.equals("Billing")|linkText.equals("Payments")|linkText.equals("Patient")|linkText.equals("Medical Records")|linkText.equals("Laboratory")|linkText.equals("ExpandAll")|linkText.equals("Lab Report EMR")|linkText.equals("EMR Audit Log")|linkText.equals("Test Trend Report")|linkText.equals("Vitals Trend Report")|linkText.equals("Medicine Prescription History")|linkText.equals("< Prev")|linkText.equals("Next >")|linkText.equals("Release Notes")|linkText.equals("Acknowledgement")|linkText.equals("Help")|linkText.equals("Customer Support"))) {
			js.executeScript("arguments[0].scrollIntoView();", link);
			link.click();
			
			try {	
// 					driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
					Thread.sleep(2000);
					driver.switchTo().frame(0);
					String src = "";
					boolean status = true;
					int s = 0;
					while(status) {
					try {
					src = driver.getPageSource();
					status = false;
					}
					catch (JavascriptException e) {
						Thread.sleep(5000);
						s++;
						if(s==2)status = false;
					}
					}
					if(src.contains("Exception")) {
						System.out.println(linkText+ " =========> "+ " Getting Exception in this link"+" For this MR No. "+ Mr_No);
						defectLinks.add(linkText);
					}
					else if(src.contains("Login - Insta HMS")) {
						new CommonActions(USERNAME,PASSWORD,"Y");
						homePage.clickOnMedicalRecordsDropDown();
						homePage.clickOnPatientEMRlink();
						patientEMR.enterMrNumberOfPatient(Mr_No);//MR No.should take from data provider
						patientEMR.clickOnGetDetailsButton();
						patientEMR.clickOnExpandAllLink();
						
						link.click();
					}
					else System.out.println("No exception in the link "+ linkText+" For this MR No. "+ Mr_No);
//					try {
//					wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[4]/div/div/div[2]/span[23]"))));
//					}
//					catch (NoSuchElementException e) {
//						System.out.println(linkText+ " =========> "+ " Getting Exception fro this link");
//					}
					
//					
//					if(driver.findElement(By.xpath("//h1[contains(text(),'Exception')]")).isDisplayed()) {
//						
//					System.out.println(linkText+" =========> "+ " Getting Exception fro this link");
//					System.out.println(links.get(m).getAttribute("href"));
//					defectLinks.add(linkText);	
//				}

			}
			catch (NoSuchElementException e) {
				System.out.println("No Exceptions");
			}
			catch (StaleElementReferenceException e) {
				
			}
			driver.switchTo().parentFrame();		
			}
			m++;
		}
		
		return defectLinks;
	} 
	
	
	
}
