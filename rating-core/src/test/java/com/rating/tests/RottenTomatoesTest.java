package com.rating.tests;

public class RottenTomatoesTest {

//    Log log = LogFactory.getLog(RottenTomatoesTest.class);
//
//    @Test
//    public void okTest() {
//        Movie movie = getMovieWithQuery("Star Trek: El Futuro Comienza");
//
//        MovieInfoDao rtRatingRatingDao = new RottenTomatoesRatingDaoImpl();
//        try {
//            MovieInfo info = rtRatingRatingDao.getReviewsDetails(movie);
//            Assert.assertTrue(info.getRating().getRating() != "");
//            Assert.assertNotNull(info.getReviews());
//        } catch (MovieNotFoundException e) {
//            Assert.fail();
//        }
//    }
//
//    @Test
//    public void notRatedTest() {
//        Movie movie = getMovieWithQuery("The Hobbit There");
//
//        MovieInfoDao rtRatingRatingDao = new RottenTomatoesRatingDaoImpl();
//        try {
//            MovieInfo rottenInfo = rtRatingRatingDao.getReviewsDetails(movie);
//            Assert.assertTrue(rottenInfo.getRating().getRating() == "");
//        } catch (MovieNotFoundException e) {
//            Assert.fail();
//        }
//    }
//
//    /**
//     * TODO: Inheritable by all movie-related unit tests TODO: This makes
//     * integration tests instead of unit ones...
//     *
//     * @param query
//     * @return
//     */
//    private Movie getMovieWithQuery(String query) {
//        TMDbMovieDaoImpl TMDbClient = new TMDbMovieDaoImpl();
//
//        // Search
//        List<Movie> movies = TMDbClient.search(query, "es", true);
//
//        // Select one
//        Movie movie = movies.get(0);
//        Assert.assertTrue(movie.getPosterPathSmall().contains("http"));
//
//        // Get full Info
//        try {
//            movie = TMDbClient.getMovieInfo(movie.getId(), "es");
//        } catch (MovieNotFoundException e) {
//            Assert.fail();
//        }
//        return movie;
//    }
//
////    @Test
////    public void aladdinTest() {
////        Movie movie = getMovieWithQuery("Aladdin");
////
////        MovieInfoDao rtRatingRatingDao = new RottenTomatoesRatingDaoImpl();
////        try {
////            MovieInfo info = rtRatingRatingDao.getReviewsDetails(movie);
////            Assert.assertTrue(info.getRating().getRating() != "");
////            Assert.assertNotNull(info.getReviews());
////        } catch (MovieNotFoundException e) {
////            Assert.fail();
////        }
////    }

}
