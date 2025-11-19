package com.aiassistance.DTO;

import lombok.Data;

@Data
public class AskingDTO {
    String message;
    Long userId;
    String sessionId;
}
