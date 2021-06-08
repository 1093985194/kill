package com.oyzy.kill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oyzy.kill.pojo.Goods;
import com.oyzy.kill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oyzy
 * @since 2021-06-05
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
