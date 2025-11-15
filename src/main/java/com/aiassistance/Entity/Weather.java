package com.aiassistance.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Weather {
    @JsonProperty("weather")
    private WeatherInfo weatherInfo;

    @JsonProperty("main")
    private MainInfo mainInfo;

    @JsonProperty("name")
    private String cityName;

    @Data
    public static class WeatherInfo {
        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;
    }

    @Data
    public static class MainInfo {
        @JsonProperty("temp")
        private double temperature;

        @JsonProperty("feels_like")
        private double feelsLike;

        @JsonProperty("humidity")
        private int humidity;
    }
}
