package com.gorest.tests.CRUD;

import com.gorest.api.UserApi;
import com.gorest.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
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

    private void assertStatusCode(int actualStatusCode, int expectedStatusCode) {
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    @Test()
    @Owner("Rahul")
    @Description("Verify that the list of the users is returned")
    public void testGetListOfUsers(ITestContext iTestContext) {
        Response response = UserApi.get();
        assertStatusCode(response.statusCode(), 200);
    }

    @Test
    @Owner("Rahul")
    @Description("Verify the details of the single user is returned")
    public void testGetSingleUser() {
        var valid_user = new User(2148, "Himadri Abbott", "abbott_himadri@gibson-kunde.example", "male", "inactive");

        Response response = UserApi.get(valid_user.getId());

        User expected_user = response.as(User.class);

        assertStatusCode(response.statusCode(), 200);
        assertThat(valid_user.getId(), equalTo(expected_user.getId()));
        assertThat(valid_user.getName(), equalTo(expected_user.getName()));
        assertThat(valid_user.getEmail(), equalTo(expected_user.getEmail()));
        assertThat(valid_user.getGender(), equalTo(expected_user.getGender()));
        assertThat(valid_user.getStatus(), equalTo(expected_user.getStatus()));

    }

    @Test
    @Owner("Rahul")
    @Description("Verify the new user can be created")
    public void testCreateNewUser() {
        User new_user = new User(
                "Yet Another User",
                "yet.user@automation.com",
                "male",
                "active"
        );

        Response response = UserApi.post(new_user);

        var expected_user = response.as(User.class);

        assertStatusCode(response.statusCode(), 201);
        assertThat(expected_user.getName(), equalTo(new_user.getName()));
        assertThat(expected_user.getEmail(), equalTo(new_user.getEmail()));
        assertThat(expected_user.getStatus(), equalTo(new_user.getStatus()));
        assertThat(expected_user.getGender(), equalTo(new_user.getGender()));

    }

    @Test
    @Owner("Rahul")
    @Description("Verify that the user can be updated")
    public void testUpdateUser() {
        var updated_user = new User(
                "Allasani Pedessana",
                "allasani_pedessana@test.com",
                "male",
                "active"
        );

        Response response = UserApi.put(updated_user);

        var expected_user = response.as(User.class);

        assertStatusCode(response.statusCode(), 200);
        assertThat(expected_user.getName(), equalTo(updated_user.getName()));
        assertThat(expected_user.getEmail(), equalTo(updated_user.getEmail()));
        assertThat(expected_user.getStatus(), equalTo(updated_user.getStatus()));
        assertThat(expected_user.getGender(), equalTo(updated_user.getGender()));
    }

    @Test
    @Owner("Rahul")
    @Description("Verify that the user can be deleted")
    public void testDeleteUser() {
        Response response = UserApi.delete(2148);
        assertStatusCode(response.statusCode(), 204);
    }
}
