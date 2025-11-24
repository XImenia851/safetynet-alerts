package org.ximenia.repository;

import com.jsoniter.JsonIterator;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.ximenia.model.DataContainer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class DataHandler {

    private final DataContainer dataContainer;

    public DataHandler() throws IOException {
        String temp = getFromResources("data.json");
        this.dataContainer = JsonIterator.deserialize(temp, DataContainer.class);
    }
    private String getFromResources(String s) throws IOException {
        InputStream is = new ClassPathResource(s).getInputStream();
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }

    public DataContainer getDataContainer() { return dataContainer; }

    public void save() {
    }
}