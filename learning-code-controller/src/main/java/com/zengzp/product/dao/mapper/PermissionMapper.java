package com.zengzp.product.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengzp.product.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Entity com.zengzp.product.dao.domain.Permission
*/
@Repository
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

   List<Permission> listByLoginName(String loginName);
}
