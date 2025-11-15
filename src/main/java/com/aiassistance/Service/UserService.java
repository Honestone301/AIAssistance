package com.aiassistance.Service;

import com.aiassistance.DTO.LoginDTO;
import com.aiassistance.DTO.RegisterDTO;
import com.aiassistance.Entity.Users;
import com.aiassistance.Result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<Users> {
    Result Register(RegisterDTO registerDTO);
    Result login(LoginDTO loginDTO);
}
