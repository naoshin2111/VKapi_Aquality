package model;

public class PostResponse {
    private int post_id;

    public int getPost_id() {
        return post_id;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "post_id=" + post_id +
                '}';
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
