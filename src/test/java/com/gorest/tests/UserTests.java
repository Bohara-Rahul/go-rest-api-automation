package com.gorest.tests;

import com.gorest.models.User;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.ITestContext;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserTests {

    RequestSpecification requestSpecification;
    Response response;
    ResponseSpecification responseSpecification;
    ValidatableResponse validatableResponse;

    @Test
    public void testGetListOfUsers(ITestContext iTestContext) {
        response = given()
                .baseUri("https://gorest.co.in/public/v2/")
                .when()
                .basePath("/users")
                .get();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void testGetSingleUser() {
        var valid_user = new User(3681834, "Som Embranthiri III", "iii_som_embranthiri@jacobi.test", "female", "inactive");

        response = given()
                .baseUri("https://gorest.co.in/public/v2/")
                .when()
                .basePath("/users/" + valid_user.getId())
                .get();

        User expected_user = response.as(User.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(valid_user.getId(), equalTo(expected_user.getId()));
        assertThat(valid_user.getName(), equalTo(expected_user.getName()));
        assertThat(valid_user.getEmail(), equalTo(expected_user.getEmail()));
        assertThat(valid_user.getGender(), equalTo(expected_user.getGender()));
        assertThat(valid_user.getStatus(), equalTo(expected_user.getStatus()));

    }

    @Test
    public void testCreateNewUser() {
        User new_user = new User(
                "New User",
                "new.user@automation.com",
                "male",
                "active"
        );

        response = given()
                .baseUri("https://gorest.co.in/public/v2/")
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .contentType(ContentType.JSON)
                .body(new_user)
                .when()
                    .post("users");

        var expected_user = response.as(User.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(expected_user.getName(), equalTo(new_user.getName()));
        assertThat(expected_user.getEmail(), equalTo(new_user.getEmail()));
        assertThat(expected_user.getStatus(), equalTo(new_user.getStatus()));
        assertThat(expected_user.getGender(), equalTo(new_user.getGender()));

    }

    @Test
    public void testUpdateUser() {
        var updated_user = new User(
                "Allasani Pedessana",
                "allasani_pedessana@test.com",
                "male",
                "active"
        );

        response = given()
                .baseUri("https://gorest.co.in/public/v2/users")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .body(updated_user)
                .when()
                .put("/2135");

        var expected_user = response.as(User.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(expected_user.getName(), equalTo(updated_user.getName()));
        assertThat(expected_user.getEmail(), equalTo(updated_user.getEmail()));
        assertThat(expected_user.getStatus(), equalTo(updated_user.getStatus()));
        assertThat(expected_user.getGender(), equalTo(updated_user.getGender()));
    }

    @Test
    public void testDeleteUser() {
        response = given().baseUri("https://gorest.co.in/public/v2/users")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .when()
                .delete("/2137");

        assertThat(response.statusCode(), equalTo(204));
    }
}
