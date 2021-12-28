import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.openqa.selenium.WebDriver

import com.kazurayam.ks.webdriverfactory.KSDriverTypeName
import com.kazurayam.ks.webdriverfactory.KSWebDriverFactory
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import groovy.json.*

class ConfigTest {
	
	static String CONFIG_PATH = './Include/resources/config.json'
	static Integer DEFAULT_WAIT_TIME = 10
	static List<String> SUPPORTED_BROWSERS = ['chrome', 'firefox']
	
	static Map<String, Object> config() {
		JsonSlurper slurper = new JsonSlurper()
		return slurper.parse(new File(CONFIG_PATH))
	}
	
	static Map<String, Object> credential() {
		LocalDateTime now = LocalDateTime.now()
		String timestamp = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(now)
		Map data = [ 'UN' + timestamp, 'PW' + timestamp ]
		return data
	}
	
	static String config_browser(Map<String, Object> config) {
		if (!config.containsKey('browser')) {
			throw new IllegalArgumentException("The config file does nnot contain 'browseer'")
		} else if (SUPPORTED_BROWSERS.contains(config['browser'])) {
			throw new IllegalArgumentException("${config['browser']} is not a supported browser")
		}
		return config['browser']
	}
	
	/**
	 * validate and return the wait time from the config data
	 * @param confiig
	 * @return
	 */
	static Integer config_wait_time(Map<String, Object> config) {
		if (config.contains('wait_time')) {
			return config['wait_time']
		} else {
			DEFAULT_WAIT_TIME
		}
	}
	
	static WebDriver browser(config_browser, config_wait_time) {
		WebDriver driver
		if (config_browser == 'chrome') {
			driver = new KSWebDriverFactory.Builder(KSDriverTypeName.CHROME_DRIVER).build()
		} else {
			throw new IllegalArgumentException("${config_browser} is not supported")
		}
		return driver
	}
	
	static WebDriver driver = null
	
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		//println testSuiteContext.getTestSuiteId()
	}

	@BeforeTestCase
	def beforeTestTestCase(TestCaseContext testCaseContext) {
		//println testCaseContext.getTestCaseId()
		//println testCaseContext.getTestCaseVariables()
		if (testCaseContext.getTestCaseId().contains("POM")) {
			Map<String, Object> config = config()
			driver = browser(
				config_browser(config),
				config_wait_time(config))
		}
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		//println testCaseContext.getTestCaseId()
		//println testCaseContext.getTestCaseStatus()
		if (testCaseContext.getTestCaseId().contains("POM")) {
			if (driver != null) {
				driver.quit()
				driver = null	
			}
		}
	}

	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		//println testSuiteContext.getTestSuiteId()
	}
}