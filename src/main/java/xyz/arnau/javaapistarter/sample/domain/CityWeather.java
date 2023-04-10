package xyz.arnau.javaapistarter.sample.domain;

import lombok.Builder;

@Builder
public record CityWeather(
        City city,
        Weather weather
) {
}
