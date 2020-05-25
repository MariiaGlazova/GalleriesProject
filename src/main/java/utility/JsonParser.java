package utility;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {

    ObjectMapper objectMapper = new ObjectMapper();

    public <T> boolean fromObjectToJSON(String json, T object) {
        try {
            objectMapper.writeValue(new File(json), object);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public <T> T fromJSONToObject(String json, Class<T> someClass) {
        try {
            return objectMapper.readValue(new File(json), someClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
