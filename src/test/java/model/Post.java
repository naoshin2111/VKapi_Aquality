package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    @JsonProperty("Response")
    private Response response;

    @Override
    public String toString() {
        return "Post{" +
                "response=" + response +
                '}';
    }

    public static class Response {
        //@JsonProperty("post_id")
        private int post_id;

        @JsonIgnore
        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }
    }

    @JsonIgnore
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


}
