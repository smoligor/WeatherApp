package com.example.weatherapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LocationNotFoundException.class)
    public ModelAndView handleLocationNotFoundException(LocationNotFoundException ex) {
        log.warn(ex.getMessage());
        return createErrorModelAndView(ex.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ModelAndView handleWebClientResponseException(WebClientResponseException ex) {
        log.error("Error from external API: {} {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
        return createErrorModelAndView("Error communicating with the weather service. Please try again later.");
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ModelAndView handleWebClientRequestException(WebClientRequestException ex) {
        log.error("Network error when calling external API", ex);
        return createErrorModelAndView("Error communicating with the weather service. Please try again later.");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        log.error("An unexpected error occurred", ex);
        return createErrorModelAndView("An unexpected error occurred. Please try again later.");
    }

    private ModelAndView createErrorModelAndView(String errorMessage) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", errorMessage);
        mav.addObject("weather", null);
        mav.setViewName("weather");
        return mav;
    }
}
