package com.wgy.shop.controller.consumer;

import com.wgy.shop.entity.*;
import com.wgy.shop.service.*;
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
public class OrderController {

    @Autowired
    AddressService addressService;

    @Autowired
    ShopCartService shopCartService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ActivityService activityService;

    @Autowired
    OrderService orderService;

    //提交订单
    @RequestMapping(value = "/order")
    public String showOrder(HttpSession session, Model model){

        //判断用户是否已经登录
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return "redirect:/login";
        }

        //查询用户的收货地址
        AddressExample ae = new AddressExample();
        ae.or().andUseridEqualTo(consumer.getUserid());
        List<Address> addressList = addressService.getAllAddressByExample(ae);
        //添加到model
        model.addAttribute("address",addressList);

        //获取当前用户的购物车信息
        ShopCartExample sce = new ShopCartExample();
        sce.or().andUseridEqualTo(consumer.getUserid());
        List<ShopCart> shopCart = shopCartService.selectByExample(sce);

        List<Goods> goodsAndImage = new ArrayList<Goods>();
        //定义初值
        Float totalPrice = 0.0f;
        Integer oldTotalPrice = 0;

        for (ShopCart cart:shopCart) {
            //根据购物车的商品id号找到商品和图片路径
            Goods goods = goodsService.selectById(cart.getGoodsid());
            List<ImagePath> imagePathList = goodsService.findImagePath(goods.getGoodsid());
            goods.setImagePaths(imagePathList);
            goods.setNum(cart.getGoodsnum());

            //获取当前的活动信息
            Activity activity = activityService.selectByKey(goods.getActivityid());
            goods.setActivity(activity);

            //根据活动信息的折扣来计算最终的价钱
            if(activity.getDiscount() != 1){
                goods.setNewPrice(goods.getPrice()*goods.getNum()* activity.getDiscount());
            }else if(activity.getFullnum() != null){
                //满减活动
                if (goods.getNum() >= activity.getFullnum()) {
                    goods.setNewPrice((float) (goods.getPrice()*(goods.getNum()-activity.getReducenum())));
                } else {
                    goods.setNewPrice((float) (goods.getPrice()*goods.getNum()));
                }
            }else{
                //无折扣
                goods.setNewPrice((float) (goods.getPrice()*goods.getNum()));
            }
            //将价格赋值并且用model返回给前端
            totalPrice = totalPrice + goods.getNewPrice();
            oldTotalPrice = oldTotalPrice + goods.getNum() * goods.getPrice();
            goodsAndImage.add(goods);
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("oldTotalPrice", oldTotalPrice);
        model.addAttribute("goodsAndImage", goodsAndImage);

        return "orderConfirm";
    }

    //结算
    @RequestMapping("/orderFinish")
    @ResponseBody
    public ResponseMessage orderFinish(Float oldPrice, Float newPrice, Boolean isPay, Integer addressid, HttpSession session){

        Consumer consumer = (Consumer) session.getAttribute("user");

        //构造条件 获取订单信息
        ShopCartExample sce = new ShopCartExample();
        sce.or().andUseridEqualTo(consumer.getUserid());
        List<ShopCart> shopCarts = shopCartService.selectByExample(sce);

        //因为是清空购物车，所以直接删除购物车信息
        for (ShopCart shopCart : shopCarts) {
            shopCartService.deleteByKey(new ShopCartKey(consumer.getUserid(),shopCart.getGoodsid()));
        }

        //提交订单信息
        //System.out.println(oldPrice);
        Order order = new Order(null, consumer.getUserid(), new Date(), oldPrice, newPrice, isPay, false, false, false, addressid,null,null);
        orderService.insertOrder(order);
        //获取自增的主键
        Integer orderId = order.getOrderid();
        //System.out.println(orderId);

        //将订单id写入到orderitem表中
        for (ShopCart shopCart:shopCarts) {
            orderService.insertOrderItem(new OrderItem(null, orderId, shopCart.getGoodsid(), shopCart.getGoodsnum()));
        }
        //System.out.println("写入成功");
        return ResponseMessage.success();
    }
}
