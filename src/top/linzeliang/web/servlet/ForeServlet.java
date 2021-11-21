package top.linzeliang.web.servlet;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.util.HtmlUtils;
import top.linzeliang.comparator.ProductDateComparator;
import top.linzeliang.comparator.ProductPriceComparator;
import top.linzeliang.dao.OrderDAO;
import top.linzeliang.dao.ProductImageDAO;
import top.linzeliang.domain.*;
import top.linzeliang.service.impl.OrderItemServiceImpl;
import top.linzeliang.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ForeServlet extends BaseForeServlet {
    /**
     * 填充分类和产品关系，跳转到主页
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String home(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String byCategory = request.getParameter("cid");

        List<Category> categories = categoryService.findAllCategory();
        List<Product> products = null;

        // 总的产品数量
        int totalProduct = 0;

        // 有cid就按照分类进行查找
        if (null != byCategory) {
            int cid = Integer.parseInt(byCategory);
            page.setParam("&cid=" + cid);
            products = productService.findSomeProductByCategoryAndValid(cid, page.getStart(), page.getCount());
            // 获取该分类下的总产品数量
            totalProduct = productService.findAllProductByCategoryAndValid(cid).size();
        } else {
            products = productService.findSomeProductByValid(page.getStart(), page.getCount());
            totalProduct = productService.findAllProductByValid().size();
        }

        page.setTotal(totalProduct);

        /*// 将产品填充到分类下
        productService.fillByCategories(categories);*/
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("page", page);

        return "home.jsp";
    }

    /**
     * 检测用户是否存在功能，用于异步请求ajax
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String checkUserExist(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String name = request.getParameter("name");

        if (!userService.isExistUser(name)) {
            return "%success";
        }

        return "%fail";
    }

    /**
     * 注册功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String register(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        // 转义名字，防止恶意注册
        name = HtmlUtils.htmlEscape(name);

        User user = new User();
        user.setName(name);
        user.setPassword(password);

        // 注册
        userService.register(user);

        return "@" + request.getServletContext().getContextPath() + "/";
    }

    /**
     * 登录功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String login(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);

        // 登录
        User user = userService.login(name, password);
        if (null == user) {
            request.setAttribute("errorMsg", "账号或密码错误");
            return "login.jsp";
        } else if (0 == user.getStatus()) {
            request.setAttribute("errorMsg", "该用户已被禁用");
            return "login.jsp";
        }

        // 获取用户，计算收藏的商品总数
        int totalCountCollect = new OrderItemServiceImpl().getTotalOrderItemByUser(user.getId());
        user.setTotalCountCollect(totalCountCollect);
        request.getSession().setAttribute("user", user);

        // 获取重定向的url，跳转回之前的页面
        String url = request.getParameter("url");

        if (null != url && 0 != url.length()) {
            String redirectURL = URLDecoder.decode(url);
            return "@" + redirectURL;
        }
        return "@" + request.getServletContext().getContextPath() + "/";
    }

    /**
     * 注销功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String logout(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        request.getSession().removeAttribute("user");

        return "%success";
    }

    /**
     * 填充产品信息功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String product(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));

        // 获取产品
        Product product = productService.getProductById(pid);
        List<ProductImage> productSingleImages = productImageService.findAllProductImage(product, ProductImageDAO.type_single);
        List<ProductImage> productDetailImages = productImageService.findAllProductImage(product, ProductImageDAO.type_detail);

        // 获取按创建日期降序排序的产品集合
        List<Product> newproudcts = productService.findSomeProductByCreateDate(0, 3);

        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        request.setAttribute("product", product);
        request.setAttribute("newproducts", newproudcts);


        return "product.jsp";
    }

    /**
     * 检查登录状态功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        User user = (User) request.getSession().getAttribute("user");

        if (null != user) {
            return "%success";
        }

        return "%fail";
    }

    /**
     * 模态登录检查功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String loginAjax(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);

        User user = userService.login(name, password);
        if (null == user) {
            return "%fail";
        } else if (0 == user.getStatus()) {
            return "%forbidden";
        }

        // 获取用户，计算收藏的商品总数
        int totalCountCollect = new OrderItemServiceImpl().getTotalOrderItemByUser(user.getId());
        user.setTotalCountCollect(totalCountCollect);
        System.out.println(totalCountCollect);
        request.getSession().setAttribute("user", user);

        return "%success";
    }

    /**
     * 添加收藏功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String addCollect(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productService.getProductByIdAndValid(pid);
        User user = (User) request.getSession().getAttribute("user");

        if (product.getUser().getId() == user.getId()) {
            return "%fail";
        }

        // 获取用户的收藏夹，判断收藏夹是否已经存在
        List<OrderItem> orderItems = orderItemService.findAllOrderItemByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == product.getId()) {
                return "%exist";
            }
        }

        // 如果收藏夹里面没有该产品，就新建一个orderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setUser(user);
        orderItem.setProduct(product);

        orderItemService.addOrderItem(orderItem);
        // 添加成功
        return "%success";
    }

    /**
     * 搜索功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String search(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String keyword = request.getParameter("keyword");

        int totalProduct = productService.searchAllProduct(keyword).size();
        page.setTotal(totalProduct);
        page.setParam("&keyword=" + keyword);

        List<Product> products = productService.searchSomeProduct(keyword, page.getStart(), page.getCount());

        request.setAttribute("products", products);
        request.setAttribute("page", page);

        return "searchResult.jsp";
    }

    /**
     * 分类后的产品排序功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String categorysort(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String byCategory = request.getParameter("cid");
        String sort = request.getParameter("sort");

        // 用于切换排序
        String hrefDate = null;
        String hrefPrice = null;

        // 设置param参数
        page.setParam("&sort=" + sort);

        // 获取指定价格范围
        int beginPrice = 0;
        if (null != request.getParameter("begin")) {
            beginPrice = Integer.parseInt(request.getParameter("begin"));
            page.setParam(page.getParam() + "&begin=" + beginPrice);
        }
        int endPrice = 999999999;
        if (null != request.getParameter("end")) {
            endPrice = Integer.parseInt(request.getParameter("end"));
            page.setParam(page.getParam() + "&end=" + endPrice);
        }

        List<Category> categories = categoryService.findAllCategory();
        // 先获取所有的产品
        List<Product> products = null;

        // 有cid就按照分类进行查找
        if (null != byCategory) {
            int cid = Integer.parseInt(byCategory);

            hrefDate = "cid=" + cid + "&";
            hrefPrice = "cid=" + cid + "&";

            products = productService.findAllProductByCategoryAndPrice(cid, beginPrice, endPrice);
            // 加上分类
            page.setParam(page.getParam() + "&cid=" + cid);
        } else {
            hrefDate = "";
            hrefPrice = "";

            products = productService.findAllProductByPrice(beginPrice, endPrice);
        }
        // 总的产品数量
        int totalProduct = products.size();
        page.setTotal(totalProduct);

        switch (sort) {
            case "priceAsc":
                Collections.sort(products, new ProductPriceComparator());
                hrefPrice += "sort=priceDesc";
                request.setAttribute("hrefPrice", hrefPrice);
                break;
            case "priceDesc":
                Collections.sort(products, new ProductPriceComparator());
                Collections.reverse(products);
                hrefPrice += "sort=priceAsc";
                request.setAttribute("hrefPrice", hrefPrice);
                break;
            case "dateAsc":
                Collections.sort(products, new ProductDateComparator());
                hrefDate += "sort=dateDesc";
                request.setAttribute("hrefDate", hrefDate);
                break;
            case "dateDesc":
                Collections.sort(products, new ProductDateComparator());
                Collections.reverse(products);
                hrefDate += "sort=dateAsc";
                request.setAttribute("hrefDate", hrefDate);
                break;
            default:
                // nowhere
        }
        // 按照分页截取产品
        if (page.getStart() + page.getCount() > totalProduct) {
            products = products.subList(page.getStart(), totalProduct);
        } else {
            products = products.subList(page.getStart(), page.getCount());
        }

        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("page", page);

        return "home.jsp";
    }

    /**
     * 搜索后的产品排序功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String searchsort(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String keyword = request.getParameter("keyword");
        if (null == keyword) {
            keyword = "";
        }
        String sort = request.getParameter("sort");

        // 用于切换排序
        String hrefDate = "keyword=" + keyword + "&";
        String hrefPrice = "keyword=" + keyword + "&";

        // 设置page参数
        page.setParam("&keyword=" + keyword + "&sort=" + sort);

        // 获取指定价格范围
        int beginPrice = 0;
        if (null != request.getParameter("begin")) {
            beginPrice = Integer.parseInt(request.getParameter("begin"));
            page.setParam(page.getParam() + "&begin=" + beginPrice);
        }
        int endPrice = 999999999;
        if (null != request.getParameter("end")) {
            endPrice = Integer.parseInt(request.getParameter("end"));
            page.setParam(page.getParam() + "&end=" + endPrice);
        }

        // 获取搜索结果的所有的产品
        List<Product> products = productService.searchAllProductByPrice(keyword, beginPrice, endPrice);
        // 获取产品数量
        int totalProduct = products.size();
        page.setTotal(totalProduct);

        switch (sort) {
            case "priceAsc":
                Collections.sort(products, new ProductPriceComparator());
                hrefPrice += "sort=priceDesc";
                request.setAttribute("hrefPrice", hrefPrice);
                break;
            case "priceDesc":
                Collections.sort(products, new ProductPriceComparator());
                Collections.reverse(products);
                hrefPrice += "sort=priceAsc";
                request.setAttribute("hrefPrice", hrefPrice);
                break;
            case "dateAsc":
                Collections.sort(products, new ProductDateComparator());
                hrefDate += "sort=dateDesc";
                request.setAttribute("hrefDate", hrefDate);
                break;
            case "dateDesc":
                Collections.sort(products, new ProductDateComparator());
                Collections.reverse(products);
                hrefDate += "sort=dateAsc";
                request.setAttribute("hrefDate", hrefDate);
                break;
            default:
                // nowhere
        }
        // 按照分页截取产品
        if (page.getStart() + page.getCount() > totalProduct) {
            products = products.subList(page.getStart(), totalProduct);
        } else {
            products = products.subList(page.getStart(), page.getCount());
        }

        request.setAttribute("products", products);
        request.setAttribute("page", page);

        return "searchResult.jsp";
    }

    /**
     * 仅仅查询指定价格范围的产品，不排序
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String categoryprice(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String byCategory = request.getParameter("cid");

        List<Category> categories = categoryService.findAllCategory();
        List<Product> products = null;

        // 获取该分类下的总产品数量
        int totalProduct = 0;

        // 获取指定价格范围
        int beginPrice = 0;
        if (null != request.getParameter("begin")) {
            beginPrice = Integer.parseInt(request.getParameter("begin"));
            page.setParam(page.getParam() + "&begin=" + beginPrice);
        }
        int endPrice = 999999999;
        if (null != request.getParameter("end")) {
            endPrice = Integer.parseInt(request.getParameter("end"));
            page.setParam(page.getParam() + "&end=" + endPrice);
        }

        // 有cid就按照分类进行查找
        if (null != byCategory) {
            int cid = Integer.parseInt(byCategory);
            page.setParam("&cid=" + cid);
            products = productService.findSomeProductByCategoryAndPrice(cid, beginPrice, endPrice, page.getStart(), page.getCount());
            totalProduct = productService.findAllProductByCategoryAndPrice(cid, beginPrice, endPrice).size();
        } else {
            products = productService.findSomeProductByPrice(beginPrice, endPrice, page.getStart(), page.getCount());
            totalProduct = productService.findAllProductByPrice(beginPrice, endPrice).size();
        }

        page.setTotal(totalProduct);

        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("page", page);

        return "home.jsp";
    }

    /**
     * 根据关键字查询指定价格范围的产品，不排序
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String searchprice(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String keyword = request.getParameter("keyword");

        // 设置page参数
        page.setParam("&keyword=" + keyword);

        // 获取指定价格范围
        int beginPrice = 0;
        if (null != request.getParameter("begin")) {
            beginPrice = Integer.parseInt(request.getParameter("begin"));
            page.setParam(page.getParam() + "&begin=" + beginPrice);
        }
        int endPrice = 999999999;
        if (null != request.getParameter("end")) {
            endPrice = Integer.parseInt(request.getParameter("end"));
            page.setParam(page.getParam() + "&end=" + endPrice);
        }

        // 获取搜索结果的所有的产品
        List<Product> products = productService.searchSomeProductByPrice(keyword, beginPrice, endPrice, page.getStart(), page.getCount());
        // 获取产品数量
        int totalProduct = productService.searchAllProductByPrice(keyword, beginPrice, endPrice).size();
        // 设置搜索结果总记录数
        page.setTotal(totalProduct);

        request.setAttribute("products", products);
        request.setAttribute("page", page);

        return "searchResult.jsp";
    }

    /**
     * 跳转到用户中心去
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String user(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return "setting.jsp";
    }

    /**
     * ajax检测用户是否登录
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String checkUserLogin(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String oldpassword = request.getParameter("oldpassword");
        User user = (User) request.getSession().getAttribute("user");

        if (user.getPassword().equals(oldpassword)) {
            return "%success";
        }

        return "%fail";
    }

    /**
     * 修改密码
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String userpasswordchange(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String password = request.getParameter("password");

        User user = (User) request.getSession().getAttribute("user");
        user.setPassword(password);

        userService.updateUserPassword(user);

        request.getSession().removeAttribute("user");

        return "@" + request.getServletContext().getContextPath() + "/";
    }

    /**
     * 更新用户信息
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String userinfochange(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int gender = Integer.parseInt(request.getParameter("gender"));
        String mobile = request.getParameter("mobile");
        String mail = request.getParameter("mail");
        String qq = request.getParameter("qq");
        String province = request.getParameter("province");
        String city = request.getParameter("city");

        User user = (User) request.getSession().getAttribute("user");
        user.setGender(gender);
        user.setMobile(mobile);
        user.setMail(mail);
        user.setQq(qq);
        user.setAddress(province + " " + city);

        userService.updateUserInfo(user);

        return "@foreuser";
    }

    /**
     * 购买功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String buy(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        // 获取要购买的产品
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productService.getProductById(pid);
        // 获取用户user
        User user = (User) request.getSession().getAttribute("user");

        if (0 == product.getStatus() || product.getUser().getId() == user.getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        request.setAttribute("product", product);

        return "buy.jsp";
    }

    /**
     * 生成订单功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String createOrder(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        // 获取产品
        int pid = Integer.parseInt(request.getParameter("pid"));
        // 获取用户user
        User user = (User) request.getSession().getAttribute("user");

        Product product = productService.getProductById(pid);
        if (0 == product.getStatus() || product.getUser().getId() == user.getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        // 获取信息
        String receiver = request.getParameter("receiver");
        String mobile = request.getParameter("mobile");
        String post = request.getParameter("post");
        String address = request.getParameter("address");
        String userMessage = request.getParameter("userMessage");

        // 产品取消显示在列表中
        product.setStatus(0);
        productService.updateProduct(product);

        // 创建订单
        Order order = new Order();
        // 生成订单号
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(1000, 9999);
        order.setUser(user);
        order.setOrderCode(orderCode);
        order.setAddress(address);
        order.setPost(post);
        order.setReceiver(receiver);
        order.setMobile(mobile);
        order.setUserMessage(userMessage);
        order.setCreateDate(new Date());
        order.setStatus(OrderDAO.waitPay);
        // 添加订单
        orderService.addOrder(order);

        // 获取订单项
        OrderItem targetProductOrderItem = null;
        // 查看收藏夹里面有没有该产品
        boolean existOrderItem = false;
        List<OrderItem> orderItems = orderItemService.findAllOrderItemByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == product.getId()) {
                targetProductOrderItem = orderItem;
                existOrderItem = true;
                break;
            }
        }
        // 不存在就创建订单项
        if (!existOrderItem) {
            targetProductOrderItem = new OrderItem();
            targetProductOrderItem.setProduct(product);
            targetProductOrderItem.setUser(user);
            // 添加订单项
            orderItemService.addOrderItem(targetProductOrderItem);
        }
        // 设置订单所属的订单项
        targetProductOrderItem.setOrder(order);
        orderItemService.updateOrderItem(targetProductOrderItem);

        return "@forepayment?oid=" + order.getId() + "&price=" + product.getPromotePrice();
    }

    /**
     * 跳转到支付页面功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String payment(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = orderService.getOrderById(oid);
        orderItemService.fillByOrder(order);

        User user = (User) request.getSession().getAttribute("user");

        if (user.getId() != order.getUser().getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        request.setAttribute("order", order);

        return "payed.jsp";
    }

    /**
     * 支付功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String payed(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = orderService.getOrderById(oid);
        // 填充orderItem
        orderItemService.fillByOrder(order);

        User user = (User) request.getSession().getAttribute("user");
        if (order.getUser().getId() != user.getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        // 如果订单已经购买过了就不能再次购买
        if (!"waitPay".equals(order.getStatus())) {
            return "payFail.jsp";
        }

        // 更新订单信息
        order.setStatus(OrderDAO.waitDelivery);
        order.setPayDate(new Date());
        orderService.updateOrder(order);

        // 更新用户的余额
        user.setBalance(user.getBalance() - orderItemService.getOrderItemByOrder(oid).getProduct().getPromotePrice());
        userService.updateUserBalance(user);

        request.setAttribute("order", order);

        return "paySuccess.jsp";
    }

    /**
     * 查看订单功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String order(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        User user = (User) request.getSession().getAttribute("user");
        List<Order> orders = orderService.findAllOrderWithoutStatus(user.getId(), OrderDAO.delete);

        // 填充订单项
        orderItemService.fillByOrders(orders);

        request.setAttribute("orders", orders);

        return "order.jsp";
    }

    /**
     * 检查余额是否充足功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String checkBalance(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productService.getProductById(pid);
        User user = (User) request.getSession().getAttribute("user");

        if (user.getBalance() < product.getPromotePrice()) {
            return "%fail";
        }
        return "%success";
    }


    /**
     * 删除订单功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String deleteOrder(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        User user = (User) request.getSession().getAttribute("user");
        Order order = orderService.getOrderById(oid);
        orderItemService.fillByOrder(order);

        if (user.getId() != order.getUser().getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        if ("finish".equals(order.getStatus())) {
            order.setStatus(OrderDAO.delete);
            orderService.updateOrder(order);
            return "%success";
        } else {
            return "%fail";
        }
    }

    /**
     * 确认收货功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String confirmReceipt(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        User user = (User) request.getSession().getAttribute("user");
        Order order = orderService.getOrderById(oid);
        orderItemService.fillByOrder(order);

        if (order.getUser().getId() != user.getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        // 订单状态改为确认收货
        order.setStatus(OrderDAO.waitReview);
        order.setConfirmDate(new Date());
        orderService.updateOrder(order);

        // 卖家账户钱增加
        User sellerUser = order.getOrderItem().getProduct().getUser();
        sellerUser.setBalance(sellerUser.getBalance() + order.getOrderItem().getProduct().getPromotePrice());
        userService.updateUserBalance(sellerUser);

        return "@foreorder";
    }

    /**
     * 进行评价功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String review(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        User user = (User) request.getSession().getAttribute("user");
        Order order = orderService.getOrderById(oid);
        orderItemService.fillByOrder(order);

        if (order.getUser().getId() != user.getId()) {
            return "@forehome";
        }

        request.setAttribute("order", order);

        return "review.jsp";
    }

    /**
     * 确认提交评价功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String doreview(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        User user = (User) request.getSession().getAttribute("user");
        Order order = orderService.getOrderById(oid);
        orderItemService.fillByOrder(order);

        String content = request.getParameter("content");
        content = HtmlUtils.htmlEscape(content);

        // 订单状态更新
        order.setStatus(OrderDAO.finish);
        orderService.updateOrder(order);

        // 创建评论
        Review review = new Review();
        review.setUser(user);
        review.setProduct(order.getOrderItem().getProduct());
        review.setContent(content);
        review.setCreateDate(new Date());
        reviewService.addReview(review);

        return "@foreorder";
    }

    /**
     * 查看订单详情功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String orderDetail(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = orderService.getOrderById(oid);
        orderItemService.fillByOrder(order);

        request.setAttribute("order", order);

        return "orderDetail.jsp";
    }

    /**
     * 收藏夹功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String collect(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        User user = (User) request.getSession().getAttribute("user");
        // 设置每页10条记录
        page.setCount(10);
        List<OrderItem> orderItems = orderItemService.findSomeOrderItemByUser(user.getId(), page.getStart(), page.getCount());
        for (OrderItem orderItem : orderItems) {
            productService.setFirstProductImage(orderItem.getProduct());
        }

        int totalCollect = orderItemService.findAllOrderItemByUser(user.getId()).size();
        page.setTotal(totalCollect);

        request.setAttribute("orderItems", orderItems);
        request.setAttribute("page", page);

        return "collect.jsp";
    }

    /**
     * 移除收藏功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String collectDelete(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oiid = Integer.parseInt(request.getParameter("oiid"));
        OrderItem orderItem = orderItemService.getOrderItemById(oiid);
        User user = (User) request.getSession().getAttribute("user");

        if (orderItem.getUser().getId() != user.getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        orderItemService.deleteOrderItem(oiid);

        return "@forecollect";
    }

    /**
     * 查看用户信息功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String userMessage(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int uid = Integer.parseInt(request.getParameter("uid"));
        User sellerUser = userService.getUserById(uid);

        List<Product> products = productService.findAllProductByUserAndValid(sellerUser.getId());
        int totalProduct = products.size();
        for (Product product : products) {
            product.setCollectCount(orderItemService.getProductCollect(product.getId(), product.getUser().getId()));
        }

        List<Review> reviews = reviewService.findAllReview(sellerUser.getId());

        request.setAttribute("reviews", reviews);
        request.setAttribute("sellerUser", sellerUser);
        request.setAttribute("products", products);
        request.setAttribute("totalProduct", totalProduct);

        return "userMessage.jsp";
    }

    /**
     * 跳转到发布商品页面
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String addProduct(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        List<Category> categorys = categoryService.findAllCategory();

        request.setAttribute("categorys", categorys);

        return "addProduct.jsp";
    }

    /**
     * 添加商品功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String confirmAddProduct(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        InputStream inputStream = null;
        // 提交上传文件时的参数
        Map<String, String> params = new HashMap<>();
        // 解析上传
        inputStream = parseUpload(request, params);

        // 获取不同文字参数值
        String name = params.get("name");
        String description = params.get("description");
        float originalPrice = Float.parseFloat(params.get("originalPrice"));
        originalPrice = (float) ( ((int)(originalPrice * 100) ) / 100.0);
        float promotePrice = Float.parseFloat(params.get("promotePrice"));
        promotePrice = (float) ( ((int)(promotePrice * 100) ) / 100.0);
        int cid = Integer.parseInt(params.get("cid"));
        Category category = categoryService.getCategoryById(cid);

        // 获取用户
        User user = (User) request.getSession().getAttribute("user");

        // 创建产品
        Product product = new Product();
        product.setName(name);
        product.setUser(user);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setCategory(category);
        product.setDescription(description);
        product.setCreateDate(new Date());
        product.setStatus(1);
        productService.addProduct(product);

        // 创建图片对象
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setType(ProductImageDAO.type_single);
        // 添加图片
        productImageService.addProductImage(productImage);

        // 生成文件和文件夹
        String fileName = productImage.getId() + ".jpg";
        String imageFolder = request.getServletContext().getRealPath("img/productSingle");
        String imageFolder_small = request.getServletContext().getRealPath("img/productSingleSmall");
        // 创建文件
        File file = new File(imageFolder, fileName);
        // 同时创建文件夹(创建指定的目录，包括所有目录，如果有不存在的父目录则会被自动创建)
        file.getParentFile().mkdirs();

        // 复制图片文件到本地磁盘中
        try {
            if (null != inputStream && 0 != inputStream.available()) {
                try (
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                ) {
                    // 1MB大小
                    byte[] b = new byte[1024 * 1024];
                    int length = 0;
                    while ((length = inputStream.read(b)) != -1) {
                        fileOutputStream.write(b, 0, length);
                    }
                    // 清空缓存
                    fileOutputStream.flush();
                    // 将文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);

                    // 缩略图
                    if (ProductImageDAO.type_single.equals(productImage.getType())) {
                        File image_single_small = new File(imageFolder_small, fileName);

                        image_single_small.getParentFile().mkdirs();

                        ImageUtil.resizeImage(file, 200, 200, image_single_small);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "@forerelease";
    }

    /**
     * 获取我发布的商品功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String release(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        User user = (User) request.getSession().getAttribute("user");
        // 设置每页10条记录
        page.setCount(10);

        List<Product> products = productService.findSomeProductByUser(user.getId(), page.getStart(), page.getCount());

        int totalProduct = productService.findAllProductByUser(user.getId()).size();
        page.setTotal(totalProduct);

        request.setAttribute("products", products);
        request.setAttribute("page", page);

        return "release.jsp";
    }

    /**
     * 删除商品功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String deleteProduct(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        User user = (User) request.getSession().getAttribute("user");
        Product product = productService.getProductById(pid);

        if (product.getUser().getId() != user.getId() || product.getStatus() != 1) {
            return "@forerelease";
        }

        List<ProductImage> productImageSingle = productImageService.findAllProductImage(product, ProductImageDAO.type_single);
        List<ProductImage> productImageDetail = productImageService.findAllProductImage(product, ProductImageDAO.type_detail);

        // 删除预览单个图片
        for (ProductImage productImage : productImageSingle) {
            String imageFolder_single = request.getServletContext().getRealPath("img/productSingle");
            String imageFolder_single_small = request.getServletContext().getRealPath("img/productSingleSmall");
            // 获取文件
            File image_single = new File(imageFolder_single, productImage.getId() + ".jpg");
            File image_single_small = new File(imageFolder_single_small, productImage.getId() + ".jpg");
            // 删除
            image_single.delete();
            image_single_small.delete();
            productImageService.deleteProductImage(productImage.getId());
        }

        // 删除详细介绍图
        for (ProductImage productImage : productImageDetail) {
            String imageFolder_detail = request.getServletContext().getRealPath("img/productDetail");
            // 获取文件
            File image_detail = new File(imageFolder_detail, productImage.getId() + ".jpg");
            // 删除
            image_detail.delete();
            productImageService.deleteProductImage(productImage.getId());
        }

        // 删除商品信息
        productService.deleteProduct(product.getId());

        return "@forerelease";
    }

    /**
     * 跳转到编辑商品页面
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String editProduct(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        User user =(User) request.getSession().getAttribute("user");
        Product product = productService.getProductById(pid);

        if (user.getId() != product.getUser().getId() || product.getStatus() != 1) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        List<Category> categorys = categoryService.findAllCategory();

        System.out.println(product.getCategory().getId());
        request.setAttribute("product", product);
        request.setAttribute("categorys", categorys);

        return "editProduct.jsp";
    }

    /**
     * 编辑产品功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String confirmEditProduct(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        User user = (User) request.getSession().getAttribute("user");
        Product product = productService.getProductById(pid);

        if (user.getId() != product.getUser().getId() || product.getStatus() != 1) {
            return "@forerelease";
        }

        // 获取参数值
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        originalPrice = (float) ( ((int)(originalPrice * 100) ) / 100.0);
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        promotePrice = (float) ( ((int)(promotePrice * 100) ) / 100.0);
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryService.getCategoryById(cid);

        product.setName(name);
        product.setDescription(description);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setCategory(category);
        // 更新
        productService.updateProduct(product);

        return "@forerelease";
    }

    /**
     * 跳转到更新商品图片页面
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String updateProductImage(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        User user =(User) request.getSession().getAttribute("user");
        Product product = productService.getProductById(pid);

        if (user.getId() != product.getUser().getId() || product.getStatus() != 1) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        List<ProductImage> pisSingle = productImageService.findAllProductImage(product, "type_single");
        ProductImage productImageSingle = null;
        if (pisSingle.size() != 0) {
            productImageSingle = pisSingle.get(0);
        }
        List<ProductImage> productImageDetail = productImageService.findAllProductImage(product, "type_detail");

        request.setAttribute("product", product);
        request.setAttribute("productImageSingle", productImageSingle);
        request.setAttribute("productImageDetail", productImageDetail);

        return "updateProductImage.jsp";
    }

    /**
     * 更新添加产品图片功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String confirmAddProductImage(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        InputStream inputStream = null;
        // 提交上传文件时的参数
        Map<String, String> params = new HashMap<>();
        // 解析上传
        inputStream = parseUpload(request, params);

        // 根据上传的参数生成productImage对象
        String type = params.get("type");
        int pid = Integer.parseInt(params.get("pid"));
        Product product = productService.getProductById(pid);
        User user = (User) request.getSession().getAttribute("user");

        if (product.getUser().getId() != user.getId() || product.getStatus() != 1) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        ProductImage productImage = new ProductImage();
        productImage.setType(type);
        productImage.setProduct(product);

        // 添加productImage对象到数据库中
        productImageService.addProductImage(productImage);

        // 生成文件和文件夹
        String fileName = productImage.getId() + ".jpg";
        String imageFolder = null;
        String imageFolder_small = null;

        // 判断图片类型
        if (ProductImageDAO.type_single.equals(productImage.getType())) {
            imageFolder = request.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = request.getServletContext().getRealPath("img/productSingleSmall");
        } else {
            imageFolder = request.getServletContext().getRealPath("img/productDetail");
        }
        // 创建文件
        File file = new File(imageFolder, fileName);
        // 同时创建文件夹(创建指定的目录，包括所有目录，如果有不存在的父目录则会被自动创建)
        file.getParentFile().mkdirs();

        // 复制文件到本地磁盘中
        try {
            if (null != inputStream && 0 != inputStream.available()) {
                try (
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                ) {
                    // 1MB大小
                    byte[] b = new byte[1024 * 1024];
                    int length = 0;
                    while ((length = inputStream.read(b)) != -1) {
                        fileOutputStream.write(b, 0, length);
                    }
                    // 清空缓存
                    fileOutputStream.flush();
                    // 将文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);

                    // 缩略图
                    if (ProductImageDAO.type_single.equals(productImage.getType())) {
                        File image_single_small = new File(imageFolder_small, fileName);

                        image_single_small.getParentFile().mkdirs();

                        ImageUtil.resizeImage(file, 200, 200, image_single_small);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "@foreupdateProductImage?pid=" + product.getId();
    }

    /**
     * 更新删除产品图片功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String confirmDeleteProductImage(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductImage productImage = productImageService.getProductImageById(id);
        User user = (User) request.getSession().getAttribute("user");

        if (productImage.getProduct().getUser().getId() != user.getId()) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        // 如果是单个图片
        if (ProductImageDAO.type_single.equals(productImage.getType())) {
            String imageFolder_single = request.getServletContext().getRealPath("img/productSingle");
            String imageFolder_single_small = request.getServletContext().getRealPath("img/productSingleSmall");
            // 获取文件
            File image_single = new File(imageFolder_single, productImage.getId() + ".jpg");
            File image_single_small = new File(imageFolder_single_small, productImage.getId() + ".jpg");
            // 删除
            image_single.delete();
            image_single_small.delete();
        } else {
            // 详细介绍图
            String imageFolder_detail = request.getServletContext().getRealPath("img/productDetail");
            // 获取文件
            File image_detail = new File(imageFolder_detail, productImage.getId() + ".jpg");
            // 删除
            image_detail.delete();
        }

        productImageService.deleteProductImage(id);

        return "@foreupdateProductImage?pid=" + productImage.getProduct().getId();
    }

    /**
     * 发货功能
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String deliveryProduct(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productService.getProductById(pid);
        User user = (User) request.getSession().getAttribute("user");

        if (product.getUser().getId() != user.getId() || !"waitDelivery".equals(product.getProgress())) {
            return "@" + request.getServletContext().getContextPath() + "/";
        }

        // 获取订单id
        int oid = productService.getOrderIdByProduct(product.getId());
        Order order = orderService.getOrderById(oid);

        if (null != order) {
            // 设置发货日期
            order.setDeliveryDate(new Date());
            // 修改订单状态为待收货
            order.setStatus(OrderDAO.waitConfirm);

            orderService.updateOrder(order);

            return "@forerelease";
        }

        return "@" + request.getServletContext().getContextPath() + "/";
    }
}
