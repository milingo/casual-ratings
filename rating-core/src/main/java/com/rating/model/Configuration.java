package com.rating.model;

import java.util.List;

/**
 * 
 * @author miguel
 *
 */
public class Configuration {
    
    private ConfigurationImages images;
    private List<String> change_keys;
    
    public Configuration () {
    }

    public ConfigurationImages getImages() {
        return images;
    }

    public void setImages(ConfigurationImages images) {
        this.images = images;
    }

    public List<String> getChange_keys() {
        return change_keys;
    }

    public void setChange_keys(List<String> change_keys) {
        this.change_keys = change_keys;
    }

}