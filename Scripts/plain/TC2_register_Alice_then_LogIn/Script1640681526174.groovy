import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String username = 'Alice'
String password = 'ThisIsNotAPassword'

WebUI.openBrowser('')
WebUI.navigateToUrl('http://127.0.0.1/')

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

WebUI.closeBrowser()