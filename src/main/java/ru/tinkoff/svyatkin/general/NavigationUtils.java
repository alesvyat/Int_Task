package ru.tinkoff.svyatkin.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class NavigationUtils {
	
	/**
	 * @param footerItemName
	 * @return true if item clicked
	 */
	public static boolean clickFooterItem(String footerItemName) {
		try {
			String footerClassName = "footer__Footer-common__root*";
			WebElement footer = GeneralUtils.waitWebElement(null, "class", footerClassName, Globals.pageLoadWaiting);
			
			String[] attributes = {"class", "text()"};
			String[] values = {"footer__Link*", footerItemName};
			WebElement item = GeneralUtils.waitWebElement(footer, attributes, values, Globals.elementLoadWaiting);
			
			Globals.sAssert.assertNotNull(item, footerItemName + " item existence verificaton");
			if (item != null)
				item.click();
			return true;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return false;
		}
	}
	
	/**
	 * Clicks payment group in Payments page
	 * Note: payment group is to be visible; this method can not slide between groups lists
	 * @param groupName
	 * @return true if item clicked
	 */
	public static boolean clickPaymentGroup(String groupName) {
		try {
			String paymentGroupBoxClassName = "UIPayments__providers*";
			WebElement paymentGroupBox = GeneralUtils.waitWebElement(null, "class", paymentGroupBoxClassName, Globals.pageLoadWaiting);
			WebElement item = GeneralUtils.waitWebElement(paymentGroupBox, "text()", groupName, Globals.elementLoadWaiting);
			
			Globals.sAssert.assertNotNull(item, item + " item existence verificaton");
			if (item != null)
				item.click();
			return true;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return false;
		}
	}
	
	/**
	 * Sets a region in Payments page
	 * If needed region is already selected, nothing will happen
	 * @param regionName
	 * @return true if item clicked
	 */
	public static boolean setRegion(String regionName) {
		try {
			WebElement paymentsContent = GeneralUtils.waitWebElement(null, "class", "ui-page-frame__content", Globals.pageLoadWaiting);
			
			String[] attributes = {"", "class"};
			String[] values = {"span", "Link__link*"};
			WebElement regionButton = GeneralUtils.waitWebElement(paymentsContent, attributes, values, Globals.elementLoadWaiting);
			
			String regionActual = regionButton.getText();
			if (regionActual.equals(regionName))
				return true;
			regionButton.click();
			return selectRegion(regionName);
		} catch (Exception e) {
			LogUtils.reportException(e);
			return false;
		}
	}
	
	/**
	 * Selects needed region from the list
	 * @param regionName
	 * @return true if item clicked
	 */
	public static boolean selectRegion(String regionName) {
		try {
			WebElement regionsList = GeneralUtils.waitWebElement(null, "class", "UiRegions__uiRegions__layout*", Globals.pageLoadWaiting);
			
			String[] attributes = {"", "class", "text()"};
			String[] values = {"span", "ui-link__text", regionName};
			WebElement item = GeneralUtils.waitWebElement(regionsList, attributes, values, Globals.elementLoadWaiting);
			Globals.sAssert.assertNotNull(item, item + " item existence verificaton");
			
			if (item != null)
				item.click();
			return true;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return false;
		}
	}
	
	/**
	 * Selects needed provider from the list
	 * @param providerName
	 * @return true if item clicked
	 */
	public static boolean selectProvider(String providerName) {
		try {
			WebElement paymentsContent = GeneralUtils.waitWebElement(null, "class", "ui-page-frame__content", Globals.pageLoadWaiting);
			
			String[] attributes = {"", "class", "text()"};
			String[] values = {"div", "UIMenu__link*", providerName};
			WebElement item = GeneralUtils.waitWebElement(paymentsContent, attributes, values, Globals.elementLoadWaiting); 
			Globals.sAssert.assertNotNull(item, item + " item existence verificaton");
			
			if (item != null)
				item.click();
			return true;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return false;
		}
	}
	
	/**
	 * Gets name of a provider specified by index
	 * @param index - provider's 0-based number
	 * @return Provider name
	 */
	public static String getProviderName(int index) {
		try {
			WebElement paymentsContent = GeneralUtils.waitWebElement(null, "class", "ui-page-frame__content", Globals.pageLoadWaiting);
			
			String xPathExpression = GeneralUtils.getXPath(paymentsContent) + "//li[" + (index + 1) + "]";
			String[] attributes = {"", "class"};
			String[] values = {"div", "UIMenu__link*"};
			WebElement item = Globals.driver.findElement(By.xpath(xPathExpression));
			item = GeneralUtils.waitWebElement(item, attributes, values, Globals.elementLoadWaiting);
			return item.getText();
		} catch (Exception e) {
			LogUtils.reportException(e);
			return "";
		}
	}
	
	public static boolean selectTab(String tabName) {
		try {
			String[] attributes = {"", "class", "text()"};
			String[] values = {"span", "Tab__tab*", tabName};
			WebElement tab = GeneralUtils.waitWebElement(null, attributes, values, Globals.elementLoadWaiting);
			
			Globals.sAssert.assertNotNull(tab, tabName + " tab existence verificaton");
			
			if (tab != null)
				tab.click();
			return true;
		} catch (Exception e) {
			LogUtils.reportException(e);
			return false;
		}
	}
}
