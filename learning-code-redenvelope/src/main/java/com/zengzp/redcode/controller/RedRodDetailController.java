package com.zengzp.redcode.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengzp.redcode.entity.RedRodDetail;
import com.zengzp.redcode.service.RedRodDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 抢红包记录表(RedRodDetail)表控制层
 *
 * @author zeengzp
 * @since 2023-04-22 20:48:48
 */
@RestController
@RequestMapping("redRodDetail")
public class RedRodDetailController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private RedRodDetailService redRodDetailService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param redRodDetail 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<RedRodDetail> page, RedRodDetail redRodDetail) {
        return success(this.redRodDetailService.page(page, new QueryWrapper<>(redRodDetail)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.redRodDetailService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param redRodDetail 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody RedRodDetail redRodDetail) {
        return success(this.redRodDetailService.save(redRodDetail));
    }

    /**
     * 修改数据
     *
     * @param redRodDetail 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody RedRodDetail redRodDetail) {
        return success(this.redRodDetailService.updateById(redRodDetail));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.redRodDetailService.removeByIds(idList));
    }
}

