package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoUploadWall {

    private String server;
    private String photo;
    private String hash;
}
