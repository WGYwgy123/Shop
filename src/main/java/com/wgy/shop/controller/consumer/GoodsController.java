package com.wgy.shop.controller.consumer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wgy.shop.entity.*;
import com.wgy.shop.service.*;
import com.wgy.shop.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 返回目录下的商品
 */
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    CateService cateService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CommentService commentService;

    @Autowired
    ConsumerService consumerService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detailGoods(Integer goodsid, Model model,
                              HttpSession session){

        //如果商品的id为空则直接返回到主界面
        if(goodsid == null){
            return "redirect:/main";
        }

        Consumer consumer = (Consumer) session.getAttribute("user");
        //要传回的数据存在HashMap中
        Map<String,Object> goodsInfo = new HashMap<String,Object>();

        //查询商品的基本信息
        Goods goods = goodsService.selectById(goodsid);
        //判断用户是否登录
        if(consumer == null){
            goods.setFav(false);
        }else{
            Favorite favorite = goodsService.selectFavByKey(new FavoriteKey(consumer.getUserid(), goodsid));
            if (favorite == null) {
                goods.setFav(false);
            } else {
                goods.setFav(true);
            }
        }
        //查询商品类别
        Category category = cateService.selectById(goods.getCategory());
        //商品图片
        List<ImagePath> imagePath = goodsService.findImagePath(goodsid);
        //商品折扣信息
        Activity activity = activityService.selectByKey(goods.getActivityid());
        goods.setActivity(activity);

        //返回数据
        goodsInfo.put("goods", goods);
        goodsInfo.put("cate", category);
        goodsInfo.put("image", imagePath);
        model.addAttribute("goodsInfo",goodsInfo);

        //创造条件，查询评论信息
        CommentExample ce = new CommentExample();
        ce.or().andGoodsidEqualTo(goods.getGoodsid());
        List<Comment> commentList = commentService.selectByExample(ce);
        for (Integer i=0;i<commentList.size();i++)
        {
            Comment comment=commentList.get(i);
            Consumer commentUser= consumerService.selectByPrimaryKey(comment.getUserid());
            comment.setUsername(commentUser.getUsername());
            commentList.set(i,comment);
        }

        return "detail";
    }

    @RequestMapping("/collect")
    @ResponseBody
    public ResponseMessage collectGoods(Integer goodsid, HttpSession session) {
        //取登录用户信息,未登录重定向至登录页面
        System.out.println(goodsid);
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null) {
            return ResponseMessage.error().addObject("msg","账户未登录，收藏失败,请登录后再试");
        }

        //添加收藏
        Favorite favorite = new Favorite();
        favorite.setCollecttime(new Date());
        favorite.setGoodsid(goodsid);
        favorite.setUserid(consumer.getUserid());

        goodsService.addFavorite(favorite);

        return ResponseMessage.success();
    }

    //删除收藏
    @RequestMapping("/deleteCollect")
    @ResponseBody
    public ResponseMessage deleteFavGoods(Integer goodsid, HttpSession session) {
        Consumer consumer = (Consumer) session.getAttribute("user");

        //删除收藏
        goodsService.deleteFavByKey(new FavoriteKey(consumer.getUserid(),goodsid));

        return ResponseMessage.success();
    }

    //查找商品
    @RequestMapping("/category")
    public String getCatGoods(String cate, Model model, HttpSession session,
                              @RequestParam(value = "page",defaultValue = "1") Integer pn){
        //从session中获取用户信息
        Consumer consumer = (Consumer) session.getAttribute("user");

        //使用分页插件完成分页
        PageHelper.startPage(pn, 16);

        //查询分类id
        CategoryExample ce = new CategoryExample();
        ce.or().andCatenameLike(cate);
        List<Category> categoryList = cateService.selectByExample(ce);

        //获取查出的目录id
        List<Integer> cateId = new ArrayList<Integer>();
        for (Category category : categoryList) {
            cateId.add(category.getCateid());
        }
        //查询数据
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.or().andDetailcateLike("%" + cate + "%");
        if (!cateId.isEmpty()) {
            goodsExample.or().andCategoryIn(cateId);
        }
        List<Goods> goodsList = goodsService.selectByExample(goodsExample);

        //获取图片地址
        for (int i = 0; i < goodsList.size(); i++) {
            Goods goods = goodsList.get(i);

            List<ImagePath> imagePathList = goodsService.findImagePath(goods.getGoodsid());

            goods.setImagePaths(imagePathList);

            //判断是否收藏
            if (consumer == null) {
                goods.setFav(false);
            } else {
                Favorite favorite = goodsService.selectFavByKey(new FavoriteKey(consumer.getUserid(), goods.getGoodsid()));
                if (favorite == null) {
                    goods.setFav(false);
                } else {
                    goods.setFav(true);
                }
            }

            goodsList.set(i, goods);
        }

        //显示几个页号
        PageInfo page = new PageInfo(goodsList,5);
        model.addAttribute("pageInfo", page);
        model.addAttribute("cate", cate);
        return "category";
    }


    //查找功能
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchGoods(@RequestParam(value = "page",defaultValue = "1") Integer pn,
                              String keyword, Model model, HttpSession session) {
        Consumer consumer = (Consumer) session.getAttribute("user");

        //一页显示几个数据
        PageHelper.startPage(pn, 16);

        //查询数据
        GoodsExample ge = new GoodsExample();
        ge.or().andGoodsnameLike("%" + keyword + "%");
        List<Goods> goodsList = goodsService.selectByExample(ge);

        //获取图片地址
        for (int i = 0; i < goodsList.size(); i++) {
            Goods goods = goodsList.get(i);

            List<ImagePath> imagePathList = goodsService.findImagePath(goods.getGoodsid());

            goods.setImagePaths(imagePathList);

            //判断是否收藏
            if (consumer == null) {
                goods.setFav(false);
            } else {
                Favorite favorite = goodsService.selectFavByKey(new FavoriteKey(consumer.getUserid(), goods.getGoodsid()));
                if (favorite == null) {
                    goods.setFav(false);
                } else {
                    goods.setFav(true);
                }
            }

            goodsList.set(i, goods);
        }


        //显示几个页号
        PageInfo page = new PageInfo(goodsList,5);
        model.addAttribute("pageInfo", page);
        model.addAttribute("keyword", keyword);

        return "search";
    }


    //添加评论
    @RequestMapping("/comment")
    @ResponseBody
    public ResponseMessage comment(Comment comment,HttpSession session){
        Consumer consumer=(Consumer) session.getAttribute("user");
        if (consumer == null) {
            return ResponseMessage.error().addObject("msg","评论失败");
        }
        comment.setUserid(consumer.getUserid());
        Date date=new Date();
        comment.setCommenttime(date);
        commentService.insertSelective(comment);
        return ResponseMessage.success();
    }
}
