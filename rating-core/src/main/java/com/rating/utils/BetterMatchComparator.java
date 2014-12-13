package com.rating.utils;

import java.util.Comparator;

import com.rating.model.Movie;

public class BetterMatchComparator implements Comparator<Movie> {
	
	private String query;
	
	public BetterMatchComparator(String query){
		this.query = query;
	}

	@Override
	public int compare(Movie m1, Movie m2) {
		if (query.toLowerCase().equals(m1.getTitle().toLowerCase())){
			return 100;
		}
		if (query.toLowerCase().equals(m2.getTitle().toLowerCase())) {
			return -100;
		}
		
		if (m1.getTitle().toLowerCase().startsWith(query)){
			return 100;
		}
		if (m2.getTitle().toLowerCase().startsWith(query)){
			return -100;
		}
		
		int i = (int)((Float.valueOf(m1.getVote_average()) - Float.valueOf(m2.getVote_average())) * 10.0);
		return i;
	}

}
