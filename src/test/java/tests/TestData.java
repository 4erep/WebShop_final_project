package tests;

import config.TestDataConfig;
import org.aeonbits.owner.ConfigFactory;

public class TestData {


    private static TestDataConfig getTestData() {
        return ConfigFactory.newInstance().create(TestDataConfig.class, System.getProperties());
    }

    public static String getWebUrl() {
        return getTestData().webUrl();
    }

    public static String getApiUrl() {
        return getTestData().apiUrl();
    }

    public static String getTestUsername() {
        return getTestData().testUsername();
    }

    public static String getTestPassword() {
        return getTestData().testPassword();
    }
}
