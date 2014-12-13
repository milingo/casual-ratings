package com.rating.model;

public enum Provider {
	IMDB, FILM_AFFINITY, ROTTEN_TOMATOES;
	
	@Override
	public String toString() {
	    if (this == FILM_AFFINITY) {
	        return "Film Affinity";
	    } else if (this == ROTTEN_TOMATOES) {
	        return "Rotten Tomatoes";
	    } else if (this == FILM_AFFINITY) {
	        return "Imdb";
	    }
	    return "";
	}
}
