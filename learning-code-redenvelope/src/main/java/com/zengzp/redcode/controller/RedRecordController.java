package com.zengzp.redcode.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengzp.redcode.constant.BaseResponse;
import com.zengzp.redcode.constant.StatusCode;
import com.zengzp.redcode.dto.RedPacketDto;
import com.zengzp.redcode.entity.RedRecord;
import com.zengzp.redcode.service.RedRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 发红包记录表(RedRecord)表控制层
 *
 * @author zeengzp
 * @since 2023-03-19 16:32:43
 */
@RestController
@RequestMapping(value = "/redRecord")
@Slf4j
public class RedRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private RedRecordService redRecordService;

    @PostMapping("/handOut")
    public R handOut(@Validated @RequestBody RedPacketDto redPacketDto, BindingResult result){
        if(result.hasErrors()){
            return failed(StatusCode.INVALIDPARAMS);
        }
        try {
           return success(redRecordService.handOut(redPacketDto)) ;
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return failed(StatusCode.FAIL);
        }

    }
    @PostMapping(value = "/rod",produces = "application/json;charset=UTF-8")
    public R Rod(@RequestParam(value = "userId") @NotBlank Integer userId, @RequestParam(value = "redId") @NotBlank String redId){
        try {
            return success(redRecordService.rod(userId,redId)) ;
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return failed(StatusCode.ROD_FAIL);
        }

    }
    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param redRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<RedRecord> page, RedRecord redRecord) {
        return success(this.redRecordService.page(page, new QueryWrapper<>(redRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.redRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param redRecord 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody RedRecord redRecord) {
        return success(this.redRecordService.save(redRecord));
    }

    /**
     * 修改数据
     *
     * @param redRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody RedRecord redRecord) {
        return success(this.redRecordService.updateById(redRecord));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.redRecordService.removeByIds(idList));
    }
}

