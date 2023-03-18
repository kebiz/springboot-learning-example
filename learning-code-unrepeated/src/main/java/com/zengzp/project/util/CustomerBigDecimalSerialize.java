package com.zengzp.project.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/9 17:23
 * @description：Bigdecimal保留小数工具类
 * @modified By：
 * @version: 1$
 */
public class CustomerBigDecimalSerialize extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(Objects.nonNull(value)) {
            //保留2位小数#代表末位是0舍去
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            //四舍五入
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String result = decimalFormat.format(value);
            //返回到前端的数据为数字类型,前端接收有可能丢失精度
            //gen.writeNumber(value.stripTrailingZeros());
            //返回到前端的数据为字符串类型
           // gen.writeString(value.stripTrailingZeros().toPlainString());
            gen.writeString(result);
            //去除0后缀,如果想统一进行保留精度，也可以采用类似处理
        }else {//如果为null的话，就写null
            gen.writeNull();
        }
    }
}
