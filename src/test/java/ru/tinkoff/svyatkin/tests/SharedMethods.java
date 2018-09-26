package ru.tinkoff.svyatkin.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import ru.tinkoff.svyatkin.general.*;


public class SharedMethods {
	/**
	 * Tests and input field specified by 'for' attribute
	 * @param forAttrValue 'for' attribute value
	 * @param inputValue input value
	 * @param errorMessageExpected expected error message under the field. Should be empty, if no error expected
	 */
	public static void testInputField(String forAttrValue, String inputValue, String errorMessageExpected) {
		try {
			String[] attributesContainer = {"", "class", "data-qa-file", "for"};
			String[] valuesContainer = {"label", "ui-input__container*", "UIInput", forAttrValue};
			WebElement inputContainer = GeneralUtils.waitWebElement(null, attributesContainer, valuesContainer, Globals.elementLoadWaiting);
			
			String[] attributesField = {"", "class", "name"};
			String[] valuesField = {"input", "ui-input__field", "provider-" + forAttrValue};
			WebElement inputField = GeneralUtils.waitWebElement(inputContainer, attributesField, valuesField, Globals.elementLoadWaiting);
			
			WebUtils.setValue(inputField, inputValue);
			
			WebElement nextSibling = null;
			String errorMessageActual = "";
			try {
				nextSibling = inputContainer.findElement(By.xpath("..")).findElement(By.xpath("following-sibling::*"));
				errorMessageActual = nextSibling.getText();
			} catch (NoSuchElementException e) {
				errorMessageActual = "";
			}
			Globals.sAssert.assertEquals(errorMessageActual, errorMessageExpected, forAttrValue + ": Error message verification");
		} catch (Exception e) {
			LogUtils.reportException(e);
		}
	}
}