package org.ximenia.repository;

import org.springframework.stereotype.Component;
import org.ximenia.model.Person;

import java.util.List;

@Component
public class PersonRepository {

    private final DataHandler dataHandler;

    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons() {
        return dataHandler.getDataContainer().getPersons();
    }

//    public List<Person> findAllEmails() {
//        return dataHandler.getDataContainer().getPersons();
//    }

    public Person findpersonByfirstNameAndLastName(String firstName, String lastName){
        return dataHandler.getDataContainer().getPersons().stream() // Stream<Person>
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst() // Optional<Person>
                .orElseGet(() -> new Person());
    }
}
