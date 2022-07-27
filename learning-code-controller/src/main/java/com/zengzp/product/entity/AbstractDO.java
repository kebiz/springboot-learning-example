package com.zengzp.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/27 15:12
 * @description：基类
 * @modified By：
 * @version: 1.0$
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractDO implements Serializable {
    private static final long serialVersionUID = -3737736141782545763L;
    @TableId(type = IdType.AUTO)
    private long id;

    private Date createTime;

    private Date updateTime;

}
