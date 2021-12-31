package flaskrtest.pages.blog

import org.openqa.selenium.By as SeleniumBy
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * wraps an blog post page in the Flaskr web app.
 * provides accessor methods to title and body
 * 
 <article class="post">
 <header>
 <div>
 <h1>東京はもうすぐ春</h1>
 <div class="about">by kazurayam on 2021-02-10</div>
 </div>
 <a class="action" href="/2/update">Edit</a>
 </header>
 <p class="body">どじょうが泳いでいます</p>
 </article>
 */
public class Post {

	static final SeleniumBy TITLE = SeleniumBy.xpath('//header/div/h1')
	static final SeleniumBy BODY  = SeleniumBy.xpath('//p[1]')
	static final SeleniumBy EDIT  = SeleniumBy.xpath('//header/a')
	static final SeleniumBy ABOUT = SeleniumBy.xpath('//header/div/div[contains(@class,"about")]')

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

	String get_about() {
		WebElement about = article.findElement(ABOUT)
		return about.getText()
	}

	String get_postid() {
		WebElement anchor = article.findElement(EDIT)
		String href = anchor.getAttribute('href')
		String postid = href.split('/')[1]
		return (postid != null) ? postid : ''
	}

	Boolean about_text_contains(String part) {
		String about = this.get_about()
		return about.contains(part)
	}
}
