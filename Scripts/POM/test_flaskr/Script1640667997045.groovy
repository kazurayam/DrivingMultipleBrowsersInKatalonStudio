import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import flaskrpages.auth.LogInPage
import flaskrpages.auth.RegisterCredentialPage
import flaskrpages.blog.CreatePostPage
import flaskrpages.blog.IndexPage
import flaskrpages.blog.Post
import flaskrpages.blog.Songs
import flaskrpages.blog.UpdatePostPage

import java.time.ZonedDateTime
import org.openqa.selenium.WebDriver

/**
 * 
 */
Map<String, Object> config = ConfigTest.config()
String config_browser = ConfigTest.config_browser(config)
Integer config_wait_time = ConfigTest.config_wait_time(config)
//
WebDriver browser = ConfigTest.browser(config_browser, config_wait_time)
Map<String, Object> credential = ConfigTest.credential()

//
println credential

IndexPage index_page = new IndexPage(browser)
index_page.load()



