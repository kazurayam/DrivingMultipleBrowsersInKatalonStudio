# Flaskr Test in Katalon Studio

I am going to explain what I have done to test a blog system using 2 windows of Chrome browsers simultaneously. I used Katalon Studio. I employed the design pattern "Page Object Model" (POM). This project is meant to be a sample code for myself in future to develop a large scale test suite.

## Application Under Test at a glance

I have a Web Application to test using Selenium WebDriver. It is a runninng sample code presented by
["Flask Tutorial"](https://flask.palletsprojects.com/en/2.0.x/tutorial/). It is

> a basic blog application called Flaskr. Users will be able to register, log in, create posts, and edit or delete their own posts.

I just typed in the sample codes as published without any changes. Later I will describe how to set it up on your own PC. Let me go through the screenshots of the web app to grasp what it is.

I open Chrome browser to visit the URL of the Application Under Test:

-   <http://127.0.0.1/>

I find the index page as follows, which has no blog posts submitted yet.

![1](./images/test_flaskr/1_start_from_here.png)

At first, I need to register a User for me before creating posts. I click the `Register` link. Then a form is presented where I am requested to type a credential (username and password pair).

![2](./images/test_flaskr/2_going_to_register_a_username.png)

I click the `Register` button. Then the user will be registered. I am transferred to the Login page.

![3](./images/test_flaskr/3_a_username_has_been_registered.png)

I re-type the credential (username and password) that I used to create my User.

![4](./images/test_flaskr/4_about_to_login.png)

I click the `Log In` button. Then I am transferred to the Index page. Please note that the username is displayed in the header. This implies that now I am logged into this blog application.

![5](./images/test_flaskr/5_the_user_have_logged_in.png)

Now I am going to create a new post. I click the `New` link. Then a empty form is displayed.

![6](./images/test_flaskr/6_opened_page_to_create_a_new_post.png)

I type texts into the `title` and `body` field.

![7](./images/test_flaskr/7_has_typed_texts_into_a_post.png)

I click the `Save` button. Then I am transferred to the index page. Please find a post has been saved into the Blob system and is now displayed in the list of posts.

![8](./images/test_flaskr/8_the_post_is_found_in_the_list.png)

## Problem to solve

You can easily imagine; I can create 2 or more users. So I should be able send multiple posts to <http://127.0.0.1/> from 2 or more browsers simultaneously using 2 users each. When a user Alice made a post, then another user Bob should be able to see the post by Alice in an instant. When Bob made a new post, then soon Alice should be able to see the Bob’s post.

This test scenario --- testing a web app with 2 browsers simultaneously --- is applicable to practical business. Let me assume I have an EC site which has dual user interface: Customer UI and Administrator UI. When a user submit an order to purchase some product, then an administrator should be able to see the order in the list of outstanding orders. I want to test both of the Customer UI and the Administrator UI. My Web UI test should submit an order in the Customer UI; then my test my test verify if the order is appearing in the Administrator UI. I want my test to simulate this dual-participants' interaction.

**But how can I do testing a web app with 2 browsers simultaneously in Katalon Studio?**

I can point out a basic problem : Katalon Studio’s `WebUI.openBrowser()` keyword can not open 2 browsers.

I made [Test Cases/analysis/WebUI\_openBrowser\_twice](Scripts/analysis/1_WebUI_openBrowser_twice/Script1640780797502.groovy) in Katalon Studio.

    import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

    WebUI.openBrowser("http://127.0.0.1/")
    WebUI.openBrowser("http://127.0.0.1/")

    WebUI.delay(1)
    WebUI.closeBrowser()

This simple script calls `WebUI.openBrowser()` keyword twice. Will we see 2 windows of browsers opened? --- No. The 1st window opens but is closed by Katalon Studio before the 2nd window opens.

![1st\_browser\_is\_closed](./images/analysis/1st_browser_is_closed_before_2nd.png)

This is the way how the `WebUI.openBrowser()` is designed. You can not open 2 browses using this keyword.

## Solution

Behind the `WebUI.openBrowser()` and other `WebUI.xxx` keywords , [Selenium WebDriver](https://www.browserstack.com/guide/selenium-webdriver-tutorial) is working. If I write a script that makes an instances of `WebDriver` class by calling `org.openwa.selenium.chrome.ChromeDriver` directly, then I can open a Chrome browser. My script can create 2 instances of `WebDriver` and keep them running. I can have 2 windows of Chrome browser and my test script can talk to them via the WebDriver API such as `driver.navigate().to("http://127.0.0.1/")`.

However, Katalon’s `WebUI.xxx` keyword, unless properly configured, do not work with the browser (a `WebDriver` instance) that my script instantiated unless my script informs `WebUI.xxx` keywords of the `WebDriver` instance. Let me show you an experiment.

[Test Cases/analysis/2\_WebUI\_keywords\_do\_not\_know](Scripts/analysis/2_WebUI_keywords_do_not_know/Script1640781667491.groovy)

    String chrome_executable_path = DriverFactory.getChromeDriverPath()
    System.setProperty('webdriver.chrome.driver', chrome_executable_path)

    WebDriver browser = new ChromeDriver()
    browser.navigate().to('http://127.0.0.1/')

    // WebUI.xxx do not know the WebDriver instance created here

    String windowTitle = WebUI.getWindowTitle()
    assert "Posts - Flaskr" == windowTitle

This script opens a Chrome browser window by calling `new ChromeDriver()`. But the script does not inform Katalon Studio of the WebDriver instance. Therefore calling `WebUI.getWindowTitle()` keyword fails.

![2 unable to get title](./images/analysis/2_unable_to_get_title.png)

How to fix this error? --- call `DriverFactory.changeWebDriver(WebDriver)`.

[TestCases/analysis/3\_how\_to\_inform\_WeebUUI\_keywords](../Scripts/analysis/3_how_to_inform_WebUI_keywords/Script1640781643037.groovy)

    import org.openqa.selenium.WebDriver
    import org.openqa.selenium.chrome.ChromeDriver

    import com.kms.katalon.core.webui.driver.DriverFactory
    import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

    String chrome_executable_path = DriverFactory.getChromeDriverPath()
    System.setProperty('webdriver.chrome.driver', chrome_executable_path)

    WebDriver browser = new ChromeDriver()
    browser.navigate().to('http://127.0.0.1/')

    // i can let WebUI.xxx keywords know the WebDriver instance created by my script
    DriverFactory.changeWebDriver(browser)

    String windowTitle = WebUI.getWindowTitle()
    assert "Posts - Flaskr" == windowTitle

This code passes.

![capable](./images/analysis/3_capable_to_get_title.png)

Now `WebUI.xxx` keywords can interact with the browser which was created by my script using `new ChromeDriver()` API.

My script can open 2 browsers by calling `new ChromeDriver()` API. My script can call `DriverFactory.changeWebDriver(WebDriver)` API so that `WebUI.xxx` keywords can interact with the browser which was created by my script. These are the magic spell you should need to know.

## Solution described

How could I actually implement a set of Web UI test that targets the "Flaskr" blog system? --- Here I will show you a code set that runs. The test code has the following characteristics.

1.  I will start with a Test Case in plain-old Katalon Studio style. At first it looks trivial.

2.  The `Flaskr` Web App has several pages (authenticating users, creating/editing/deleting blob posts), which is small comparing to the real-world Web Apps, but my Test Case in plain-old Katalon Studio style soon gets large with a lot of code duplications I add more test scenarios.

3.  In order to make my tests easier to understand and maintainable, I will introduce the [POM (Page Object Model)](https://www.guru99.com/page-object-model-pom-page-factory-in-selenium-ultimate-guide.html). With the POM concept in mind, I can implement my tests well modularized.

4.  Writing/understanding tests with POM requires good enough programming skill.

My code set is large enough and complex. The target Web App ("Flaskr") deserves it. I could not make it any simpler. I hope that people with sufficient programming skill in Java/Groovy would be able to read the source code and understand them without verbose descriptions. I would note some essentials points, but I would not explain the code in too much detail.

### Setting up Envrionment

#### Installing docker command

I installed `docker` command into my Mac Book Air

    $ brew install docker
    ...

I checked if the `docker` command is running.

    $ docker --version
    Docker version 20.10.2, build 2291f61
    :~
    $

Windows users can use "Docker Desktop on Windnows" of course,

-   <https://docs.docker.com/desktop/windows/install/>

#### Starting up HTTP Server App at <http://127.0.0.1/>

I made a temporary directory with any name.

    $ cd ~
    $ mkdir flaskr

In the temp directory, I started a web app using a docker image.

    $ cd ~/flaskr
    $ docker run -it -p 80:8080 --rm kazurayam/flaskr-kazurayam:1.0.3
    Serving on http://0.0.0.0:8080

Or, you can use the following shell script in the root directory of this project.

-   [startup\_flaskr.sh](./startup_flaskr.sh)

<!-- -->

    if [ ! -e ./tmp ]; then
        mkdir tmp
    fi 
    cd ./tmp
    docker run -it -p 80:8080 --rm kazurayam/flaskr-kazurayam:1.0.3
    cd -

Now I can open a browser and visit the following URL.

-   <http://127.0.0.1:80/>

![flaskr just started](docs/images/flaskr_just_started.png)

I made a docker image which is publicly available at Docker Hub :

-   <https://hub.docker.com/repository/docker/kazurayam/flaskr-kazurayam>

## Sample codes

### Test Cases/plain/TC1\_open\_close

### Test Cases/plain/TC2\_register\_Alice\_then\_LogIn

### Test Cases/POM/test\_flaskr

### Test Cases/POM/test\_flaskr\_modularized

### Test Cases/POM/test\_flaskr\_final

A Movie is available at …​

1.  I will use **Flaskr** at `http://127.0.0.1` as a partner for me to develop a set of Web UI test scripts in Katalon Studio.

2.  I will open 2 Chrome browsers. On each, I will visit the Flaskr site and interact with it. I will keep 2 browsers open and operate on them simultaneously.

3.  On one browser, I will register a user **Alice** and make some posts.

4.  On another browser, I will register another user **Bob** and make some posts.

5.  Alice should be able to read the posts made by Bob. Bob should be able to read the posts made by Alice. My web ui test in Katalon Studio will check this conversation.
