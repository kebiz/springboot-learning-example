package com.zengzp.cart.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/30 15:16
 * @description：sku库
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_sku")
public class Sku implements Serializable {
    //商品id
    @TableId(value = "id",type = IdType.AUTO)
    private  long id;
    //名称
    private String name;
    //编码
    private String sn;
    //价格
    private Double price;
    //数量
    private Integer num;
    //警戒数量
    private Integer alertNum;
    //图片地址
    private String image;
    //图片列表
    private String images;
    //重量
    private Double weight;
    //SPUID
    private long spuId;
    //分类ID
    private  long categoryId;
    //分类名称
    private String categoryName;
    //品牌名称
    private String brandName;
    //销量
    private Integer saleNum;
    //规格
    private String spec;
    //评论数
    private Integer commentNum;
    //状态 正常--下架--删除
    private String status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    /**
     * 版本号
     */
    @Version
    private Long version;



}
