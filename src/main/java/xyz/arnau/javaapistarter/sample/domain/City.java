package xyz.arnau.javaapistarter.sample.domain;

import lombok.Builder;

@Builder
public record City(
        String name,
        String countryCode
) {
}
