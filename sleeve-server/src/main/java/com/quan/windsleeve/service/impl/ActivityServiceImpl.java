package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.Activity;
import com.quan.windsleeve.repository.ActivityRepository;
import com.quan.windsleeve.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * 通过活动名获取一条完整的activity数据
     * @param name
     * @return
     */
    @Override
    public Activity findActivityByName(String name) {
        return activityRepository.findOneByName(name);
    }
}
