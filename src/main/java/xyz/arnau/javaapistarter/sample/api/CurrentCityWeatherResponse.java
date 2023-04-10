package xyz.arnau.javaapistarter.sample.api;

import lombok.Builder;

@Builder
public record CurrentCityWeatherResponse(
        CityResponse city,
        WeatherResponse current
) {
}
