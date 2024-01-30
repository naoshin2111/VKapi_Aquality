package model;

import lombok.Data;

@Data
public class PhotoWallResponse {

    private String server;
    private String photo;
    private String hash;
}
