package com.zengzp.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengzp.product.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 16:13
 * @description：商品信息Mapper类
 * @modified By：
 * @version: 1.0$
 */
@Repository
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    @Update("UPDATE product_info SET product_name=#{productName},product_price=#{productPrice},product_status=#{productStatus} WHERE product_id =#{productId}")
    void updateProduct(ProductInfo productInfo);
}
