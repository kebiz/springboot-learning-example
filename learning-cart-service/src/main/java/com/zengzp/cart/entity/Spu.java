package com.zengzp.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/30 15:17
 * @description：spu库
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spu implements Serializable {
    //ID
    private long id;
    //名称
    private String name;
    //副标题
    private String caption;
    //货号
    private String sn;
    //品牌Id
    private long brandId;
    //品牌名称
    private String brandName;
    //一级分类Id
    private long category1Id;
    //二级分类Id
    private long category2Id;
    //三级分类Id
    private long category3Id;
    //模板Id
    private long templateId;
    //运费模板Id
    private long freightId;
    //图片
    private String image;
    //图片列表
    private String images;
    //售后服务
    private String saleService;
    //介绍
    private  String introduction;
    //规格列表
    private String spacItems;
    //参数列表
    private String paraItems;
    //销量
    private Integer saleNum;
    //评论数
    private Integer commentNum;
    //是否上架
    private String isMarketable;
    //是否启用规格
    private  String isEnableSpac;
    //是否删除
    private  String isDelete;
    //审核状态
    private String status;

}
