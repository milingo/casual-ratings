package com.rating.utils;

import java.util.Comparator;

import com.rating.model.MovieCredit;

/**
 * 
 * @author miguel
 * 
 */
public class MovieCreditComparator implements Comparator<MovieCredit> {

    @Override
    public int compare(MovieCredit o1, MovieCredit o2) {
        if (o1.getReleaseDate() != null && o2.getReleaseDate() != null) {
            return o1.getReleaseDate().compareTo(o2.getReleaseDate());
        }
        return 1;
    }

}
