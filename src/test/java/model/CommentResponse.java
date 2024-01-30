package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentResponse {

    @JsonProperty(value = "comment_id")
    private int commentId;

    @JsonProperty(value = "parents_stack")
    private String[] parentsStack;
}
