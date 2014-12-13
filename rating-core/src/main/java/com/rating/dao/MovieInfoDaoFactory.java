package com.rating.dao;

import com.rating.dao.impl.FilmAffinityRatingDaoImpl;
import com.rating.dao.impl.IMDbRatingDaoImpl;
import com.rating.dao.impl.RottenTomatoesRatingDaoImpl;
import com.rating.model.Provider;

public class MovieInfoDaoFactory {
	
	public static MovieInfoDaoFactory instance;
	
	public static MovieInfoDaoFactory getInstance() {
		if (instance == null) {
			instance = new MovieInfoDaoFactory();
		}
		return instance;
	}

	public MovieInfoDao getMovieInfoDao(Provider provider) {
		if (provider == Provider.IMDB) {
			return new IMDbRatingDaoImpl();
		} else if (provider == Provider.ROTTEN_TOMATOES) {
			return new RottenTomatoesRatingDaoImpl();
		} else if (provider == Provider.FILM_AFFINITY) {
			return new FilmAffinityRatingDaoImpl();
		}
		return null;
	}

}
