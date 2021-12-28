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
import org.openqa.selenium.By as SeleniumBy

import com.kazurayam.ks.testobject.By as By
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * wraps an blog post page in the Flaskr web app.
 * provides accessor methods to title and body
 * 
 *  <article class="post">
      <header>
        <div>
          <h1>東京はもうすぐ春</h1>
          <div class="about">by kazurayam on 2021-02-10</div>
        </div>
        <a class="action" href="/2/update">Edit</a>
      </header>
      <p class="body">どじょうが泳いでいます</p>
    </article>
    """
 */
public class Post {
	
	static final SeleniumBy TITLE = SeleniumBy.xpath('//header/div/h1')
	static final SeleniumBy BODY  = SeleniumBy.xpath('//p[1]')
	static final SeleniumBy EDIT  = SeleniumBy.xpath('//header/a')

	WebElement article
	
	Post(WebElement article) {
		this.article = article
	}

	String get_title() {
		WebElement title = article.findElement(TITLE)
		return title.getText()
	}
	
	String get_body() {
		WebElement body = article.findElement(BODY) 
		return body.getText()
	}
	
	String get_postid() {
		WebElement anchor = article.findElement(EDIT)
		String href = anchor.getAttribute('href')
		String postid = href.split('/')[1]
		return (postid != null) ? postid : ''
	}

}
