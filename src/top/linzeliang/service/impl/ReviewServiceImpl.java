package top.linzeliang.service.impl;

import top.linzeliang.dao.ReviewDAO;
import top.linzeliang.dao.impl.ReviewDAOImpl;
import top.linzeliang.domain.Review;
import top.linzeliang.service.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    final private ReviewDAO reviewDAO = new ReviewDAOImpl();

    @Override
    public int getTotalReviewByUser(int uid) {
        return reviewDAO.getTotalReviewByUser(uid);
    }

    @Override
    public void addReview(Review review) {
        reviewDAO.addReview(review);
    }

    @Override
    public void deleteReview(int id) {
        reviewDAO.deleteReview(id);
    }

    @Override
    public Review getReviewById(int id) {
        return reviewDAO.getReviewById(id);
    }

    @Override
    public List<Review> findAllReview(int uid) {
        return reviewDAO.listReview(uid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Review> findSomeReview(int uid, int start, int count) {
        return reviewDAO.listReview(uid, start, count);
    }
}
