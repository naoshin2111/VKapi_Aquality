package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class PhotoSaveResponse {

    @JsonProperty(value = "id")
    private String photoId;
    @JsonProperty(value = "owner_id")
    private String ownerId;
    @JsonProperty(value = "album_id")
    private String albumId;
    private Long date;
    @JsonProperty(value = "access_key")
    private String accessKey;
    private List<Size> sizes;
    private String text;
    @JsonProperty(value = "has_tags")
    private boolean hasTags;
    @JsonProperty(value = "web_view_token")
    private String webViewToken;
}
