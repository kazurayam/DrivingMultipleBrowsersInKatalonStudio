package flaskrtest.pages.blog

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import flaskrtest.actions.LoginAction
import flaskrtest.actions.LogoutAction
import flaskrtest.actions.PostAction
import flaskrtest.data.Song
import flaskrtest.data.Songs
import flaskrtest.data.User

public class IndexPageTest {

	private static Logger logger_ = LoggerFactory.getLogger(getClass())

	URL url = new URL('http://127.0.0.1/')
	WebDriver browser
	Song song_of_miyuki
	Song song_of_queen

	@Before
	void setup() {
		WebUI.openBrowser('')
		browser = DriverFactory.getWebDriver()
		// login as Alice
		LoginAction.do_login(browser, url, User.Alice)
		// Alice creates a post with a song, and then logs out
		song_of_miyuki = Songs.get(0)
		PostAction.new_post(browser, url, User.Alice, song_of_miyuki)
		LogoutAction.do_logout(browser, url)

		// using the same browser, login as Bob
		LoginAction.do_login(browser, url, User.Bob)
		// Bob creates a post with a song, and then logs out
		song_of_queen = Songs.get(1)
		PostAction.new_post(browser, url, User.Bob, song_of_queen)
		LogoutAction.do_logout(browser, url)

		// using the same browser, login as Alice again
		LoginAction.do_login(browser, url, User.Alice)

		// using the same browser, we will continue tests as Alice
	}

	@After
	void teardown() {
		if (browser != null) {
			browser.quit()
		}
	}


	@Ignore
	@Test
	void test_get_posts() {
		IndexPage indexPage = new IndexPage(browser)
		List<Post> posts = indexPage.get_posts()
		logger_.info("posts.size() is " + posts.size())
		assert posts.size() > 0
	}

	@Test
	void test_get_posts_by_Alice() {
		IndexPage indexPage = new IndexPage(browser)
		List<Post> posts = indexPage.get_posts_by(User.Alice)
		assert posts.size() > 0
		//println "posts.get(0).get_title() = " + posts.get(0).get_title()
		//println "posts.get(0).get_about() = " + posts.get(0).get_about()
		//println "posts.get(0).get_postid() = " + posts.get(0).get_postid()
		assert posts.get(0).get_title().contains(song_of_miyuki.title)
	}

	
	@Test
	void test_get_posts_by_Bob() {
		IndexPage indexPage = new IndexPage(browser)
		List<Post> posts = indexPage.get_posts_by(User.Bob)
		assert posts.size() > 0
		//println "posts.get(0).get_title() = " + posts.get(0).get_title()
		//println "posts.get(0).get_about() = " + posts.get(0).get_about()
		//println "posts.get(0).get_postid() = " + posts.get(0).get_postid()
		assert posts.get(0).get_title().contains(song_of_queen.title)
	}

	
	@Test
	void test_get_posts_by_Chage() {
		IndexPage indexPage = new IndexPage(browser)
		List<Post> posts = indexPage.get_posts_by(User.Chage)
		assert posts.size() == 0
	}
}
