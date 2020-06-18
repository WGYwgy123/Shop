package com.wgy.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:springMVC.xml"})
//指定基于web的测试
@WebAppConfiguration
public class WebTest {

    //模仿浏览器发送请求，声明一个模拟请求的对象
    private MockMvc mockMvc;
    //需要一个web容器
    @Autowired
    private WebApplicationContext context;

    //构建一个前置方法
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testLogin() throws Exception {
        //通过perform发送请求
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/main")
                .param("username", "wgy")
                .param("password", "123")).andReturn();

       /* 找不到javax.servlet.http.HttpServletResponse的类文件
          是因为tomcat没配合，容器依赖没加载（jsp/servlet）
        */
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
