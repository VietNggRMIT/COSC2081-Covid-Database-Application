package com.data.covid.model.dto;

import java.time.LocalDate;

public class Record {

    private String location;
    private LocalDate date;
    private Integer newCases;
    private Integer newDeaths;
    private Integer peopleVaccinated;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNewCases() {
        return newCases;
    }

    public void setNewCases(Integer newCases) {
        this.newCases = newCases;
    }

    public Integer getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(Integer newDeaths) {
        this.newDeaths = newDeaths;
    }

    public Integer getPeopleVaccinated() {
        return peopleVaccinated;
    }

    public void setPeopleVaccinated(Integer peopleVaccinated) {
        this.peopleVaccinated = peopleVaccinated;
    }

    @Override
    public String toString() {
        return "Record{" +
                "location='" + location + '\'' +
                ", date=" + date +
                ", newCases=" + newCases +
                ", newDeaths=" + newDeaths +
                ", peopleVaccinated=" + peopleVaccinated +
                '}';
    }
}
