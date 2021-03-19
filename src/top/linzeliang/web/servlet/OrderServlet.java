package top.linzeliang.web.servlet;

import top.linzeliang.dao.OrderDAO;
import top.linzeliang.domain.Order;
import top.linzeliang.domain.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class OrderServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        List<Order> orders = orderService.findSomeOrder(page.getStart(), page.getCount());

        // 填充订单的订单项信息
        orderItemService.fillByOrders(orders);
        int totalOrder = orderService.getTotalOrder();
        page.setTotal(totalOrder);

        request.setAttribute("orders", orders);
        request.setAttribute("page", page);

        return "admin/listOrder.jsp";
    }

    /**
     * 确认发货
     *
     * @param: request
     * @param: response
     * @param: page
     * @return: String
     */
    public String delivery(HttpServletRequest request, HttpServletResponse response, PageBean page) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = orderService.getOrderById(oid);

        if (null != order) {
            // 设置发货日期
            order.setDeliveryDate(new Date());
            // 修改订单状态为待收货
            order.setStatus(OrderDAO.waitConfirm);

            orderService.updateOrder(order);

            return "%success";
        }

        return "%fail";
    }
}
