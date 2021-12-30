import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// preparatio for ChromeDriver
String chrome_executable_path = DriverFactory.getChromeDriverPath()
System.setProperty('webdriver.chrome.driver', chrome_executable_path)

// open a Chome browser for Alice
WebDriver browser1 = new ChromeDriver()
layoutWindow(browser1, new Dimension(720, 640), new Point(0, 0))
// Alice logs in
login(browser1, "Alice", "AliceInTheWonderLad")

// open a Chrome browser for Bob
WebDriver browser2 = new ChromeDriver()
layoutWindow(browser2, new Dimension(720, 640), new Point(720, 0))
// Bob logs in
login(browser2, "Bob", "LikeARollingStone")

// Alice makes a post

// Bob makes a post

// Alice likes Bob's post

// Bob likes Alice's post

WebUI.delay(3)

// close both browsers
browser1.quit()
browser2.quit()


def login(WebDriver browser, String username, String password) {
	DriverFactory.changeWebDriver(browser)
	browser.navigate().to('http://127.0.0.1/')
	// ensure we are on the index page
	WebUI.verifyElementPresent(findTestObject("blog/IndexPage/h1_flaskr"), 10)
	WebUI.verifyElementPresent(findTestObject("blog/IndexPage/a_Register"), 3)
	WebUI.verifyElementPresent(findTestObject("blog/IndexPage/a_Log In"), 3)
	
	// we want to navigate to the Register page
	WebUI.click(findTestObject("blog/IndexPage/a_Register"))
	
	// make sure we are on the Register page
	WebUI.verifyElementPresent(findTestObject("auth/RegisterCredentialPage/input_Register"), 3)
	
	// we want to register a user
	WebUI.setText(findTestObject("auth/RegisterCredentialPage/input_username"), username)
	WebUI.setText(findTestObject("auth/RegisterCredentialPage/input_password"), password)
	WebUI.click(findTestObject("auth/RegisterCredentialPage/input_Register"))
	
	// check if the user is already registered
	boolean alreadyRegistered = WebUI.waitForElementPresent(findTestObject("auth/RegisterCredentialPage/div_flash"), 3)
	if (alreadyRegistered) {
		KeywordUtil.markWarning("usernamee ${username} is already registered.")
		// we are still on the Register page
		// so we want to navigate to the Log In page
		WebUI.click(findTestObject("auth/RegisterCredentialPage/a_Log In"))
	}
	
	// now we are on the Login page
	// now let us log in
	WebUI.verifyElementPresent(findTestObject("auth/LogInPage/input_Log In"), 3)
	WebUI.setText(findTestObject("auth/LogInPage/input_username"), username)
	WebUI.setText(findTestObject("auth/LogInPage/input_password"), password)
	WebUI.click(findTestObject("auth/LogInPage/input_Log In"))
	
	// now we should be are on the index page
	// have I successfully logged in?
	WebUI.verifyElementPresent(findTestObject("blog/IndexPage/nav_span_username", ["username": username]), 3)
	
	
}

def layoutWindow(WebDriver browser, Dimension dimension, Point point) {
	browser.manage().window().setSize(dimension)
	browser.manage().window().setPosition(point)
}