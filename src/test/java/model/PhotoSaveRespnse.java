package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoSaveRespnse {

    private String photoId;
    private String ownerId;
}
