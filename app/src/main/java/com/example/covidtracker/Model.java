package com.example.covidtracker;

public class Model {
    private final String state;
    private final String district;
    private final long cases;
    private final long recovered;
    private final long deaths;
    private final long active;
    private final long migratedOthers;
    private boolean isExpanded;


    //constructor
    public Model(String state, String district, long cases, long recovered, long deaths, long active, long migratedOthers) {
        this.state = state;
        this.district = district;
        this.cases = cases;
        this.recovered = recovered;
        this.deaths = deaths;
        this.active = active;
        this.migratedOthers = migratedOthers;
        this.isExpanded = false;
    }

    //getter and setters

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public long getCases() {
        return cases;
    }

    public long getRecovered() {
        return recovered;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getActive() {
        return active;
    }

    public long getMigratedOthers() {
        return migratedOthers;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
