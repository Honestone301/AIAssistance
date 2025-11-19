package com.aiassistance.Contoller;

import com.aiassistance.DTO.LoginDTO;
import com.aiassistance.DTO.RegisterDTO;
import com.aiassistance.Result.Result;
import com.aiassistance.Service.SerivceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atom/user")
public class UserController {
    @Autowired
    UserServiceImpl userServiceimpl;
    @PostMapping("/register")
    public Result Register(@RequestBody RegisterDTO registerDTO){
        return userServiceimpl.Register(registerDTO);
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        System.out.println("现在是："+loginDTO);
        return userServiceimpl.login(loginDTO);
    }
}
