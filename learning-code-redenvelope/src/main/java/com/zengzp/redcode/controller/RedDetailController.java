package controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengzp.redcode.entity.RedDetail;
import com.zengzp.redcode.service.RedDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 红包明细金额(RedDetail)表控制层
 *
 * @author zeengzp
 * @since 2023-04-22 20:38:41
 */
@RestController
@RequestMapping("redDetail")
public class RedDetailController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private RedDetailService redDetailService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param redDetail 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<RedDetail> page, RedDetail redDetail) {
        return success(this.redDetailService.page(page, new QueryWrapper<>(redDetail)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.redDetailService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param redDetail 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody RedDetail redDetail) {
        return success(this.redDetailService.save(redDetail));
    }

    /**
     * 修改数据
     *
     * @param redDetail 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody RedDetail redDetail) {
        return success(this.redDetailService.updateById(redDetail));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.redDetailService.removeByIds(idList));
    }
}

