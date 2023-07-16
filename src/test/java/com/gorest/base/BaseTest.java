package com.gorest.base;

import io.restassured.specification.RequestSpecification;

public class BaseTest {

    RequestSpecification requestSpecification;
    RequestSpecification responseSpecification;
    public String BASE_URL;

    public BaseTest() {
        BASE_URL = "https://gorest.co.in/public/v2";
    }
}
