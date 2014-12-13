package com.rating.model.RottenTomatoes;

import java.util.List;

/**
 * 
 * @author miguel
 * 
 */
public class RTReviewsResponse {

    private String total;
    private List<RTReview> reviews;

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the reviews
     */
    public List<RTReview> getReviews() {
        return reviews;
    }

    /**
     * @param reviews
     *            the reviews to set
     */
    public void setReviews(List<RTReview> reviews) {
        this.reviews = reviews;
    }

}