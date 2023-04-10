package xyz.arnau.javaapistarter.sample.domain;

import lombok.Builder;

@Builder
public record Weather(
        String description,
        float temperature,
        float humidity
) {
}
