package com.zengzp.redcode.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.zengzp.redcode.entity.RedRecord;
import org.springframework.stereotype.Repository;

/**
 * 发红包记录表(RedRecord)表数据库访问层
 *
 * @author zeengzp
 * @since 2023-03-19 16:32:43
 */
@Repository
@Mapper
public interface RedRecordDao extends BaseMapper<RedRecord> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RedRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RedRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RedRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RedRecord> entities);

}

