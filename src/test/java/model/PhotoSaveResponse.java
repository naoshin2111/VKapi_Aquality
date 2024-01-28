package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoSaveResponse {

    private String photoId;
    private String ownerId;
}
