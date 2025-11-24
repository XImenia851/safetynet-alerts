package org.ximenia.repository;

import org.springframework.stereotype.Component;
import org.ximenia.model.MedicalRecord;
import org.ximenia.model.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordRepository {

    private final DataHandler dataHandler;
    public MedicalRecordRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
    public List<MedicalRecord> findAllMedicalRecords() {
        return dataHandler.getDataContainer().getMedicalrecords();
    }

    public MedicalRecord findMedicalWithFirstNameAndLastName(String firstName, String lastName) {
        return dataHandler.getDataContainer().getMedicalrecords().stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst()
                .orElseGet(() -> new MedicalRecord());
    }

    private boolean isUnder18(String birthdate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        return !calendar.getTime().after(date);
    }



    public List<Person> findAllPersonByAddress(String address) {
        return dataHandler.getDataContainer().getPersons().
                stream().filter(p -> p.getAddress().
                        equals(address)).collect(Collectors.toList());

    }

    public void savePerson (Person person) {
        dataHandler.getDataContainer().getPersons().add(person);
        dataHandler.save();
    }
}


