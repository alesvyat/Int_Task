package ru.tinkoff.svyatkin.tests;

import org.testng.asserts.SoftAssert;

import ru.tinkoff.svyatkin.general.*;

public class SharedTests {
	public static void beforeClass() {
		Globals.sAssert = new SoftAssert();
		try {
			Globals.initialize();
			BrowserUtils.launchBrowser(Globals.mainPageAddress);
		} catch(Exception e) {
			LogUtils.reportException(e);
		} finally {
			Globals.sAssert.assertAll();
		}
	}
	
	public static void afterClass() {
		Globals.sAssert = new SoftAssert();
		try {
			Globals.sAssert = new SoftAssert();
			try {
				BrowserUtils.closeBrowser();
			} catch(Exception e) {
				LogUtils.reportException(e);
			} finally {
				Globals.sAssert.assertAll();
			}
		} catch(Exception e) {
			LogUtils.reportException(e);
		} finally {
			Globals.sAssert.assertAll();
		}
	}
}
