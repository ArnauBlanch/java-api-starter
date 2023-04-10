package xyz.arnau.javaapistarter.sample.repository.openweather.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class MainResponse {
    float temp;
    float feelsLike;
    float tempMin;
    float pressure;
    float humidity;
    float seaLevel;
    float grndLevel;
}
