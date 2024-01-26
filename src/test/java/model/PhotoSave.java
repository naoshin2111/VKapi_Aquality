package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoSave {

    private String photoId;
    private String ownerId;
}
