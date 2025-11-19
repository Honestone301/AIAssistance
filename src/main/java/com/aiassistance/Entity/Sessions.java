package com.aiassistance.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sessions")
public class Sessions {
    @TableId("session_id")  // Changed from @TableField to @TableId  , save as the primary key
    String sessionId;
    @TableField("user_id")
    Integer userId;
    @TableField("created_at")
    Date createAt;
    @TableField("updated_at")
    Date updateAt;
    @TableField("is_deleted")
    Integer isDeleted;
    String status;
    String title;

}
