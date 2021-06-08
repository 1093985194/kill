package com.oyzy.kill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyzy.kill.pojo.KillOrder;
import com.oyzy.kill.pojo.User;
import com.oyzy.kill.service.IGoodsService;
import com.oyzy.kill.service.IKillGoodsService;
import com.oyzy.kill.service.IKillOrderService;
import com.oyzy.kill.service.IOrderService;
import com.oyzy.kill.service.impl.OrderServiceImpl;
import com.oyzy.kill.vo.GoodsVo;
import com.oyzy.kill.vo.RespBean;
import com.oyzy.kill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import com.oyzy.kill.pojo.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/kill")
public class KillController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IKillOrderService killOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *windows优化前：333qps
     * linux 优化前：
     * @param model:页面
     * @param user：秒杀用户
     * @param goodsId：秒杀商品id
     * @return
     */
    @RequestMapping("/dokill2")
    public String dokill2(Model model, User user,Long goodsId){
        if (user == null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goodsVo.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢
        KillOrder killOrder = killOrderService.getOne(new QueryWrapper<KillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (killOrder !=null){
            model.addAttribute("errmsg",RespBeanEnum.REPEATE_ERROR.getMessage());
            return "secKillFail";
        }

        Order order = orderService.kill(user,goodsVo);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);
        return "orderDetail";

    }



    /**
     *windows优化前：333qps
     * 优化后：573
     * @param :页面
     * @param user：秒杀用户
     * @param goodsId：秒杀商品id
     * @return
     */
    @RequestMapping(value = "/dokill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean dokill( User user, Long goodsId){
        if (user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount()<1){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //判断是否重复抢
//        KillOrder killOrder = killOrderService.getOne(new QueryWrapper<KillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));

        KillOrder killOrder = (KillOrder)redisTemplate.opsForValue().get("order:"+user.getId()+":"+goods.getId());
        if (killOrder !=null){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        Order order = orderService.kill(user,goods);
        return RespBean.success(order);

    }

    /**
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user,Long goodsId){
        if (user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = killOrderService.getResult(user,goodsId);

        return RespBean.success(orderId);
    }



}
