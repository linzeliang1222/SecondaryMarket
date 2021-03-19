package top.linzeliang.web.servlet;

import top.linzeliang.domain.Category;
import top.linzeliang.domain.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CategoryServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        String categoryName = request.getParameter("name");

        Category category = new Category();
        category.setName(categoryName);
        categoryService.addCategory(category);

        return "@admin-category-list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int id = Integer.parseInt(request.getParameter("id"));

        categoryService.deleteCategory(id);

        return "@admin-category-list?page.start=" + page.getStart();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int id = Integer.parseInt(request.getParameter("id"));

        // 将获得的要修改的分类存储到request域中，提供给editCategory页面
        Category category = categoryService.getCategoryById(id);

        request.setAttribute("category", category);
        request.setAttribute("page", page);

        return "admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int id = Integer.parseInt(request.getParameter("id"));
        String categoryName = request.getParameter("name");

        Category category = new Category();
        category.setId(id);
        category.setName(categoryName);
        categoryService.updateCategory(category);

        return "@admin-category-list?page.start=" + page.getStart();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        List<Category> categories = categoryService.findSomeCategory(page.getStart(), page.getCount());
        int totalCategory = categoryService.getTotalCategory();
        page.setTotal(totalCategory);

        request.setAttribute("categories", categories);
        request.setAttribute("page", page);

        return "admin/listCategory.jsp";
    }
}
