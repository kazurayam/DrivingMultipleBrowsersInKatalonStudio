package flaskrtest.pages.blog

import static org.junit.Assert.*
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.junit.Test
import org.junit.Before
import org.junit.After
import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import flaskrtest.data.User
import flaskrtest.data.Song
import flaskrtest.data.Songs
import flaskrtest.actions.LoginAction
import flaskrtest.actions.PostAction

public class PostTest {

	private static Logger logger_ = LoggerFactory.getLogger(getClass())

	URL url = new URL('http://127.0.0.1/')
	WebDriver browser

	@Before
	void setup() {
		WebUI.openBrowser('')
		browser = DriverFactory.getWebDriver()
		// login as Alice
		LoginAction.do_login(browser, url, User.Alice)
		// Alice creates a post with a song
		Song song_of_miyuki = Songs.get(0)
		PostAction.new_post(browser, url, User.Alice, song_of_miyuki)
	}

	@After
	void teardown() {
		if (browser != null) {
			browser.quit()
		}
	}

	@Test
	void test_get_postid() {
		IndexPage indexPage = new IndexPage(browser)
		List<Post> posts = indexPage.get_posts_by(User.Alice)
		if (posts.size() > 0) {
			Post post = posts.get(0)
			assert post.get_postid().isInteger()
		} else {
			fail("posts are empty")
		}
	}
}
