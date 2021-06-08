package com.oyzy.kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyzy.kill.mapper.KillOrderMapper;
import com.oyzy.kill.pojo.KillOrder;
import com.oyzy.kill.pojo.User;
import com.oyzy.kill.service.IKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oyzy
 * @since 2021-06-05
 */
@Service
public class KillOrderServiceImpl extends ServiceImpl<KillOrderMapper, KillOrder> implements IKillOrderService {

    @Autowired
    private KillOrderMapper killOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Long getResult(User user, Long goodsId) {
        KillOrder killOrder = killOrderMapper.selectOne(new QueryWrapper<KillOrder>()
                .eq("user_id", user.getId()).eq("goods_id", goodsId));

        if (killOrder!=null){
            return killOrder.getOrderId();
        }else if (redisTemplate.hasKey("isStockEmpty:"+goodsId)){
            return -1L;
        }else {
            return 0L;
        }
    }
}
