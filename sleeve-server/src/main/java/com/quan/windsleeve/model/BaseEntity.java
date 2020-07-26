package com.quan.windsleeve.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
//当一个类定义为基类时，如果其他类引用了它，在序列化时，可能不能被序列化，需要添加该注解
@MappedSuperclass
public abstract class BaseEntity {

    //添加完该注解后，表示在进行序列化时，不会将当前字段序列化
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;
}
