package com.zengzp.product.dao.mapper;

import com.zengzp.product.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.zengzp.product.entity.UserInfo
 */
@Repository
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




