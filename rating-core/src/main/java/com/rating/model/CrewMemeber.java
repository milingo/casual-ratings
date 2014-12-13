package com.rating.model;

//{
//"department": "Directing",
//"id": 1,
//"job": "Director",
//"name": "George Lucas",
//"profile_path": "/7Q5FVw6RhI1gsr1QHmJZuwxshRF.jpg"
//}

/**
 * 
 * @author miguel
 * 
 */
public class CrewMemeber {

    private String department;
    // TODO: enum ???
    private String job;
    private String name;
    private String id;
    private String profile_path;

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department
     *            the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the job
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job
     *            the job to set
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the profile_path
     */
    public String getProfile_path() {
        return profile_path;
    }

    /**
     * @param profile_path
     *            the profile_path to set
     */
    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

}
