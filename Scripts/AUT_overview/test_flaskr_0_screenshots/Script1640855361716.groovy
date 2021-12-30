import org.openqa.selenium.WebDriver

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import flaskrtest.ConfigTest
import flaskrtest.data.Song
import flaskrtest.data.Songs
import flaskrtest.pages.auth.LogInPage
import flaskrtest.pages.auth.RegisterCredentialPage
import flaskrtest.pages.blog.CreatePostPage
import flaskrtest.pages.blog.IndexPage
import flaskrtest.pages.blog.Post

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

/**
Automated Web UI testing for the flaskr-kazurayam application
developed by the pywebapp subproject.

0. We assume the Docker container of flakr-kazurayam is running.
1. Navigate to http://localhost:80/
2. Navigate to the Register form page where
    we register a new credential.
    We dynamically generate Username/Password
    based on the current timestamp.
3. Do Login using the newly added credential.
4. Make a new post. Type title and body.
5. Verify if the post is visible in the list.
6. Edit the post text and save it.
7. Verify if the latest post text is displayed in the list of posts.
8. then, identify the post by its ID, and update it with a body text slightly changed.
9. then, identify the post by its ID, and delete it
 */

// read the configuration

Map<String, Object> config = ConfigTest.config()
String config_browser = ConfigTest.config_browser(config)
Integer config_wait_time = ConfigTest.config_wait_time(config)

// open a browser
WebDriver browser = ConfigTest.browser(config_browser, config_wait_time)
browser.manage().window().setSize(new Dimension(800, 640));
browser.manage().window().setPosition(new Point(0, 0));

Map<String, Object> credential = ConfigTest.credential()
//println credential

// prepare a directory to save screenshots
Path outdir = Paths.get(RunConfiguration.getProjectDir()).resolve("build/tmp/testOutput/test_flaskr")
if (Files.exists(outdir)) {
	outdir.toFile().deleteDir()
}
Files.createDirectories(outdir)


Song song = Songs.get(0)

// let's start playing on the Flaskr web app
IndexPage index_page = new IndexPage(browser)
index_page.load()

// assert that the "Posts" header is displayed in the index page
assert index_page.posts_header_exists()
takeScreenshot(browser, outdir, "1_start_from_here")
	
// register a new credential into the database
// - open the Register page
index_page.open_register_page()
RegisterCredentialPage register_credential_page = new RegisterCredentialPage(browser)
assert register_credential_page.register_button_exists()

// - type username and password, then click the Register button
register_credential_page.type_username(credential['username'])
register_credential_page.type_password(credential['password'])
takeScreenshot(browser, outdir, "2_going_to_register_a_username")
register_credential_page.do_register()

// - now we are transferred to the Login page
LogInPage login_page = new LogInPage(browser)
assert login_page.login_button_exists()
takeScreenshot(browser, outdir, "3_a_username_has_been_registered")
	
// Login with the added credential
// - type credentials and do login
login_page.type_username(credential['username'])
login_page.type_password(credential['password'])
takeScreenshot(browser, outdir, "4_about_to_login")
login_page.do_login()

// - make sure we are on the index page
assert index_page.posts_header_exists()
takeScreenshot(browser, outdir, "5_the_user_have_logged_in")
	
// create a new Post
index_page.open_create_post_page()
CreatePostPage create_post_page = new CreatePostPage(browser)
assert create_post_page.save_button_exists()
takeScreenshot(browser, outdir, "6_opened_page_to_create_a_new_post")	

// type title
String title = song.title + " --- " + song.by
create_post_page.type_title(title)
// type body
String lyric = song.lyric
create_post_page.type_body(lyric)
takeScreenshot(browser, outdir, "7_has_typed_texts_into_a_post")
// save the post
create_post_page.do_save()

// verify if the post is present in the index page
Post post = index_page.get_post_latest()
assert post != null
assert post.get_title() == title
assert post.get_body() == lyric
takeScreenshot(browser, outdir, "8_the_post_is_found_in_the_list")

browser.quit()
	

def takeScreenshot(WebDriver browser, Path dir, String name) {
	DriverFactory.changeWebDriver(browser)
	WebUI.takeScreenshot(dir.resolve(name + ".png").toString())
}