package flaskrtest.pages.blog

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kazurayam.ks.testobject.By as By
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class CreatePostPage {
	
	static final TestObject TITLE_INPUT = By.id('title')
	static final TestObject BODY_INPUT  = By.id('body')
	static final TestObject SAVE_BUTTON = By.xpath('//input[@type="submit" and @value="Save"]')

	private WebDriver browser
	
	CreatePostPage(WebDriver browser) {
		this.browser = browser
	}

	Boolean save_button_exists() {
		DriverFactory.changeWebDriver(browser)
		WebElement save_button = WebUI.findWebElement(SAVE_BUTTON)
		return save_button != null
	}

	void type_title(String title) {
		DriverFactory.changeWebDriver(browser)
		WebUI.clearText(TITLE_INPUT)
		WebUI.sendKeys(TITLE_INPUT, title)
	}

	void type_body(String body) {
		DriverFactory.changeWebDriver(browser)
		WebUI.clearText(BODY_INPUT)
		WebUI.sendKeys(BODY_INPUT, body)
	}

	void do_save() {
		DriverFactory.changeWebDriver(browser)
		WebUI.click(SAVE_BUTTON)
	}
}
