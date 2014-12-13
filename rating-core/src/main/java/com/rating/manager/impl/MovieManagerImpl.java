package com.rating.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rating.MovieDao;
import com.rating.TMDbMovieDaoImpl;
import com.rating.dao.MovieInfoDao;
import com.rating.dao.MovieInfoDaoFactory;
import com.rating.exception.MovieNotFoundException;
import com.rating.manager.MovieManager;
import com.rating.model.Cast;
import com.rating.model.Image;
import com.rating.model.Movie;
import com.rating.model.MovieImage;
import com.rating.model.MovieImages;
import com.rating.model.MovieInfo;
import com.rating.model.Person;
import com.rating.model.Provider;

/**
 * 
 * @author miguel
 * 
 */
@Service
public class MovieManagerImpl implements MovieManager {

    static Log log = LogFactory.getLog(TMDbMovieDaoImpl.class);
    static final String COOL_POSTERS_PROPERTIES = "/com/rating/poster.properties";

    @Autowired
    private MovieDao movieDao;

    public MovieManagerImpl() {
    }

    /**
     * 
     */
    public List<Movie> search(String query, String language, boolean autocomplete) {
        return movieDao.search(query, language, autocomplete);
    }

    /**
     * 
     */
    public Movie getMovieInfo(String movieId, String language) throws MovieNotFoundException {
        Movie movie = movieDao.getMovieInfo(movieId, language);
        Cast cast = movieDao.getMovieCast(movieId);
        MovieImages images = movieDao.getImages(movieId);

        if (cast != null && cast.getCrew() != null) {
            movie.setDirectors(cast.getDirectors());
        }

        if (cast != null && cast.getCast() != null) {
            movie.setActors(cast.getCast());
        }

        if (images != null) {
            if (images.getBackdrops() != null) {
                List<Image> backdrops = new ArrayList<Image>();
                for (MovieImage tmdbImage : images.getBackdrops()) {
                    int smallIndex = 0;
                    int mediumIndex = (int) Math.floor(movie.getBackdropImageSizes().size() / 2.0);
                    int largeIndex = movie.getBackdropImageSizes().size() - 1;

                    String pathSmall = movie.getImages_base_url()
                            + movie.getBackdropImageSizes().get(smallIndex)
                            + tmdbImage.getFile_path();
                    String pathLarge = movie.getImages_base_url()
                            + movie.getBackdropImageSizes().get(largeIndex)
                            + tmdbImage.getFile_path();
                    String pathMedium = movie.getImages_base_url()
                            + movie.getBackdropImageSizes().get(mediumIndex)
                            + tmdbImage.getFile_path();
                    backdrops.add(new Image(pathSmall, pathMedium, pathLarge));
                }
                movie.setBackdropImages(backdrops);
            }
            if (images.getPosters() != null) {
                int smallIndex = 0;
                int mediumIndex = (int) Math.ceil(movie.getPosterImagesSizes().size() / 2.0);
                int largeIndex = movie.getPosterImagesSizes().size() - 1;

                List<Image> posters = new ArrayList<Image>();
                for (MovieImage tmdbImage : images.getPosters()) {
                    String pathSmall = movie.getImages_base_url()
                            + movie.getPosterImagesSizes().get(smallIndex)
                            + tmdbImage.getFile_path();
                    String pathLarge = movie.getImages_base_url()
                            + movie.getPosterImagesSizes().get(largeIndex)
                            + tmdbImage.getFile_path();
                    String pathMedium = movie.getImages_base_url()
                            + movie.getPosterImagesSizes().get(mediumIndex)
                            + tmdbImage.getFile_path();
                    posters.add(new Image(pathSmall, pathMedium, pathLarge));
                }
                movie.setPosterImages(posters);
            }
        }

        // Cool Posters
        Properties coolPosterProperties = new Properties();
        try {
            coolPosterProperties.load(this.getClass().getResourceAsStream(COOL_POSTERS_PROPERTIES));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String coolPosterPaths = coolPosterProperties.getProperty(movieId);
        if (coolPosterPaths != null) {
            String[] paths = coolPosterPaths.split(";");
            List<Image> coolPosterImages = new ArrayList<Image>();
            for (String p : paths) {
                String path = "/resources/posters/" + p;
                Image coolPoster = new Image(path, path, path);
                coolPosterImages.add(coolPoster);
            }
            movie.setPosterCoolImages(coolPosterImages);
        }

        log.info("MOVIE INFO: " + movie.toString());

        return movie;
    }

    /**
     * 
     */
    public MovieInfo getReviewsDetails(Movie movie, Provider provider)
            throws MovieNotFoundException {
        MovieInfoDao movieInfoDao = MovieInfoDaoFactory.getInstance().getMovieInfoDao(provider);
        return movieInfoDao.getReviewsDetails(movie);
    }

    /**
     * 
     * @param id
     * @return
     */
    public Person getPersonInfo(String id, String language) {
        Person person = movieDao.getPerson(id, language);
        person.setBiography(removeReferences(person.getBiography()));

        log.info("PERSON INFO: " + person.toString());

        return person;
    }

    /**
     * 
     * @param biography
     */
    private String removeReferences(String text) {
        if (text != null) {
            return text.replace("From Wikipedia, the free encyclopedia.", "")
                    .replace("From Wikipedia, the free encyclopedia", "").trim();
        }
        return text;
    }

}
