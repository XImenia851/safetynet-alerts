package org.ximenia.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireDto {
    private String station;
    private String phoneNumber;
    private int age;
    private String lastName;
    private String firstName;
    private List<String> medications;
    private List <String> allergies;
}
