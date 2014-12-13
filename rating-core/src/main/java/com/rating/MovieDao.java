package com.rating;

import java.util.List;

import com.rating.exception.MovieNotFoundException;
import com.rating.model.Cast;
import com.rating.model.Movie;
import com.rating.model.MovieImages;
import com.rating.model.Person;

/**
 * 
 * @author miguel
 * 
 */
public interface MovieDao {

    /**
     * 
     * @param query
     * @param language
     * @return
     */
    public List<Movie> search(String query, String language, boolean autocomplete);

    /**
     * 
     * @param movieId
     * @param language
     * @return
     * @throws MovieNotFoundException
     */
    public Movie getMovieInfo(String movieId, String language) throws MovieNotFoundException;

    /**
     * 
     * @param movieId
     * @return
     * @throws MovieNotFoundException
     */
    public Cast getMovieCast(String movieId) throws MovieNotFoundException;

    /**
     * 
     * @param movieId
     * @return
     * @throws MovieNotFoundException
     */
    public MovieImages getImages(String movieId) throws MovieNotFoundException;
    
    /**
     * 
     * @return
     */
    public Person getPerson(String id, String language);

}
