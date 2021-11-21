package top.linzeliang.web.servlet;

import top.linzeliang.domain.Category;
import top.linzeliang.domain.PageBean;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class ProductServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryService.getCategoryById(cid);

        String productName = request.getParameter("name");
        String description = request.getParameter("description");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        originalPrice = (float) ( ((int)(originalPrice * 100) ) / 100.0);
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        promotePrice = (float) ( ((int)(promotePrice * 100) ) / 100.0);
        String seller = request.getParameter("seller");
        // 获取user
        User user = userService.getUserByName(seller);

        Product product = new Product();
        product.setName(productName);
        product.setDescription(description);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setUser(user);
        product.setCreateDate(new Date());
        product.setCategory(category);
        product.setStatus(1);

        productService.addProduct(product);

        return "@admin-product-list?cid=" + category.getId();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getProductById(id);

        productService.deleteProduct(id);

        return "@admin-product-list?cid=" + product.getCategory().getId() + "&page.start=" + page.getStart();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getProductById(id);


        request.setAttribute("product", product);
        request.setAttribute("page", page);

        return "admin/editProduct.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryService.getCategoryById(cid);

        String productName = request.getParameter("name");
        String description = request.getParameter("description");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        originalPrice = (float) ( ((int)(originalPrice * 100) ) / 100.0);
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        promotePrice = (float) ( ((int)(promotePrice * 100) ) / 100.0);
        String seller = request.getParameter("seller");
        // 获取user
        User user = userService.getUserByName(seller);

        Product product = new Product();
        product.setId(id);
        product.setName(productName);
        product.setDescription(description);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setUser(user);
        product.setCreateDate(new Date());
        product.setCategory(category);

        productService.updateProduct(product);

        return "@admin-product-list?cid=" + category.getId() + "&page.start=" + page.getStart();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int cid = Integer.parseInt(request.getParameter("cid"));

        Category category = categoryService.getCategoryById(cid);
        List<Product> products = productService.findSomeProductByCategory(cid, page.getStart(), page.getCount());
        int totalProduct = productService.getTotalProductByCategory(category.getId());
        page.setTotal(totalProduct);
        page.setParam("&cid=" + category.getId());

        request.setAttribute("products", products);
        request.setAttribute("category", category);
        request.setAttribute("page", page);

        return "admin/listProduct.jsp";
    }

    public String checkUser(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String seller = request.getParameter("seller");

        // 通过用户名检查user
        if (null != seller && userService.isExistUser(seller)) {
            return "%success";
        }

        return "%fail";
    }
}
