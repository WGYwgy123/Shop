package com.wgy.shop.service.impl;

import com.wgy.shop.dao.AddressMapper;
import com.wgy.shop.dao.OrderItemMapper;
import com.wgy.shop.dao.OrderMapper;
import com.wgy.shop.entity.*;
import com.wgy.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void insertOrder(Order order) {
        orderMapper.insertSelective(order);
        //System.out.println(order);
    }

    @Override
    public void insertOrderItem(OrderItem orderItem) {
        orderItemMapper.insertSelective(orderItem);
    }

    @Override
    public List<Order> selectOrderByExample(OrderExample oe) {
        return orderMapper.selectByExample(oe);
    }

    @Override
    public List<OrderItem> getOrderItemByExample(OrderItemExample oie) {
        return orderItemMapper.selectByExample(oie);
    }

    @Override
    public void deleteById(Integer orderid) {
        orderMapper.deleteByPrimaryKey(orderid);
    }

    @Override
    public Order selectByPrimaryKey(Integer orderid) {
        return orderMapper.selectByPrimaryKey(orderid);
    }

    @Override
    public void updateOrderByKey(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Address getAddressByKey(Integer addressid) {
        return addressMapper.selectByPrimaryKey(addressid);
    }
}
