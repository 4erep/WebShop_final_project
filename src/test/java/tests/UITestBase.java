package tests;

import api.Authorization;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.*;
import static helpers.WebConfigHelper.isVideoOn;
import static tests.TestData.*;


public class UITestBase {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = getApiUrl();
        Configuration.baseUrl = getWebUrl();
        configureDriver();
    }
    protected String product = "/50s-rockabilly-polka-dot-top-jr-plus-size";


    public void setAuthCookieFiles() {
        Map<String, String> cookies = new Authorization().getCookiesAuthorization(getTestUsername(), getTestPassword());

        open("http://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png");
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookies.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", cookies.get("NOPCOMMERCE.AUTH")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookies.get("ARRAffinity")));
    }

    @AfterEach
    public void addAttachments(){
        String sessionId = getSessionId();

        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());

        closeWebDriver();

        if (isVideoOn()) attachVideo(sessionId);
    }
}
