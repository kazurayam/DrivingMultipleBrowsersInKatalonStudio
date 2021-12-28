import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import plain.testobject.Nav

WebUI.openBrowser('')
WebUI.navigateToUrl('http://127.0.0.1/')

WebUI.verifyElementPresent(Nav.Flaskr(), 10)
WebUI.verifyElementPresent(Nav.Register(), 3)
WebUI.verifyElementPresent(Nav.LogIn(), 3)

WebUI.delay(3)
WebUI.closeBrowser()