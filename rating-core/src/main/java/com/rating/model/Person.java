package com.rating.model;

import java.util.List;

//"adult": false,
//"also_known_as": [],
//"biography": "From Wikipedia, the free encyclopedia.\n\nWilliam Bradley \"Brad\" Pitt (born December 18, 1963) is an American actor and film producer.  
//"birthday": "1963-12-18",
//"deathday": "",
//"homepage": "http://simplybrad.com/",
//"id": 287,
//"name": "Brad Pitt",
//"place_of_birth": "Shawnee, Oklahoma, United States",
//"profile_path": "/w8zJQuN7tzlm6FY9mfGKihxp3Cb.jpg"

/**
 * 
 * @author miguel
 * 
 */
public class Person {

    private String biography;
    private String id;
    private String name;
    private String birthday;
    private String deathday;
    private String place_of_birth;
    private String profile_path;
    
    private MovieCredits movie_credits;
    private ProfileImages images;
   
    private String profileImages_base_url;
    private List<String> profileImagesSizes;

    /**
     * @return the biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * @param biography
     *            the biography to set
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the deathday
     */
    public String getDeathday() {
        return deathday;
    }

    /**
     * @param deathday
     *            the deathday to set
     */
    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    /**
     * @return the place_of_birth
     */
    public String getPlace_of_birth() {
        return place_of_birth;
    }

    /**
     * @param place_of_birth
     *            the place_of_birth to set
     */
    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    /**
     * @return the profile_path
     */
    public String getProfile_path() {
        return profile_path;
    }

    /**
     * @param profile_path
     *            the profile_path to set
     */
    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    /**
     * @return the profileImages_base_url
     */
    public String getProfileImages_base_url() {
        return profileImages_base_url;
    }

    /**
     * @param profileImages_base_url the profileImages_base_url to set
     */
    public void setProfileImages_base_url(String profileImages_base_url) {
        this.profileImages_base_url = profileImages_base_url;
    }

    /**
     * @return the profileImagesSizes
     */
    public List<String> getProfileImagesSizes() {
        return profileImagesSizes;
    }

    /**
     * @param profileImagesSizes the profileImagesSizes to set
     */
    public void setProfileImagesSizes(List<String> profileImagesSizes) {
        this.profileImagesSizes = profileImagesSizes;
    }
    
    //---------- Calculated ----------------

    /**
     * Calculated URL for ProfileImage - original.
     * @return path to ProfileImage
     */
    public String getProfileImagePath() {
        return profileImages_base_url + "original" + profile_path;
    }
    
    /**
     * Calculated URL for ProfileImage - small.
     * @return path to ProfileImage
     */
    public String getProfileImagePathSmall() {
        if (getProfileImagesSizes() != null) {
            if (getProfileImagesSizes().size() > 1) {
                return profileImages_base_url + getProfileImagesSizes().get(1) + profile_path;
            } else {
                return profileImages_base_url + getProfileImagesSizes().get(0) + profile_path;
            }
        }
        return "";
    }
    
    /**
     * Calculated URL for ProfileImage - big.
     * @return path to ProfileImage
     */
    public String getProfileImagePathLarge() {
        if (getProfileImagesSizes() != null) {
            return profileImages_base_url
                    + getProfileImagesSizes().get(getProfileImagesSizes().size() - 2)
                    + profile_path;
        }
        return "";
    }

    /**
     * @return the movie_credits
     */
    public MovieCredits getMovie_credits() {
        return movie_credits;
    }

    /**
     * @param movie_credits the movie_credits to set
     */
    public void setMovie_credits(MovieCredits movie_credits) {
        this.movie_credits = movie_credits;
    }

    /**
     * @return the images
     */
    public ProfileImages getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(ProfileImages images) {
        this.images = images;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Person [biography=" + biography + ", id=" + id + ", name=" + name + ", birthday="
                + birthday + ", deathday=" + deathday + ", place_of_birth=" + place_of_birth
                + ", profile_path=" + profile_path + ", movie_credits=" + movie_credits
                + ", images=" + images + ", profileImages_base_url=" + profileImages_base_url
                + ", profileImagesSizes=" + profileImagesSizes + "]";
    }
    
}
