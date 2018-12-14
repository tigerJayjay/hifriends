package com.easybug.web;

import com.easybug.annocation.Login;
import com.easybug.cache.BlackListCacheManager;
import com.easybug.common.ResultJson;
import com.easybug.config.JWT;
import com.easybug.model.TokenBlackList;
import com.easybug.model.User;
import com.easybug.service.login.ILogin;
import com.easybug.service.token.ITokenService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Api
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private ILogin loginService;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private JWT jwt;
    @Autowired
    BlackListCacheManager cacheManager;
    @ApiOperation(value = "登陆")
    @ApiImplicitParam(name="user",value = "登陆信息",paramType = "body",dataType = "User")
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public ResultJson login(@RequestBody User user){
        ResultJson result = new ResultJson();
            User u = loginService.login(user);
            if(u!=null){
                result.setStatus(200);
                result.setMessage("登陆成功!");
                String token = jwt.generateToken(user.getuId());
                result.setData(token);
            }else{
                result.setStatus(500);
                result.setMessage("登陆失败!");
            }
        return result;
    }

    @ApiOperation(value = "注册")
    @ApiImplicitParam(name="user",value = "注册信息",paramType = "body",dataType = "User")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultJson register(@RequestBody User user){
        ResultJson result = new ResultJson();
        if( loginService.register(user)){
            result.setStatus(200);
            result.setMessage("注册成功!");
            result.setData(user.getuId());
        }else{
            result.setStatus(500);
            result.setMessage("注册失败!");
        }
        return result;
    }
    @ApiOperation(value = "登出")
    @Login
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResultJson loginout(HttpServletRequest request){
        String token = (String)request.getHeader("token");
        TokenBlackList tokenBlackList = new TokenBlackList();
        ResultJson result = new ResultJson();
        Date expiration = null;
        Integer id;
        if(token!=null){
            Claims claims = jwt.getClaimByToken(token);
            expiration = claims.getExpiration();
            tokenBlackList.setToken(token);
            tokenBlackList.setExpiredTime(expiration);
        }
        if((id = tokenService.insertTokenList(tokenBlackList))!=null){
            result.setStatus(200);
            result.setMessage("登出成功!");
        }else{
            result.setStatus(500);
            result.setMessage("登出失败!");
        }
        return result;
    }
}
