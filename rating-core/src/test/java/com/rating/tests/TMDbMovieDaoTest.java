package com.rating.tests;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.rating.TMDbMovieDaoImpl;
import com.rating.exception.MovieNotFoundException;
import com.rating.model.Cast;
import com.rating.model.Movie;

/**
 * 
 * @author miguel
 *
 */
public class TMDbMovieDaoTest {

    Log log = LogFactory.getLog(TMDbMovieDaoTest.class);
    
    @Test
    public void basicTest() {
        getMovieWithQuery("Star Trek: El Futuro Comienza"); 
    }

    @Test
    public void castTest() {        
        TMDbMovieDaoImpl movieDao = new TMDbMovieDaoImpl();
        Movie movie = getMovieWithQuery("Star Trek: El Futuro Comienza");
        
        try {
            Cast cast = movieDao.getMovieCast(movie.getId());
            Assert.assertNotNull(cast);
            Assert.assertTrue(cast.getDirectors().get(0).getName().equals("J.J. Abrams"));
        } catch (MovieNotFoundException e) {
           Assert.fail();
        }
       
    }

    /**
     * TODO: Inheritable by all movie-related unit tests TODO: This makes
     * integration tests instead of unit ones...
     * 
     * @param query
     * @return
     */
    private Movie getMovieWithQuery(String query) {
        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();

        // Search
        List<Movie> movies = TMDbClient.search(query, "en", true);

        // Select one
        Movie movie = movies.get(0);
        Assert.assertTrue(movie.getPosterPathSmall().contains("http"));

        // Get full Info
        try {
            movie = TMDbClient.getMovieInfo(movie.getId(), "es");
        } catch (MovieNotFoundException e) {
            Assert.fail();
        }
        return movie;
    }

}
