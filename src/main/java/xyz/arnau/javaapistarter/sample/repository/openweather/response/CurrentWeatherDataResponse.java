package xyz.arnau.javaapistarter.sample.repository.openweather.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CurrentWeatherDataResponse {
    String name;
    SysResponse sys;
    MainResponse main;
    List<WeatherResponse> weather;
}
