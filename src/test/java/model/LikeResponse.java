package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LikeResponse {
    private int count;
    private List<Integer> users;

}
