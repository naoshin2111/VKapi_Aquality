package utils;

import config.ApiUrlConfig;
import config.TestUserConfig;
import constant.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.*;
import java.io.File;
import static io.restassured.RestAssured.given;

public class VKApiUtils {

    private final String ACCESS_TOKEN = TestUserConfig.getToken();
    private final String API_BASE_URL = ApiUrlConfig.getApiUrl();

    public PostResponse createPost(String message) {
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131")
                .queryParam("message", message)
                .when()
                .post(EndPoints.WALL_POST)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int postId = response.jsonPath().getInt("response.post_id");
        return new PostResponse(postId);
    }

    public PhotoServerResponse uploadPhotoToServer() {
        Response uploadServerResponse = given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131")
                .get(EndPoints.PHOTOS_GET_WALL_UPLOAD_SERVER)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String uploadUrl = uploadServerResponse.jsonPath().getString("response.upload_url");
        return new PhotoServerResponse(uploadUrl);
    }

    public PhotoWallResponse uploadPhotoToWall(String imagePath, String uploadUrl) {
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
        return new PhotoWallResponse(server, photo, hash);
    }

    public PhotoSaveResponse savePhotoToWall(PhotoWallResponse PhotoWallResponse) {
        Response savePhotoResponse = given()
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131")
                .queryParam("server", PhotoWallResponse.getServer())
                .queryParam("photo", PhotoWallResponse.getPhoto())
                .queryParam("hash", PhotoWallResponse.getHash())
                .post(EndPoints.PHOTOS_SAVE_WALL_PHOTO)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String photoId = savePhotoResponse.jsonPath().getString("response[0].id");
        String ownerId = savePhotoResponse.jsonPath().getString("response[0].owner_id");
        return new PhotoSaveResponse(photoId, ownerId);
    }

    public PostResponse editPostWithPhoto(int postId, String newText, String attachment) {
        Response editPostResponse = given()
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131")
                .queryParam("post_id", postId)
                .queryParam("message", newText)
                .queryParam("attachments", attachment)
                .post(EndPoints.WALL_EDIT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int id = editPostResponse.jsonPath().getInt("response.post_id");
        return new PostResponse(id);
    }

    public CommentResponse addCommentToPost(int postId, String message) {
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131")
                .queryParam("post_id", postId)
                .queryParam("message", message)
                .when()
                .post(EndPoints.WALL_CREATE_COMMENT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int commentId = response.jsonPath().getInt("response.comment_id");
        return new CommentResponse(commentId);
    }

    public LikeResponse checkLikedByUser(String ownerId, String type, int itemId) {
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131")
                .queryParam("user_id", ownerId)
                .queryParam("type", type)
                .queryParam("owner_id", ownerId)
                .queryParam("item_id", itemId)
                .get(EndPoints.LIKES_IS_LIKED)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int liked = response.jsonPath().getInt("response.liked");
        int copied = response.jsonPath().getInt("response.copied");
        return new LikeResponse(liked, copied);
    }

    public PostDeleteResponse deletePost(int postId) {
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL)
                .queryParam("access_token", ACCESS_TOKEN)
                .queryParam("v", "5.131")
                .queryParam("post_id", postId)
                .when()
                .post(EndPoints.WALL_DELETE)
                .then()
                .statusCode(200)
                .extract()
                .response();

        boolean isDeleted = response.jsonPath().getInt("response") == 1;
        return new PostDeleteResponse(isDeleted);
    }
}
