package org.ximenia.service.dto;

import org.ximenia.model.Person;

import java.util.List;

public class ChildAlertDto {

    private String firstName;
    private String lastName;
    private String age;
    private List<Person> households;

    public ChildAlertDto() {
    }

    public ChildAlertDto(String firstName, String lastName, String age, List<Person> households) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.households = households;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Person> getHouseholds() {
        return households;
    }

    public void setHouseholds(List<Person> households) {
        this.households = households;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
