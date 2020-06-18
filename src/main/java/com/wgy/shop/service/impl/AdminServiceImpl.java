package com.wgy.shop.service.impl;

import com.wgy.shop.dao.AdminMapper;
import com.wgy.shop.entity.Admin;
import com.wgy.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin selectByName(Admin admin) {
        return adminMapper.selectByName(admin);
    }
}
