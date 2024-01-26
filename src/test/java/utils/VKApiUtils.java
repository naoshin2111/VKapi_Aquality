package utils;

import config.ApiUrlConfig;
import config.TestUserConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.PhotoSave;
import model.PhotoUploadServer;
import model.PhotoUploadWall;
import model.Post;
import java.io.File;
import static io.restassured.RestAssured.given;

public class VKApiUtils {

    private final String accessToken = TestUserConfig.getToken();
    private final String apiBaseUrl = ApiUrlConfig.getApiUrl();

    public Post createPost(String message) {

        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri(apiBaseUrl)
                .queryParam("access_token", accessToken)
                .queryParam("v", "5.131")
                .queryParam("message", message)
                .when()
                .post("/wall.post")
                .then()
                .statusCode(200)
                .extract()
                .response();

        int postId = response.jsonPath().getInt("response.post_id");
        return new Post(postId);
    }

    public PhotoUploadServer getWallUploadServer() {
        Response uploadServerResponse = given()
                .contentType(ContentType.JSON)
                .baseUri(apiBaseUrl)
                .queryParam("access_token", accessToken)
                .queryParam("v", "5.131")
                .get("/photos.getWallUploadServer")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String uploadUrl = uploadServerResponse.jsonPath().getString("response.upload_url");
        return new PhotoUploadServer(uploadUrl);
    }

    public PhotoUploadWall uploadPhotoToWall(String imagePath, String uploadUrl) {
        Response uploadedPhotoResponse = given()
                .multiPart("photo", new File(imagePath))
                .when()
                .post(uploadUrl)
                .then()
                .statusCode(200)
                .extract()
                .response();


        String server = uploadedPhotoResponse.jsonPath().getString("server");
        String photo = uploadedPhotoResponse.jsonPath().getString("photo");
        String hash = uploadedPhotoResponse.jsonPath().getString("hash");

        return new PhotoUploadWall(server, photo, hash);
    }

    public PhotoSave saveWallPhoto(PhotoUploadWall photoUploadWall) {
        Response savePhotoResponse = given()
                .baseUri(apiBaseUrl)
                .queryParam("access_token", accessToken)
                .queryParam("v", "5.131")
                .queryParam("server", photoUploadWall.getServer())
                .queryParam("photo", photoUploadWall.getPhoto())
                .queryParam("hash", photoUploadWall.getHash())
                .post("/photos.saveWallPhoto")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String photoId = savePhotoResponse.jsonPath().getString("response[0].id");
        System.out.println(photoId);
        String ownerId = savePhotoResponse.jsonPath().getString("response[0].owner_id");

        System.out.println(savePhotoResponse.getBody().asString());
        return new PhotoSave(photoId, ownerId);
    }

    public Post editPostWithPhoto(int postId, String newText, String attachment) {
        Response editPostResponse = given()
                .baseUri(apiBaseUrl)
                .queryParam("access_token", accessToken)
                .queryParam("v", "5.131")
                .queryParam("post_id", postId)
                .queryParam("message", newText)
                .queryParam("attachments", attachment)
                .post("/wall.edit")
                .then()
                .statusCode(200)
                .extract()
                .response();

        int id = editPostResponse.jsonPath().getInt("response.post_id");
        return new Post(id);
    }
}
