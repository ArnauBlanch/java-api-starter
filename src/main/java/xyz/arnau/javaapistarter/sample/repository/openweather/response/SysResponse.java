package xyz.arnau.javaapistarter.sample.repository.openweather.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SysResponse {
    int type;
    long id;
    String country;
    long sunrise;
    long sunset;
}
