package top.linzeliang.domain;

import top.linzeliang.dao.OrderDAO;

import java.util.Date;

/**
 * @Description: Order实体类
 * @Author: LinZeLiang
 * @Date: 2021-02-08
 */
public class Order {
    private int id;
    private User user;
    /**
     * 订单编号
     */
    private String orderCode;
    private String address;
    /**
     * 邮编
     */
    private String post;
    /**
     * 收货人姓名
     */
    private String receiver;
    private String mobile;
    private String userMessage;
    private Date createDate;
    private Date payDate;
    /**
     * 发货日期
     */
    private Date deliveryDate;
    /**
     * 确认收货日期
     */
    private Date confirmDate;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 一个产品对应一个订单项，一个订单项对应一个订单
     */
    private OrderItem orderItem;
    /**
     * 价格
     */
    private float totalPrice;

    /**
     * 将订单状态输出为中文格式
     *
     * @return: String
     */
    public String getStatusDescription() {
        String description = null;

        switch (status) {
            case OrderDAO.waitPay:
                description = "待付款";
                break;
            case OrderDAO.waitDelivery:
                description = "待发货";
                break;
            case OrderDAO.waitConfirm:
                description = "待收货";
                break;
            case OrderDAO.waitReview:
                description = "待评价";
                break;
            case OrderDAO.finish:
                description = "完成";
                break;
            case OrderDAO.delete:
                description = "删除";
                break;
            default:
                description = "未知";
        }

        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
