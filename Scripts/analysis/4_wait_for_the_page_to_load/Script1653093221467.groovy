// Test Cases/analysis/4_wait_for_the_page_to_load

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String chrome_executable_path = DriverFactory.getChromeDriverPath()
System.setProperty('webdriver.chrome.driver', chrome_executable_path)

WebDriver browser = new ChromeDriver()
browser.navigate().to('http://127.0.0.1/')

// i can let WebUI.xxx keywords know the WebDriver instance created by my script
DriverFactory.changeWebDriver(browser)

// wait for the page to load
TestObject tObj = makeTestObject("site_name", "//h1[text()='Flaskr']")
//TestObject tObj = makeTestObject("site_name", "//h1[text()='FlaskR']")   // this will fail
WebUI.verifyElementPresent(tObj, 5, FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()

// helper method to create an instance of TestObject
TestObject makeTestObject(String id, String xpath) {
	TestObject tObj = new TestObject(id)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}