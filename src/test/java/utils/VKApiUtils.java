package utils;

import constant.Endpoints;
import io.restassured.response.Response;
import model.*;
import org.apache.http.HttpStatus;
import java.io.File;

import static io.restassured.RestAssured.given;

public class VKApiUtils extends BaseApiUtils{


    public PostResponse createPost(String message) {
        Response response = getBaseRequestSpecification()
                .queryParam("message", message)
                .when()
                .post(Endpoints.WALL_POST)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int postId = response.jsonPath().getInt("response.post_id");
        return new PostResponse(postId);
    }

    public PhotoServerResponse uploadPhotoToServer() {
        Response uploadServerResponse = getBaseRequestSpecification()
                .get(Endpoints.PHOTOS_GET_WALL_UPLOAD_SERVER)
                .then()
                .statusCode(HttpStatus.SC_OK)
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
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        String server = uploadedPhotoResponse.jsonPath().getString("server");
        String photo = uploadedPhotoResponse.jsonPath().getString("photo");
        String hash = uploadedPhotoResponse.jsonPath().getString("hash");
        return new PhotoWallResponse(server, photo, hash);
    }

    public PhotoSaveResponse savePhotoToWall(PhotoWallResponse PhotoWallResponse) {
        Response savePhotoResponse = getBaseRequestSpecification()
                .queryParam("server", PhotoWallResponse.getServer())
                .queryParam("photo", PhotoWallResponse.getPhoto())
                .queryParam("hash", PhotoWallResponse.getHash())
                .post(Endpoints.PHOTOS_SAVE_WALL_PHOTO)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        String photoId = savePhotoResponse.jsonPath().getString("response[0].id");
        String ownerId = savePhotoResponse.jsonPath().getString("response[0].owner_id");
        return new PhotoSaveResponse(photoId, ownerId);
    }

    public PostResponse editPostWithPhoto(int postId, String newText, String attachment) {
        Response editPostResponse = getBaseRequestSpecification()
                .queryParam("post_id", postId)
                .queryParam("message", newText)
                .queryParam("attachments", attachment)
                .post(Endpoints.WALL_EDIT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int id = editPostResponse.jsonPath().getInt("response.post_id");
        return new PostResponse(id);
    }

    public CommentResponse addCommentToPost(int postId, String message) {
        Response response = getBaseRequestSpecification()
                .queryParam("post_id", postId)
                .queryParam("message", message)
                .when()
                .post(Endpoints.WALL_CREATE_COMMENT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int commentId = response.jsonPath().getInt("response.comment_id");
        return new CommentResponse(commentId);
    }

    public LikeResponse checkLikedByUser(String ownerId, String type, int itemId) {
        Response response = getBaseRequestSpecification()
                .queryParam("user_id", ownerId)
                .queryParam("type", type)
                .queryParam("owner_id", ownerId)
                .queryParam("item_id", itemId)
                .get(Endpoints.LIKES_IS_LIKED)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int liked = response.jsonPath().getInt("response.liked");
        int copied = response.jsonPath().getInt("response.copied");
        return new LikeResponse(liked, copied);
    }

    public PostDeleteResponse deletePost(int postId) {
        Response response = getBaseRequestSpecification()
                .queryParam("post_id", postId)
                .when()
                .post(Endpoints.WALL_DELETE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        boolean isDeleted = response.jsonPath().getInt("response") == 1;
        return new PostDeleteResponse(isDeleted);
    }
}
