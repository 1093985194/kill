package com.oyzy.kill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyzy.kill.exception.GlobalException;
import com.oyzy.kill.mapper.UserMapper;
import com.oyzy.kill.pojo.User;
import com.oyzy.kill.service.IUserService;
import com.oyzy.kill.utils.CookieUtil;
import com.oyzy.kill.utils.MD5Util;
import com.oyzy.kill.utils.UUIDUtil;
import com.oyzy.kill.vo.LoginVo;
import com.oyzy.kill.vo.RespBean;
import com.oyzy.kill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 *  接口实现类
 * </p>
 *
 * @author oyzy
 * @since 2021-06-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登陆实现类
     * @param loginVo
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();


        User user = userMapper.selectById(mobile);
        if (user==null){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        //判断密码是否正确
        if (!MD5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPasssword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket = UUIDUtil.uuid();
        //将cookie存储到redis
        redisTemplate.opsForValue().set("user:"+ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        System.out.println("ticket:"+ticket);
        return RespBean.success(ticket);
    }

    /**
     * 通过cookie获取用户
     * @param userTicket
     * @return
     */
    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user!=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }


    /**
     * 更新密码
     * @param userTicket
     * @param
     * @param password
     * @return
     */
    @Override
    public RespBean updatePassword(String userTicket, String password,
                                   HttpServletRequest request,HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user == null){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPasssword(MD5Util.inputPassToDBPass(password,user.getSlat()));
        int i = userMapper.updateById(user);
        if (i==1){
            //删除Redis
            redisTemplate.delete("user:"+userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }
}
