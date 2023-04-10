package xyz.arnau.javaapistarter.sample.repository.openweather.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class WeatherResponse {
    String id;
    String main;
    String description;
    String icon;
}
