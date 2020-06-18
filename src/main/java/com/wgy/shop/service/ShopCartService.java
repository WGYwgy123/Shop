package com.wgy.shop.service;

import com.wgy.shop.entity.ShopCart;
import com.wgy.shop.entity.ShopCartExample;
import com.wgy.shop.entity.ShopCartKey;

import java.util.List;

public interface ShopCartService {

    //通过商品和用户的id号来查找是否已经在购物车
    public ShopCart selectCartByKey(ShopCartKey shopCartKey);

    //添加到购物车
    public void addShopCart(ShopCart shopCart);

    //查找用户的购物车信息
    public List<ShopCart> selectByExample(ShopCartExample sce);

    //根据用户和商品的id来删除购物车的商品
    public void deleteByKey(ShopCartKey shopCartKey);

    public void updateCartByKey(ShopCart shopCart);
}
