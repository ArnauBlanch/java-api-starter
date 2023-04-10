package xyz.arnau.javaapistarter.sample.repository.openweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.arnau.javaapistarter.sample.repository.openweather.response.CurrentWeatherDataResponse;

public interface OpenWeatherApi {
    @GET("/data/2.5/weather")
    Call<CurrentWeatherDataResponse> getCurrentWeatherData(
            @Query("q") String city,
            @Query("units") String units,
            @Query("apiKey") String apiKey);
}
