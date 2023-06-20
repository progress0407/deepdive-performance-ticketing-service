package numble.deepdive.performanceticketingservice;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import support.DataCleaner;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

//    @Autowired
//    DataCleaner dataCleaner;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
//        dataCleaner.execute();
    }

    protected ValidatableResponse post(final String uri, final Object requestBody) {
        return RestAssured.given().log().all()
                .body(requestBody)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .when().post(uri)
                .then().log().all();
    }

    protected ValidatableResponse post(final String uri, final Object requestBody, final String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(requestBody)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .when().post(uri)
                .then().log().all();
    }

    protected ValidatableResponse get(final String uri) {
        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .when().get(uri)
                .then().log().all();
    }

    protected ValidatableResponse get(final String uri, final String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .accept(APPLICATION_JSON_VALUE)
                .when().get(uri)
                .then().log().all();
    }

    protected ValidatableResponse put(final String uri, final Object requestBody, final String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(requestBody)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .when().put(uri)
                .then().log().all();
    }

    protected ValidatableResponse delete(final String uri, final String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .when().delete(uri)
                .then().log().all();
    }

    protected ValidatableResponse delete(final String uri, final Object requestBody, final String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(requestBody)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .when().delete(uri)
                .then().log().all();
    }
}
