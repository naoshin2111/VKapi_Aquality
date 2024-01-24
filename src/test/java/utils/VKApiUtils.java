package utils;

import config.ApiUrlConfig;
import config.TestUserConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Post;
import model.PostResponse;

import static io.restassured.RestAssured.given;

public class VKApiUtils {

    private final String accessToken = TestUserConfig.getToken();
    private final String apiBaseUrl = ApiUrlConfig.getApiUrl();

    public PostResponse createPost(String message) {

        String response = given()
                .contentType(ContentType.JSON)
                .baseUri(apiBaseUrl)
                .queryParam("access_token", accessToken)
                .queryParam("v", "5.131")
                .queryParam("message", message)
                .when()
                .post("/wall.post").body().asString();
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
        System.out.println(response);
        JsonPath jsonPath = new JsonPath(response);
        int postId = jsonPath.getInt("response.post_id");
        PostResponse post = new PostResponse();
        post.setPost_id(postId);
        return post;
    }

}
