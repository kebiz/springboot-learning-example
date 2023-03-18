package com.zengzp.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengzp.cart.entity.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {
    @Update(value = "update t_sku  set num=num - #{saleNum} , sale_num=sale_num + #{saleNum} ,version=version+1 where id=#{skuId} and version=#{version}" )
    int  updateByStock(@Param("skuId") Long stuId,@Param("saleNum") int saleNum,@Param("version") Long version);
}
