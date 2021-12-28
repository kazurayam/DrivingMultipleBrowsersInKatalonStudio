package plain.testobject

import com.kazurayam.ks.testobject.By
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

public class LogIn {

	static TestObject form_input_username() {
		return By.xpath("/html/body/section/form/input[@id='username']")
	}

	static TestObject form_input_password() {
		return By.xpath("/html/body/section/form/input[@id='password']")
	}

	static TestObject form_input_LogIn() {
		return By.xpath("/html/body/section/form/input[@value='Log In']")
	}
}
