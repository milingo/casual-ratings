package com.rating.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//"id": 63,
//"title": "Twelve Monkeys",
//"character": "Jeffrey Goines",
//"original_title": "Twelve Monkeys",
//"poster_path": "/vxiIABQhiFlfODwamoevrzXvowU.jpg",
//"release_date": "1995-12-27",
//"adult": false
public class MovieCredit {

    private String id;
    private String original_title;
    private String title;
    private String release_date;
    private String poster_path;
    private String character;
    private String department;
    private String job;

    private Configuration configuration;

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
     * @return the original_title
     */
    public String getOriginal_title() {
        return original_title;
    }

    /**
     * @param original_title
     *            the original_title to set
     */
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the release_date
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     * @param release_date
     *            the release_date to set
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     * @return the poster_path
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     * @param poster_path
     *            the poster_path to set
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     * @return the character
     */
    public String getCharacter() {
        return character;
    }

    /**
     * @param character
     *            the character to set
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration
     *            the configuration to set
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    // -------------- Calculated ---------------------

    /**
     * Calculated URL for poster - original.
     * 
     * @return path to poster
     */
    public String getPosterPath() {
        return configuration.getImages().getBase_url() + "original" + poster_path;
    }

    /**
     * Calculated URL for poster - small.
     * 
     * @return path to poster
     */
    public String getPosterPathSmall() {
        return configuration.getImages().getBase_url() + "w154" + poster_path;
    }

    /**
     * Calculated URL for poster - big.
     * 
     * @return path to poster
     */
    public String getPosterPathLarge() {
        return configuration.getImages().getBase_url()
                + configuration.getImages().getPoster_sizes()
                        .get(configuration.getImages().getPoster_sizes().size() - 2) + poster_path;
    }
    
    /**
     * 
     * @return
     */
    public Date getReleaseDate() {
        if (release_date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return formatter.parse(release_date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 
     * @return
     */
    public String getRelease_year() {
        Date rDate = getReleaseDate();
        if (rDate != null) {
            SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
            return yearFormatter.format(rDate);
        }
        return null;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the job
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job the job to set
     */
    public void setJob(String job) {
        this.job = job;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MovieCredit [id=" + id + ", original_title=" + original_title + ", title=" + title
                + ", release_date=" + release_date + ", poster_path=" + poster_path
                + ", character=" + character + ", department=" + department + ", job=" + job
                + ", configuration=" + configuration + "]";
    }

}
