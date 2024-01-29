package utils;

import config.EnvironmentConfig;
import config.TestUserConfig;
import constant.Parameters;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiUtils {

    private static final String ACCESS_TOKEN = TestUserConfig.getToken();
    private static final String API_BASE_URL = EnvironmentConfig.getApiUrl();
    private static final String API_VERSION = EnvironmentConfig.getApiVersion();

    protected RequestSpecification getBaseRequestSpecification() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL)
                .queryParam(Parameters.TOKEN, ACCESS_TOKEN)
                .queryParam(Parameters.VERSION, API_VERSION);
    }
}
