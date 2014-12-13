package com.rating.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author miguel
 * 
 */
public class MovieCredits {

    private List<MovieCredit> cast;
    private List<MovieCredit> crew;

    /**
     * @return the cast
     */
    public List<MovieCredit> getCast() {
        return cast;
    }

    /**
     * @param cast
     *            the cast to set
     */
    public void setCast(List<MovieCredit> cast) {
        this.cast = cast;
    }

    /**
     * @return the crew
     */
    public List<MovieCredit> getCrew() {
        return crew;
    }

    /**
     * @param crew
     *            the crew to set
     */
    public void setCrew(List<MovieCredit> crew) {
        this.crew = crew;
    }

    /**
     * @return the director
     */
    public List<MovieCredit> getDirector() {
        List<MovieCredit> directors = new ArrayList<MovieCredit>();
        for (MovieCredit mc : crew) {
            if (mc.getJob().toUpperCase().equals(Job.DIRECTOR.getStringId())) {
                directors.add(mc);
            }
        }
        return directors;
    }

    /**
     * @return the productor
     */
    public List<MovieCredit> getProductor() {
        List<MovieCredit> producers = new ArrayList<MovieCredit>();
        for (MovieCredit mc : crew) {
            if (mc.getJob().toUpperCase().equals(Job.PRODUCER.getStringId())) {
                producers.add(mc);
            }
        }
        return producers;
    }

    /**
     * @return the writer
     */
    public List<MovieCredit> getWriter() {
        List<MovieCredit> writers = new ArrayList<MovieCredit>();
        for (MovieCredit mc : crew) {
            if (mc.getJob().toUpperCase().equals(Job.WRITER.getStringId())) {
                writers.add(mc);
            }
        }
        return writers;
    }

    /**
     * @return the actor
     */
    public List<MovieCredit> getActor() {
        return cast;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MovieCredits [cast=" + cast + ", crew=" + crew + "]";
    }

}
