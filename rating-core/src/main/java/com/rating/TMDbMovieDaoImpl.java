package com.rating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import com.rating.exception.MovieNotFoundException;
import com.rating.model.Cast;
import com.rating.model.Configuration;
import com.rating.model.Movie;
import com.rating.model.MovieCredit;
import com.rating.model.MovieImages;
import com.rating.model.Person;
import com.rating.model.ProfileImage;
import com.rating.utils.BetterMatchComparator;
import com.rating.utils.MovieCreditComparator;

/**
 * 
 * @author miguel
 * 
 */
@Repository
public class TMDbMovieDaoImpl implements MovieDao {

    private static final String API_PROPERTIES = "/com/rating/tmdb.properties";
    private static final String API_KEY_PROPERTY = "api.key";

    private static final String BASE_URL = "http://api.themoviedb.org/3";
    private static final String AUTOCOMPLETE_VALUE = "ngram";
    private static final String ACTOR_IMAGES_VALUE = "images";
    private static final String ACTOR_MOVIES_VALUE = "movie_credits";

    private static final String API_KEY_PARAM = "api_key";
    private static final String QUERY_PARAM = "query";
    private static final String LANGUAGE_PARAM = "language";
    private static final String AUTOCOMPLETE_PARAM = "search_type";
    private static final String EXTENSION_PARAM = "append_to_response";

    static Log log = LogFactory.getLog(TMDbMovieDaoImpl.class);

    private static Configuration configuration;

    /**
     * Constructor. Gets the (static) TMDb configuration.
     */
    public TMDbMovieDaoImpl() {
        configuration = configure();
    }

    /**
     * 
     * @return
     */
    public Configuration configure() {
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("api_key", getApiConfig().getProperty(API_KEY_PROPERTY)));
        HttpGet request = buildRequest(BASE_URL + "/configuration", nameValuePairs);

        Configuration configuration = null;

        try {
            HttpResponse response = client.execute(request);
            // Get the response

            String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
            // BufferedReader resp = new BufferedReader(new InputStreamReader(
            // response.getEntity().getContent()));

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(resp);
            JsonNode jsonResponse = mapper.readTree(jp);

            configuration = mapper.readValue(jsonResponse, Configuration.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return configuration;
    }

    /**
     * 
     */
    public List<Movie> search(String query, String language, boolean autocomplete) {
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(API_KEY_PARAM, getApiConfig().getProperty(API_KEY_PROPERTY)));
        nameValuePairs.add(new BasicNameValuePair(QUERY_PARAM, query));
        nameValuePairs.add(new BasicNameValuePair(LANGUAGE_PARAM, language));
        if (autocomplete) {
            nameValuePairs.add(new BasicNameValuePair(AUTOCOMPLETE_PARAM, AUTOCOMPLETE_VALUE));
        }
        HttpGet request = buildRequest(BASE_URL + "/search/movie", nameValuePairs);

        try {
            HttpResponse response = client.execute(request);
            // Get the response
            String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
            // BufferedReader resp = new BufferedReader(new InputStreamReader(
            // response.getEntity().getContent()));

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(resp);
            JsonNode jsonResponse = mapper.readTree(jp);
            log.info("Matching Films: " + jsonResponse.get("results"));

            List<Movie> movies = new ArrayList<Movie>();
            for (JsonNode node : jsonResponse.get("results")) {
                Movie movie = mapper.readValue(node, Movie.class);
                movie.setImages_base_url(configuration.getImages().getBase_url());
                movie.setImages_secure_base_url(configuration.getImages().getSecure_base_url());
                movie.setBackdropImageSizes(configuration.getImages().getBackdrop_sizes());
                movie.setPosterImagesSizes(configuration.getImages().getPoster_sizes());
                movies.add(movie);
            }

            Collections.sort(movies, Collections.reverseOrder(new BetterMatchComparator(query)));
            log.info("Matching Films sorted: " + movies);

            return movies;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     */
    public Cast getMovieCast(String movieId) throws MovieNotFoundException {
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(API_KEY_PARAM, getApiConfig().getProperty(API_KEY_PROPERTY)));
        HttpGet request = buildRequest(BASE_URL + "/movie/" + movieId + "/casts", nameValuePairs);

        try {
            HttpResponse response = client.execute(request);
            Cast cast = parseResponse(response, Cast.class);
            return cast;
        } catch (IOException e) {
            log.error("NETWORK ERROR: " + e.getClass() + " " + e.getMessage());
        }

        return null;
    }

    /**
     * 
     */
    public MovieImages getImages(String movieId) throws MovieNotFoundException {
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(API_KEY_PARAM, getApiConfig().getProperty(API_KEY_PROPERTY)));
        HttpGet request = buildRequest(BASE_URL + "/movie/" + movieId + "/images", nameValuePairs);

        try {
            HttpResponse response = client.execute(request);
            MovieImages images = parseResponse(response, MovieImages.class);
            return images;
        } catch (IOException e) {
            log.error("NETWORK ERROR: " + e.getClass() + " " + e.getMessage());
        }

        return null;
    }

    /**
     * 
     */
    public Movie getMovieInfo(String movieId, String language) throws MovieNotFoundException {
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(API_KEY_PARAM, getApiConfig().getProperty(API_KEY_PROPERTY)));
        nameValuePairs.add(new BasicNameValuePair(LANGUAGE_PARAM, language));
        HttpGet request = buildRequest(BASE_URL + "/movie/" + movieId, nameValuePairs);

        Movie movie = null;

        try {
            HttpResponse response = client.execute(request);
            // Get the response
            String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("TMDb MOVIE INFO RESPONSE: " + resp);
            // BufferedReader resp = new BufferedReader(new InputStreamReader(
            // response.getEntity().getContent()));

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(resp);
            JsonNode jsonResponse = mapper.readTree(jp);

            JsonNode status = jsonResponse.get("status_code");
            if (status != null && status.asInt() == 6) {
                throw new MovieNotFoundException();
            }

            movie = mapper.readValue(jsonResponse, Movie.class);
            movie.setImages_base_url(configuration.getImages().getBase_url());
            movie.setImages_secure_base_url(configuration.getImages().getSecure_base_url());
            movie.setBackdropImageSizes(configuration.getImages().getBackdrop_sizes());
            movie.setPosterImagesSizes(configuration.getImages().getPoster_sizes());

            // movie.setImdb_id(jsonResponse.get("imdb_id").asText());
            // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // movie.setRelease_date(dateFormat.parse(jsonResponse.get("release_date").asText()));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // } catch (ParseException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
        }

        return movie;
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
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json");
        log.info(request);
        return request;
    }

    /**
     * 
     * @param response
     * @return
     */
    private static <T> T parseResponse(HttpResponse response, Class<T> parseTo) {

        try {
            String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
            // BufferedReader resp = new BufferedReader(new InputStreamReader(
            // response.getEntity().getContent()));

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonFactory factory = mapper.getJsonFactory();
            JsonParser jp = factory.createJsonParser(resp);
            JsonNode jsonResponse = mapper.readTree(jp);

            return mapper.readValue(jsonResponse, parseTo);

        } catch (IOException e) {
            log.error("PARSE ERROR: " + e.getClass() + " " + e.getMessage());
        }

        return null;
    }
    
    /**
     * 
     */
    public Person getPerson(String id, String language) {
        HttpClient client = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(API_KEY_PARAM, getApiConfig().getProperty(API_KEY_PROPERTY)));
        // We append also images and movies
        nameValuePairs.add(new BasicNameValuePair(EXTENSION_PARAM, ACTOR_IMAGES_VALUE + "," + ACTOR_MOVIES_VALUE));
        HttpGet request = buildRequest(BASE_URL + "/person/" + id, nameValuePairs);

        try {
            HttpResponse response = client.execute(request); 
            Person person = parseResponse(response, Person.class);
            log.info("TMDb PERSON INFO RESPONSE: " + person.toString());
            
            person.setProfileImages_base_url(configuration.getImages().getBase_url());
            person.setProfileImagesSizes(configuration.getImages().getProfile_sizes());
            
            // Set configuration for images
            if (person.getImages().getProfiles() != null) {
                for (ProfileImage image : person.getImages().getProfiles()) {
                    image.setConfiguration(configuration);
                }
            }
            
            // Set configuration for movie credits
            if (person.getMovie_credits() != null && person.getMovie_credits().getCast() != null) {
                Collections.sort(person.getMovie_credits().getCast(), Collections.reverseOrder(new MovieCreditComparator()));
                for (MovieCredit credit : person.getMovie_credits().getCast()) {
                    credit.setConfiguration(configuration);
                }
            }
            if (person.getMovie_credits() != null && person.getMovie_credits().getCrew() != null) {
                Collections.sort(person.getMovie_credits().getCrew(), Collections.reverseOrder(new MovieCreditComparator()));
                for (MovieCredit credit : person.getMovie_credits().getCrew()) {
                    credit.setConfiguration(configuration);
                }
            }
            
            return person;
        } catch (IOException e) {
            log.error("NETWORK ERROR: " + e.getClass() + " " + e.getMessage());
        }

        return null;
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
