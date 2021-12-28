package plain.testobject

import com.kazurayam.ks.testobject.By
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

public class Nav {

	static TestObject Flaskr() {
		return By.xpath("/html/body/nav/h1[text()='Flaskr']")
	}

	static TestObject LogOut() {
		return By.xpath("/html/body/nav/ul/li/a[text()='Log Out']")
	}

	static TestObject Register() {
		return By.xpath("/html/body/nav/ul/li/a[text()='Register']")
	}

	static TestObject LogIn() {
		return By.xpath("/html/body/nav/ul/li/a[text()='Log In']")
	}

	static TestObject username(String username) {
		return By.xpath("/html/body/nav/ul/li/span[text()='${username}']")
	}
}
