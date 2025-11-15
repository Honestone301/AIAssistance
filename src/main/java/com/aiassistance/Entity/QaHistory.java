package com.aiassistance.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("qa_history")
public class QaHistory {
    @TableField("qa_id")
    private String qaId;
    @TableField("user_id")
    private String userId;
    String question;
    String answer;
    @TableField("create_ai")
    Date createAi;
    @TableField("session_id")
    String sessionId;
}
