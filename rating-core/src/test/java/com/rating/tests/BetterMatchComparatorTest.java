package com.rating.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.rating.model.Movie;
import com.rating.utils.BetterMatchComparator;

public class BetterMatchComparatorTest {

	Log log = LogFactory.getLog(BetterMatchComparatorTest.class);

	@Test
	public void searchTest() {
		BetterMatchComparator comparator = new BetterMatchComparator("El golpe");
		
		Movie m1 = new Movie();
		m1.setTitle("Ellas dan el golpe");
        m1.setVote_average("5.2");
		
		Movie m2 = new Movie();
		m2.setTitle("El golpe");
		m2.setVote_average("8.8");
		
		Movie m3 = new Movie();
		m3.setTitle("Los ultimos golpes de El Torete (Perros callejeros III)");
		m3.setVote_average("0.0");
		
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(m1);
		movies.add(m3);
		movies.add(m2);
		
		int i = comparator.compare(m2, m1);
		Assert.assertTrue("compare(m2, m1) = " + i, i>0);
		
		i = comparator.compare(m2, m3);
		Assert.assertTrue("compare(m2, m3) = " + i, i>0);
		
		Collections.sort(movies, Collections.reverseOrder(comparator));
		
		for (Movie m : movies){
			log.info(m);
		}

	}

}
