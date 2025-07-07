package ca.rjdsilv.buyrecipes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class TestUtils {
    private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder.json().build();

    @SneakyThrows
    public static <T> String toJson(T obj) {
        return MAPPER.writeValueAsString(obj);
    }
}
