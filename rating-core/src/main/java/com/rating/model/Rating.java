package com.rating.model;

/**
 * 
 * @author miguel
 *
 */
public class Rating {
	
	private String rating;
	private int votes;
	
	public Rating (String rating, int votes) {
		setRating(rating);
		setVotes(votes);
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
