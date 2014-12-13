package com.rating.model;

import java.util.List;

/**
 * 
 * @author miguel
 * 
 */
public class MovieInfo {

    private Rating rating;
    private List<Review> reviews;
    private String tagline;
    private String link;
    private String customId;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link
     *            the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the customId
     */
    public String getCustomId() {
        return customId;
    }

    /**
     * @param customId the customId to set
     */
    public void setCustomId(String customId) {
        this.customId = customId;
    }

}
