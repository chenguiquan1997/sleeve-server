package com.quan.windsleeve.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quan.windsleeve.exception.http.ObjectConvertException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.List;

/**
 * 泛型中的两个参数，第一个表示转换成实体类中的属性时，需要转换成的类型，这里为List<Object>
 * 第二个参数表示转换成数据库属性时，需要转换成的类型，这里为String
 */
@Convert
public class ListAndJson implements AttributeConverter<List<Object>,String> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Object> objects) {
        try {
            if( objects == null) {
                return null;
            }
            return objectMapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //在Java中，如果一个方法声明了返回值类型，那么当前方法必须要返回数据。如果在方法中抛出一个
            //异常以后，那么他就可以不需要返回数据
            throw new ObjectConvertException(70000);
        }
    }

    @Override
    public List<Object> convertToEntityAttribute(String s) {
        try {
            if(s == null) {
                return null;
            }
            return objectMapper.readValue(s,List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ObjectConvertException(70000);
        }
    }
}
