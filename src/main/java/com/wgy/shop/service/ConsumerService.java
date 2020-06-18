package com.wgy.shop.service;

import com.wgy.shop.entity.Consumer;
import com.wgy.shop.entity.ConsumerExample;

import java.util.List;

public interface ConsumerService {

    //根据帐号密码查询用户
    public List<Consumer> selectByExample(ConsumerExample ce);

    //插入用户
    public void insertSelective(Consumer consumer);

    public Consumer selectByPrimaryKey(Integer userid);

    public void updateByPrimaryKeySelective(Consumer consumer);

    public void deleteUserById(Integer userid);
}
