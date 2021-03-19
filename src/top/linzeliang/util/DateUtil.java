package top.linzeliang.util;

/**
 * @Description: 日期工具类，用于java.util.Date类与java.sql.Timestamp 类的互相转换
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public class DateUtil {
    /**
     * 将java.util.Date转为java.sql.Timestamp，由于java.sql.Date只能存储日期不能存储时间，所以我们使用Timestamp
     *
     * @param: date
     * @return: java.sql.Timestamp
     */
    public static java.sql.Timestamp d2t(java.util.Date date) {
        if (null == date) {
            return null;
        }
        return new java.sql.Timestamp(date.getTime());
    }

    /**
     * 将java.sql.Timestamp转为java.util.Date
     *
     * @param: timestamp
     * @return: java.util.Date
     */
    public static java.util.Date t2d(java.sql.Timestamp timestamp) {
        if (null == timestamp) {
            return null;
        }
        return new java.util.Date(timestamp.getTime());
    }
}
