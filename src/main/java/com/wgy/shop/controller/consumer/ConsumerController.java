package com.wgy.shop.controller.consumer;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wgy.shop.entity.*;
import com.wgy.shop.service.*;
import com.wgy.shop.utils.MailTest;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消费者控制器
 */
@Controller
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    AddressService addressService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    private CommentService commentService;

    //返回login页面
    @RequestMapping(value = "/login")
    public String loginView(){
        return "login";
    }

    //返回register页面
    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    //登录验证
    @RequestMapping(value = "/loginconfirm")
    @ResponseBody
    public ResponseMessage loginConfirm(Consumer consumer,
                               HttpServletRequest request,
                               @RequestParam("confirmlogo") String confirmlogo){

        //获取会话
        HttpSession session = request.getSession();
        //获取验证码
        String certCode = (String) session.getAttribute("certCode");
        //判断验证码是否正确
        if(!confirmlogo.equals(certCode)){
            return ResponseMessage.error().addObject("msg","验证码错误");
        }
        List<Consumer> consumerList = new ArrayList<Consumer>();
        //创建查询条件
        ConsumerExample ce = new ConsumerExample();
        ce.or().andUsernameEqualTo(consumer.getUsername())
                .andPasswordEqualTo(consumer.getPassword());
        //查询用户  记住重构空参方法
        System.out.println(consumer.getUsername()+" "+consumer.getPassword());
        consumerList = consumerService.selectByExample(ce);
        //判断是否查询得到用户
        if(!consumerList.isEmpty()){
            session.setAttribute("user", consumerList.get(0));
            return ResponseMessage.success();
        }else {
            return ResponseMessage.error().addObject("msg","帐号或者密码有误，请重新登录");
        }
    }

    //注册
    @RequestMapping(value = "/registerresult")
    @ResponseBody
    public ResponseMessage registerResult(Consumer consumer, String confirmPassword, String emailcode,
                                          HttpSession session){
        List<Consumer> consumerList = new ArrayList<Consumer>();
        //创建条件
        ConsumerExample ce = new ConsumerExample();
        ce.or().andUsernameLike(consumer.getUsername());
        consumerList = consumerService.selectByExample(ce);

        //判断用户名是否已经存在
        if(!consumerList.isEmpty()){
            return ResponseMessage.error().addObject("msg","用户名被占用");
        }

        //判断名字是否符合标准
        String regxname = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\u2e80-\u9FFF]{2,5})";
        if(!consumer.getUsername().matches(regxname)){
            return ResponseMessage.error().addObject("msg","用户名必须是2-5位中文或者3-16位英文和数字的组合");
        }

        //判断密码是否符合标准
        String regxpasswd = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        String test = "wgysgdtc123";
        if(!consumer.getPassword().matches(regxpasswd)){
            return ResponseMessage.error().addObject("msg","密码由数字和字母组成,且长度要在6-16位之间");
        }

        //判断密码是否统一
        if(!consumer.getPassword().matches(confirmPassword)){
            return ResponseMessage.error().addObject("msg","两次密码不相同，请确认");
        }

        //判断邮箱是否校验
        String emailconfirm = (String) session.getAttribute("emailconfirm");
        if(!emailcode.equals(emailconfirm)){
            return ResponseMessage.error().addObject("msg","验证码错误，请重试");
        }

        //判断手机号码格式
        String regxtel = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
        if(!consumer.getTelephone().matches(regxtel)){
            return ResponseMessage.error().addObject("msg","手机号码格式不正确");
        }
        Date RegTime = new Date();
        consumer.setRegtime(RegTime);
        consumerService.insertSelective(consumer);
        return  ResponseMessage.success();
    }

    /**
     * 校验验证码是否正确
     * @param username 传入用户名
     * @param email 传入邮箱
     * @param session 使用会话
     * @return 返回结果
     */
    @RequestMapping(value = "/registerconfirm")
    @ResponseBody
    public ResponseMessage registerConfirm(String username, String email,
                                           HttpSession session){
        char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        //判断邮箱是否存在
        List<Consumer> consumerList = new ArrayList<Consumer>();

        ConsumerExample ce2 = new ConsumerExample();
        ce2.or().andEmailLike(email);
        consumerList = consumerService.selectByExample(ce2);
        System.out.println(consumerList.isEmpty());
        //判断邮箱是否已经存在
        if(!consumerList.isEmpty()){
            return ResponseMessage.error().addObject("msg","邮箱已注册");
        }

        //判断邮箱是否符合标准
        String regxemail = "^([a-zA-Z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if(!email.matches(regxemail)) {
            return ResponseMessage.error().addObject("msg", "邮箱格式不正确");
        }
        // 取随机产生的认证码
        String strEnsure = "";
        for (int i = 0; i < 6; ++i) {
            strEnsure += mapTable[(int) (mapTable.length * Math.random())];
        }
        MailTest sendMail = new MailTest();
        sendMail.sendEmail(email,"欢迎使用淘一淘购物网站",
                "尊敬的用户:"+username+"，您好！您正在注册淘一淘网站，本次注册邮箱的验证码为"+strEnsure);
        //将信息放入到session
        session.setAttribute("emailconfirm", strEnsure);
        return ResponseMessage.success();
    }

    //账户管理
    @RequestMapping("/information")
    public String information(Model model, HttpSession session){
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return "redirect:/login";
        }
        Consumer consumer2 = consumerService.selectByPrimaryKey(consumer.getUserid());
        model.addAttribute("user", consumer2);
        return "information";
    }

    //保存修改的信息
    @RequestMapping("/saveInfo")
    @ResponseBody
    public ResponseMessage saveInfo(String name, String email, String telephone,
                                    HttpServletRequest request, HttpSession session){
        //HttpSession session=request.getSession();
        System.out.println(name+email+telephone);
        List<Consumer> consumerList=new ArrayList<Consumer>();
        Consumer consumer=(Consumer)session.getAttribute("user");
        System.out.println(consumer.getUsername()+" "+consumer.getEmail()+" "+consumer.getTelephone());

        //判断用户名是否重名
        ConsumerExample ce1=new ConsumerExample();
        ce1.createCriteria().andUsernameEqualTo(name);
        consumerList=consumerService.selectByExample(ce1);
        if (!consumerList.isEmpty() && !name.equals(consumer.getUsername()))
        {
            return ResponseMessage.error().addObject("msg","更新失败,用户名已被使用");
        }
        System.out.println("1检测通过");
        //判断邮箱是否相同
        ConsumerExample ce2=new ConsumerExample();
        ce2.createCriteria().andEmailEqualTo(email);
        consumerList=consumerService.selectByExample(ce2);
        if (!consumerList.isEmpty() && !email.equals(consumer.getEmail()))
        {
            return ResponseMessage.error().addObject("msg","更新失败,邮箱已被使用");
        }
        System.out.println("2检测通过");
        //判断手机号码是否相同
        ConsumerExample ce3=new ConsumerExample();
        ce3.createCriteria().andTelephoneEqualTo(telephone);
        consumerList=consumerService.selectByExample(ce3);
        if (!consumerList.isEmpty() && !telephone.equals(consumer.getTelephone()))
        {
            return ResponseMessage.error().addObject("msg","更新失败,手机号码已被使用");
        }
        consumer.setUserid(consumer.getUserid());
        consumer.setUsername(name);
        consumer.setEmail(email);
        consumer.setTelephone(telephone);
        consumerService.updateByPrimaryKeySelective(consumer);
        return ResponseMessage.success();
    }

    //保存修改的密码
    @RequestMapping("/savePsw")
    @ResponseBody
    public ResponseMessage savePsw(String Psw,HttpServletRequest request, HttpSession session)
    {
        Consumer consumer=(Consumer) session.getAttribute("user");
        consumer.setPassword(Psw);
        consumerService.updateByPrimaryKeySelective(consumer);
        return ResponseMessage.success();
    }

    //登出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        return "redirect:/login";
    }

    //地址管理
    @RequestMapping("/info/address")
    public String address(HttpServletRequest request, Model model, HttpSession session){
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return "redirect:/login";
        }
        //创建条件
        AddressExample ae = new AddressExample();
        ae.or().andUseridEqualTo(consumer.getUserid());
        List<Address> addressList = addressService.getAllAddressByExample(ae);
        model.addAttribute("addressList", addressList);
        return "address";
    }

    //保存地址
    @RequestMapping("/saveAddr")
    @ResponseBody
    public ResponseMessage saveAddr(Address address){
        addressService.updateByPrimaryKeySelective(address);
        return ResponseMessage.success();
    }

    //删除地址
    @RequestMapping("/deleteAddr")
    @ResponseBody
    public ResponseMessage deleteAddr(Address address){
        addressService.deleteByPrimaryKey(address.getAddressid());
        return ResponseMessage.success();
    }

    //添加收货地址
    @RequestMapping("/insertAddr")
    @ResponseBody
    public ResponseMessage insertAddr(Address address, HttpSession session){
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return ResponseMessage.error().addObject("msg","登录异常，请重新登录");
        }
        address.setUserid(consumer.getUserid());
        addressService.insertSelective(address);
        return ResponseMessage.success();
    }

    //跳转到订单页面
    @RequestMapping("/info/list")
    public String list(HttpSession session,Model model){
        Consumer consumer = (Consumer) session.getAttribute("user");
        if(consumer == null){
            return  "redirect:/login";
        }
        //创造条件将结果通过model返回
        OrderExample oe = new OrderExample();
        oe.or().andUseridEqualTo(consumer.getUserid());
        List<Order> orderList = orderService.selectOrderByExample(oe);
        model.addAttribute("orderList",orderList);

        Order order;
        Address address;
        //查询订单详情
        List<OrderItem> orderItemList=new ArrayList<OrderItem>();
        for (Integer i = 0; i < orderList.size(); i++) {

            order=orderList.get(i);
            OrderItemExample oie = new OrderItemExample();
            oie.or().andOrderidEqualTo(order.getOrderid());

            orderItemList = orderService.getOrderItemByExample(oie);
            List<Goods> goodsList = new ArrayList<Goods>();
            List<Integer> goodsIdList = new ArrayList<Integer>();

            for (Integer j = 0; j < orderItemList.size(); j++) {
                goodsIdList.add(orderItemList.get(j).getGoodsid());
            }

            GoodsExample ge = new GoodsExample();
            ge.or().andGoodsidIn(goodsIdList);
            goodsList = goodsService.selectByExample(ge);

            order.setGoodsInfo(goodsList);
            address=addressService.selectByPrimaryKey(order.getAddressid());
            order.setAddress(address);
            orderList.set(i,order);
        }

        model.addAttribute("orderList",orderList);
        return "list";
    }

    //跳转到收藏夹
    @RequestMapping("/info/favorite")
    public String showFavorite(@RequestParam(value = "page",defaultValue = "1") Integer pn,
                               HttpSession session,Model model){
        Consumer consumer=(Consumer)session.getAttribute("user");
        if (consumer == null) {
            return "redirect:/login";
        }

        //使用分页插件  一页显示几个数据
        PageHelper.startPage(pn, 16);
        //创造条件
        FavoriteExample fe = new FavoriteExample();
        fe.or().andUseridEqualTo(consumer.getUserid());
        List<Favorite> favoriteList = goodsService.selectFavByExample(fe);

        List<Integer> goodsIdList = new ArrayList<Integer>();
        for (Favorite favorite:favoriteList) {
            goodsIdList.add(favorite.getGoodsid());
        }

        GoodsExample ge = new GoodsExample();
        List<Goods> goodsList = new ArrayList<Goods>();
        if (!goodsIdList.isEmpty()) {
            ge.or().andGoodsidIn(goodsIdList);
            goodsList = goodsService.selectByExample(ge);
        }

        //获取图片地址
        for (int i = 0; i < goodsList.size(); i++) {
            Goods goods = goodsList.get(i);

            List<ImagePath> imagePathList = goodsService.findImagePath(goods.getGoodsid());
            System.out.println(imagePathList.get(0).getPath());
            goods.setImagePaths(imagePathList);
            //判断是否收藏
            goods.setFav(true);
            goodsList.set(i, goods);
        }
        //显示几个页号
        PageInfo page = new PageInfo(goodsList,5);
        model.addAttribute("pageInfo", page);

        return "favorite";
    }

    //删除订单
    @RequestMapping("/deleteList")
    @ResponseBody
    public ResponseMessage deleteList(Order order){
        orderService.deleteById(order.getOrderid());
        return ResponseMessage.success();
    }

    @RequestMapping("/finishList")
    @ResponseBody
    public ResponseMessage finishiList(Integer orderid){
        Order order=orderService.selectByPrimaryKey(orderid);
        order.setIsreceive(true);
        order.setIscomplete(true);
        orderService.updateOrderByKey(order);
        return ResponseMessage.success();
    }
}
