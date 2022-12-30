package com.zengzp.product.dao;


import com.zengzp.product.entity.TestSource;
import com.zengzp.product.entity.TestTarget;
import com.zengzp.product.vo.TestVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.auditing.MappingAuditableBeanWrapperFactory;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/9 11:33
 * @description：转换基类
 * @modified By：
 * @version: 1$
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class Converter {
    private  static final  ThreadLocal<String[]> extraFilledLocalBuffer =new ThreadLocal<String[]>();


    //@Mapping(target = "nick",source = "userNick")
    public abstract TestTarget convert(TestSource source);
    //@Mapping(target ="extra",source = "source",qualifiedByName = "convertToExtra")
    @Mapping(target = "age",source = "age",resultType = String.class)
    @Mapping(target = "idCard",source = "testConfig.idCard")
    @Mapping(target = "telPhone",source = "testConfig.telPhone")
    public abstract TestVO convertToVO(TestSource source);
    @Named("convertToExtra")
    public String convertToExtra(TestSource source){
       return String.format("%s,%s",source.getAge(),source.getUserNick());
    }
    @Mapping(target ="age",source = "vo",qualifiedByName = "convertToAge")
    @Mapping(target ="nick",source = "vo",qualifiedByName = "convertToNick")
    public abstract TestTarget convertToDO(TestVO vo);

    @Named("convertToAge")
    public Long convertToAge(TestVO vo){
        if(extraFilledLocalBuffer.get()==null){
            extraFilledLocalBuffer.set(extraFiled(vo));
        }

        return Long.valueOf(extraFilledLocalBuffer.get()[0]);
    }
    @Named("convertToNick")
    public String convertToNick(TestVO vo){
        if(extraFilledLocalBuffer.get()==null){
            extraFilledLocalBuffer.set(extraFiled(vo));
        }
        return extraFilledLocalBuffer.get()[1];
    }

    public String[] extraFiled(TestVO vo){
        return null;
       //return vo.getExtra().split(",");
    }

}
