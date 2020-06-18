package com.wgy.test;

import com.wgy.shop.dao.ConsumerMapper;
import com.wgy.shop.entity.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


/**
 * 单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DaoTest {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Test
    public void testConsumerInsert(){
        //创建Consumer对象  String username, String password, Date regtime, String email, String telephone
        Consumer consumer = new Consumer("wgy","123",new Date(), "wgy@qq.com","15558733685");
        consumerMapper.insertSelective(consumer);
        System.out.println("插入成功");
    }
}
