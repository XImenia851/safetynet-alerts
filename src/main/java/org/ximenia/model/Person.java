package org.ximenia.model;

public class Person {
    private  String firstName;
    private String lastName;
    private String adress;;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public Person() {
    }

    public Person(String firstName, String lastName, String adress, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
}
