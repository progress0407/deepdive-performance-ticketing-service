package numble.deepdive.performanceticketingservice;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import numble.deepdive.performanceticketingservice.auth.dto.LoginRequest;
import numble.deepdive.performanceticketingservice.auth.dto.LoginResponse;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateRequest;
import numble.deepdive.performanceticketingservice.booking.dto.PaymentInfoCreateRequest;
import numble.deepdive.performanceticketingservice.global.exception.ExceptionResponse;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateResponse;
import numble.deepdive.performanceticketingservice.support.datacleaner.DataCleaner;
import numble.deepdive.performanceticketingservice.user.dto.BusinessUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.GeneralUserCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    DataCleaner dataCleaner;

    @Autowired
    DataSource dataSource;

//    @PostConstruct
//    public void init() {
//        dataCleaner = new DataCleaner(entityManager);
//        dataCleaner.init();
//    }

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
        dataCleaner.execute();
        System.out.println("dataSource = " + dataSource);
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

    protected void 정상_생성_검증(long createdId, ExtractableResponse<Response> response) {

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(CREATED.value()),
                () -> assertThat(createdId).isPositive()
        );
    }

    protected static void 예외_검증(HttpStatus httpStatus, String exceptionMessage, ExtractableResponse<Response> response) {

        String exceptionMessageLocal = response.as(ExceptionResponse.class).getMessage();

        assertAll(
                () -> HTTP코드_검증(response, httpStatus),
                () -> assertThat(exceptionMessageLocal).isEqualTo(exceptionMessage)
        );
    }

    protected void 일반_회원가입() {

        var generalUserCreateRequest = new GeneralUserCreateRequest("test_user@gmail.com", "sw cho", "password");
        registerSampleUser(generalUserCreateRequest);
    }

    protected void 사업자_회원가입() {

        var businessUserCreateRequest = new BusinessUserCreateRequest("test_biz_user@gmail.com", "sw cho", "password", "1234-5678");
        registerBusinessUser(businessUserCreateRequest);
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

    protected VenueCreateResponse 공연장_생성요청(String accessToken) {

        return post("/venues", venueCreateRequest(), accessToken).extract().as(VenueCreateResponse.class);
    }

    protected PerformanceCreateResponse 공연_생성요청(long venueId, String accessToken) {

        return post("/performances", performanceCreateRequest(venueId), accessToken).extract().as(PerformanceCreateResponse.class);
    }

    protected VenueCreateRequest venueCreateRequest() {

        var seatRequests = Set.of(
                new SeatCreateRequest("A1", "VIP"),
                new SeatCreateRequest("A2", "VIP"),
                new SeatCreateRequest("B1", "GENERAL"),
                new SeatCreateRequest("B2", "GENERAL")
        );

        return new VenueCreateRequest("[테스트 용도] 어떤 한 공연장", seatRequests);
    }

    protected PerformanceCreateRequest performanceCreateRequest(long venueId) {

        return new PerformanceCreateRequest(venueId,
                "이무진 공연",
                "2023-06-30",
                "14:00",
                "16:00",
                Map.of("일반", 10_000, "VIP", 50_000)
        );
    }

    protected BookingCreateRequest bookingCreateRequest(long performanceId) {

        var seatRequests = List.of(
                new SeatCreateRequest("A1", "VIP"),
                new SeatCreateRequest("B1", "GENERAL")
        );

        var paymentInfoCreateRequest =
                new PaymentInfoCreateRequest("credit_card", "1234 5678 9012 3456", "12/24", 890);

        return new BookingCreateRequest(performanceId, seatRequests, 10_000 + 50_000, paymentInfoCreateRequest);
    }
}
