package com.aiassistance.Service.SerivceImpl;

import com.aiassistance.DTO.SessionsDTO;
import com.aiassistance.Entity.QaHistory;
import com.aiassistance.Entity.Sessions;
import com.aiassistance.Mapper.QaHistoryMapper;
import com.aiassistance.Mapper.SessionsMapper;
import com.aiassistance.Result.Result;
import com.aiassistance.Service.SessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImpl extends ServiceImpl<SessionsMapper, Sessions> implements SessionService {
    @Autowired
    QaHistoryServiceImpl  qaHistoryServiceImpl;


    @Override
    public Result createSession(SessionsDTO sessionsDTO) {
        String dateTime=new Date().toString();

        Sessions session=new Sessions();
        session.setUserId(sessionsDTO.getUserId());
        session.setCreateAt(new Date());
        session.setUpdateAt(new Date());
        session.setIsDeleted(0);
        session.setStatus("active");
        session.setTitle(dateTime);

        this.save(session);

        Sessions NewSession=  new Sessions();
        NewSession.setUserId(session.getUserId());
        NewSession.setTitle(dateTime);

        NewSession=this.lambdaQuery()
                .eq(Sessions::getTitle,dateTime)
                .eq(Sessions::getUserId,sessionsDTO.getUserId())
                .one();

        return Result.success("create session success",NewSession.getSessionId());

    }

//    @Override
//    public Result getSession(SessionsDTO sessionsDTO) {
//        return Result.success("get session success",sessionsDTO.getSessionId());
//    }

    @Override
    public Result getSessionHistory(String sessionId) {
        // 先获取session信息，通过sessionId去获取List<QaHistory>
        // 再把QaHistory最近五条拼接进RequestDTO里去访问API
        Sessions sessions=new Sessions();
        sessions=this.lambdaQuery()
                .eq(Sessions::getSessionId,sessionId)
                .one();
        // 直接返回 qaHistoryServiceImpl 的结果，不再嵌套
        return qaHistoryServiceImpl.getQaHistoryBySessionId(sessionId);

    }

    @Override
    public Result deleteSession(String sessionId) {
        return Result.success("delete session success");
    }

    @Override
    public Result getAllSessions(SessionsDTO sessionsDTO) {
        return null;
    }
}
