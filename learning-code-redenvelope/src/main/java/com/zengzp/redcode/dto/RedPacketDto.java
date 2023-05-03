package com.zengzp.redcode.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author zengzp
 * @Date 2023/5/1 17:43
 * @Version 1.0
 **/
@Data
@ToString
public class RedPacketDto {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer amount;
    @NotNull
    private Integer total;
}
