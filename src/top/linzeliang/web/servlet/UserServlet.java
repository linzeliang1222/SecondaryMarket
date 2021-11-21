package top.linzeliang.web.servlet;

import top.linzeliang.domain.PageBean;
import top.linzeliang.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return "";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return "";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return "";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return "";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        List<User> users = userService.findAllUser();

        int totalUser = userService.getTotalUser();
        page.setTotal(totalUser);

        request.setAttribute("users", users);
        request.setAttribute("page", page);

        return "admin/listUser.jsp";
    }

    /**
     * 查询、修改用户的状态
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String queryUserStatus(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int uid = Integer.parseInt(request.getParameter("uid"));
        User user = userService.getUserById(uid);

        if (0 == user.getStatus()) {
            int res = userService.setUserStatus(uid, 1);
            // 修改成功才返回disable
            if (1 == res) {
                return "%disable";
            }
        } else if (1 == user.getStatus()) {
            int res = userService.setUserStatus(uid, 0);
            // 修改成功才返回start
            if (1 == res) {
                return "%start";
            }
        }
        return "%fail";
    }
}
