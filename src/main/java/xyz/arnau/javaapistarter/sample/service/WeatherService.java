package xyz.arnau.javaapistarter.sample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.arnau.javaapistarter.sample.domain.CityWeather;
import xyz.arnau.javaapistarter.sample.repository.WeatherApiService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherApiService weatherApiService;

    public Optional<CityWeather> getCurrentWeather(String city) {
        return weatherApiService.getCurrentWeather(city);
    }
}
