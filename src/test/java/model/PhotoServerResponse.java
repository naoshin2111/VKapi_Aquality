package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhotoServerResponse {

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "upload_url")
    private String uploadUrl;

    @JsonProperty(value = "album_id")
    private String albumId;
}
