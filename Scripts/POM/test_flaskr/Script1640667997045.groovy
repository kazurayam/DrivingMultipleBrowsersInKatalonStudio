import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import flaskrtest.ConfigTest
import flaskrtest.Songs
import flaskrtest.pages.auth.LogInPage
import flaskrtest.pages.auth.RegisterCredentialPage
import flaskrtest.pages.blog.CreatePostPage
import flaskrtest.pages.blog.IndexPage
import flaskrtest.pages.blog.Post

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
Map<String, Object> credential = ConfigTest.credential()
//println credential

IndexPage index_page = new IndexPage(browser)
index_page.load()

// assert that the "Posts" header is displayed in the index page
assert index_page.posts_header_exists()

// register a new credential into the database
// - open the Register page
index_page.open_register_page()
register_credential_page = new RegisterCredentialPage(browser)
assert register_credential_page.register_button_exists()

// - type username and password, then click the Register button
register_credential_page.type_username(credential['username'])
register_credential_page.type_password(credential['password'])
register_credential_page.do_register()

// - now we are transferred to the Login page
login_page = new LogInPage(browser)
assert login_page.login_button_exists()

WebUI.delay(1)

// Login with the added credential
// - type credentials and do login
login_page.type_username(credential['username'])
login_page.type_password(credential['password'])
login_page.do_login()

// - make sure we are on the index page
assert index_page.posts_header_exists()

// create a new Post
index_page.open_create_post_page()
create_post_page = new CreatePostPage(browser)
assert create_post_page.save_button_exists()

// type title
Map<String, String> song = Songs.song(0)
String title = song.get('title')  + " --- " + song.get('by')
create_post_page.type_title(title)
// type body
String lyric = song.get('lyric')
create_post_page.type_body(lyric)
// save the post
create_post_page.do_save()

// verify if the post is present in the index page
Post post = index_page.get_post_latest()
assert post != null
assert post.get_title() == title
assert post.get_body() == lyric

WebUI.delay(1)

browser.quit()

