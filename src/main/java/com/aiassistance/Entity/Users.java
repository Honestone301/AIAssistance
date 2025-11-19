package com.aiassistance.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class Users {
    @TableId("user_id")  // Changed from @TableField to @TableId
    int userId;
    @TableField("username")
    String username;
    @TableField("password_hash")
    String password;
    @TableField("created_at")
    Date createdAt;
    @TableField("updated_at")
    Date updatedAt;
    String email;
    String name;


}
