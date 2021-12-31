import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import flaskrtest.data.Song
import flaskrtest.data.Songs
import flaskrtest.pages.auth.LogInPage
import flaskrtest.pages.auth.RegisterCredentialPage
import flaskrtest.pages.blog.CreatePostPage
import flaskrtest.pages.blog.IndexPage
import flaskrtest.pages.blog.Post

// preparation for ChromeDriver
String chrome_executable_path = DriverFactory.getChromeDriverPath()
System.setProperty('webdriver.chrome.driver', chrome_executable_path)

// open a Chome browser for Alice
WebDriver browser0 = new ChromeDriver()
layoutWindow(browser0, new Dimension(720, 800), new Point(0, 0))

// open a Chrome browser for Bob
WebDriver browser1 = new ChromeDriver()
layoutWindow(browser1, new Dimension(720, 800), new Point(720, 0))

// Alice logs in
login(browser0, "Alice", "AliceInTheWonderLand")

// Bob logs in
login(browser1, "Bob", "LikeARollingStone")

Song song_of_miyuki = Songs.get(0)
Song song_of_queen  = Songs.get(1)

// Alice makes a post
post(browser0, "Alice", song_of_miyuki)

// Bob makes a post
post(browser1, "Bob", song_of_queen)

// ensure Alice finds the song that Bob posted
finds(browser0, "Alice", "Bob", song_of_queen)

// ensure Bob finds the song that Alice posted
finds(browser1, "Bob", "Alic", song_of_miyuki)

WebUI.delay(1)

// close 2 browsers
browser0.quit()
browser1.quit()

/**
 * 
 * @param browser
 * @param username
 * @param password
 * @return
 */
def login(WebDriver browser, String username, String password) {
	// let's start playing on the Flaskr web app
	IndexPage index_page = new IndexPage(browser)
	index_page.load()
	
	// ensuree we are on the index page
	assert index_page.app_header_exists()
	assert index_page.register_anchor_exists()
	assert index_page.login_anchor_exists()
	assert index_page.posts_header_exists()
		
	// we want to navigate to thee Register page
	index_page.open_register_page()
	RegisterCredentialPage register_credential_page = new RegisterCredentialPage(browser)
	
	// make sure we are o the Register page
	assert register_credential_page.register_button_exists()
	
	// we want to register a user
	register_credential_page.type_username(username)
	register_credential_page.type_password(password)
	register_credential_page.do_register()
	
	// check if the user is already registered
	boolean alreadyRegistered = register_credential_page.flash_exists()
	if (alreadyRegistered) {
		KeywordUtil.markWarning("usernamee ${username} is already registered.")
		// we are still on the Register page
		// so we want to navigate to the Log In page
		register_credential_page.do_login()
	}
	
	// - now we are transferred to the Login page
	LogInPage login_page = new LogInPage(browser)
	assert login_page.login_button_exists()
	
	// Login with the added credential
	// - type credentials and do login
	login_page.type_username(username)
	login_page.type_password(password)
	login_page.do_login()
	
	// - make sure we are on the index page
	assert index_page.posts_header_exists()
}

/**
 * 
 * @param browser
 * @param usernname
 * @param song
 * @return
 */
def post(WebDriver browser, String username, Song song) {
	DriverFactory.changeWebDriver(browser)
	// let's start from the index page
	IndexPage index_page = new IndexPage(browser)
	index_page.load()
	
	// we want to navigate to the CreatePost page
	index_page.open_create_post_page()
	CreatePostPage create_post_page = new CreatePostPage(browser)
	assert create_post_page.save_button_exists()
	
	// type title
	String title = song.title + " --- " + song.by
	create_post_page.type_title(title)
	// type body
	String lyric = song.lyric
	create_post_page.type_body(lyric)
	// save the post
	create_post_page.do_save()
	
	// now we are on the index page
	// make sure that the 1st article is the song just posted by usernname
	Post post = index_page.get_post_latest()
	assert post != null
	assert post.get_title() == title
	assert post.about_text_contains(username)
	assert post.get_body() == lyric
}

def finds(WebDriver browser, String username, String somebody, Song song) {
	DriverFactory.changeWebDriver(browser)
	// let's start from the index page
	IndexPage index_page = new IndexPage(browser)
	index_page.load()
	// find a post by somebody
	List<Post> postsBySomebody = index_page.get_posts_by(somebody)
	assert postsBySomebody.size() > 0
}

/**
 *
 * @param browser
 * @param dimension
 * @param point
 * @return
 */
def layoutWindow(WebDriver browser, Dimension dimension, Point point) {
	browser.manage().window().setSize(dimension)
	browser.manage().window().setPosition(point)
}