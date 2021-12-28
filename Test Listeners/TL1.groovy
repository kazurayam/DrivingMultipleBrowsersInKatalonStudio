import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import flaskrtest.ConfigTest

class TL1 {
	
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		//println testSuiteContext.getTestSuiteId()
	}

	@BeforeTestCase
	def beforeTestTestCase(TestCaseContext testCaseContext) {
		//println testCaseContext.getTestCaseId()
		//println testCaseContext.getTestCaseVariables()
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		//println testCaseContext.getTestCaseId()
		//println testCaseContext.getTestCaseStatus()
	}

	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		//println testSuiteContext.getTestSuiteId()
	}
}