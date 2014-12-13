package com.rating.manager;

import java.util.List;

import com.rating.exception.MovieNotFoundException;
import com.rating.exception.MovieNotRatedYetException;
import com.rating.exception.ReviewsNotFoundException;
import com.rating.model.Movie;
import com.rating.model.MovieInfo;
import com.rating.model.Person;
import com.rating.model.Provider;

public interface MovieManager {

    /**
     * 
     * @param req
     * @param language
     * @return
     */
    public List<Movie> search(String req, String language, boolean autocomplete);

    /**
     * 
     * @param movieId
     * @return
     * @throws MovieNotFoundException
     */
    public Movie getMovieInfo(String movieId, String language) throws MovieNotFoundException;

    /**
     * 
     * @param movie
     * @param provider
     * @return
     * @throws MovieNotFoundException
     * @throws MovieNotRatedYetException
     * @throws ReviewsNotFoundException
     */
    public MovieInfo getReviewsDetails(Movie movie, Provider provider) throws MovieNotFoundException;
    
    /**
     * 
     * @param id
     * @return
     */
    public Person getPersonInfo(String id, String language);

}
