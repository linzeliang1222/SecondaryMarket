package top.linzeliang.service;

import top.linzeliang.domain.Review;

import java.util.List;

/**
 * @Description: TODO
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface ReviewService {
    /**
     * 获取用户的所有评价
     *
     * @param: uid
     * @return: int
     */
    int getTotalReviewByUser(int uid);

    void addReview(Review review);

    void deleteReview(int id);

    Review getReviewById(int id);

    List<Review> findAllReview(int uid);

    List<Review> findSomeReview(int uid, int start, int count);
}
