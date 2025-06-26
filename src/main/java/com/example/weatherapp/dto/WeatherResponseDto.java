package com.example.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {

    @JsonProperty("current_weather")
    private CurrentWeatherDto currentWeather;

}

