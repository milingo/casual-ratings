package com.rating.model;

import java.util.Date;

public class Review {

    private Date date;
    private String critic;
    private ReviewScore score;
    private String originalSource;
    private Provider source;
    private String quote;
    private String originalLink;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
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
     * @return the originalSource
     */
    public String getOriginalSource() {
        return originalSource;
    }

    /**
     * @param originalSource
     *            the originalSource to set
     */
    public void setOriginalSource(String originalSource) {
        this.originalSource = originalSource;
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
     * @return the originalLink
     */
    public String getOriginalLink() {
        return originalLink;
    }

    /**
     * @param originalLink
     *            the originalLink to set
     */
    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    /**
     * @return the source
     */
    public Provider getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Provider source) {
        this.source = source;
    }

    /**
     * @return the score
     */
    public ReviewScore getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(ReviewScore score) {
        this.score = score;
    }

}
