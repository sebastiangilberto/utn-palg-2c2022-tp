package com.palg.tp.mapper;

import com.palg.tp.examples.Auto;
import com.palg.tp.examples.Motor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class JsonMapperTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new JsonMapper();
    }

    @Test
    void objectToJson() {
        Auto auto = new Auto(
                "ford",
                "gris",
                4,
                40L,
                BigDecimal.valueOf(5500),
                new Motor("v8", 1000)
        );

        String json = this.mapper.toJson(auto);

        assertThat(json, is(equalTo("{\"color\":\"gris\",\"ruedas\":4,\"precio\":5500,\"motor\":{\"caballosFuerza\":1000}}")));
    }

    @Test
    void toObject(){

        Auto expectedAuto = new Auto(
                "audi",
                "verde",
                0,
                100L,
                null,
                new Motor("v15", 0)
        );

        String json = "{\"color\":\"verde\",\"marca\": \"audi\",\"combustible\":100,\"motor\":{\"tipo\":\"v15\"}}";

        Auto auto = this.mapper.toObject(json, Auto.class);

        assertThat(auto, is(equalTo(expectedAuto)));
    }
}


