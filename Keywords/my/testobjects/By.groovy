package my.testobjects

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

public class By {
	
	static TestObject xpath(String expr) {
		TestObject tObj = new TestObject(UUID.randomUUID().toString())
		tObj.addProperty('xpath', ConditionType.EQUALS, expr)
	}
}
