package com.example.weatherapp.controller;

import com.example.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/")
    public Mono<String> showForm(Model model) {
        return showWeatherForm("Hamburg, Germany", model);
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

    @PostMapping("/weather")
    public Mono<String> showWeatherForm(@RequestParam(required = false) String location, Model model) {
        model.addAttribute("location", location);
        if (location == null || location.trim().isEmpty()) {
            model.addAttribute("weather", null);
            model.addAttribute("error", "Please enter a location");
            return Mono.just("weather");
        }

        return weatherService.getWeather(location)
                .map(weatherData -> {
                    model.addAttribute("weather", weatherData);
                    model.addAttribute("error", null);
                    return "weather";
                });
    }
}
