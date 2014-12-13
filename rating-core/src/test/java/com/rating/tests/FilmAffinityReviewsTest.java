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
import com.rating.model.Review;
import com.rating.model.ReviewScore;

public class FilmAffinityReviewsTest {

//    Log log = LogFactory.getLog(FilmAffinityReviewsTest.class);
//
//    /**
//     *
//     */
//    @Test
//    public void reviewsScore1Test() {
//        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();
//
//        // Search
//        String searchString = "Mission Impossible";
//        String language = "en";
//        List<Movie> movies = TMDbClient.search(searchString, language, true);
//
//        // Select one
//        Movie movie = null;
//        int i = 0;
//        while (movie == null && i < movies.size()) {
//            if (movies.get(i).getRelease_year().equals("1996")) {
//                movie = movies.get(i);
//            }
//            i++;
//        }
//
//        // FILM AFFINITY
//        MovieInfoDao affinityRatingRatingDao = new FilmAffinityRatingDaoImpl();
//        try {
//            // Get full Info
//            movie = TMDbClient.getMovieInfo(movie.getId(), language);
//            MovieInfo affinityInfo = affinityRatingRatingDao.getReviewsDetails(movie);
//
//            Assert.assertNotNull(affinityInfo.getReviews());
//
//            boolean anyReviewWithScore = false;
//            int n = 0;
//            while (!anyReviewWithScore && n < affinityInfo.getReviews().size()) {
//                anyReviewWithScore = affinityInfo.getReviews().get(n).getScore() != null
//                        && affinityInfo.getReviews().get(n).getScore() != ReviewScore.NEUTRAL;
//                n++;
//            }
//            Assert.assertTrue(anyReviewWithScore);
//
//        } catch (MovieNotFoundException e) {
//            Assert.fail();
//        }
//    }
//
//    /**
//     *
//     */
//    @Test
//    public void reviewsScore2Test() {
//        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();
//
//        // Search
//        String searchString = "Pacific Rim";
//        String language = "es";
//        List<Movie> movies = TMDbClient.search(searchString, language, true);
//
//        // Select one
//        Movie movie = movies.get(0);
//
//        // FILM AFFINITY
//        MovieInfoDao affinityRatingRatingDao = new FilmAffinityRatingDaoImpl();
//        try {
//            // Get full Info
//            movie = TMDbClient.getMovieInfo(movie.getId(), language);
//            MovieInfo affinityInfo = affinityRatingRatingDao.getReviewsDetails(movie);
//
//            Assert.assertNotNull(affinityInfo.getReviews());
//
//            boolean anyReviewWithScore = false;
//            int n = 0;
//            while (!anyReviewWithScore && n < affinityInfo.getReviews().size()) {
//                anyReviewWithScore = affinityInfo.getReviews().get(n).getScore() != null
//                        && affinityInfo.getReviews().get(n).getScore() != ReviewScore.NEUTRAL;
//                n++;
//            }
//            Assert.assertTrue(anyReviewWithScore);
//
//        } catch (MovieNotFoundException e) {
//            Assert.fail();
//        }
//    }
//
//    /**
//     *
//     */
//    @Test
//    public void reviewsFormat1Test() {
//        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();
//
//        // Search
//        String searchString = "Charade";
//        String language = "es";
//        List<Movie> movies = TMDbClient.search(searchString, language, true);
//
//        // Select one
//        Movie movie = null;
//        int i = 0;
//        while (movie == null && i < movies.size()) {
//            if (movies.get(i).getRelease_year().equals("1963")) {
//                movie = movies.get(i);
//            }
//            i++;
//        }
//
//        // FILM AFFINITY
//        MovieInfoDao affinityRatingRatingDao = new FilmAffinityRatingDaoImpl();
//        try {
//            // Get full Info
//            movie = TMDbClient.getMovieInfo(movie.getId(), language);
//            MovieInfo affinityInfo = affinityRatingRatingDao.getReviewsDetails(movie);
//
//            Assert.assertNotNull(affinityInfo.getReviews());
//            for (Review review : affinityInfo.getReviews()) {
//                if (review.getCritic().equals("Fernando Morales")) {
//                    // Tests the correct removal of "
//                    Assert.assertFalse(review.getQuote().contains("\""));
//                    Assert.assertTrue(review.getQuote().contains("Buena"));
//                }
//            }
//        } catch (MovieNotFoundException e) {
//            Assert.fail();
//        }
//    }
//
//    /**
//     *
//     */
//    @Test
//    public void reviewsFormat2Test() {
//        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();
//
//        // Search
//        String searchString = "Guerra Mundial Z";
//        String language = "es";
//        List<Movie> movies = TMDbClient.search(searchString, language, true);
//
//        // Select one
//        Movie movie = movies.get(0);
//
//        // FILM AFFINITY
//        MovieInfoDao affinityRatingRatingDao = new FilmAffinityRatingDaoImpl();
//        try {
//            // Get full Info
//            movie = TMDbClient.getMovieInfo(movie.getId(), language);
//            MovieInfo affinityInfo = affinityRatingRatingDao.getReviewsDetails(movie);
//
//            Assert.assertNotNull(affinityInfo.getReviews());
//            for (Review review : affinityInfo.getReviews()) {
//                // Tests the correct removal of "
//                Assert.assertFalse(review.getQuote().contains("\""));
//            }
//        } catch (MovieNotFoundException e) {
//            Assert.fail();
//        }
//    }

}
