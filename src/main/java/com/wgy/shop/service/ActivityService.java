package com.wgy.shop.service;

import com.wgy.shop.entity.Activity;
import com.wgy.shop.entity.ActivityExample;

import java.util.List;

public interface ActivityService {
    //根据活动的id返回活动信息
    public Activity selectByKey(Integer activityid);

    public void deleteByActivityId(Integer activityid);

    public void insertActivitySelective(Activity activity);

    public List<Activity> getAllActivity(ActivityExample activityExample);
}
