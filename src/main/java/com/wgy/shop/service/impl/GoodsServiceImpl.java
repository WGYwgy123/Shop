package com.wgy.shop.service.impl;

import com.wgy.shop.dao.FavoriteMapper;
import com.wgy.shop.dao.GoodsMapper;
import com.wgy.shop.dao.ImagePathMapper;
import com.wgy.shop.entity.*;
import com.wgy.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    ImagePathMapper imagePathMapper;

    @Override
    public List<Goods> selectByExample(GoodsExample ge) {
        return goodsMapper.selectByExampleWithBLOBs(ge);
    }

    /**
     * 查询商品的图片路径
     * @param goodsid 传入商品id
     * @return 返回查询结果
     */
    @Override
    public List<ImagePath> findImagePath(Integer goodsid) {
        //创造查询条件
        ImagePathExample ipe = new ImagePathExample();
        ipe.or().andGoodidEqualTo(goodsid);
        return imagePathMapper.selectByExample(ipe);
    }

    /**
     * 通过主键商品id来查询
     * @param goodsid 传入商品id
     * @return 返回商品信息
     */
    @Override
    public Goods selectById(Integer goodsid) {
        return goodsMapper.selectByPrimaryKey(goodsid);
    }

    @Override
    public void addFavorite(Favorite favorite) {
        favoriteMapper.insertSelective(favorite);
    }

    @Override
    public void deleteFavByKey(FavoriteKey favoriteKey) {
        favoriteMapper.deleteByPrimaryKey(favoriteKey);
    }

    @Override
    public List<Favorite> selectFavByExample(FavoriteExample fe) {
        return favoriteMapper.selectByExample(fe);
    }

    @Override
    public void addImagePath(ImagePath imagePath) {
        imagePathMapper.insertSelective(imagePath);
    }

    @Override
    public void deleteGoodsById(Integer goodsid) {
        goodsMapper.deleteByPrimaryKey(goodsid);
    }

    @Override
    public void updateGoodsById(Goods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public Integer addGoods(Goods goods) {
        goodsMapper.insertSelective(goods);
        return goods.getGoodsid();
    }

    /**
     * 查找商品
     * @param ge 传入目录的Id
     * @return 返回查询到的10条商品记录
     */
    @Override
    public List<Goods> selectByExampleLimit(GoodsExample ge) {
        return goodsMapper.selectByExampleWithBLOBsLimit(ge);
    }

    /**
     * 查询用户的收藏夹
     * @param favoriteKey 传入favoriteKey
     * @return 返回查询到的收藏夹
     */
    @Override
    public Favorite selectFavByKey(FavoriteKey favoriteKey) {
        return favoriteMapper.selectByPrimaryKey(favoriteKey);
    }
}
