package com.zengzp.redcode.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.zengzp.redcode.entity.RedRodDetail;
import org.springframework.stereotype.Repository;

/**
 * 抢红包记录表(RedRodDetail)表数据库访问层
 *
 * @author zeengzp
 * @since 2023-04-22 20:48:48
 */
@Repository
@Mapper
public interface RedRodDetailDao extends BaseMapper<RedRodDetail> {

/**
* 批量新增数据（MyBatis原生foreach方法）
*
* @param entities List<RedRodDetail> 实例对象列表
* @return 影响行数
*/
int insertBatch(@Param("entities") List<RedRodDetail> entities);

/**
* 批量新增或按主键更新数据（MyBatis原生foreach方法）
*
* @param entities List<RedRodDetail> 实例对象列表
* @return 影响行数
* @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
*/
int insertOrUpdateBatch(@Param("entities") List<RedRodDetail> entities);

}

