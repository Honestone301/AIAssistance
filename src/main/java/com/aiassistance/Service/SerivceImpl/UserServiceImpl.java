package com.aiassistance.Service.SerivceImpl;

import com.aiassistance.Constant.MessageConstant;
import com.aiassistance.DTO.LoginDTO;
import com.aiassistance.Result.Result;
import com.aiassistance.Utill.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aiassistance.DTO.RegisterDTO;
import com.aiassistance.Entity.Users;
import com.aiassistance.Mapper.UserMapper;
import com.aiassistance.Service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,Users> implements UserService {
    @Override
    public Result Register(RegisterDTO registerDTO) {
        String password=registerDTO.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        String username=registerDTO.getUsername();
        String email=registerDTO.getEmail();
        Users user=this.lambdaQuery()
                .eq(Users::getUsername, username)
                .one();
        if(user!=null){
            return Result.error(400, MessageConstant.ACCOUNT+MessageConstant.ALREADY_EXISTS);
        }
        user=new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        boolean save = this.save(user);
        if(save){
            return Result.success(MessageConstant.USER+MessageConstant.ERROR+MessageConstant.SUCCESS);
        }else{
            return Result.error(400, MessageConstant.USER+MessageConstant.REGISTER+MessageConstant.FAILED);
        }

    }

    @Override
    public Result login(LoginDTO loginDTO) {
        String passwordMD5=DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        Users user=this.lambdaQuery()
                .eq(Users::getUsername, loginDTO.getUsername())
                .one();
        if(user==null){
            return Result.error(400, MessageConstant.USER+MessageConstant.NOT_EXIST);
        }
        if(!user.getPassword().equals(passwordMD5)){
            return Result.error(400, MessageConstant.USER+MessageConstant.OR+MessageConstant.PASSWORD+MessageConstant.ERROR);
        }
        String token= JWT.generateToken(loginDTO.getUsername());

        return Result.success(MessageConstant.USER+MessageConstant.LOGIN+MessageConstant.SUCCESS);
    }
}
