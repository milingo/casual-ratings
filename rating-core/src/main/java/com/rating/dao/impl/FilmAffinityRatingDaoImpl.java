package com.rating.dao.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rating.dao.MovieInfoDao;
import com.rating.exception.MovieNotFoundException;
import com.rating.exception.MovieNotRatedYetException;
import com.rating.exception.ReviewsNotFoundException;
import com.rating.model.Movie;
import com.rating.model.MovieInfo;
import com.rating.model.Provider;
import com.rating.model.Rating;
import com.rating.model.Review;
import com.rating.model.ReviewScore;

/**
 * Takes care of the Film Affinity data source.
 * 
 * @author miguel
 * 
 */
public class FilmAffinityRatingDaoImpl implements MovieInfoDao {

    private static final String FILMAFFINITY_URL = "http://www.filmaffinity.com/";
    private static final String FILMAFFINITY_SEARCH_URL = FILMAFFINITY_URL + "en/advsearch.php";

    private static final String MOVIE_LINK_HTML_PATTERN = "a[href*=/en/film]";
    // This is for the /en/film version
    // private static final String MOVIE_RATING_HTML_PATTERN =
    // "[style*=color:#990000; font-size:22px; font-weight: bold;]";
    // and this is or the /es/film version
    private static final String MOVIE_RATING_HTML_PATTERN = "div[id*=movie-rat-avg]";
    private static final String MOVIE_REVIEWS_HTML_PATTERN = ".pro-review";

    private static final String SEARCH_PARAM_QUERY = "stext";
    private static final String SEARCH_PARAM_QUERY_BY = "stype[]";
    private static final String SEARCH_PARAM_QUERY_YEAR_FROM = "fromyear";
    private static final String SEARCH_PARAM_QUERY_YEAR_TO = "toyear";

    Log log = LogFactory.getLog(FilmAffinityRatingDaoImpl.class);

    /**
     * Constructor.
     */
    public FilmAffinityRatingDaoImpl() {
    }

    /**
     * 
     */
    public MovieInfo getReviewsDetails(Movie movie) throws MovieNotFoundException {

        MovieInfo movieInfo = new MovieInfo();
        try {
            movieInfo.setLink(getLink(movie));
            Document mainPage = getMainPage(movieInfo.getLink());
            movieInfo.setRating(getRating(mainPage));
            movieInfo.setReviews(getReviews(mainPage));
        } catch (MovieNotFoundException e) {
            throw e;
        } catch (MovieNotRatedYetException e) {
            movieInfo.setRating(new Rating("", 0));
        } catch (ReviewsNotFoundException e) {
            movieInfo.setReviews(null);
        } catch (Exception e) {
            // Return the movieInfo at it is...
        }
        return movieInfo;
    }

    /**
     * Gets the Film Affinity html for the given movie.
     */
    private Document getMainPage(String link) throws MovieNotFoundException {
        try {
            Document movieHtmlPage = Jsoup.connect(link).timeout(0).get();
            return movieHtmlPage;
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new MovieNotFoundException();
        }
    }

    /**
     * Gets the Film Affinity link for the given movie.
     */
    private String getLink(Movie movie) throws MovieNotFoundException {

        Document searchPage = null;
        String relativePath = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

            // http://www.filmaffinity.com/es/advsearch.php?stext=Matrix&stype%5B%5D=title&genre=&country=&fromyear=1999&toyear=1999
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair(SEARCH_PARAM_QUERY, movie.getOriginal_title()));
            params.add(new BasicNameValuePair(SEARCH_PARAM_QUERY_BY, "title"));
            params.add(new BasicNameValuePair(SEARCH_PARAM_QUERY_YEAR_FROM, dateFormat.format(movie
                    .getReleaseDate())));
            params.add(new BasicNameValuePair(SEARCH_PARAM_QUERY_YEAR_TO, dateFormat.format(movie
                    .getReleaseDate())));
            String url = FILMAFFINITY_SEARCH_URL + "?"
                    + URLEncodedUtils.format(params, "ISO-8859-1");
            log.info("Filmaffinity URL: " + url.toString());

            long timestamp1 = new Date().getTime();
            searchPage = Jsoup.connect(url).timeout(0).get();
            long timestamp2 = new Date().getTime();
            log.info("Jsoup filmaffinity time: " + (timestamp2 - timestamp1));

            // Now we have the search page with one or more movies - let's try
            // to filter the results if there are more than one
            Map<String, String> possibleMovies = new HashMap<String, String>();

            // Possible values of this list are:
            // <a href="/es/film777460.html" class="">Up</a>
            // <a href="/es/film461062.html" class=""><img
            // src="http://pics.filmaffinity.com/Up_in_the_Air-461062-small.jpg"
            // border="0" /></a>
            Elements filmPathElements = searchPage.select(MOVIE_LINK_HTML_PATTERN);
            log.info(filmPathElements);
            for (Element el : filmPathElements) {
                if (!el.toString().contains("<img")) {
                    possibleMovies.put(el.text(), el.attr("href"));
                }
            }
            log.info(possibleMovies);

            if (filmPathElements == null || filmPathElements.size() == 0) {
                log.info(searchPage);
                throw new MovieNotFoundException();
            }

            boolean moreThanOneMovie = possibleMovies.size() > 1;
            if (moreThanOneMovie) {
                for (String possibleTitle : possibleMovies.keySet()) {
                    if (movie.getOriginal_title().toLowerCase().equals(possibleTitle.toLowerCase())) {
                        relativePath = possibleMovies.get(possibleTitle);
                    }
                }

                if (relativePath == null) {
                    log.info(searchPage);
                    throw new MovieNotFoundException();
                }

            } else {
                relativePath = filmPathElements.get(0).attr("href");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new MovieNotFoundException();
        }

        // TODO: The title of our movie is in English (we looked it up that way)
        // but FilmAffinityRatingDAO is prepared to scrap on the Spanish page of
        // the film (does not work on the English one). Luckily, the search page
        // works on both languages, so we search the movie on the English page
        // and then turn the link into the Spanish one. Quick fix for now...
        return FILMAFFINITY_URL + relativePath.replace("/en/", "/es/");
    }

    /**
     * Film Affinity rating for the movie represented by the given Document.
     */
    private Rating getRating(Document page) throws MovieNotRatedYetException {
        Elements ratingElements = page.select(MOVIE_RATING_HTML_PATTERN);
        if (ratingElements == null || ratingElements.isEmpty()) {
            throw new MovieNotRatedYetException();
        } else {
            String affinityRating = ratingElements.get(0).text();
            log.info(affinityRating);
            return new Rating(affinityRating, 0);
        }
    }

    /**
	 * 
	 */
    private List<Review> getReviews(Document page) throws ReviewsNotFoundException {
        Elements faReviews = page.select(MOVIE_REVIEWS_HTML_PATTERN);
        
        if (faReviews == null || faReviews.isEmpty()) {
            throw new ReviewsNotFoundException();
        } else {
            
            // Convert them to model entities
            List<Review> reviews = new ArrayList<Review>();
            for (Element faReview : faReviews) {
                Elements possibleQuotes = faReview.getElementsByTag("div");
                if (possibleQuotes != null && possibleQuotes.size() > 0) {
                    Review review = new Review();
                    review.setSource(Provider.FILM_AFFINITY);

                    String quote = possibleQuotes.get(1).text().replace(String.valueOf((char) 160), " ").trim();
                    if (quote.startsWith("\"")) {
                        quote = quote.substring(1, quote.length() - 1);
                    }
                    String[] quoteSplitted = quote.split("Puntuaci");
                    review.setQuote(quoteSplitted[0]);

                    Elements possibleSources = faReview.getElementsByClass("pro-crit-med");
                    String[] sourcePair = possibleSources.get(0).text().split(":");
                    if (sourcePair != null && sourcePair.length == 1) {
                        review.setOriginalSource(sourcePair[0]);
                    } else if (sourcePair != null && sourcePair.length == 2) {
                        review.setCritic(sourcePair[0]);
                        review.setOriginalSource(sourcePair[1]);
                    }

                    // Scores are inside "img" elements with a "title" attribute
                    Elements possibleScores = faReview.getElementsByTag("img");
                    if (possibleScores != null && !possibleScores.isEmpty()) {
                        for (Element score :possibleScores) {
                            if (score.getElementsByAttribute("title") != null) {
                                review.setScore(valueOf(score.attr("title")));
                            }
                        }
                    }

                    reviews.add(review);
                }
            }
            return reviews;
        }
    }

    // <div class="pro-review">
    // <a title="Leer cr&iacute;tica completa"
    // href="http://www.rollingstone.com/reviews/movie/26845701/review/28454855/up"
    // target="blank">
    // <div>
    // &quot;Un impresionante viaje al reino de la pura imaginaci&oacute;n.
    // (...) maravillosamente divertida y conmovedora. (...) 'Up' logra un
    // milagro. Simplemente si&eacute;ntense y cont&eacute;mplenla volar. (...)
    // Puntuaci&oacute;n: &#9733;&#9733;&#9733;&#9733; (sobre 4).&quot;&nbsp;
    // <img alt="external-link" src="/images/full_review_icon.gif" />
    // </div> </a>
    // <div class="pro-crit-med">
    // Peter Travers: Rolling Stone
    // <img align="top" alt="cr&iacute;tica positiva"
    // title="cr&iacute;tica positiva" src="/images/gif/pro_pos3.gif" />
    // </div>
    // </div>

    
    /**
     * 
     * @return
     */
    private ReviewScore valueOf(String faScore) {
        if (faScore.contains("positiva")) {
            return ReviewScore.POSITIVE;
        }
        if (faScore.contains("negativa")) {
            return ReviewScore.NEGATIVE;
        }
        return ReviewScore.NEUTRAL;
    }
    
}
