package com.rating.model;

/**
 * 
 * @author miguel
 * 
 */
public class Image {

    // Full path to image
    private String pathSmall;
    private String pathMedium;
    private String pathLarge;
    
    public Image(String pathSmall, String pathMedium, String pathLarge) {
        this.pathLarge = pathLarge;
        this.pathSmall = pathSmall;
        this.pathMedium = pathMedium;
    }

    /**
     * @return the pathSmall
     */
    public String getPathSmall() {
        return pathSmall;
    }

    /**
     * @param pathSmall
     *            the pathSmall to set
     */
    public void setPathSmall(String pathSmall) {
        this.pathSmall = pathSmall;
    }

    /**
     * @return the pathLarge
     */
    public String getPathLarge() {
        return pathLarge;
    }

    /**
     * @param pathLarge
     *            the pathLarge to set
     */
    public void setPathLarge(String pathLarge) {
        this.pathLarge = pathLarge;
    }

    /**
     * @return the pathMedium
     */
    public String getPathMedium() {
        return pathMedium;
    }

    /**
     * @param pathMedium the pathMedium to set
     */
    public void setPathMedium(String pathMedium) {
        this.pathMedium = pathMedium;
    }

}
