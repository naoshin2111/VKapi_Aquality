package utils;

import config.EnvironmentConfig;
import config.TestUserConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiUtils {

    private static final String ACCESS_TOKEN = TestUserConfig.getToken();
    private static final String API_BASE_URL = EnvironmentConfig.getApiUrl();

    protected RequestSpecification getBaseRequestSpecification() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131");
    }
}
