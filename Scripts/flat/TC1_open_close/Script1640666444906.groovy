import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('http://127.0.0.1/')

WebUI.verifyElementPresent(my.TestObjects.nav_Flaskr(), 10)
WebUI.verifyElementPresent(my.TestObjects.nav_register(), 3)
WebUI.verifyElementPresent(my.TestObjects.nav_login(), 3)

WebUI.delay(3)
WebUI.closeBrowser()