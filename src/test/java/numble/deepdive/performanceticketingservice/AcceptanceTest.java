package numble.deepdive.performanceticketingservice;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import numble.deepdive.performanceticketingservice.auth.dto.LoginRequest;
import numble.deepdive.performanceticketingservice.auth.dto.LoginResponse;
import numble.deepdive.performanceticketingservice.user.dto.BusinessUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.GeneralUserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import support.DataCleaner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

//    @Autowired
//    DataCleaner dataCleaner;

    @BeforeEach
    protected void setUp() {
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

    protected static void HTTP코드_검증(ExtractableResponse<Response> response, HttpStatus httpStatus) {

        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    protected void 사업자_회원가입() {
        var businessUserCreateRequest = new BusinessUserCreateRequest("test_biz_user@gmail.com", "sw cho", "password", "1234-5678");
        registerBusinessUser(businessUserCreateRequest);
    }

    protected void 일반_회원가입() {
        var generalUserCreateRequest = new GeneralUserCreateRequest("test_user@gmail.com", "sw cho", "password");
        registerSampleUser(generalUserCreateRequest);
    }

    protected String 사업자_로그인_후_토큰_반환() {
        return post("/login", new LoginRequest("test_biz_user@gmail.com", "password")).extract().as(LoginResponse.class).getAccessToken();
    }

    protected String 일반_유저_로그인_후_토큰_반환() {
        return post("/login", new LoginRequest("test_user@gmail.com", "password")).extract().as(LoginResponse.class).getAccessToken();
    }

    protected void registerSampleUser(GeneralUserCreateRequest request) {

        post("/users", request);
    }

    protected void registerBusinessUser(BusinessUserCreateRequest request) {

        post("/businesses", request);
    }
}
