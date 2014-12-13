package com.rating.model;

/**
 * 
 * @author miguel
 * 
 */
public class ProfileImage {

    private String file_path;
    private String width;
    private String height;
    private String iso_639_1;
    private String aspect_ratio;

    private Configuration configuration;
    
    /**
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the file_path
     */
    public String getFile_path() {
        return file_path;
    }

    /**
     * @param file_path
     *            the file_path to set
     */
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the iso_639_1
     */
    public String getIso_639_1() {
        return iso_639_1;
    }

    /**
     * @param iso_639_1
     *            the iso_639_1 to set
     */
    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    /**
     * @return the aspect_ratio
     */
    public String getAspect_ratio() {
        return aspect_ratio;
    }

    /**
     * @param aspect_ratio
     *            the aspect_ratio to set
     */
    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }
    
    // -------------- Calculated ---------------------

    /**
     * Calculated URL for poster - original.
     * 
     * @return path to poster
     */
    public String getImagePath() {
        return configuration.getImages().getBase_url() + "original" + file_path;
    }

    /**
     * Calculated URL for poster - small.
     * 
     * @return path to poster
     */
    public String getImagePathSmall() {
        return configuration.getImages().getBase_url() + "w154" + file_path;
    }

    /**
     * Calculated URL for poster - big.
     * 
     * @return path to poster
     */
    public String getImagePathLarge() {
        return configuration.getImages().getBase_url()
                + configuration.getImages().getPoster_sizes()
                        .get(configuration.getImages().getPoster_sizes().size() - 2) + file_path;
    }

}