package xyz.arnau.javaapistarter.sample;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@WireMockTest(httpPort = 8081)
public class SampleEndToEndTests {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void givenACity_shouldReturnCurrentWeather() {
        given().
                queryParam("city", "Barcelona").
                when().
                get("/weather/current").
                then().
                statusCode(OK.value()).
                body(
                        "city.name", equalTo("Barcelona"),
                        "city.countryCode", equalTo("ES"),
                        "current.description", equalTo("Sunny"),
                        "current.temperature", equalTo(25.5f),
                        "current.humidity", equalTo(75f));
    }
}
