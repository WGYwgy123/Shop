package com.wgy.shop.service.impl;

import com.wgy.shop.dao.CommentMapper;
import com.wgy.shop.entity.Comment;
import com.wgy.shop.entity.CommentExample;
import com.wgy.shop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> selectByExample(CommentExample ce) {
        return commentMapper.selectByExample(ce);
    }

    @Override
    public void insertSelective(Comment comment) {
        commentMapper.insertSelective(comment);
    }
}
