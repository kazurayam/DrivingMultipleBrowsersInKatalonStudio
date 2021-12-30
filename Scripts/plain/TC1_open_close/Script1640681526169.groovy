import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('http://127.0.0.1/')

WebUI.verifyElementPresent(findTestObject("blog/IndexPage/h1_flaskr"), 10)
WebUI.verifyElementPresent(findTestObject("blog/IndexPage/a_Register"), 3)
WebUI.verifyElementPresent(findTestObject("blog/IndexPage/a_Log In"), 3)

WebUI.delay(1)

WebUI.closeBrowser()