package com.rating.dao;

import com.rating.exception.MovieNotFoundException;
import com.rating.model.Movie;
import com.rating.model.MovieInfo;

/**
 * 
 * @author miguel
 *
 */
public interface MovieInfoDao {

    /**
     * 
     * @param movie
     * @return
     * @throws com.rating.exception.MovieNotFoundException
     */
    public MovieInfo getReviewsDetails(Movie movie) throws MovieNotFoundException;

}
