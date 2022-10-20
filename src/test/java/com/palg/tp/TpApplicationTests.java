package com.palg.tp;

import com.palg.tp.examples.Animal;
import com.palg.tp.examples.Auto;
import com.palg.tp.examples.Motor;
import com.palg.tp.listener.ConsoleLoggerSessionListener;
import com.palg.tp.manager.SessionManager;
import com.palg.tp.mapper.ObjectMapper;
import com.palg.tp.persistence.PersistentObjects;
import com.palg.tp.persistence.PersistentObjectsImpl;
import com.palg.tp.repository.ObjectDetailRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class TpApplicationTests {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ObjectDetailRepository repository;

    @Autowired
    SessionManager manager;

    @Test
    void prueba() throws InterruptedException {
        PersistentObjects po = new PersistentObjectsImpl(mapper, manager);
        po.addListener(new ConsoleLoggerSessionListener());

        po.createSession(1L, 5000L);
        po.createSession(2L, 8000L);

        Thread.sleep(10000L);

        po.store(1L, "hola mundo");

        Thread.sleep(1000L);

        String fromDB = (String) po.load(1L, String.class);

        Thread.sleep(10000L);

        Auto auto = new Auto(
                "audi",
                "verde",
                4,
                100L,
                BigDecimal.valueOf(999999),
                new Motor("v15", 1500)
        );

        po.store(1L, auto);

        po.store(2L, 12345);

        Thread.sleep(10000L);

        po.load(1L, Auto.class);

        this.manager.printEverything();

        po.store(2L, new Animal(10, "mono", true, true));

        po.remove(1L, String.class);

        Thread.sleep(2000L);

        po.destroySession(1L);

        Thread.sleep(2000L);

        this.manager.printEverything();

        po.createSession(2L, 1000L);
    }
}
