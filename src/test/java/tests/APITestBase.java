package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static helpers.DriverHelper.configureDriver;
import static tests.TestData.getApiUrl;

public class APITestBase {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = getApiUrl();
        configureDriver();
    }
}
