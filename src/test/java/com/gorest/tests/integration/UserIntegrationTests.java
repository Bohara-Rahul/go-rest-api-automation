package com.gorest.tests.integration;

import com.gorest.api.UserApi;
import com.gorest.models.User;
import io.qameta.allure.Owner;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.jfr.Description;
import org.testng.ITestContext;
import org.testng.annotations.Test;

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
                "John",
                "john@automation.com",
                "male",
                "active"
        );

        response = UserApi.post(new_user);

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
    @Description("Verify that the details of the user can be returned")
    public void testGetSingleUser(ITestContext iTestContext) {
        var id = (Integer) iTestContext.getAttribute("id");
        var valid_user = new User(id, "Himadri Abbott", "abbott_himadri@gibson-kunde.example", "male", "inactive");

        response = UserApi.get(valid_user.getId());

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
        var id = (Integer) iTestContext.getAttribute("id");
        var updated_user = new User(
                "Jane",
                "jane@test.com",
                "female",
                "active"
        );

        response = UserApi.put(id, updated_user);

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
    public void testDeleteUser(ITestContext iTestContext) {
        var id = (Integer) iTestContext.getAttribute("id");
        response = UserApi.delete(id);
        assertThat(response.statusCode(), equalTo(204));
    }
}
