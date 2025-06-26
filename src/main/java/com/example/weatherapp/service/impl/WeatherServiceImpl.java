package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.CurrentWeatherDto;
import com.example.weatherapp.dto.GeoDto;
import com.example.weatherapp.dto.WeatherData;
import com.example.weatherapp.dto.WeatherResponseDto;
import com.example.weatherapp.mapper.WeatherConditionMapper;
import com.example.weatherapp.service.GeoService;
import com.example.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WebClient webClient;
    private final WeatherConditionMapper weatherConditionMapper;
    private final GeoService geoService;

    @Value("${weather.api.open-meteo-url}")
    private String openMeteoUrl;

    public WeatherServiceImpl(WebClient webClient, WeatherConditionMapper weatherConditionMapper, GeoService geoService) {
        this.webClient = webClient;
        this.weatherConditionMapper = weatherConditionMapper;
        this.geoService = geoService;
    }

    @Override
    public Mono<WeatherData> getWeather(String location) {
        return geoService.getGeo(location)
                .flatMap(this::getWeatherFromApi);
    }

    private Mono<WeatherData> getWeatherFromApi(GeoDto geo) {
        return webClient.get()
                .uri(openMeteoUrl, uriBuilder -> uriBuilder
                        .queryParam("latitude", geo.getLat())
                        .queryParam("longitude", geo.getLon())
                        .queryParam("current_weather", true)
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponseDto.class)
                .map(weatherResponse -> {
                    CurrentWeatherDto current = weatherResponse.getCurrentWeather();
                    double temperature = current.getTemperature();
                    double windSpeed = current.getWindSpeed();
                    int weatherCode = current.getWeatherCode();
                    String condition = weatherConditionMapper.getCondition(weatherCode);
                    String displayName = geo.getDisplayName() != null ? geo.getDisplayName() : geo.getName();
                    return new WeatherData(displayName, temperature, condition, windSpeed);
                });
    }
}
