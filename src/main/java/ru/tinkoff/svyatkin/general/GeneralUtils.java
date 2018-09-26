package ru.tinkoff.svyatkin.general;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GeneralUtils {
	//private static SoftAssert sAssert;
	
	
	/**
	 * Returns xpath of needed element.
	 * Got from http://stackoverflow.com/questions/4176560/webdriver-get-elements-xpath.
	 * This method is needed because there is no way to WAIT an element INSIDE ELEMENT - only inside whole driver
	 * @param driver
	 * @param element
	 * @return (String) xpath
	 */
	public static String getXPath(WebElement element) {
		try {
			return "//" + (String)((JavascriptExecutor) Globals.driver).executeScript(""
					+ "gPt=function(c){"
						/*+ "if(c.id!==''){"	//	I don't understand WTF is it. But let it be here in comment
							+ "return'id(\"'+c.id+'\")'"
						+ "}"*/
						+ "if(c===document.body){"
							+ "return c.tagName"
						+ "}"
						+ "var a=0;"
						+ "var e=c.parentNode.childNodes;"
						+ "for(var b=0;b<e.length;b++){"
							+ "var d=e[b];"
							+ "if(d===c){"
								+ "return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'"
							+ "}"
							+ "if(d.nodeType===1&&d.tagName===c.tagName){"
								+ "a++"
							+ "}"
						+ "}"
					+ "};"
					+ "return gPt(arguments[0]).toLowerCase();", element);
		} catch (Exception e) {
			LogUtils.reportException(e);
			return "";
		}
	}
	
	public static WebElement waitWebElement(WebElement parentElement, String attribute, String value, long timeOutInMillis) {
		try {
			String[] attributes = {attribute};
			String[] values = {value};
			return waitWebElement(parentElement, attributes, values, timeOutInMillis);
		} catch (Exception e) {
			LogUtils.reportException(e);
			return null;
		}
	}
	
	/**
	 * Waits a child of some parent element by attributes
	 * @param parentElement
	 * @param attributes	//	if attribute is a tag name, enter "" (empty) for 1-st element of this array
	 * @param values	// if attribute should be absent, enter "!" in its value
	 * @param timeOutInMillis
	 * @return
	 */
	public static WebElement waitWebElement(WebElement parentElement, String[] attributes, String[] values, long timeOutInMillis) {
		try {
			String xPath = "";
			int iStart;
			if(parentElement != null)
				xPath = getXPath(parentElement);
			if(attributes[0].equals("")) {
				xPath +="//" + values[0];
				iStart = 1;
				if (iStart < attributes.length)
					xPath += "[";
			}
			else {
				xPath += "//*[";
				iStart = 0;
			}
			for(int i = iStart; i < attributes.length; i++) {
				String andStr = i > iStart ? " and " : "";
				String at = (attributes[i].equals(".") || attributes[i].equals("text()")) ? "" : "@";
				if (values[i] == "!")
					xPath += andStr + "not(@" + attributes[i] + ")";
				else if (values[i].contains("*"))
					xPath += andStr + "contains(" + at + attributes[i] + ", '" + values[i].replaceAll("\\*",  "") + "')";
				else
					xPath += andStr + at + attributes[i] + "='" + values[i] + "'";
			}
			if (iStart < attributes.length)
				xPath += "]";
			//System.out.println("web element xPath = " + xPath);
			WebDriverWait wdWait = new WebDriverWait(Globals.driver, timeOutInMillis / 1000);
			try {
				WebElement result = wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
				return result;
			} catch (TimeoutException e) {
				return null;
			}
		} catch (Exception e) {
			LogUtils.reportException(e);
			return null;
		}
	}
	
	/**
	 * Waits a child of some parent element by 'id' attribute
	 * @param parentElement
	 * @param id
	 * @param timeOutInMillis
	 * @return
	 */
	public static WebElement waitWebElementById(WebElement parentElement, String id, long timeOutInMillis) {
		try {
			return waitWebElement(parentElement, "id", id, timeOutInMillis);
		} catch (Exception e) {
			LogUtils.reportException(e);
			return null;
		}
	}
	
	/**
	 * Waits alert
	 * @param text
	 * @param timeOutInMillis
	 * @return Alert object
	 */
	public static Alert waitAlert(String text, long timeOutInMillis) {
		try {
			Alert alert = null;
			try {
				alert = (new WebDriverWait(Globals.driver, timeOutInMillis / 1000)).until(ExpectedConditions.alertIsPresent());
				if (!text.equals(""))
					Globals.sAssert.assertEquals(alert.getText(), text, "Alert text verification");
			} catch (TimeoutException e) {}
			return alert;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return null;
		}
	}
	
	/**
	 * Hovers mouse on specified element
	 * Needed because org.openqa.selenium.interactions.Actions.moveToElement(..) does not work
	 * @param element
	 */
	public static void moveToElement(WebElement element) {
		try {
			String code = "var fireOnThis = arguments[0];"
						+ "var evObj = document.createEvent('MouseEvents');"
						+ "evObj.initEvent( 'mouseover', true, true );"
						+ "fireOnThis.dispatchEvent(evObj);";
			((JavascriptExecutor) Globals.driver).executeScript(code, element);
		} catch (Exception e) {
			LogUtils.reportException(e);
		}
	}
	
	/**
	 * Get Windows build number
	 * As for 2018, needed for Windows 10 only
	 * @return Windows build number
	 */
	public static String getWindowsBuildNumber() {
		try {
			final String buildWord = "Build";
			
			String osName = System.getProperty("os.name");
			if (!osName.contains("Windows"))
				return "";
			
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("SYSTEMINFO");
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			
			String line;
			String bn = "";
			while((line = in.readLine()) != null) {
				if(line.contains("OS Version")) {
					bn = line.substring(line.lastIndexOf(buildWord) + buildWord.length(), line.length()).trim();
					break;
				}
			}
			
			System.out.println("bn = " + bn);
			return bn;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return "";
		}
	}
}
