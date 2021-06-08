package com.oyzy.kill.controller;


import com.oyzy.kill.service.IUserService;
import com.oyzy.kill.vo.LoginVo;
import com.oyzy.kill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean dologin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return iUserService.doLogin(loginVo,request,response);
    }
}
