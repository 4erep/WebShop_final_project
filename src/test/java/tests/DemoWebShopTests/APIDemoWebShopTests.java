package tests.DemoWebShopTests;

import api.Authorization;
import customAnnotations.JiraIssue;
import customAnnotations.JiraIssues;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.APITestBase;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static templates.ReportTemplates.filters;
import static tests.TestData.getTestPassword;
import static tests.TestData.getTestUsername;

@Tag("API")
@Owner("kvrudnev")
@Feature("API tests for DemoWebShop")
public class APIDemoWebShopTests extends APITestBase {

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Send e-mail from product page with API")
    public void SendEmailToFriendWithAuthorizationAPITest() {
        Map<String, String> cookiesAuth = new Authorization().getCookiesAuthorization(getTestUsername(), getTestPassword());

        Response response =
                given()
                        .filter(filters().customTemplates())
                        .contentType(ContentType.URLENC)
                        .cookies(cookiesAuth)
                        .body("FriendEmail=www%40test.ru&YourEmailAddress=qaguru%40qa.guru&PersonalMessage=Tes1&send-email=Send+email")
                        .when()
                        .post("/productemailafriend/4")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

        assertThat(response.asString(), containsString("Your message has been sent"));
    }

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Add product to wishlist with API")
    public void addToWishListWithAuthorizationAPITest() {
        Map<String, String> cookiesAuth = new Authorization().getCookiesAuthorization(getTestUsername(), getTestPassword());
        given()
                .cookies(cookiesAuth)
                .contentType(ContentType.URLENC)
                .body("giftcard_4.RecipientName=Test&giftcard_4.SenderName=qaguru&giftcard_4.Message=Hello&giftcard_4.EnteredQuantity=1")
                .when()
                .post("/addproducttocart/details/4/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @JiraIssues({@JiraIssue("QC3-28")})
    @DisplayName("Add product to cart with API")
    public void addToCartWithoutAuthorizationAPITest() {
        Map<String, String> cookies = new Authorization().getCookiesWithoutAuthorization();
            given()
                    .filter(filters().customTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .cookies(cookies)
                    .body("product_attribute_16_5_4=14&product_attribute_16_6_5=15&product_attribute_16_3_6=18&product_attribute_16_4_7=44&product_attribute_16_8_8=22&addtocart_16.EnteredQuantity=1")
                    .when()
                    .post("/addproducttocart/details/16/1")
                    .then()
                    .statusCode(200)
                    .log().body()
                    .body("success", is(true))
                    .body("updatetopcartsectionhtml", equalTo("(1)"));


        }


    }
