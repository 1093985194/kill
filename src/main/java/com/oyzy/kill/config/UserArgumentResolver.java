package com.oyzy.kill.config;

import com.oyzy.kill.pojo.User;
import com.oyzy.kill.service.IUserService;
import com.oyzy.kill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService userService;
    /**
     * 只有supportsParameter返回为true后才能执行resolveArgument
     * @param methodParameter:传入需要判断的元素
     *
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> type = methodParameter.getParameterType();
        /*
        判断传入的类型是否为User类型
         */
        return type== User.class;
    }

    /**
     *通过Cookie查找到User
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        //通过nativeWebRequest获取HttpServletRequest与HttpServletResponse
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String userTicket = CookieUtil.getCookieValue(request, "userTicket");

        if (StringUtils.isEmpty(userTicket)){
            return null;
        }
        return userService.getUserByCookie(userTicket,request,response);
    }
}
