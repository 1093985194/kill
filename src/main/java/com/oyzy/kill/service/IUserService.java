package com.oyzy.kill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oyzy.kill.pojo.User;
import com.oyzy.kill.vo.LoginVo;
import com.oyzy.kill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类：接口
 * </p>
 *
 * @author oyzy
 * @since 2021-06-03
 */
public interface IUserService extends IService<User> {
    /*
    登陆接口
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过cookie获取用户
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

    /**
     * 更新密码
     * @param userTicket
     * @param
     * @param password
     * @return
     */
    RespBean updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);

}
