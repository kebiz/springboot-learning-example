package com.zengzp.product.entity.param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/22 11:40
 * @description：分页参数
 * @modified By：
 * @version: 1.0$
 */
@Data
public class PageParam<T> {
    private long size;
    private long current;

    public IPage<T> createPage(){
        IPage<T> objectPage = new Page<>();
        objectPage.setSize(size == 0L?5L:size).setCurrent(current == 0L?1L:current);
        return objectPage;
    }

    public PageParam() {
        this.size=5L;
        this.current=1L;
    }

    public PageParam(long size, long current) {
        this.size=size == 0L?5L:size;
        this.current=current == 0L?1L:current;
    }
}
