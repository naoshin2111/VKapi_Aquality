package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoWallResponse {

    private String server;
    private String photo;
    private String hash;
}
