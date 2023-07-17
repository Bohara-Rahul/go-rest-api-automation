package com.gorest.api;

import com.gorest.models.Post;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostApi {

    public static Response get() {
        return given()
                .baseUri("https://gorest.co.in/public/v2/users/2148")
                .when()
                    .basePath("/posts")
                    .get();
    }

    public static Response post(Post post) {
        return given()
                .baseUri("https://gorest.co.in/public/v2/users/2149")
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .contentType(ContentType.JSON)
                .when()
                    .basePath("/posts")
                    .body(post)
                    .post();
    }
}
