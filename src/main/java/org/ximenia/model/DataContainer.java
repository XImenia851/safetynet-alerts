package org.ximenia.model;

import java.util.List;

public class DataContainer {

    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<Person> getPersons()
    {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
    }
}
