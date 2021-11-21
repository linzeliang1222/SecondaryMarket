package top.linzeliang.dao;

import top.linzeliang.domain.Review;

import java.util.List;

/**
 * @Description: Review对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface ReviewDAO {
    /**
     * 根据uid获取某类产品下的总评论数
     *
     * @param: uid
     * @return: int
     */
    int getTotalReviewByUser(int uid);

    void addReview(Review review);

    void deleteReview(int id);

    /**
     * 通过id获取评论
     *
     * @param: id
     * @return: Review
     */
    Review getReviewById(int id);

    /**
     * 获取某用户的评论集合
     *
     * @param: uid
     * @param: start
     * @param: count
     * @return: List<Review>
     */
    List<Review> listReview(int uid, int start, int count);
}
