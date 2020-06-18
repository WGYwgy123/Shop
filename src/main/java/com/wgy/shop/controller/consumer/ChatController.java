package com.wgy.shop.controller.consumer;

import com.wgy.shop.entity.*;
import com.wgy.shop.service.ChatService;
import com.wgy.shop.service.ConsumerService;
import com.wgy.shop.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    ConsumerService consumerService;

    @RequestMapping("/chat")
    public String showChat(HttpSession session, Model model, Integer sendto) {
        Consumer loginuser = (Consumer) session.getAttribute("user");
        if (loginuser == null) {
            return "redirect:/login";
        }
        /*//查询历史消息聊天对象
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        ChatExample chatExample = new ChatExample();
        chatExample.or().andReceiveuserEqualTo(user.getUserid());
//        chatExample.or().andSenduserEqualTo(user.getUserid());
//        chatExample.setOrderByClause("MsgTime asc");
        List<Chat> chatList1 = chatService.selectChatByExample(chatExample);

        ChatExample chatExample2 = new ChatExample();
//        chatExample.or().andReceiveuserEqualTo(user.getUserid());
        chatExample2.or().andSenduserEqualTo(user.getUserid());
//        chatExample.setOrderByClause("MsgTime asc");
        List<Chat> chatList2 = chatService.selectChatByExample(chatExample2);

        //获取userid列表
        List<Integer> useridList = new ArrayList<>();
        for (Chat chat : chatList1) {
            useridList.add(chat.getSenduser());
        }
        for (Chat chat : chatList2) {
            useridList.add(chat.getReceiveuser());
        }

        //获取用户信息
        UserExample userExample = new UserExample();
        userExample.or().andUseridIn(useridList);
        List<User> userList = userService.selectByExample(userExample);
        model.addAttribute("chatuserlist", userList);*/

        if (sendto != null) {
            Consumer user = consumerService.selectByPrimaryKey(sendto);
            model.addAttribute("sendto", user);
        }
        return "chat";
    }


    @RequestMapping("/chatto")
    @ResponseBody
    public ResponseMessage getChatTo(HttpSession session, Model model, Integer sendto) {
        //查询历史消息聊天对象
        Consumer consumer = (Consumer) session.getAttribute("user");
        if (consumer == null) {
            return ResponseMessage.error().addObject("msg","未登录");
        }
        ChatExample ce = new ChatExample();
        ce.or().andReceiveuserEqualTo(consumer.getUserid());
//        chatExample.or().andSenduserEqualTo(user.getUserid());
//        chatExample.setOrderByClause("MsgTime asc");
        List<Chat> chatList1 = chatService.selectChatByExample(ce);

        ChatExample ce2 = new ChatExample();
//        chatExample.or().andReceiveuserEqualTo(user.getUserid());
        ce2.or().andSenduserEqualTo(consumer.getUserid());
//        chatExample.setOrderByClause("MsgTime asc");
        List<Chat> chatList2 = chatService.selectChatByExample(ce2);

        //获取userid列表
        List<Integer> useridList = new ArrayList<Integer>();
        for (Chat chat : chatList1) {
            useridList.add(chat.getSenduser());
        }
        for (Chat chat : chatList2) {
            useridList.add(chat.getReceiveuser());
        }

        if (sendto != null) {
            useridList.add(sendto);
        }

        //获取用户信息
        ConsumerExample ue = new ConsumerExample();
        ue.or().andUseridIn(useridList);
        List<Consumer> userList = consumerService.selectByExample(ue);

        return ResponseMessage.success().addObject("userlist",userList);
    }


    @RequestMapping("/getMessage")
    @ResponseBody
    public ResponseMessage getMessageInfo(Integer senduser, Integer receiveuser, HttpSession session) {

        Consumer consumer = (Consumer) session.getAttribute("user");
        if (consumer == null) {
            return ResponseMessage.error().addObject("msg","未登录");
        }

        ChatExample chatExample = new ChatExample();
        chatExample.or().andReceiveuserEqualTo(senduser).andSenduserEqualTo(receiveuser);
        chatExample.or().andSenduserEqualTo(senduser).andReceiveuserEqualTo(receiveuser);
        chatExample.setOrderByClause("MsgTime asc");
        List<Chat> chatList = chatService.selectChatByExample(chatExample);

        return ResponseMessage.success().addObject("message", chatList);
    }

    @RequestMapping("/admin/chat")
    public String frontChat(Integer sendto, Model model, HttpSession session) {

        Admin adminuser = (Admin) session.getAttribute("admin");
        if (adminuser == null) {
            return "redirect:/admin/login";
        }

        if (sendto != null) {
            Consumer consumer = consumerService.selectByPrimaryKey(sendto);
            model.addAttribute("sendto", consumer);
        }
        return "admin/adminChat";
    }

    @RequestMapping("/adminchat")
    @ResponseBody
    public ResponseMessage adminChat(HttpSession session, Model model, Integer sendto) {

        //查询历史消息聊天对象
        Admin adminuser = (Admin) session.getAttribute("admin");
        if (adminuser == null) {
            return ResponseMessage.error().addObject("msg","请先登录");
        }
        Integer userid = 5;
        ChatExample chatExample = new ChatExample();
        chatExample.or().andReceiveuserEqualTo(userid);
//        chatExample.or().andSenduserEqualTo(user.getUserid());
//        chatExample.setOrderByClause("MsgTime asc");
        List<Chat> chatList1 = chatService.selectChatByExample(chatExample);

        ChatExample chatExample2 = new ChatExample();
//        chatExample.or().andReceiveuserEqualTo(user.getUserid());
        chatExample2.or().andSenduserEqualTo(userid);
//        chatExample.setOrderByClause("MsgTime asc");
        List<Chat> chatList2 = chatService.selectChatByExample(chatExample2);

        //获取userid列表
        List<Integer> useridList = new ArrayList<Integer>();
        for (Chat chat : chatList1) {
            useridList.add(chat.getSenduser());
        }
        for (Chat chat : chatList2) {
            useridList.add(chat.getReceiveuser());
        }

        if (sendto != null) {
            useridList.add(sendto);
        }

        //获取用户信息
        ConsumerExample userExample = new ConsumerExample();
        userExample.or().andUseridIn(useridList);
        List<Consumer> userList = consumerService.selectByExample(userExample);
//        model.addAttribute("chatuserlist", userList);
        return ResponseMessage.success().addObject("userlist",userList);
    }

    @RequestMapping("/sendMessage")
    @ResponseBody
    public ResponseMessage saveMessage(Chat chat) {
//        System.out.println(chat.getMsgcontent());
        chat.setMsgtime(new Date());
        chatService.insertChatSelective(chat);
        return ResponseMessage.success();
    }

    @RequestMapping("/chatrobot")
    public String showChatRobot() {
        return "chatrobot";
    }
}
