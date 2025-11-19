package com.aiassistance.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("qa_history")
public class QaHistory {
    @TableId("qa_id")
    private Long qaId;
    @TableField("user_id")
    private Long userId;
    @TableField("question")
    String question;
    @TableField("answer")
    String answer;
    @TableField("created_at")
    Date createdAt;
    @TableField("session_id")
    String sessionId;
}
