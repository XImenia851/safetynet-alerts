package org.ximenia.repository;

import org.springframework.stereotype.Component;
import org.ximenia.model.FireStation;

import java.util.List;

@Component
public class FireStationRepository {

    private final DataHandler dataHandler;
    public FireStationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
    public List<FireStation> findAllFireStations()
    {
        return dataHandler.getDataContainer().getFirestations();
    }
}
