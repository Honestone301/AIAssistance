package com.aiassistance.Service.SerivceImpl;

import com.aiassistance.Entity.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class WeatherService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    // 使用免费的OpenWeatherMap API（需要注册获取API密钥）
    private static final String API_KEY = "your_api_key_here";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherService() {
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 获取指定城市的天气信息
     * @param cityName 城市名称
     * @return 天气信息
     */
    public Weather getWeatherByCity(String cityName) {
        try {
            // 调用天气API
            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(API_URL)
                            .queryParam("q", cityName)
                            .queryParam("appid", API_KEY)
                            .queryParam("units", "metric") // 使用摄氏度
                            .queryParam("lang", "zh_cn") // 中文描述
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 解析JSON响应
            Weather weather = objectMapper.readValue(response, Weather.class);

            // 打印到控制台
            printWeatherInfo(weather);

            return weather;
        } catch (Exception e) {
            log.error("获取天气信息失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 打印天气信息到控制台
     */
    private void printWeatherInfo(Weather weather) {
        if (weather == null) {
            log.error("天气信息为空");
            return;
        }

        log.info("========== 天气信息 ==========");
        log.info("城市: {}", weather.getCityName());

        if (weather.getWeatherInfo() != null) {
            log.info("天气: {}", weather.getWeatherInfo().getMain());
            log.info("描述: {}", weather.getWeatherInfo().getDescription());
        }

        if (weather.getMainInfo() != null) {
            log.info("温度: {}°C", weather.getMainInfo().getTemperature());
            log.info("体感温度: {}°C", weather.getMainInfo().getFeelsLike());
            log.info("湿度: {}%", weather.getMainInfo().getHumidity());
        }

        log.info("==============================");
    }
}
