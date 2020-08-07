package com.quan.windsleeve.vo;

import com.quan.windsleeve.model.Activity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
public class ActivityPureVO {

    private Long id;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;

    public ActivityPureVO(Activity activity) {
        BeanUtils.copyProperties(activity,this);
    }
}
