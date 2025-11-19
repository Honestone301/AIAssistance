package com.aiassistance.Service.SerivceImpl;

import com.aiassistance.DTO.AskingDTO;
import com.aiassistance.DTO.DeepseekRequestDTO;
import com.aiassistance.DTO.DeepseekResponseDTO;
import com.aiassistance.DTO.EntireQaDTO;
import com.aiassistance.Entity.QaHistory;
import com.aiassistance.Mapper.QaHistoryMapper;
import com.aiassistance.Result.Result;
import com.aiassistance.Service.DeepSeekService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeepseekServiceImpl extends ServiceImpl<QaHistoryMapper, QaHistory> implements DeepSeekService {
    @Autowired
    QaHistoryServiceImpl qaHistoryServiceImpl;
    @Autowired
    SessionServiceImpl sessionsServiceImpl;

    // 配置 WebClient 基础 URL 和超时
    private final WebClient webClient;
    private final String apiUrl;

    public DeepseekServiceImpl(
            @Value("${deepseek.api.url}") String apiUrl,
            @Value("${deepseek.api.key}") String apiKey
    ) {
        // WebClient 基础 URL 和超时
        this.apiUrl = apiUrl;

        // 超时配置
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(60)) // 增加到60秒
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                ))
                .build();
    }


    // 抽取API调用为单独方法，避免代码重复并增强错误处理
    private String callDeepSeekApi(DeepseekRequestDTO request) {
        try {
            return webClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(
                                            new RuntimeException("DeepSeek API Error: " + clientResponse.statusCode() + ". Response: " + errorBody)
                                    ))
                    )
                    .bodyToMono(DeepseekResponseDTO.class)
                    .timeout(Duration.ofSeconds(60)) // 请求超时设置
                    .map(response -> {
                        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                            return response.getChoices().get(0).getMessage().getContent();
                        }
                        return "No response from DeepSeek API";
                    })
                    .block();
        } catch (Exception e) {
            System.err.println("DeepSeek API call failed: " + e.getMessage());
            // 返回友好的错误消息给用户
            return "抱歉，请求处理超时，请稍后再试。";
        }
    }


    public String chat(AskingDTO askingDTO) {
        if (askingDTO == null || askingDTO.getMessage() == null || askingDTO.getUserId() == null) {
            throw new IllegalArgumentException("请求参数不完整");
        }
        String sessionId = askingDTO.getSessionId();

        Result result=sessionsServiceImpl.getSessionHistory(sessionId);
        List<QaHistory> qaHistoryList=new  ArrayList<>();

        if(result !=null&&result.getData()!=null){
            try {
                // 使用instanceof进行安全检查
                if (result.getData() instanceof List) {
                    // 安全地将data转换为List并添加到qaHistoryList
                    qaHistoryList.addAll((List<QaHistory>) result.getData());
                } else {
                    System.err.println("Warning: Result data is not a List");
                }
            } catch (ClassCastException e) {
                // 捕获类型转换异常并记录错误信息
                System.err.println("Error casting result data to List<QaHistory>: " + e.getMessage());
            }
        }

        // 构建请求
        DeepseekRequestDTO request = new DeepseekRequestDTO();
        // 取最近五条历史记录
        List<QaHistory> recentQaHistoryList = qaHistoryList.isEmpty() ?
                new ArrayList<>() :
                qaHistoryList.subList(0, Math.min(5, qaHistoryList.size()));

        // 检查会话是否存在历史记录
        if (!recentQaHistoryList.isEmpty()) {

            List<DeepseekRequestDTO.Message> msg=buildMessageContext(recentQaHistoryList,askingDTO.getMessage());

            // 使用内部 Message 类而不是外部 Message 实体

            request = DeepseekRequestDTO.builder()
                    .model("deepseek-chat")
                    .messages(msg)
                    .build();

        }else{
            DeepseekRequestDTO.Message meg=new DeepseekRequestDTO.Message("user",askingDTO.getMessage());
            List<DeepseekRequestDTO.Message> messages=new ArrayList<>();
            messages.add(meg);
            request = DeepseekRequestDTO.builder()
                    .model("deepseek-chat")
                    .messages(messages)
                    .build();
        }
        // 调用API
        String responses = callDeepSeekApi(request);

        EntireQaDTO entireQaDTO=new EntireQaDTO();
        entireQaDTO.setUserId(askingDTO.getUserId());
        entireQaDTO.setSessionId(sessionId);
        entireQaDTO.setMessage(askingDTO.getMessage());
        entireQaDTO.setAnswer(responses);
        qaHistoryServiceImpl.saveQaHistory(entireQaDTO);
        return responses;
    }


    private List<DeepseekRequestDTO.Message> buildMessageContext(List<QaHistory> qaHistoryList, String currentMessage) {
        List<DeepseekRequestDTO.Message> messages=new ArrayList<>();
        for(QaHistory qaHistory:qaHistoryList){
            messages.add(new DeepseekRequestDTO.Message("user",qaHistory.getQuestion()));
            messages.add(new DeepseekRequestDTO.Message("assistant",qaHistory.getAnswer()));
        }
        messages.add(new DeepseekRequestDTO.Message("user",currentMessage));
        return messages;
    }
}