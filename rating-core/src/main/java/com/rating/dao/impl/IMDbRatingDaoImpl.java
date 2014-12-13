package com.rating.dao.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.rating.dao.MovieInfoDao;
import com.rating.exception.MovieNotFoundException;
import com.rating.exception.MovieNotRatedYetException;
import com.rating.exception.ReviewsNotFoundException;
import com.rating.model.Movie;
import com.rating.model.MovieInfo;
import com.rating.model.Rating;

public class IMDbRatingDaoImpl implements MovieInfoDao {

    private static final String IMDB_BASE_URL = "http://www.imdb.com/";

    Log log = LogFactory.getLog(IMDbRatingDaoImpl.class);

    public IMDbRatingDaoImpl() {
    }

    /**
     * 
     */
    public MovieInfo getReviewsDetails(Movie movie) throws MovieNotFoundException {

        MovieInfo movieInfo = new MovieInfo();
        try {
            movieInfo.setLink(getLink(movie));
            Document mainPage = getMainPage(getLink(movie));
            movieInfo.setRating(getRating(mainPage));
            // movieInfo.setReviews(getReviews(mainPage));
        } catch (MovieNotFoundException e) {
            throw e;
        } catch (MovieNotRatedYetException e) {
            movieInfo.setRating(new Rating("", 0));
        } catch (Exception e) {
            // Return the movieInfo at it is...
        }
        return movieInfo;
    }

    /**
     * 
     * @param movie
     * @return
     * @throws MovieNotFoundException
     */
    private String getLink(Movie movie) throws MovieNotFoundException {
        if (movie.getImdb_id() == null) {
            throw new MovieNotFoundException();
        }
        return IMDB_BASE_URL + "title/" + movie.getImdb_id();
    }

    /**
     * 
     * @param link
     * @return
     * @throws MovieNotFoundException
     */
    private Document getMainPage(String link) throws MovieNotFoundException {
        if (link == null || link.equals("")) {
            throw new MovieNotFoundException();
        }

        Document doc1 = null;
        try {
            long timestamp1 = new Date().getTime();
            doc1 = Jsoup.connect(link).get();
            long timestamp2 = new Date().getTime();
            log.info("Jsoup imdb time: " + (timestamp2 - timestamp1));
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new MovieNotFoundException();
        }

        return doc1;
    }

    /**
     * 
     * @param page
     * @return
     * @throws MovieNotRatedYetException
     */
    private Rating getRating(Document page) throws MovieNotRatedYetException {
        Elements startDetails = page.select("div[class=star-box-details]");
        Elements ratingElements = startDetails.get(0).select("span[itemprop=ratingValue]");

        if (ratingElements == null || ratingElements.isEmpty()) {
            throw new MovieNotRatedYetException();
        } else {
            String imdbRating = ratingElements.get(0).text();
            log.info(imdbRating);
            Rating rating = new Rating(imdbRating, 0);
            return rating;
        }
    }

    /**
     * 
     * @param page
     * @return
     * @throws ReviewsNotFoundException
     */
    public List<String> getReviews(Document page) throws ReviewsNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
