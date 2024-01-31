package constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Endpoints {

    private static final String WALL = "/wall.";
    private static final String PHOTOS = "/photos.";
    private static final String LIKES = "/likes.";
    public static final String WALL_POST = WALL + "post";
    public static final String WALL_EDIT = WALL + "edit";
    public static final String WALL_CREATE_COMMENT = WALL + "createComment";
    public static final String WALL_DELETE = WALL + "delete";
    public static final String PHOTOS_GET_WALL_UPLOAD_SERVER = PHOTOS + "getWallUploadServer";
    public static final String PHOTOS_SAVE_WALL_PHOTO = PHOTOS + "saveWallPhoto";
    public static final String LIKES_IS_LIKED = LIKES + "isLiked";
}
