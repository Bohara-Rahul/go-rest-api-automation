package com.gorest.api;

import com.gorest.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {
    RequestSpecification requestSpecification;
    Response response;
    ResponseSpecification responseSpecification;
    ValidatableResponse validatableResponse;

    public static Response get() {
        return given()
                .baseUri("https://gorest.co.in/public/v2/")
                .when()
                .basePath("/users")
                .get();
    }

    public static Response get(int id) {
        return given()
                .baseUri("https://gorest.co.in/public/v2/")
                .when()
                .basePath("/users/" + id)
                .get();
    }

    public static Response post(User new_user) {
        return given()
                .baseUri("https://gorest.co.in/public/v2/")
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .contentType(ContentType.JSON)
                .body(new_user)
                .when()
                .post("users");
    }

    public static Response put(User updated_user) {
        return given()
                .baseUri("https://gorest.co.in/public/v2/users")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .body(updated_user)
                .when()
                .put("/2148");
    }

    public static Response put(int id, User updated_user) {
        return given()
                .baseUri("https://gorest.co.in/public/v2/users")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .body(updated_user)
                .when()
                .put(String.valueOf(id));
    }

    public static Response delete(int id) {
        return given().baseUri("https://gorest.co.in/public/v2/users")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .when()
                .delete("/"+id);
    }
}
