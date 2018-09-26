package ru.tinkoff.svyatkin.tests;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import ru.tinkoff.svyatkin.general.*;


public class TFF_SMK_00 {
	private String providerWanted;
	
	@Test
	public void f() {
		Globals.sAssert = new SoftAssert();
		try {
			NavigationUtils.clickFooterItem("Платежи");
			NavigationUtils.clickPaymentGroup("ЖКХ");
			NavigationUtils.setRegion("г. Москва");
			Thread.sleep(3000);	//	sorry
			this.providerWanted = NavigationUtils.getProviderName(0);
			Globals.sAssert.assertEquals(this.providerWanted, "ЖКУ-Москва", "Provider name verification");
			NavigationUtils.selectProvider(this.providerWanted);
			NavigationUtils.selectTab("Оплатить ЖКУ в Москве");
			Thread.sleep(1000);	//sorry
			
			SharedMethods.testInputField("payerCode", "", "Поле обязательное");
			SharedMethods.testInputField("payerCode", "9", "Поле неправильно заполнено");
			//SharedMethods.testInputField("period", "", "Поле обязательное");	//	works incorrectly
			//SharedMethods.testInputField("period", "131313", "Поле заполнено некорректно");	//	works incorrectly
			
		} catch(Exception e) {
			LogUtils.reportException(e);
		} finally {
			Globals.sAssert.assertAll();
		}
	}
	
	@BeforeClass
	public void beforeClass() {
		SharedTests.beforeClass();
	}
	
	@AfterClass
	public void afterClass() {
		SharedTests.afterClass();
	}

}
