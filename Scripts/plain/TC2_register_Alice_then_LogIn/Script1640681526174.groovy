import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import plain.testobject.LogIn
import plain.testobject.Nav
import plain.testobject.Register

String username = 'Alice'
String password = 'ThisIsNotAPassword'

WebUI.openBrowser('')
WebUI.navigateToUrl('http://127.0.0.1/')

// we will be on the entry page
WebUI.verifyElementPresent(Nav.Flaskr(), 10)
WebUI.verifyElementPresent(Nav.Register(), 3)
WebUI.verifyElementPresent(Nav.LogIn(), 3)

// we want to navigate to the Register page
WebUI.click(Nav.Register())

// make sure we are on the Register page
WebUI.verifyElementPresent(Register.form_input_Register(), 3)

// we want to register a user
WebUI.setText(Register.form_input_username(), username)
WebUI.setText(Register.form_input_password(), password)
WebUI.click(Register.form_input_Register())

// check if the user is already registered
boolean alreadyRegistered = WebUI.waitForElementPresent(Register.flash(), 3)
if (alreadyRegistered) {
	KeywordUtil.markWarning("usernamee ${username} is already registered.")
	// we are still on the Register page
	// so we want to navigate to the Log In page
	WebUI.click(Nav.LogIn())
}

// now we are on the Login page
// now let us log in
WebUI.verifyElementPresent(LogIn.form_input_LogIn(), 3)
WebUI.setText(LogIn.form_input_username(), username)
WebUI.setText(LogIn.form_input_password(), password)
WebUI.click(LogIn.form_input_LogIn())

// have I successfully logged in?
WebUI.verifyElementPresent(Nav.username(username), 3)

WebUI.delay(3)

WebUI.closeBrowser()