package ru.tinkoff.svyatkin.general;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import ru.tinkoff.svyatkin.general.Globals;

public class BrowserUtils {
	/**
	 * Launches needed browser and opens needed URL
	 * @param address
	 * @param browser
	 * @return WebDriver object
	 */
	public static WebDriver launchBrowser(String address, String browser) {
		try {
			WebDriver webDriver = null;
			switch (browser) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", Globals.driverPath);
				
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", Globals.downloadsDir);
				chromePrefs.put("safebrowsing.enabled", "true");
				chromeOptions.addArguments("--start-maximized");
				chromeOptions.setExperimentalOption("prefs", chromePrefs);
				DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
				desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				
				webDriver = new ChromeDriver(desiredCapabilities);
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", Globals.driverPath);
				if (!Globals.binaryPath.equals(""))
					System.setProperty("webdriver.firefox.bin", Globals.binaryPath);
				
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.download.dir", Globals.downloadsDir);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/xml");
				
				//webDriver = new FirefoxDriver(firefoxProfile);
				webDriver = new FirefoxDriver();
				webDriver.manage().window().maximize();
				break;
			case "ie":
				System.setProperty("webdriver.ie.driver", Globals.driverPath);
				webDriver = new InternetExplorerDriver();
				webDriver.manage().window().maximize();
				break;
			case "edge":
				System.setProperty("webdriver.edge.driver", Globals.driverPath);
				webDriver = new EdgeDriver();
				webDriver.manage().window().maximize();
				break;
			default:
				Globals.sAssert.fail(browser + " is undefined browser");
				return null;
			}
			webDriver.get(address);
			Thread.sleep(3000);
			Globals.driver = webDriver;
			return webDriver;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return null;
		}
	}
	
	/**
	 * Launches browser and opens needed URL. Browser specified in apps.xml
	 * @param address
	 * @return
	 */
	public static WebDriver launchBrowser(String address) {
		try {
			return launchBrowser(address, Globals.currentDriver);
		} catch (Exception e) {
			LogUtils.reportException(e);
			return null;
		}
	}
	
	/**
	 * Closes browser
	 */
	public static void closeBrowser() {
		try {
			/*
			 * Firefox crashes during quit method using
			 * https://github.com/mozilla/geckodriver/issues/173
			 */
			if(Globals.currentDriver.equals("firefox")) {
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
				Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
				Thread.sleep(3000);
			}
			else
				Globals.driver.quit();
		} catch (Exception e) {
			LogUtils.reportException(e);
		} 
	}
	
}
