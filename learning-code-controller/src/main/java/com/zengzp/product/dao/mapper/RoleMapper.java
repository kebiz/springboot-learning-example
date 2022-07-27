package com.zengzp.product.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengzp.product.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Entity com.zengzp.product.entity.Role
*/
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

   List<Role> listRolesByLoginName(String loginName);
}
