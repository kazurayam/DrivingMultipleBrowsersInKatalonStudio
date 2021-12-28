package com.kazurayam.ks.testobject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

import org.openqa.selenium.WebElement

public class By {

	static TestObject xpath(String expr) {
		return xpath(UUID.randomUUID().toString(), expr)
	}

	static TestObject xpath(String id, String expr) {
		TestObject tObj = new TestObject(id)
		tObj.addProperty('xpath', ConditionType.EQUALS, expr)
		return tObj
	}

	static TestObject xpath(String expr, Map<String, String> binding) {
		return xpath(UUID.randomUUID().toString(), expr, binding)
	}

	static TestObject xpath(String id, String expr, Map<String, String> binding) {
		def engine = new groovy.text.SimpleTemplateEngine()
		def template = engine.createTemplate(expr).make(binding)
		TestObject tObj = new TestObject(id)
		tObj.addProperty('xpath', ConditionType.EQUALS, template.toString())
		return tObj
	}
}
