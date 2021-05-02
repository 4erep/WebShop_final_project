package config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(org.aeonbits.owner.Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/testdata.properties"
})
public interface TestDataConfig extends Config {
    @Key("user.login")
    String testUsername();

    @Key("user.password")
    String testPassword();

    @Key("web.url")
    String webUrl();

    @Key("api.url")
    String apiUrl();
}
