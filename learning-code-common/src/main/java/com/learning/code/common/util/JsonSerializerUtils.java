package com.learning.code.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/9 16:06
 * @description：将double类型保留两位小数工具类
 * @modified By：
 * @version: 1$
 */
@Component
public class JsonSerializerUtils extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.nonNull(value)) {
            //保留2位小数#代表末位是0舍去
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            //四舍五入
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String result = decimalFormat.format(value);
            jsonGenerator.writeNumber(Double.valueOf(result));
        } else {
            jsonGenerator.writeNumber(Double.valueOf(0));
        }
    }
}
