package ru.tinkoff.svyatkin.general;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class WebUtils {
	/**
	 * Sets value for some elements, such as check boxes, radio buttons, drop-down lists, input fields
	 * For now, it works for input fields only
	 * @param element
	 * @param value
	 */
	public static void setValue(WebElement element, Object value) {
		try {
			String className = element.getAttribute("class");
			switch (className) {
				case "ui-input__field":
					element.click();
					element.clear();
					element.sendKeys(value.toString());
					element.sendKeys(Keys.TAB);
					break;
				default:
					Globals.sAssert.fail(className + " is illegal class name");
			}
		} catch (Exception e) {
			LogUtils.reportException(e);
		}
	}
}
