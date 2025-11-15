package com.aiassistance.Contoller;

import com.aiassistance.Entity.Weather;
import com.aiassistance.Service.SerivceImpl.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/atom/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/getWeather")
    public String getWeather(@RequestParam(defaultValue = "Beijing") String city) {
        Weather weather = weatherService.getWeatherByCity(city);

        if (weather != null) {
            return "成功获取" + city + "的天气信息，请查看控制台输出";
        } else {
            return "获取" + city + "的天气信息失败";
        }
    }
}
