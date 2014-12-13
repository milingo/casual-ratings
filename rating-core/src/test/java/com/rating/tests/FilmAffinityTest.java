package com.rating.tests;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.rating.TMDbMovieDaoImpl;
import com.rating.dao.MovieInfoDao;
import com.rating.exception.MovieNotFoundException;
import com.rating.dao.impl.FilmAffinityRatingDaoImpl;
import com.rating.model.Movie;
import com.rating.model.MovieInfo;

public class FilmAffinityTest {

    Log log = LogFactory.getLog(FilmAffinityTest.class);
    

//    @Test
//    public void searchTitleWithSeveralMatches1Test() {
//        searchTitleWithSeveralMatches("Star trek");
//    }
//
//    /**
//     * 
//     */
//    @Test
//    public void searchTitleWithSeveralMatches2Test() {
//        searchTitleWithSeveralMatches("La caza");
//    }

    public void searchTitleWithSeveralMatches(String searchString) {
        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();

        // Search
        String language = "es";
        List<Movie> movies = TMDbClient.search(searchString, language, true);

        // Select one
        Movie movie = movies.get(0);
        Assert.assertTrue(movie.getPosterPathSmall().contains("http"));

        // FILM AFFINITY
        MovieInfoDao affinityRatingRatingDao = new FilmAffinityRatingDaoImpl();
        try {
            // Get full Info
            movie = TMDbClient.getMovieInfo(movie.getId(), language);

            MovieInfo affinityInfo = affinityRatingRatingDao.getReviewsDetails(movie);
            Assert.assertTrue(affinityInfo.getRating().getRating() != "");
            Assert.assertNotNull(affinityInfo.getReviews());
        } catch (MovieNotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void searchTitleWithStrangeChars1Test() {
        searchTitleWithStrangeChars("La guerra de las galaxias: Episodio IV - Una nueva esperanza");
    }

//    @Test
//    public void searchTitleWithStrangeChars2Test() {
//        searchTitleWithStrangeChars("La habitación de Fermat");
//    }

    /**
     * 
     * @param searchString
     */
    public void searchTitleWithStrangeChars(String searchString) {
        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();

        // Search
        String language = "es";
        List<Movie> movies = TMDbClient.search(searchString, language, true);

        // Select one
        Movie movie = movies.get(0);

        // FILM AFFINITY
        MovieInfoDao affinityRatingRatingDao = new FilmAffinityRatingDaoImpl();
        try {
            // Get full Info
            movie = TMDbClient.getMovieInfo(movie.getId(), language);

            MovieInfo affinityInfo = affinityRatingRatingDao.getReviewsDetails(movie);
            Assert.assertTrue(affinityInfo.getRating().getRating() != "");
            Assert.assertNotNull(affinityInfo.getReviews());
        } catch (MovieNotFoundException e) {
            Assert.fail();
        }

    }

}
