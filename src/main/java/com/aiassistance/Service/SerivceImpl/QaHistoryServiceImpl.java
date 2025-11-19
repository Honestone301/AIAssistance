package com.aiassistance.Service.SerivceImpl;

import com.aiassistance.DTO.AskingDTO;
import com.aiassistance.DTO.EntireQaDTO;
import com.aiassistance.Entity.QaHistory;
import com.aiassistance.Mapper.QaHistoryMapper;
import com.aiassistance.Result.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aiassistance.Service.QaHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QaHistoryServiceImpl extends ServiceImpl<QaHistoryMapper, QaHistory> implements QaHistoryService {

    @Override
    public Result<List<QaHistory>> getQaHistoryBySessionId(String sessionId) {
        List<QaHistory> qaHistoryList=this.lambdaQuery()
                .eq(QaHistory::getSessionId,sessionId)
                .list();
        return Result.success("get qa history success",qaHistoryList);
    }

    @Override
    public void saveQaHistory(EntireQaDTO entireQaDTO) {
        QaHistory qaHistory=new QaHistory();
        qaHistory.setSessionId(entireQaDTO.getSessionId());
        qaHistory.setQuestion(entireQaDTO.getMessage());
        qaHistory.setAnswer(entireQaDTO.getAnswer());
        qaHistory.setUserId(entireQaDTO.getUserId());
        this.saveOrUpdate(qaHistory);
    }
}
