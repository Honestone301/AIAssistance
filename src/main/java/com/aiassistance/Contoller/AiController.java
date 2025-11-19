package com.aiassistance.Contoller;

import com.aiassistance.DTO.AskingDTO;
import com.aiassistance.DTO.SessionsDTO;
import com.aiassistance.Mapper.SessionsMapper;
import com.aiassistance.Result.Result;
import com.aiassistance.Service.SerivceImpl.DeepseekServiceImpl;
import com.aiassistance.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atom")
public class AiController {

    @Autowired
    private DeepseekServiceImpl deepseekService;
    @Autowired
    private SessionsMapper sessionsMapper;
    @Autowired
    private SessionService sessionService;

    @PostMapping("/chat")
    public String chat(@RequestBody AskingDTO askingDTO) {
        return deepseekService.chat(askingDTO);
    }

    @PostMapping("/sessions")
    public Result createSession(@RequestBody SessionsDTO sessionsDTO) {
        return sessionService.createSession(sessionsDTO);
    }
    // 获取所有会话
    @GetMapping("/sessions")
    public Result getAllSessions(@RequestBody SessionsDTO sessionsDTO) {
        return sessionService.getAllSessions(sessionsDTO);
    }
    // 获取会话历史记录
    @GetMapping("/sessions/{sessionId}/history")
    public Result getSessionHistory(@PathVariable String sessionId) {
        return sessionService.getSessionHistory(sessionId);
    }
    // 删除会话
    @DeleteMapping("/sessions/{sessionId}")
    public Result deleteSession(@PathVariable String sessionId) {
        return sessionService.deleteSession(sessionId);
    }
}