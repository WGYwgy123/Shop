package com.wgy.shop.controller.consumer;

import com.wgy.shop.entity.*;
import com.wgy.shop.service.GoodsService;
import com.wgy.shop.service.ShopCartService;
import com.wgy.shop.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.caucho.HessianClientInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    ShopCartService shopCartService;

    @Autowired
    GoodsService goodsService;

    //将商品添加到购物车
    @RequestMapping("/addCart")
    public String addCart(ShopCart shopCart, HttpSession session) {
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null) {
            return "redirect:/login";
        }

        //判断是否已经加入购物车
        ShopCart shopCart1 = shopCartService.selectCartByKey(new ShopCartKey(consumer.getUserid(), shopCart.getGoodsid()));
        if (shopCart1 != null) {
            return "redirect:/showcart";
        }

        //用户
        shopCart.setUserid(consumer.getUserid());

        //加入时间
        shopCart.setCatedate(new Date());

        shopCartService.addShopCart(shopCart);

        //返回到购物车页面
        return "redirect:/showcart";
    }

    /*@RequestMapping(value = "/addCart")
    @ResponseBody
    public ResponseMessage addCart(ShopCart shopCart, HttpSession session){
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return ResponseMessage.error().addObject("msg","请先登录");
        }
        //判断是否已经加入购物车
        ShopCart sc = shopCartService.selectCartByKey(new ShopCartKey(consumer.getUserid(), shopCart.getGoodsid()));
        if(sc != null){
            //如果不为空直接跳转到购物车界面，不往里面添加
            return ResponseMessage.error().addObject("msg","购物车中已有该商品");
        }
        //设置用户和时间
        shopCart.setUserid(consumer.getUserid());
        shopCart.setGoodsid(shopCart.getGoodsid());
        shopCart.setCatedate(new Date());

        System.out.println(shopCart.getGoodsid()+" "+shopCart.getUserid());
        shopCartService.addShopCart(shopCart);
        return ResponseMessage.success();
    }*/
    //跳转到购物车
    @RequestMapping("/showcart")
    public String showCart(HttpSession session) {
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return "redirect:/login";
        }
        return "shopcart";
    }

   /* @RequestMapping("/showcart2")
    @ResponseBody
    public ResponseMessage showCart2(HttpSession session) {
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return ResponseMessage.error().addObject("msg","请登录后再试");
        }
        return ResponseMessage.success();
    }*/

    @RequestMapping("/cartjson")
    @ResponseBody
    //获取用户的购物车
    public ResponseMessage getCart(HttpSession session){
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return ResponseMessage.error().addObject("msg","请先登录");
        }

        //获取当前用户的购物车信息
        ShopCartExample sce = new ShopCartExample();
        sce.or().andUseridEqualTo(consumer.getUserid());
        List<ShopCart> shopCart = shopCartService.selectByExample(sce);

        //从集合中获取到购物车中的商品信息
        List<Goods> goodsAndImage = new ArrayList<Goods>();
        for (ShopCart cart:shopCart) {
            //通过id获取商品
            Goods goods = goodsService.selectById(cart.getGoodsid());

            List<ImagePath> imagePathList = goodsService.findImagePath(goods.getGoodsid());
            goods.setImagePaths(imagePathList);
            goods.setNum(cart.getGoodsnum());
            goodsAndImage.add(goods);
        }
        return ResponseMessage.success().addObject("msg",goodsAndImage);
    }

    //删除购物车的商品
    @RequestMapping(value = "/deleteCart/{goodsid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage deleteCart(@PathVariable("goodsid")Integer goodsid, HttpSession session) {
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null) {
            return ResponseMessage.error().addObject("msg","请先登录");
        }

        shopCartService.deleteByKey(new ShopCartKey(consumer.getUserid(), goodsid));
        return ResponseMessage.success();
    }

    //更新购物车
    @RequestMapping("/update")
    @ResponseBody
    public ResponseMessage updateCart(Integer goodsid,Integer num,HttpSession session) {
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null) {
            return ResponseMessage.error().addObject("msg","请先登录");
        }

        ShopCart shopCart = new ShopCart();
        shopCart.setUserid(consumer.getUserid());
        shopCart.setGoodsid(goodsid);
        shopCart.setGoodsnum(num);
        shopCartService.updateCartByKey(shopCart);
        return ResponseMessage.success();
    }
}
