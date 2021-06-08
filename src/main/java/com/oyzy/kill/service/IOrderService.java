package com.oyzy.kill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oyzy.kill.pojo.Order;
import com.oyzy.kill.pojo.User;
import com.oyzy.kill.vo.GoodsVo;
import com.oyzy.kill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oyzy
 * @since 2021-06-05
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user
     * @param goodsVo
     * @return
     */
    Order kill(User user, GoodsVo goodsVo);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);
}
