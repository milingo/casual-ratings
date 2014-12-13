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
import com.rating.dao.impl.IMDbRatingDaoImpl;
import com.rating.dao.impl.RottenTomatoesRatingDaoImpl;
import com.rating.model.Movie;
import com.rating.model.MovieInfo;

public class CompleteFlowIntegrationTest {

    Log log = LogFactory.getLog(CompleteFlowIntegrationTest.class);

    @Test
    public void searchTest() {
        searchTest(getMovie("Regreso al futuro"));
    }

    /**
     * 
     * @return
     */
    private Movie getMovie(String searchString) {
        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();

        // Search
        List<Movie> movies = TMDbClient.search(searchString, "es", true);

        // Select one
        Movie movie = movies.get(0);
        Assert.assertTrue(movie.getPosterPathSmall().contains("http"));

        return movie;
    }

    /**
     * 
     * @param movie
     */
    public void searchTest(Movie movie) {
        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();

        // IMDB
        MovieInfoDao imdbRatingRatingDao = new IMDbRatingDaoImpl();
        MovieInfo imdbInfo = null;
        try {
            // Get full Info
            movie = TMDbClient.getMovieInfo(movie.getId(), "es");
            imdbInfo = imdbRatingRatingDao.getReviewsDetails(movie);
        } catch (MovieNotFoundException e) {
            // Assert.fail();
        }

        // FILM AFFINITY
        MovieInfoDao affinityRatingRatingDao = new FilmAffinityRatingDaoImpl();
        MovieInfo affinityInfo = null;
        try {
            affinityInfo = affinityRatingRatingDao.getReviewsDetails(movie);
        } catch (MovieNotFoundException e) {
            // Assert.fail();
        }

        // ROTTEN TOMATOES
        MovieInfoDao rtRatingDao = new RottenTomatoesRatingDaoImpl();
        MovieInfo rottenInfo = null;
        try {
            rottenInfo = rtRatingDao.getReviewsDetails(movie);
        } catch (MovieNotFoundException e) {
            // Assert.fail();
        }

        log.info("TMDb: " + movie.getVote_average());
        if (imdbInfo.getRating() != null) {
            log.info("IMDb: " + imdbInfo.getRating().getRating());
        }
        if (affinityInfo.getRating() != null) {
            log.info("FilmAffinity: " + affinityInfo.getRating().getRating());
        }
        if (rottenInfo.getRating() != null) {
            log.info("RottenTomatoes: " + rottenInfo.getRating().getRating());
        }

    }

}
