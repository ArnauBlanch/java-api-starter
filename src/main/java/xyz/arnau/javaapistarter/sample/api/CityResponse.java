package xyz.arnau.javaapistarter.sample.api;

import lombok.Builder;

@Builder
public record CityResponse(
        String name,
        String countryCode
) {
}
