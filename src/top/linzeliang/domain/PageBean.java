package top.linzeliang.domain;

/**
 * @Description: 分页PageBean
 * @Author: LinZeLiang
 * @Date: 2021-02-08
 */
public class PageBean {
    /**
     * 记录开始的位置
     */
    private int start;
    /**
     * 每页记录数量
     */
    private int count;
    /**
     * 总记录数
     */
    private int total;
    /**
     *  param用于存储某些id的，在实现分页的时候用到
     */
    private String param;

    public PageBean(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 是否存在前一页，用来判断当前页的位置有没有到达顶端
     *
     * @return: boolean
     */
    public boolean isPrevious() {
        if (0 == start) {
            return true;
        }
        return false;
    }

    /**
     * 是否存在后一页，用来判断当前页的位置有没有到达末尾
     *
     * @return: boolean
     */
    public boolean isEnd() {
        if (getLast() == start) {
            return true;
        }
        return false;
    }

    /**
     * 获取最后一页的开始位置 start
     *
     * @return: int
     */
    public int getLast() {
        int lastPageStart = 0;

        // 如果总的记录数刚好被每页的记录数整数，那么最后一页的起始位置就是 total - count
        if (0 == total % count) {
            lastPageStart = total - count;
        } else {
            // 如果不能整除，最后一页的开始就是总的页数减去最后的多余的那几条页数
            lastPageStart = total - total % count;
        }

        return lastPageStart;
    }

    /**
     * 获取总页数
     *
     * @return: int
     */
    public int getTotalPage() {
        int totalPage = 0;
        // 计算总页数
        totalPage = (0 == total % count) ? total / count : total / count + 1;

        // 如果总页数为0，就设置默认为1
        if (0 == totalPage) {
            totalPage = 1;
        }

        return totalPage;
    }
}
