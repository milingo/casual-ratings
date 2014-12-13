package com.rating.model.RottenTomatoes;

/**
 * 
 * @author miguel
 * 
 */
public class RTReview {

    private String date;
    private String critic;
    private String original_score;
    private String freshness;
    private String publication;
    private String quote;
    private RTLinks links;

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the critic
     */
    public String getCritic() {
        return critic;
    }

    /**
     * @param critic
     *            the critic to set
     */
    public void setCritic(String critic) {
        this.critic = critic;
    }

    /**
     * @return the original_score
     */
    public String getOriginal_score() {
        return original_score;
    }

    /**
     * @param original_score
     *            the original_score to set
     */
    public void setOriginal_score(String original_score) {
        this.original_score = original_score;
    }

    /**
     * @return the freshness
     */
    public String getFreshness() {
        return freshness;
    }

    /**
     * @param freshness
     *            the freshness to set
     */
    public void setFreshness(String freshness) {
        this.freshness = freshness;
    }

    /**
     * @return the publication
     */
    public String getPublication() {
        return publication;
    }

    /**
     * @param publication
     *            the publication to set
     */
    public void setPublication(String publication) {
        this.publication = publication;
    }

    /**
     * @return the quote
     */
    public String getQuote() {
        return quote;
    }

    /**
     * @param quote
     *            the quote to set
     */
    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * @return the links
     */
    public RTLinks getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(RTLinks links) {
        this.links = links;
    }

}
