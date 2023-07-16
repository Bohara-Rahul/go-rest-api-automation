package com.gorest.tests.integration;

import com.gorest.models.User;
import io.qameta.allure.Owner;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.jfr.Description;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserIntegrationTests {
    RequestSpecification requestSpecification;
    Response response;
    ResponseSpecification responseSpecification;
    ValidatableResponse validatableResponse;

    @Test(groups = "integration")
    @Owner("Rahul Bohara")
    @Description("Verify that the user can be created")
    public void testCreateNewUser(ITestContext iTestContext) {
        User new_user = new User(
                "Another User",
                "another.user@automation.com",
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

        var user_id = JsonPath.from(response.asString()).getString("id");
        iTestContext.setAttribute("id", user_id);

        var expected_user = response.as(User.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(expected_user.getName(), equalTo(new_user.getName()));
        assertThat(expected_user.getEmail(), equalTo(new_user.getEmail()));
        assertThat(expected_user.getStatus(), equalTo(new_user.getStatus()));
        assertThat(expected_user.getGender(), equalTo(new_user.getGender()));
    }

    @Test(groups = "integration", dependsOnMethods = "testCreateNewUser")
    @Owner("Rahul Bohara")
    @Description("Verify that the details of the user can be queried")
    public void testGetSingleUser(ITestContext iTestContext) {
        var id = (Integer) iTestContext.getAttribute("id");
        var valid_user = new User(id, "Himadri Abbott", "abbott_himadri@gibson-kunde.example", "male", "inactive");

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

    @Test(groups = "integration", dependsOnMethods = "testGetSingleUser")
    @Owner("Rahul Bohara")
    @Description("Verify that the user can be updated")
    public void testUpdateUser(ITestContext iTestContext) {
        var id = (String) iTestContext.getAttribute("id");
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
                .put(id);

        var expected_user = response.as(User.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(expected_user.getName(), equalTo(updated_user.getName()));
        assertThat(expected_user.getEmail(), equalTo(updated_user.getEmail()));
        assertThat(expected_user.getStatus(), equalTo(updated_user.getStatus()));
        assertThat(expected_user.getGender(), equalTo(updated_user.getGender()));
    }

    @Test(groups = "integration", dependsOnMethods = "testUpdateUser")
    @Owner("Rahul Bohara")
    @Description("Verify that the user can be deleted")
    public void testDeleteUser() {
        response = given().baseUri("https://gorest.co.in/public/v2/users")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer c40da3c265404cce4faa7630c1900fb093833bfff182e24f453737a1dcfb7d48")
                .when()
                .delete("/3681803");

        assertThat(response.statusCode(), equalTo(204));
    }
}
