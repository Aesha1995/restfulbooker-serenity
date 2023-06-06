package com.restfulbooker.testbase;

import com.restfulbooker.constants.Path;
import com.restfulbooker.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

/**
 * Created by Mamta
 */
public class TestBase {

    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
     //   RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
        RestAssured.basePath = Path.BOOKING;
    }
}
