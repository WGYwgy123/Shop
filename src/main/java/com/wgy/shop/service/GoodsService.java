package com.wgy.shop.service;

import com.wgy.shop.entity.*;

import java.util.List;

public interface GoodsService {

    //根据目录Id查找商品
    public List<Goods> selectByExampleLimit(GoodsExample ge);
    //获取用户的收藏夹
    public Favorite selectFavByKey(FavoriteKey favoriteKey);

    public List<Goods> selectByExample(GoodsExample ge);
    //根据商品id获取到图片的路径
    List<ImagePath> findImagePath(Integer goodsid);
    //根据id查找商品
    public Goods selectById(Integer goodsid);
    //添加到收藏夹
    public void addFavorite(Favorite favorite);
    //从收藏夹中删除
    public void deleteFavByKey(FavoriteKey favoriteKey);
    //查询收藏夹的信息
    public List<Favorite> selectFavByExample(FavoriteExample fe);

    public void addImagePath(ImagePath imagePath);

    public void deleteGoodsById(Integer goodsid);

    public void updateGoodsById(Goods goods);

    public Integer addGoods(Goods goods);
}
