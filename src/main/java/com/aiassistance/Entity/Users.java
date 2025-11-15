package com.aiassistance.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class Users {
    @TableField("user_id")
    int userId;
    @TableField("username")
    String username;
    @TableField("password_hash")
    String password;
    @TableField("creat_at")
    Date creatAt;
    @TableField("update_at")
    Date updateAt;
    String email;
    String name;


}
