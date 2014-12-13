package com.rating.model;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author miguel
 * 
 */
public class MovieCreditsByJobDeserializer extends JsonDeserializer<MovieCredits> {

    @Override
    public MovieCredits deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {

        JsonNode node = jp.readValueAsTree();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MovieCredits movieCredits = mapper.readValue(node, MovieCredits.class);
        // movieCredits.setDirecor();

        return movieCredits;
    }

}
