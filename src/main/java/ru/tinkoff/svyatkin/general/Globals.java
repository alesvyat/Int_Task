package ru.tinkoff.svyatkin.general;

import org.w3c.dom.*;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;

public class Globals {
	/*
	 * Constants
	 */
	public static final int pageLoadWaiting = 5000;
	public static final int elementLoadWaiting = 1000;
	
	
	/*
	 * Variables
	 */
	public static String projectPath;
	public static String testDataFileName;
	public static WebDriver driver;
	public static SoftAssert sAssert;
	
	public static String currentDriver;
	public static String driverPath;
	public static String binaryPath;
	public static String downloadsDir;
	
	public static String mainPageHost;
	public static String mainPagePort;
	public static String mainPageAddress;
	
	
	/*
	 * Methods
	 */
	public static void initialize() {
		try {
			projectPath = System.getProperty("user.dir");
			testDataFileName = "testData.xml";
			
			//	Load apps.xml
			File appsXml = new File(Globals.projectPath + "\\conf", "apps.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(appsXml);
			XPath xPath =  XPathFactory.newInstance().newXPath();
			
			NodeList nodeList = (NodeList) xPath.compile("apps/browser/current").evaluate(doc, XPathConstants.NODESET);
			currentDriver = nodeList.item(0).getTextContent().toLowerCase();
			
			switch(currentDriver) {
				case "chrome":
					nodeList = (NodeList) xPath.compile("apps/browser/chrome").evaluate(doc, XPathConstants.NODESET);
					driverPath = nodeList.item(0).getAttributes().getNamedItem("driverPath").getNodeValue();
					
					/*colorofWorkingService = "rgba(6, 164, 23, 1)";
					colorOfStoppedService = "rgba(170, 26, 26, 1)";*/
					
					break;
				case "firefox":
					nodeList = (NodeList) xPath.compile("apps/browser/firefox").evaluate(doc, XPathConstants.NODESET);
					driverPath = nodeList.item(0).getAttributes().getNamedItem("driverPath").getNodeValue();
					binaryPath = nodeList.item(0).getAttributes().getNamedItem("binaryPath").getNodeValue();
					
					/*colorofWorkingService = "rgb(6, 164, 23)";
					colorOfStoppedService = "rgb(170, 26, 26)";*/
					
					break;
				case "ie":
					nodeList = (NodeList) xPath.compile("apps/browser/ie").evaluate(doc, XPathConstants.NODESET);
					driverPath = nodeList.item(0).getAttributes().getNamedItem("driverPath").getNodeValue();
					
					/*colorofWorkingService = "rgba(6, 164, 23, 1)";
					colorOfStoppedService = "rgba(170, 26, 26, 1)";*/
					
					break;
				case "edge":
					nodeList = (NodeList) xPath.compile("apps/browser/edge").evaluate(doc, XPathConstants.NODESET);
					driverPath = nodeList.item(0).getAttributes().getNamedItem("driverPath").getNodeValue();
					
					/*colorofWorkingService = "rgb(6, 164, 23)";
					colorOfStoppedService = "rgb(170, 26, 26)";*/
					
					break;
			}
			
			nodeList = (NodeList) xPath.compile("apps/browser/downloadsDir").evaluate(doc, XPathConstants.NODESET);
			downloadsDir = nodeList.item(0).getTextContent();
			
			nodeList = (NodeList) xPath.compile("apps/url").evaluate(doc, XPathConstants.NODESET);
			mainPageHost = nodeList.item(0).getAttributes().getNamedItem("host").getNodeValue();
			mainPagePort = nodeList.item(0).getAttributes().getNamedItem("port").getNodeValue();
			mainPagePort = (mainPagePort == "") ? mainPagePort : ":" + mainPagePort;
			mainPageAddress = "http://" + mainPageHost + mainPagePort;
			
			
		} catch(Exception e) {
			LogUtils.reportException(e);
		}
	}
	
}
