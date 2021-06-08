package com.oyzy.kill.vo;

import com.oyzy.kill.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * extends Goods:继承Goods中的属性
 */
public class GoodsVo extends Goods {

    private BigDecimal killPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

}
