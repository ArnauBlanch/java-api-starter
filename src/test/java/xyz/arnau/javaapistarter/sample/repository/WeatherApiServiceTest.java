package xyz.arnau.javaapistarter.sample.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.arnau.javaapistarter.sample.domain.CityWeather;
import xyz.arnau.javaapistarter.sample.repository.openweather.OpenWeatherApi;
import xyz.arnau.javaapistarter.sample.repository.openweather.response.CurrentWeatherDataResponse;
import xyz.arnau.javaapistarter.sample.repository.openweather.response.MainResponse;
import xyz.arnau.javaapistarter.sample.repository.openweather.response.SysResponse;
import xyz.arnau.javaapistarter.sample.repository.openweather.response.WeatherResponse;

import java.util.Optional;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

class WeatherApiServiceTest {

    private static final String API_KEY = "API_KEY";
    private static final Gson JSON_MAPPER = new GsonBuilder()
            .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private final MockWebServer mockWebServer = new MockWebServer();
    private final OpenWeatherApi openWeatherApi = new Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder().setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create()))
            .build()
            .create(OpenWeatherApi.class);

    private final WeatherApiService apiService = new WeatherApiService(API_KEY, openWeatherApi);

    @Nested
    class GetCurrentWeather {
        private static final CurrentWeatherDataResponse CURRENT_WEATHER_RESPONSE =
                CurrentWeatherDataResponse.builder()
                        .name("Barcelona")
                        .weather(singletonList(WeatherResponse.builder().main("Sunny").build()))
                        .sys(SysResponse.builder().country("ES").build())
                        .main(MainResponse.builder().temp(12.3f).humidity(75f).build())
                        .build();

        @Test
        void shouldMakeRequestToOpenWeatherApi() throws InterruptedException {
            enqueueOkResponse(CURRENT_WEATHER_RESPONSE);

            apiService.getCurrentWeather("Barcelona");

            RecordedRequest recordedRequest = mockWebServer.takeRequest();
            assertThat(recordedRequest.getRequestUrl().encodedPath()).isEqualTo("/data/2.5/weather");
            assertThat(recordedRequest.getRequestUrl().queryParameter("q")).isEqualTo("Barcelona");
            assertThat(recordedRequest.getRequestUrl().queryParameter("units")).isEqualTo("metric");
            assertThat(recordedRequest.getRequestUrl().queryParameter("apiKey")).isEqualTo("API_KEY");
        }

        @Test
        void when200_returnsCityWeather() {
            enqueueOkResponse(CURRENT_WEATHER_RESPONSE);

            Optional<CityWeather> currentWeather = apiService.getCurrentWeather("Barcelona");

            assertThat(currentWeather).isNotEmpty();
            assertThat(currentWeather.get().city().name()).isEqualTo("Barcelona");
            assertThat(currentWeather.get().city().countryCode()).isEqualTo("ES");
            assertThat(currentWeather.get().weather().description()).isEqualTo("Sunny");
            assertThat(currentWeather.get().weather().temperature()).isEqualTo(12.3f);
            assertThat(currentWeather.get().weather().humidity()).isEqualTo(75f);
        }

        @Test
        void when404_returnsEmpty() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(NOT_FOUND.value()));

            Optional<CityWeather> currentWeather = apiService.getCurrentWeather("Barcelona");

            assertThat(currentWeather).isEmpty();
        }
    }

    private void enqueueOkResponse(Object body) {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK.value())
                .setBody(JSON_MAPPER.toJson(body)));
    }
}