package com.zengzp.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/1 10:13
 * @description：订单主表
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    //订单ID
    private Long id;
    //用户名
    private String userName;
    //数量
    private Integer totalNum;
    //总金额
    private Double totalMoney;
    //优惠金额
    private  Double preMoney;
    //支付金额
    private Double payMoney;
    //邮费
    private Double postFee;
    //支付类型 1：在线支付  0：货到付款
    private String payType;
    //物流单号
    private String shippingCode;
    //物流名称
    private  String shippingName;
    //收货人
    private  String receiverContact;
    //收货人手机号码
    private String receiverMobile;
    //收货人地址
    private String receiverAddress;
    //交易流水号
    private  String transactionId;
    //订单来源 1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面
    private String sourceType;
    //订单状态
    private String orderStatus;
    //发货状态
    private  String consignStatus;
    //支付状态
    private  String payStatus;
    //订单创建时间
    private Date createTime;
    //订单更新时间
    private Date updateTime;
    //交易完成时间
    private  Date endTime;
    //交易关闭时间
    private Date closeTime;
    //发货时间
    private Date consignTime;
    //付款时间
    private Date payTime;




}
