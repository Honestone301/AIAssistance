//package com.aiassistance.Config;
//
//import io.netty.channel.ChannelOption;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import reactor.netty.http.client.HttpClient;
//import java.time.Duration;
//
//@Configuration
//public class ExternalApiConfig {
//
//    @Bean
//    public WebClient webClient() {
//        HttpClient httpClient = HttpClient.create()
//                .responseTimeout(Duration.ofSeconds(10))
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
//
//        return WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
//    }
//}