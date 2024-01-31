package utils;

import constants.Endpoints;
import constants.Parameters;
import models.*;
import org.apache.http.HttpStatus;
import java.io.File;
import static io.restassured.RestAssured.given;

public class VkApiUtils extends BaseApiUtils {

    private static final String RESPONSE = "response";
    private static final String EMPTY_PATH = "";

    public PostResponse createPost(String message) {
        return getBaseRequestSpecification()
                .queryParam(Parameters.MESSAGE, message)
                .when()
                .post(Endpoints.WALL_POST)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getObject(RESPONSE, PostResponse.class);
    }

    public PhotoServerResponse uploadPhotoToServer() {
        return getBaseRequestSpecification()
                .get(Endpoints.PHOTOS_GET_WALL_UPLOAD_SERVER)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getObject(RESPONSE, PhotoServerResponse.class);
    }

    public PhotoWallResponse uploadPhotoToWall(String imagePath, String uploadUrl) {
        return given()
                .multiPart(Parameters.PHOTO, new File(imagePath))
                .when()
                .post(uploadUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getObject(EMPTY_PATH, PhotoWallResponse.class);
    }

    public PhotoSaveResponse savePhotoToWall(PhotoWallResponse photoWallResponse) {
        return getBaseRequestSpecification()
                .queryParam(Parameters.SERVER, photoWallResponse.getServer())
                .queryParam(Parameters.PHOTO, photoWallResponse.getPhoto())
                .queryParam(Parameters.HASH, photoWallResponse.getHash())
                .post(Endpoints.PHOTOS_SAVE_WALL_PHOTO)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getList(RESPONSE, PhotoSaveResponse.class)
                .get(0);
    }

    public PostResponse editPostWithPhoto(int postId, String newText, String attachment) {
        return getBaseRequestSpecification()
                .queryParam(Parameters.POST_ID, postId)
                .queryParam(Parameters.MESSAGE, newText)
                .queryParam(Parameters.ATTACHMENTS, attachment)
                .post(Endpoints.WALL_EDIT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getObject(RESPONSE, PostResponse.class);
    }

    public CommentResponse addCommentToPost(int postId, String message) {
        return getBaseRequestSpecification()
                .queryParam(Parameters.POST_ID, postId)
                .queryParam(Parameters.MESSAGE, message)
                .when()
                .post(Endpoints.WALL_CREATE_COMMENT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getObject(RESPONSE, CommentResponse.class);
    }

    public LikeResponse checkLikedByUser(String ownerId, String type, int itemId) {
        return getBaseRequestSpecification()
                .queryParam(Parameters.USER_ID, ownerId)
                .queryParam(Parameters.TYPE, type)
                .queryParam(Parameters.ITEM_ID, itemId)
                .get(Endpoints.LIKES_IS_LIKED)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getObject(RESPONSE, LikeResponse.class);
    }

    public PostDeleteResponse deletePost(int postId) {
        return getBaseRequestSpecification()
                .queryParam(Parameters.POST_ID, postId)
                .when()
                .post(Endpoints.WALL_DELETE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .jsonPath()
                .getObject(EMPTY_PATH, PostDeleteResponse.class);
    }
}
