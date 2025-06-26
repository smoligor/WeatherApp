package com.example.weatherapp.service;

import com.example.weatherapp.dto.WeatherData;
import reactor.core.publisher.Mono;

public interface WeatherService {
    Mono<WeatherData> getWeather(String location);
}
