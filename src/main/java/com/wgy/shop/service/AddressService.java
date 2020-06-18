package com.wgy.shop.service;

import com.wgy.shop.entity.Address;
import com.wgy.shop.entity.AddressExample;

import java.util.List;

public interface AddressService {
    //查询当前用户的所有收货地址
    public List<Address> getAllAddressByExample(AddressExample ae);

    //更新收货地址
    public void updateByPrimaryKeySelective(Address address);

    //删除收货地址
    public void deleteByPrimaryKey(Integer addressid);

    //插入收货地址
    public void insertSelective(Address address);
    //根据id查询地址
    public Address selectByPrimaryKey(Integer addressid);
}
