package com.wgy.shop.service.impl;

import com.wgy.shop.dao.CategoryMapper;
import com.wgy.shop.entity.Category;
import com.wgy.shop.entity.CategoryExample;
import com.wgy.shop.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateServiceImpl implements CateService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> selectByExample(CategoryExample ce) {
        return categoryMapper.selectByExample(ce);
    }

    @Override
    public Category selectById(Integer category) {
        return categoryMapper.selectByPrimaryKey(category);
    }

    @Override
    public void deleteByPrimaryKey(Integer cateid) {
        categoryMapper.deleteByPrimaryKey(cateid);
    }

    @Override
    public void updateByPrimaryKeySelective(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void insertSelective(Category category) {
        categoryMapper.insertSelective(category);
    }
}
