package com.aiassistance.Service;

import com.aiassistance.DTO.EntireQaDTO;
import com.aiassistance.Entity.QaHistory;
import com.aiassistance.Result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface QaHistoryService extends IService<QaHistory> {
    Result<List<QaHistory>> getQaHistoryBySessionId(String sessionId);
    void saveQaHistory(EntireQaDTO entireQaDTO);
}
