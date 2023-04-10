package xyz.arnau.javaapistarter.sample.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.javaapistarter.sample.domain.CityWeather;
import xyz.arnau.javaapistarter.sample.service.WeatherService;

import java.util.Optional;

@RestController
@RequestMapping("/weather")
@Tag(name = "Sample")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    @Operation(summary = "Get current weather")
    public ResponseEntity<CurrentCityWeatherResponse> getCurrentWeather(@RequestParam String city) {
        Optional<CityWeather> currentWeather = weatherService.getCurrentWeather(city);
        return currentWeather
                .map(cityWeather -> ResponseEntity.ok(mapResponse(cityWeather)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private CurrentCityWeatherResponse mapResponse(CityWeather currentWeather) {
        return CurrentCityWeatherResponse.builder()
                .city(CityResponse.builder()
                        .name(currentWeather.city().name())
                        .countryCode(currentWeather.city().countryCode())
                        .build())
                .current(WeatherResponse.builder()
                        .description(currentWeather.weather().description())
                        .temperature(currentWeather.weather().temperature())
                        .humidity(currentWeather.weather().humidity())
                        .build())
                .build();
    }
}
