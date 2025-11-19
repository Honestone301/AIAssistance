package com.aiassistance.Service;

import com.aiassistance.DTO.SessionsDTO;
import com.aiassistance.Entity.Sessions;
import com.aiassistance.Result.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

public interface SessionService extends IService<Sessions> {

    Result createSession(SessionsDTO sessionsDTO);

    Result getSessionHistory(String sessionId);

    Result deleteSession(String sessionId);

    Result getAllSessions(SessionsDTO sessionsDTO);
}
