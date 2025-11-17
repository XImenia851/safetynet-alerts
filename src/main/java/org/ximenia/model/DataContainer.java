package org.ximenia.model;

import java.util.List;

public class DataContainer {

    private List<Person> persons;
    private List<FirstStation> fireStations;
    private List<Person> getPersons()
    {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FirstStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(List<FirstStation> fireStations) {
        this.fireStations = fireStations;
    }
}
