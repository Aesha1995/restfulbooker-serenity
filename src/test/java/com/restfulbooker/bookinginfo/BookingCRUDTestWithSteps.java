package com.restfulbooker.bookinginfo;

import com.restfulbooker.testbase.TestBase;
import com.restfulbooker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Mamta
 */
@RunWith(SerenityRunner.class)
public class BookingCRUDTestWithSteps extends TestBase {
    static String username = "admin";
    static String password = "password123";
    static String firstname = "Admin" + TestUtils.getRandomValue();
    static String lastname = "Bhai" + TestUtils.getRandomValue();
    static int totalprice = 100;
    static Boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static int bookingId;
    static String token = "7339d479dff9bf3";

    @Steps
    BookingSteps bookingSteps;

    @Title("This is for authentication")
    @Test
    public void test001() {
        bookingSteps.getAuthentication(username, password).log().all().statusCode(200);
    }

    @Title("This will create a new Booking")
    @Test
    public void test002() {
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2023-10-01");
        bookingsDatesData.put("checkout", "2023-10-10");
        ValidatableResponse response = bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingsDatesData, additionalneeds);
        response.log().all().statusCode(200);
        bookingId = response.extract().path("bookingid");
        System.out.println(bookingId);
    }

    @Title("Verify if the Booking was done correctly")
    @Test
    public void test003() {
        ValidatableResponse response = bookingSteps.getBookingWithBookingId(1268);
        response.statusCode(200).log().ifValidationFails();
        response.body("firstname", equalTo("Admin"), "lastname", equalTo("Bhai"),
                "totalprice", equalTo(totalprice), "depositpaid", equalTo(depositpaid),
                "additionalneeds", equalTo("Wheelchair"));
    }

    @Title("Update the booking")
    @Test
    public void test004() {
        bookingId = 1406;
        firstname = "AA";
        lastname = "BB";
        additionalneeds = "Vegan1";
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2018-01-01");
        bookingsDatesData.put("checkout", "2019-01-01");
        ValidatableResponse response = bookingSteps.updateBooking(bookingId, firstname, lastname, totalprice, depositpaid, bookingsDatesData, additionalneeds, token);
        response.log().all().statusCode(201);
        response.body("firstname", equalTo(firstname), "lastname", equalTo(lastname),
                "additionalneeds", equalTo(additionalneeds));
    }

    @Title("Delete the booking")
    @Test
    public void test005() {
        bookingId = 475;
        ValidatableResponse response = bookingSteps.deleteBookingWithBookingId(bookingId, token);
        response.log().all().statusCode(200);
        bookingSteps.getBookingWithBookingId(bookingId).log().all().statusCode(404);
    }
}
