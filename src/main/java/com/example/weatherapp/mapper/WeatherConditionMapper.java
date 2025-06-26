package com.example.weatherapp.mapper;

import org.springframework.stereotype.Component;

@Component
public class WeatherConditionMapper {

    public String getCondition(int code) {
        return switch (code) {
            case 0 -> "Clear Sky";
            case 1 -> "Mainly Clear";
            case 2 -> "Partially Cloudy";
            case 3 -> "Overcast";
            case 45 -> "Fog";
            case 48 -> "Depositing Rime Fog";
            case 51 -> "Light Drizzle";
            case 53 -> "Moderate Drizzle";
            case 55 -> "Dense Drizzle";
            case 56 -> "Light Freezing Drizzle";
            case 57 -> "Dense Freezing Drizzle";
            case 61 -> "Light Rain";
            case 63 -> "Moderate Rain";
            case 65 -> "Heavy Rain";
            case 66 -> "Light Freezing Rain";
            case 67 -> "Heavy Freezing Rain";
            case 71 -> "Light Snowfall";
            case 73 -> "Moderate Snowfall";
            case 75 -> "Heavy Snowfall";
            case 77 -> "Snow Grains";
            case 80 -> "Light Rain Showers";
            case 81 -> "Moderate Rain Showers";
            case 82 -> "Violent Rain Showers";
            case 85 -> "Light Snow Showers";
            case 86 -> "Heavy Snow Showers";
            case 95 -> "Thunderstorm";
            case 96 -> "Thunderstorm with Slight Hail";
            case 99 -> "Thunderstorm with Heavy Hail";
            default -> "Unknown";
        };
    }
}
