package com.wgy.shop.service;

import com.wgy.shop.entity.Comment;
import com.wgy.shop.entity.CommentExample;

import java.util.List;

public interface CommentService {

    //根据条件查找评论
    public List<Comment> selectByExample(CommentExample ce);
    //添加评论
    public void insertSelective(Comment comment);
}
