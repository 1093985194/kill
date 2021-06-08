package com.oyzy.kill.controller;


import com.oyzy.kill.pojo.User;
import com.oyzy.kill.service.IOrderService;
import com.oyzy.kill.vo.OrderDetailVo;
import com.oyzy.kill.vo.RespBean;
import com.oyzy.kill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oyzy
 * @since 2021-06-05
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private IOrderService orderService;

    /**
     * 功能描述: 订单详情
     *
     * @param:
     * @return: 乐字节：专注线上IT培训
     * 答疑老师微信：lezijie
     * @since: 1.0.0
     * @Author:zhoubin
     */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail = orderService.detail(orderId);
        return RespBean.success(detail);
    }

}
