package com.aiassistance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
// 1. 请求体
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeepseekRequestDTO {
    private String model = "deepseek-chat";
    public List<Message> messages;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}

