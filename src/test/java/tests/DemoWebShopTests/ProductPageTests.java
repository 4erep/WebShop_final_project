package tests.DemoWebShopTests;

import com.codeborne.selenide.SelenideElement;
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
import static io.qameta.allure.Allure.step;

@Tag("UI")
@Owner("kvrudnev")
@Feature("Product page tests")
public class ProductPageTests extends UITestBase {
    SelenideElement basket = $("a[href='/wishlist'] .wishlist-qty");

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Send e-mail from product page with UI")
    public void SendEmailToFriendWithCookiesAuthorizationUITest() {
        step("Set cookies", this::setAuthCookieFiles);
        step("Open product page", () -> open("" + product));
        step("Send e-mail", () -> {
            $("[value='Email a friend']").click();
            $("#FriendEmail").val("Test@com.ru");
            $("#PersonalMessage").val("TestMessage");
            $("[value='Send email']").click();
        });
        step("Check that message has been sent", () -> {
            $(".result").shouldHave(text("Your message has been sent."));
        });
    }

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Add product to wishlist with UI")
    public void addProductToWishList() {
        step("Set cookies", this::setAuthCookieFiles);
        step("Open product page", () -> open("" + product));
        String initialCount = basket.getText().
                replaceAll("[^\\d.]", "");
        int initialWishListCount = Integer.parseInt(initialCount);
        String count = String.valueOf(initialWishListCount + 1);

        step("Add product to wishlist", () -> $(".add-to-wishlist-button").click());
        step("Check that product was added", () -> {
            basket.shouldHave(text(count).
                    because(String.format("???????????????? ???????????????????? %s ???? ?????????????????? ?? ?????????????????????? ??????????????????????", count)));
        });
    }
}
