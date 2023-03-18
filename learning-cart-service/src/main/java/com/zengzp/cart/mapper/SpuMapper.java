package com.zengzp.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengzp.cart.entity.Spu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/3/15 11:24
 * @description：Mapper类
 * @modified By：
 * @version: 1$
 */
@Repository
@Mapper
public interface SpuMapper extends BaseMapper<Spu> {
}
