package com.rating.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Movie {

	// {"adult":false,"backdrop_path":"/7u3pxc0K1wx32IleAkLv78MKgrw.jpg","id":603,"original_title":"The Matrix",
	// "release_date":"1999-03-31","poster_path":"/gynBNzwyaHKtXqlEKKLioNkjKgN.jpg","popularity":22808.63,
	// "title":"The Matrix","vote_average":9.1,"vote_count":253}

	private String id;
	private String vote_average;
	private String vote_count;
	private String original_title;
	private String title;
	private String imdb_id;
	private String release_date;
	private String poster_path;	
	private String backdrop_path;
	private long budget;
	private long revenue;
	private String overview;
	private String runtime;
	private String status;
	private String tagline;
	private List<Genre> genres;
	private List<Actor> actors;
	private List<CrewMemeber> directors;
	private List<Counrty> production_countries;
	private List<Image> backdropImages;
	private List<Image> posterImages;
	private List<Image> posterCoolImages;
    private List<String> backdropImageSizes;
    private List<String> posterImagesSizes;
	
	// configuration
	private String images_base_url;
	private String images_secure_base_url;
 

	public String getVote_average() {
		return vote_average;
	}

	public void setVote_average(String vote_average) {
		this.vote_average = vote_average;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setImages_base_url(String images_base_url) {
        this.images_base_url = images_base_url;
    }
    
    public void setImages_secure_base_url(String images_secure_base_url) {
        this.images_secure_base_url = images_secure_base_url;
    }
    
    /**
     * Calculated URL for poster - original.
     * @return path to poster
     */
    public String getPosterPath() {
        return images_base_url + "original" + poster_path;
    }
    
    /**
     * Calculated URL for poster - small.
     * @return path to poster
     */
    public String getPosterPathSmall() {
        return images_base_url + "w154" + poster_path;
    }
    
    /**
     * Calculated URL for poster - big.
     * @return path to poster
     */
    public String getPosterPathLarge() {
        return images_base_url + getPosterImagesSizes().get(getPosterImagesSizes().size() - 2) + poster_path;
    }

    /**
     * @return the vote_count
     */
    public String getVote_count() {
        return vote_count;
    }

    /**
     * @param vote_count the vote_count to set
     */
    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    /**
     * @return the backdrop_path
     */
    public String getBackdrop_path() {
        return backdrop_path;
    }

    /**
     * @param backdrop_path the backdrop_path to set
     */
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    /**
     * @return the budget
     */
    public long getBudget() {
        return budget;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(long budget) {
        this.budget = budget;
    }

    /**
     * @return the revenue
     */
    public long getRevenue() {
        return revenue;
    }

    /**
     * @param revenue the revenue to set
     */
    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    /**
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview the overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return the runtime
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * @param runtime the runtime to set
     */
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * @param tagline the tagline to set
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * @return the genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * @param genres the genres to set
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * @return the poster_path
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     * @return the images_base_url
     */
    public String getImages_base_url() {
        return images_base_url;
    }

    /**
     * @return the images_secure_base_url
     */
    public String getImages_secure_base_url() {
        return images_secure_base_url;
    }
    
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
    
    public String getRelease_year() {
        Date rDate = getReleaseDate();
        if (rDate != null) {
            SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
            return yearFormatter.format(rDate);
        }
        return null;
    }
    
    /**
     * @return the directors
     */
    public List<CrewMemeber> getDirectors() {
        return directors;
    }

    /**
     * @param directors the directors to set
     */
    public void setDirectors(List<CrewMemeber> directors) {
        this.directors = directors;
    }

    /**
     * @return the production_countries
     */
    public List<Counrty> getProduction_countries() {
        return production_countries;
    }

    /**
     * @param production_countries the production_countries to set
     */
    public void setProduction_countries(List<Counrty> production_countries) {
        this.production_countries = production_countries;
    }
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Movie [id=" + id + ", vote_average=" + vote_average
                + ", vote_count=" + vote_count + ", original_title="
                + original_title + ", title=" + title + ", imdb_id=" + imdb_id
                + ", release_date=" + release_date + ", poster_path="
                + poster_path + ", backdrop_path=" + backdrop_path
                + ", budget=" + budget + ", revenue=" + revenue + ", overview="
                + overview + ", runtime=" + runtime + ", status=" + status
                + ", tagline=" + tagline + ", genres=" + genres
                + ", director=" + directors
                + ", images_base_url=" + images_base_url
                + ", images_secure_base_url=" + images_secure_base_url + "]";
    }

    /**
     * @return the backdropImages
     */
    public List<Image> getBackdropImages() {
        return backdropImages;
    }

    /**
     * @param backdropImages the backdropImages to set
     */
    public void setBackdropImages(List<Image> backdropImages) {
        this.backdropImages = backdropImages;
    }

    /**
     * @return the posterImages
     */
    public List<Image> getPosterImages() {
        return posterImages;
    }

    /**
     * @param posterImages the posterImages to set
     */
    public void setPosterImages(List<Image> posterImages) {
        this.posterImages = posterImages;
    }

    /**
     * @return the backdropImageSizes
     */
    public List<String> getBackdropImageSizes() {
        return backdropImageSizes;
    }

    /**
     * @param backdropImageSizes the backdropImageSizes to set
     */
    public void setBackdropImageSizes(List<String> backdropImageSizes) {
        this.backdropImageSizes = backdropImageSizes;
    }

    /**
     * @return the posterImagesSizes
     */
    public List<String> getPosterImagesSizes() {
        return posterImagesSizes;
    }

    /**
     * @param posterImagesSizes the posterImagesSizes to set
     */
    public void setPosterImagesSizes(List<String> posterImagesSizes) {
        this.posterImagesSizes = posterImagesSizes;
    }

    /**
     * @return the actors
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * @param actors the actors to set
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * @return the posterCoolImages
     */
    public List<Image> getPosterCoolImages() {
        return posterCoolImages;
    }

    /**
     * @param posterCoolImages the posterCoolImages to set
     */
    public void setPosterCoolImages(List<Image> posterCoolImages) {
        this.posterCoolImages = posterCoolImages;
    }


}
