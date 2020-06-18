package com.wgy.shop.service;

import com.wgy.shop.entity.Chat;
import com.wgy.shop.entity.ChatExample;

import java.util.List;

public interface ChatService {
    List<Chat> selectChatByExample(ChatExample ce);

    void insertChatSelective(Chat chat);
}
