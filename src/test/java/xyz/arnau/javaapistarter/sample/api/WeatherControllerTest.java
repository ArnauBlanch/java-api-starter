package xyz.arnau.javaapistarter.sample.api;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import xyz.arnau.javaapistarter.sample.domain.City;
import xyz.arnau.javaapistarter.sample.domain.CityWeather;
import xyz.arnau.javaapistarter.sample.domain.Weather;
import xyz.arnau.javaapistarter.sample.service.WeatherService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController controller;

    @Nested
    class GetCurrentWeather {
        private static final String CITY = "Barcelona";

        @Test
        public void whenCityIsFound_shouldReturnOkWithCurrentWeather() {
            when(weatherService.getCurrentWeather(CITY)).thenReturn(Optional.of(
                    CityWeather.builder()
                            .city(City.builder()
                                    .name(CITY)
                                    .countryCode("ES").build())
                            .weather(Weather.builder()
                                    .description("Sunny")
                                    .temperature(25.5f)
                                    .humidity(75).build())
                            .build()
            ));

            ResponseEntity<CurrentCityWeatherResponse> response = controller.getCurrentWeather(CITY);

            assertThat(response.getStatusCode().value()).isEqualTo(OK.value());

            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().city().name()).isEqualTo(CITY);
            assertThat(response.getBody().city().countryCode()).isEqualTo("ES");

            assertThat(response.getBody().current().description()).isEqualTo("Sunny");
            assertThat(response.getBody().current().temperature()).isEqualTo(25.5f);
            assertThat(response.getBody().current().humidity()).isEqualTo(75f);
        }

        @Test
        public void whenCityIsNotFound_shouldReturnNotFound() {
            when(weatherService.getCurrentWeather(CITY)).thenReturn(Optional.empty());

            ResponseEntity<CurrentCityWeatherResponse> response = controller.getCurrentWeather(CITY);

            assertThat(response.getStatusCode().value()).isEqualTo(NOT_FOUND.value());
        }
    }
}