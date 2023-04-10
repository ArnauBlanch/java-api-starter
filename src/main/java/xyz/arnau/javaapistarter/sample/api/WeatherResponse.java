package xyz.arnau.javaapistarter.sample.api;

import lombok.Builder;

@Builder
public record WeatherResponse(
        String description,
        float temperature,
        float humidity
) {
}
