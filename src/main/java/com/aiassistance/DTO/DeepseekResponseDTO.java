package com.aiassistance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 2. 响应体（根据 DeepSeek API 文档简化）
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeepseekResponseDTO {
    private List<Choice> choices;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private Message message;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Message {
            private String content;
        }
    }
}