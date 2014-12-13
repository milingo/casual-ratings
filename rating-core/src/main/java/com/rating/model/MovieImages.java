package com.rating.model;

import java.util.List;

public class MovieImages {

    private List<MovieImage> backdrops;
    private List<MovieImage> posters;

    /**
     * @return the backdrops
     */
    public List<MovieImage> getBackdrops() {
        return backdrops;
    }

    /**
     * @param backdrops
     *            the backdrops to set
     */
    public void setBackdrops(List<MovieImage> backdrops) {
        this.backdrops = backdrops;
    }

    /**
     * @return the posters
     */
    public List<MovieImage> getPosters() {
        return posters;
    }

    /**
     * @param posters
     *            the posters to set
     */
    public void setPosters(List<MovieImage> posters) {
        this.posters = posters;
    }

}
