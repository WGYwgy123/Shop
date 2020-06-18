package com.wgy.shop.service.impl;

import com.wgy.shop.dao.ConsumerMapper;
import com.wgy.shop.entity.Consumer;
import com.wgy.shop.entity.ConsumerExample;
import com.wgy.shop.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 通过条件查询用户
     * @param ce
     * @return
     */
    @Override
    public List<Consumer> selectByExample(ConsumerExample ce) {
        //System.out.println(consumerMapper.selectByExample(ce));
        return consumerMapper.selectByExample(ce);
    }

    /**
     * 注册用户
     * @param consumer
     */
    @Override
    public void insertSelective(Consumer consumer) {
        consumerMapper.insertSelective(consumer);
    }

    /**
     * 根据用户id查询用户
     * @param userid
     * @return
     */
    @Override
    public Consumer selectByPrimaryKey(Integer userid) {
        return consumerMapper.selectByPrimaryKey(userid);
    }

    @Override
    public void updateByPrimaryKeySelective(Consumer consumer) {
        consumerMapper.updateByPrimaryKeySelective(consumer);
    }

    @Override
    public void deleteUserById(Integer userid) {
        consumerMapper.deleteByPrimaryKey(userid);
    }
}
