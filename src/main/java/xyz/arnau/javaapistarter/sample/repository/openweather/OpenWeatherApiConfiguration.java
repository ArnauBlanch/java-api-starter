package xyz.arnau.javaapistarter.sample.repository.openweather;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

@Configuration
public class OpenWeatherApiConfiguration {

    @Value("${openWeather.apiUrl}")
    private String apiUrl;

    @Bean
    public OpenWeatherApi openWeatherApi() {
        var retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create()))
                .build();

        return retrofit.create(OpenWeatherApi.class);
    }
}
