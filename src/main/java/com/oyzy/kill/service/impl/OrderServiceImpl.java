package com.oyzy.kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyzy.kill.exception.GlobalException;
import com.oyzy.kill.mapper.OrderMapper;
import com.oyzy.kill.pojo.KillGoods;
import com.oyzy.kill.pojo.KillOrder;
import com.oyzy.kill.pojo.Order;
import com.oyzy.kill.pojo.User;
import com.oyzy.kill.service.IGoodsService;
import com.oyzy.kill.service.IKillGoodsService;
import com.oyzy.kill.service.IKillOrderService;
import com.oyzy.kill.service.IOrderService;
import com.oyzy.kill.vo.GoodsVo;
import com.oyzy.kill.vo.OrderDetailVo;
import com.oyzy.kill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oyzy
 * @since 2021-06-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IKillGoodsService killGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IKillOrderService killOrderService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;



    @Transactional
    @Override
    public Order kill(User user, GoodsVo goodsVo) {
        //秒杀商品表减库存
        KillGoods killGoods = killGoodsService.getOne(new QueryWrapper<KillGoods>().
                eq("goods_id", goodsVo.getId()));

        killGoods.setStockCount(killGoods.getStockCount()-1);

        boolean killGoodsResult = killGoodsService.update(new UpdateWrapper<KillGoods>().
                setSql("stock_count = stock_count-1").eq("goods_id",goodsVo.getId()).gt("stock_count",0));

        if (!killGoodsResult){
            return null;
        }



        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(killGoods.getKillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        KillOrder killOrder = new KillOrder();
        killOrder.setUserId(user.getId());
        killOrder.setOrderId(order.getId());
        killOrder.setGoodsId(goodsVo.getId());
        killOrderService.save(killOrder);
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+goodsVo.getId(),killOrder);

        return order;
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId==null){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(order);
        orderDetailVo.setGoodsVo(goodsVo);
        return orderDetailVo;
    }
}
