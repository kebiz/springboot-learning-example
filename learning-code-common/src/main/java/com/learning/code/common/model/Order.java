package com.learning.code.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.learning.code.common.util.JsonSerializerUtils;
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
@TableName("t_order")
public class Order implements Serializable {
    //订单ID
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    //用户名
    private String userName;
    //数量
    private Integer totalNum;
    //总金额
    @JsonSerialize(using = JsonSerializerUtils.class)
    private Double totalMoney;
    //优惠金额
    @JsonSerialize(using = JsonSerializerUtils.class)
    private  Double preMoney;
    //支付金额
    @JsonSerialize(using = JsonSerializerUtils.class)
    private Double payMoney;
    //邮费
    @JsonSerialize(using = JsonSerializerUtils.class)
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
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //订单更新时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //交易完成时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private  Date endTime;
    //交易关闭时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;
    //发货时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date consignTime;
    //付款时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;




}
