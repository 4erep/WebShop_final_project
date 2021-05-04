package tests.DemoWebShopTests;

import com.codeborne.selenide.Condition;
import customAnnotations.JiraIssue;
import customAnnotations.JiraIssues;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.UITestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.DriverHelper.getConsoleLogs;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static tests.TestData.getTestPassword;
import static tests.TestData.getTestUsername;

@Tag("UI")
@Owner("kvrudnev")
@Feature("Main page tests")
public class MainPageTests extends UITestBase {

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("UI login test")
    public void login() {
        step("Login with username and password", () -> {
            open("");
            $(".ico-login").click();
            $("#Email").val(getTestUsername());
            $("#Password").val(getTestPassword()).pressEnter();
        });
        step("Check that the correct user is logged in", () -> {
            $(".account").shouldHave(text(getTestUsername()));
        });
    }

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Subscribe to the newsletter")
    public void subscribeToNewsletter() {
        step("open url", () -> open(""));
        step("Enter e-mail to text field", () -> {
            $("#newsletter-email").val("www@test.ru");
            $("#newsletter-subscribe-button").click();
        });
        step("Check that user is subscribed", () -> {
            $("#newsletter-result-block").shouldHave(text("Thank you for signing up!"));
        });
    }


    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Check that all blocks are visible on page")
    public void checkAllBlocksLoadedTest() {
        step("Check that all blocks are visible", () -> {
            open("");
            $("img[alt='Tricentis Demo Web Shop']").shouldBe(Condition.visible);
            $(".header-links-wrapper").shouldBe(Condition.visible);
            $(".search-box").shouldBe(Condition.visible);
            $(".header-menu").shouldBe(Condition.visible);
            $(".leftside-3").shouldBe(Condition.visible);
            $(".rightside-3").shouldBe(Condition.visible);
            $(".center-3").shouldBe(Condition.visible);
        });
    }

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Console log should not contain errors")
    void checkConsoleLogErrorsTest() {
        step("Open  Url", () -> open(""));
        step("Verify there are no errors in console log", () -> {
            String consoleLogs = getConsoleLogs();
            assertThat(consoleLogs, not(containsString("SEVERE")));
        });
    }
}
