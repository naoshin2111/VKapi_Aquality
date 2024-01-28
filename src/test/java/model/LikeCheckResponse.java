package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeCheckResponse {
    private int liked;
    private int copied;
}
