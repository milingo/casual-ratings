package com.rating.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

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
import com.rating.model.RottenTomatoes.RTReview;
import com.rating.model.RottenTomatoes.RTReviewsResponse;

/**
 * 
 * @author miguel
 * 
 */
public class RottenTomatoesRatingDaoImpl implements MovieInfoDao {

    private static final String API_PROPERTIES = "/com/rating/rotten.properties";
    private static final String API_KEY_PROPERTY = "api.key";

    private static final String ROTTEN_TOMATOES_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0";
    private static final String ROTTEN_TOMATOES_ALIAS_PATH = "/movie_alias.json";
    private static final String ROTTEN_TOMATOES_REVIEWS_PATH = "/movies/{0}/reviews.json";

    private static final String API_KEY_PARAMETER = "apikey";
    private static final String ALIAS_IMDB_PARAMETER = "type";
    private static final String ALIAS_IMDB_ID_PARAMETER = "id";
    private static final String REVIEW_TYPE_PARAMETER = "review_type";
    // private static final String PAGE_PARAMETER = "page";
    // private static final String PAGE_LIMIT_PARAMETER = "page_limit";
    // private static final String COUNTRY_PARAMETER = "country";

    private static final String ERROR_TAG = "error";
    private static final String MOVIE_NOT_RATED_ERROR = "-1";
    private static final String MOVIE_NOT_FOUND_ERROR = "could not find a movie";

    Log log = LogFactory.getLog(RottenTomatoesRatingDaoImpl.class);

    public RottenTomatoesRatingDaoImpl() {
    }

    /**
     * 
     */
    public MovieInfo getReviewsDetails(Movie movie) throws MovieNotFoundException {

        MovieInfo movieInfo = new MovieInfo();
        try {
            JsonNode aliasResponse = getBasicInfo(movie);
            movieInfo.setCustomId(getCustomId(aliasResponse));
            movieInfo.setLink(getLink(aliasResponse));
            movieInfo.setRating(getRating(aliasResponse));
            movieInfo.setReviews(getReviews(aliasResponse));
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
	 * 
	 */
    private List<Review> getReviews(JsonNode basicInfo) throws MovieNotFoundException,
            ReviewsNotFoundException {
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair(API_KEY_PARAMETER, getApiConfig().getProperty(API_KEY_PROPERTY)));
        nameValuePairs.add(new BasicNameValuePair(REVIEW_TYPE_PARAMETER, "top_critic"));
        String reviewsPath = MessageFormat.format(ROTTEN_TOMATOES_REVIEWS_PATH,
                new Object[] { getCustomId(basicInfo) });
        HttpGet request = buildRequest(ROTTEN_TOMATOES_BASE_URL + reviewsPath, nameValuePairs);
        request.setHeader("Accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(rd);
            JsonNode jsonResponse = mapper.readTree(jp);
            log.info(jsonResponse);

            // Movie not found :
            // {"error":"Could not find a movie with the specified id"}
            JsonNode error = jsonResponse.get(ERROR_TAG);
            if (error != null) {
                String errorDescription = error.asText();
                if (errorDescription.toLowerCase().startsWith(MOVIE_NOT_FOUND_ERROR)) {
                    throw new MovieNotFoundException();
                } else {
                    log.error("ROTTEN TOMATOES ERROR with movie with rotten tomatoes id = "
                            + getCustomId(basicInfo) + ": " + errorDescription);
                }
            }

            RTReviewsResponse rottenReviews = mapper.readValue(jsonResponse,
                    RTReviewsResponse.class);
            if (rottenReviews == null || rottenReviews.getReviews() == null
                    || rottenReviews.getReviews().isEmpty()) {
                throw new ReviewsNotFoundException();
            }

            // Convert them to model entities
            List<Review> reviews = new ArrayList<Review>();
            for (RTReview rottenReview : rottenReviews.getReviews()) {
                if (rottenReview.getQuote() != null && rottenReview.getQuote() != "") {
                    Review review = new Review();
                    review.setSource(Provider.ROTTEN_TOMATOES);
                    review.setCritic(rottenReview.getCritic());
                    review.setQuote(rottenReview.getQuote());
                    review.setScore(valueOf(rottenReview.getFreshness()));
                    review.setOriginalSource(rottenReview.getPublication());
                    if (rottenReview.getLinks() != null
                            && rottenReview.getLinks().getReview() != null) {
                        review.setOriginalLink(rottenReview.getLinks().getReview());
                    }
                    reviews.add(review);
                }
            }
            return reviews;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     */
    private String getLink(JsonNode basicInfo) {
        String link = basicInfo.get("links").get("alternate").asText();
        return link;
    }

    /**
     * 
     */
    private String getCustomId(JsonNode basicInfo) {
        String id = basicInfo.get("id").asText();
        return id;
    }

    /**
     * Inaccurancies: GIA (0123865), Amour (1602620), Transformers (0418279),
     * Star Wars: Episode I - The Phantom Menace (0120915), Aladdin (0103639),
     * Naranja mecanica
     */
    public Rating getRating(JsonNode basicInfo) throws MovieNotRatedYetException {
        String ratingString = basicInfo.get("ratings").get("critics_score").asText();
        log.info(ratingString);
        if (ratingString.equals(MOVIE_NOT_RATED_ERROR)) {
            throw new MovieNotRatedYetException();
        }

        Rating rating = new Rating(ratingString, 0);
        return rating;
    }

    /**
     * 
     */
    private JsonNode getBasicInfo(Movie movie) throws MovieNotFoundException {
        DefaultHttpClient client = new DefaultHttpClient();
        
        // TODO: Move to a common class!!!!!
        client.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(final HttpResponse response,
                    final HttpContext context) throws HttpException,
                    IOException {
                HttpEntity entity = response.getEntity();
                Header encheader = entity.getContentEncoding();
                if (encheader != null) {
                    HeaderElement[] codecs = encheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(
                                    entity));
                            return;
                        }
                    }
                }
            }
        });

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair(API_KEY_PARAMETER, getApiConfig().getProperty(API_KEY_PROPERTY)));
        nameValuePairs.add(new BasicNameValuePair(ALIAS_IMDB_PARAMETER, "imdb"));
        nameValuePairs.add(new BasicNameValuePair(ALIAS_IMDB_ID_PARAMETER, movie.getImdb_id()
                .substring(2)));
        HttpGet request = buildRequest(ROTTEN_TOMATOES_BASE_URL + ROTTEN_TOMATOES_ALIAS_PATH,
                nameValuePairs);
        request.setHeader("Accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(rd);
            JsonNode jsonResponse = mapper.readTree(jp);
            log.info(jsonResponse);

            // Movie not found :
            // {"error":"Could not find a movie with the specified id"}
            JsonNode error = jsonResponse.get(ERROR_TAG);
            if (error != null) {
                String errorDescription = error.asText();
                if (errorDescription.toLowerCase().startsWith(MOVIE_NOT_FOUND_ERROR)) {
                    throw new MovieNotFoundException();
                } else {
                    log.error("ROTTEN TOMATOES ERROR with movie with imdb id = "
                            + movie.getImdb_id() + ": " + errorDescription);
                }
            }

            return jsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     * @param url
     * @param qparams
     * @return
     */
    private HttpGet buildRequest(String url, List<NameValuePair> qparams) {
        if (qparams != null) {
            url = url + "?" + URLEncodedUtils.format(qparams, "UTF-8");
        }
        return new HttpGet(url);
    }

    /**
     * 
     * @return
     */
    private ReviewScore valueOf(String rottenFreshness) {
        if (rottenFreshness.equals("fresh")) {
            return ReviewScore.POSITIVE;
        }
        if (rottenFreshness.equals("rotten")) {
            return ReviewScore.NEGATIVE;
        }
        return ReviewScore.NEUTRAL;
    }


    // TODO: Properties Util and inject
    public Properties getApiConfig() {
        Properties apiConfiguration = new Properties();
        try {
            apiConfiguration.load(this.getClass().getResourceAsStream(API_PROPERTIES));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return apiConfiguration;
    }
}
