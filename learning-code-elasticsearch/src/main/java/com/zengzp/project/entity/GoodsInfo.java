package com.zengzp.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/1 10:47
 * @description：商品信息
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo {
    private long id;
    private String name;
    private String imgUrl;
    private Double price;
}
