package org.ximenia.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import org.apache.commons.io.IOUtils;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Component
public class DataHandler {

    private final Data data;

    public DataHandler() throws IOException {
        String temp = getFromRessources("data.json");
        this.data = JsonIterator.deserialize(temp, Data.class);
    }
    private String getFromRessources(String s) throws IOException {
        InputStream is = new ClassPathResource(s).getInputStream();
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }

    public Data getData() {
        return data;
    }

    public void save(){
        String json = JsonStream.serialize(data);
        BufferedWriter writer = null;
    }
}
