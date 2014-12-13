package com.rating.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author miguel
 * 
 */
public class Cast {

    /**
     * 
     * @author miguel
     * 
     */
    public enum Job {
        DIRECTOR("Director"), AUTHOR("Author"), PRODUCER("Producer"), EXECUTIVE_PRODUCER(
                "Executive Producer"), OMS_COMPOSER("Original Music Composer"), PHOTOGRAPHY_DIRECTOR(
                "Director of Photography");

        private final String job;

        Job(String job) {
            this.job = job;
        }

        public String getValue() {
            return job;
        }
    }

    private List<Actor> cast;
    private List<CrewMemeber> crew;

    /**
     * @return the cast
     */
    public List<Actor> getCast() {
        return cast;
    }

    /**
     * @param cast
     *            the cast to set
     */
    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    /**
     * @return the crew
     */
    public List<CrewMemeber> getCrew() {
        return crew;
    }

    /**
     * @param crew
     *            the crew to set
     */
    public void setCrew(List<CrewMemeber> crew) {
        this.crew = crew;
    }

    /**
     * @return the crew
     */
    public List<CrewMemeber> getDirectors() {
        List<CrewMemeber> directors = new ArrayList<CrewMemeber>();
        for (CrewMemeber member : crew) {
            if (Job.DIRECTOR.getValue().equals(member.getJob())) {
                directors.add(member);
            }
        }
        return directors;
    }

}
