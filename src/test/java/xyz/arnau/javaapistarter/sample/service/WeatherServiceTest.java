package xyz.arnau.javaapistarter.sample.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.javaapistarter.sample.domain.City;
import xyz.arnau.javaapistarter.sample.domain.CityWeather;
import xyz.arnau.javaapistarter.sample.domain.Weather;
import xyz.arnau.javaapistarter.sample.repository.WeatherApiService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private static final String CITY = "Barcelona";

    @Mock
    private WeatherApiService weatherApiService;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void shouldReturnCurrentWeather() {
        var cityWeatherFromApi = CityWeather.builder()
                .city(City.builder()
                        .name(CITY)
                        .countryCode("ES").build())
                .weather(Weather.builder()
                        .description("Sunny")
                        .temperature(25.5f)
                        .humidity(75f).build())
                .build();

        when(weatherApiService.getCurrentWeather(CITY)).thenReturn(Optional.of(cityWeatherFromApi));

        Optional<CityWeather> cityWeather = weatherService.getCurrentWeather(CITY);

        assertThat(cityWeather.get()).isEqualTo(cityWeatherFromApi);
    }
}