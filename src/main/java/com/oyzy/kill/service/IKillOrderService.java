package com.oyzy.kill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oyzy.kill.pojo.KillOrder;
import com.oyzy.kill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oyzy
 * @since 2021-06-05
 */
public interface IKillOrderService extends IService<KillOrder> {

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(User user, Long goodsId);
}
