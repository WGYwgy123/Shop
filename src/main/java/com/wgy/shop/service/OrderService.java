package com.wgy.shop.service;

import com.wgy.shop.entity.*;

import java.util.List;

public interface OrderService {
    //提交订单
    public void insertOrder(Order order);
    //将信息插入到orderitem中
    public void insertOrderItem(OrderItem orderItem);
    //查找订单信息
    public List<Order> selectOrderByExample(OrderExample oe);
    //拿到订单详情
    public List<OrderItem> getOrderItemByExample(OrderItemExample oie);
    //删除订单
    public void deleteById(Integer orderid);
    //根据主键查询信息
    public Order selectByPrimaryKey(Integer orderid);
    //更新订单信息
    public void updateOrderByKey(Order order);

    public Address getAddressByKey(Integer addressid);
}
