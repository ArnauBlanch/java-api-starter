package xyz.arnau.javaapistarter.sample.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import xyz.arnau.javaapistarter.sample.domain.City;
import xyz.arnau.javaapistarter.sample.domain.CityWeather;
import xyz.arnau.javaapistarter.sample.domain.Weather;
import xyz.arnau.javaapistarter.sample.repository.openweather.OpenWeatherApi;
import xyz.arnau.javaapistarter.sample.repository.openweather.response.CurrentWeatherDataResponse;

import java.io.IOException;
import java.util.Optional;

@Component
public class WeatherApiService {
    private static final String UNITS = "metric";

    private final String apiKey;
    private final OpenWeatherApi openWeatherApi;

    public WeatherApiService(
            @Value("${openWeather.apiKey}") String apiKey,
            OpenWeatherApi openWeatherApi) {
        this.apiKey = apiKey;
        this.openWeatherApi = openWeatherApi;
    }

    public Optional<CityWeather> getCurrentWeather(String city) {
        try {
            Response<CurrentWeatherDataResponse> response = openWeatherApi.getCurrentWeatherData(city, UNITS, apiKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                return Optional.of(mapResponse(response.body()));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CityWeather mapResponse(CurrentWeatherDataResponse response) {
        return CityWeather.builder()
                .city(City.builder()
                        .name(response.getName())
                        .countryCode(response.getSys().getCountry())
                        .build())
                .weather(Weather.builder()
                        .description(response.getWeather().get(0).getMain())
                        .temperature(response.getMain().getTemp())
                        .humidity(response.getMain().getHumidity())
                        .build())
                .build();
    }
}
