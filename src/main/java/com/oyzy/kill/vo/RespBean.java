package com.oyzy.kill.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//公共返回对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object object;

    /**
     *成功返回结果
     */
    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(), null);
    }

    /**
     *成功返回结果
     */
    public static RespBean success(Object object){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBean.success().getMessage(),object);
    }

    /**
     * 功能描述: 失败返回结果
     *成功为500
     * 失败代码各有不同
     */
    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),null);
    }

    /**
     * 功能描述: 失败返回结果
     *成功为500
     * 失败代码各有不同
     */
    public static RespBean error(RespBeanEnum respBeanEnum,Object object){
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),object);
    }

}
