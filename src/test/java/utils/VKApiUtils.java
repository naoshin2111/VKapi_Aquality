package utils;

import config.ApiUrlConfig;
import config.TestUserConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Post;
import model.PostResponse;

import java.io.File;

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
                .post("/wall.post")
                .then()
                .statusCode(200)
                .extract()
                .response().body().asString();

        JsonPath jsonPath = new JsonPath(response);
        int postId = jsonPath.getInt("response.post_id");
        PostResponse post = new PostResponse();
        post.setPost_id(postId);
        return post;
    }

//    public String uploadPhoto(String imagePath) {
//
//        Response uploadServerResponse = given()
//                .baseUri(apiBaseUrl)
//                .queryParam("access_token", accessToken)
//                .queryParam("v", "5.131")
//                .get("/photos.getWallUploadServer")
//                .then()
//
//                .statusCode(200)
//                .extract()
//                .response();
//
//        System.out.println(uploadServerResponse.getBody().asString());
//
//
//        String uploadUrl = uploadServerResponse.jsonPath().getString("response.upload_url");
//
//        System.out.println(uploadUrl);
//        return "";

//
//        Response uploadedPhotoResponse = given()
//                .multiPart("file", new File(imagePath)) // Ensure this matches the API's expected parameter
//                .when()
//                .post(uploadUrl)
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        System.out.println(uploadedPhotoResponse);
//
//        if (uploadedPhotoResponse.jsonPath().getString("photo").equals("[]")) {
//            throw new RuntimeException("Photo upload failed: " + uploadedPhotoResponse.getBody().asString());
//        }
//
//
//        String server = uploadedPhotoResponse.jsonPath().getString("server");
//        String photo = uploadedPhotoResponse.jsonPath().getString("photo");
//        String hash = uploadedPhotoResponse.jsonPath().getString("hash");
//
//        Response savePhotoResponse = given()
//                .baseUri(apiBaseUrl)
//                .queryParam("access_token", accessToken)
//                .queryParam("v", "5.131")
//                .queryParam("server", server)
//                .queryParam("photos_list", photo)
//                .queryParam("hash", hash)
//                .when()
//                .post("/photos.saveWallPhoto")
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        System.out.println(savePhotoResponse);
//        return savePhotoResponse.jsonPath().getString("response[0].id");
//    }


//    public int getUserId() {
//        String response = given()
//                .baseUri(apiBaseUrl)
//                .queryParam("access_token", accessToken)
//                .queryParam("v", "5.131")
//                .when()
//                .post("/users.get")
//                .then()
//                .statusCode(200)
//                .extract()
//                .response()
//                .body()
//                .asString();
//
//        System.out.println("getUserID");
//        System.out.println(response);
//
//        JsonPath jsonPath = new JsonPath(response);
//        return jsonPath.getInt("response[0].id");
//    }
//
//    public boolean editPost(int postId, String newText, String photoId) {
//        int userId = getUserId(); // Fetch the user ID
//        String attachments = photoId != null ? "photo" + userId + "_" + photoId : "";
//
//        Response editPostResponse = given()
//                .baseUri(apiBaseUrl)
//                .queryParam("access_token", accessToken)
//                .queryParam("v", "5.131")
//                .queryParam("post_id", postId)
//                .queryParam("message", newText)
//                .queryParam("attachments", attachments)
//                .when()
//                .post("/wall.edit")
//                .then()
//                .contentType(ContentType.JSON)
//                .statusCode(200)
//                .extract()
//                .response();
//
//        System.out.println(editPostResponse);
//
//        return editPostResponse.jsonPath().getBoolean("response");
//    }

    public String getWallUploadServer() {
        Response uploadServerResponse = given()
                .baseUri(apiBaseUrl)
                .queryParam("access_token", accessToken)
                .queryParam("v", "5.131")
                .get("/photos.getWallUploadServer")
                .then()
                .statusCode(200)
                .extract()
                .response();

        //System.out.println(uploadServerResponse.getBody().asString());
        return uploadServerResponse.jsonPath().getString("response.upload_url");
    }

    public String uploadPhotoToWall(String imagePath, String uploadUrl) {
        Response uploadedPhotoResponse = given()
                .multiPart("photo", new File(imagePath))
                .when()
                .post(uploadUrl)
                .then()
                .statusCode(200)
                .extract()
                .response();

        //System.out.println(uploadedPhotoResponse.getBody().asString());
        String server = uploadedPhotoResponse.jsonPath().getString("server");
        String photo = uploadedPhotoResponse.jsonPath().getString("photo");
        String hash = uploadedPhotoResponse.jsonPath().getString("hash");

        Response savePhotoResponse = given()
                .baseUri(apiBaseUrl)
                .queryParam("access_token", accessToken)
                .queryParam("v", "5.131")
                .queryParam("server", server)
                .queryParam("photo", photo)
                .queryParam("hash", hash)
                .post("/photos.saveWallPhoto")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(savePhotoResponse.getBody().asString());
        String photoId = savePhotoResponse.jsonPath().getString("response[0].id");
        String ownerId = savePhotoResponse.jsonPath().getString("response[0].owner_id");

        return "photo" + ownerId + "_" + photoId;
    }

    public boolean editPostWithPhoto(int postId, String newText, String attachment) {
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

        //System.out.println(editPostResponse.getBody().asString());
        return editPostResponse.jsonPath().getBoolean("response");
    }
}
