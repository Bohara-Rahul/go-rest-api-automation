package com.gorest.tests.integration;

import com.gorest.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserIntegrationTests {
    RequestSpecification requestSpecification;
    Response response;
    ResponseSpecification responseSpecification;
    ValidatableResponse validatableResponse;

    @Test(priority = 1)
    public void shouldBeAbleToCreateNewUser() {
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
    

}
