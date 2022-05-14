package testSuites;

import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.Base;
import pages.CorporateWellness;
import pages.HospitalNames;
import pages.TopCities;
import utils.XLUtility;

public class TestCases extends Base {
	HospitalNames hn = new HospitalNames();
	TopCities tc = new TopCities();
	CorporateWellness ca = new CorporateWellness();
	@BeforeTest
	public void invokeBrowser() {
		logger = report.createTest("Executing Test Cases");
        hn.invokeBrowser();
//		try {
//			hn.invokeBrowser();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		reportPass("Browser is Invoked");
	}

	@Test(priority = 1)
	public void hospitalNames() throws InterruptedException {

		hn.openURL("websiteURLKey");
		reportPass("URL is Opened");
		hn.getHospitalNames();
		reportPass("Hospitals are Retrieved");
	}

	@Test(priority = 2)
	public void TopCities() {
		hn.openURL("websiteURLKey");
		reportPass("Navigated Back to the Home Page");
		tc.getCities();
		reportPass("Top Cities are retrieved");
	}

	
	@Test(priority = 3,dataProvider = "LoginData")
	public void invalidcorporateWellness(String name , String orgName, String email, String contact, String exp) throws InterruptedException, IOException {
		tc.openURL("websiteURLKey");
		reportPass("Navigated Back to the Home Page");
		ca.setup();
		ca.registrationtest(name,orgName,email,contact,exp);
		
		reportPass("Data is entered succesfully");
	}
	
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		String path = ".\\src\\test\\resources\\TestData.xlsx";
		XLUtility xlutil = new XLUtility(path);

		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);

		String loginData[][] = new String[totalrows][totalcols];

		for (int i = 1; i <= totalrows; i++) // 1
		{
			for (int j = 0; j < totalcols; j++) // 0
			{
				loginData[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
			}

		}

		return loginData;
	}
	
	@AfterTest
	public void closeBrowser() {
		ca.endReport();
		ca.closeBrowser();
	}

}
