package com.wgy.shop.service;

import com.wgy.shop.entity.Category;
import com.wgy.shop.entity.CategoryExample;

import java.util.List;

public interface CateService {
    //根据目录返回目录集合
    public List<Category> selectByExample(CategoryExample ce);
    //根据目录id返回目录
    public Category selectById(Integer category);

    public void deleteByPrimaryKey(Integer cateid);

    public void updateByPrimaryKeySelective(Category category);

    public void insertSelective(Category category);
}
