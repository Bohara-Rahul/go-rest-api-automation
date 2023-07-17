package com.gorest.tests.CRUD;

import com.gorest.api.PostApi;
import com.gorest.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostTests {

    Response response;
    @Test
    public void testGetListOfPosts() {
        response = PostApi.get();
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void testCreateNewPost() {
        var new_post = new Post(2149, "Test Post 2", "Test Post 2 description");
        response = PostApi.post(new_post);

        Post expected_post = response.as(Post.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(new_post.getUser_id(), equalTo(expected_post.getUser_id()));
        assertThat(new_post.getTitle(), equalTo(expected_post.getTitle()));
        assertThat(new_post.getBody(), equalTo(expected_post.getBody()));
    }
}
