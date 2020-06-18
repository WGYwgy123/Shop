package com.wgy.shop.service.impl;

import com.wgy.shop.dao.ChatMapper;
import com.wgy.shop.entity.Chat;
import com.wgy.shop.entity.ChatExample;
import com.wgy.shop.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatMapper chatMapper;

    @Override
    public List<Chat> selectChatByExample(ChatExample ce) {
        return chatMapper.selectByExample(ce);
    }

    @Override
    public void insertChatSelective(Chat chat) {
        chatMapper.insertSelective(chat);
    }
}
