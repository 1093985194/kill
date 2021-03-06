package com.oyzy.kill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oyzy.kill.pojo.Goods;
import com.oyzy.kill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oyzy
 * @since 2021-06-05
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
