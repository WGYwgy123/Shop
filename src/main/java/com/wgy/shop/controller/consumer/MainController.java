package com.wgy.shop.controller.consumer;

import com.wgy.shop.entity.*;
import com.wgy.shop.service.CateService;
import com.wgy.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页面控制器
 */
@Controller
public class MainController {

    @Autowired
    private CateService cateService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 加载主界面的所有商品
     * @param model 定义模型
     * @param session 定义会话
     * @return 返回到主界面
     */
    @RequestMapping("/main")
    public String showAllGoods(Model model, HttpSession session){
        //通过session获取id
        Integer consumerId;
        Consumer consumer = (Consumer) session.getAttribute("consumer");
        if(consumer == null){
            consumerId = null;
        }else{
            consumerId = consumer.getUserid();
        }

        //数码分类
        List<Goods> digGoods = getCateGoods("数码", consumerId);
        //将商品分类加入到模型中
        model.addAttribute("digGoods", digGoods);

        //家电
        List<Goods> houseGoods = getCateGoods("家电", consumerId);
        model.addAttribute("houseGoods", houseGoods);

        //服饰
        List<Goods> colGoods = getCateGoods("服饰", consumerId);
        model.addAttribute("colGoods", colGoods);

        //书籍
        List<Goods> bookGoods = getCateGoods("书籍", consumerId);
        model.addAttribute("bookGoods", bookGoods);

        return "main";
    }

    /**
     * 返回查询到的目录
     * @param cate 传入的目录
     * @param consumerId 用户的Id
     * @return 返回查询到的目录信息
     */
    private List<Goods> getCateGoods(String cate, Integer consumerId) {
        //创造查询目录的条件
        CategoryExample ce = new CategoryExample();
        //or()方法，会产生一个新的Criteria对象
        ce.or().andCatenameLike(cate);
        List<Category> categoryList = cateService.selectByExample(ce);

        //判断返回的结果
        if(categoryList.size() == 0){
            return null;
        }

        //查询刚才查到的目录内商品
        GoodsExample ge = new GoodsExample();
        List<Integer> CateId = new ArrayList<Integer>();
        for (Category category:categoryList) {
            //存放目录的Id
            CateId.add(category.getCateid());
        }

        ge.or().andCategoryIn(CateId);
        //返回10条商品记录
        List<Goods> goodsList = goodsService.selectByExampleLimit(ge);

        List<Goods> goodsAndImage = new ArrayList<Goods>();
        //获取商品的图片
        for(Goods goods:goodsList){
            //判断用户的登录状态
            if(consumerId == null){
                goods.setFav(false);
            }else{
                //登录情况下查找用户的收藏夹
                Favorite favorite = goodsService.selectFavByKey(new FavoriteKey(consumerId, goods.getGoodsid()));
                if (favorite == null) {
                    goods.setFav(false);
                } else {
                    goods.setFav(true);
                }
            }

            List<ImagePath> imagePathList = goodsService.findImagePath(goods.getGoodsid());
            goods.setImagePaths(imagePathList);
            goodsAndImage.add(goods);
        }
        return goodsAndImage;
    }
}
