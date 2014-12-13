package com.rating.model;

public enum Job {
    ACTOR(""), DIRECTOR("DIRECTOR"), PRODUCER("PRODUCER"), WRITER("WRITER");

    private String stringId;

    Job(String value) {
        setStringId(value);
    }

    /**
     * @return the stringId
     */
    public String getStringId() {
        return stringId;
    }

    /**
     * @param stringId
     *            the stringId to set
     */
    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

}
