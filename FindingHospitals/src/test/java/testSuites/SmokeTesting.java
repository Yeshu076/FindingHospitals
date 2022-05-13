package testSuites;

import java.net.MalformedURLException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.Base;
import pages.CorporateWellness;
import pages.HospitalNames;
import pages.TopCities;

public class SmokeTesting extends Base {
	HospitalNames hn = new HospitalNames();
	TopCities tc = new TopCities();
	CorporateWellness ca = new CorporateWellness();

	@BeforeTest
	public void invokeBrowser() {
		try {
			hn.invokeBrowser();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hn.openURL("websiteURLKey");
	}

	@Test
	public void testing() throws InterruptedException {
		hn.openURL("websiteURLKey");
		hn.getHospitalNames();
	}

	@AfterTest
	public void closeBrowser() {
		ca.closeBrowser();
	}

}
