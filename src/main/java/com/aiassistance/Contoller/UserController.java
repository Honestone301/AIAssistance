package com.aiassistance.Contoller;

import com.aiassistance.DTO.LoginDTO;
import com.aiassistance.DTO.RegisterDTO;
import com.aiassistance.Result.Result;
import com.aiassistance.Service.SerivceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/atom/user")
public class UserController {
    @Autowired
    UserServiceImpl userServiceimpl;
    @PostMapping("Register")
    public Result Register(RegisterDTO regiserDTO){
        return userServiceimpl.Register(regiserDTO);
    }
    @PostMapping("login")
    public Result login(LoginDTO loginDTO){
        return userServiceimpl.login(loginDTO);
    }
}
