package com.palg.tp;

import com.palg.tp.examples.Animal;
import com.palg.tp.examples.Auto;
import com.palg.tp.examples.Motor;
import com.palg.tp.examples.Persona;
import com.palg.tp.exception.SessionNotExistsException;
import com.palg.tp.listener.ConsoleLoggerSessionListener;
import com.palg.tp.listener.SessionListener;
import com.palg.tp.manager.SessionManager;
import com.palg.tp.mapper.ObjectMapper;
import com.palg.tp.persistence.PersistentObjects;
import com.palg.tp.repository.ObjectDetailRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class PersistableAndNotPersistableTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ObjectDetailRepository repository;

    @Autowired
    SessionManager manager;

    @Autowired
    PersistentObjects po;

    SessionListener listener = new ConsoleLoggerSessionListener();

    /*
    * Del auto se persiste el color, las ruedas, precio, y del motor solamente el tipo
    * */
    Auto auto1 = new Auto(
            "audi",
            "verde",
            4,
            100L,
            BigDecimal.valueOf(999999),
            new Motor("v15", 1500)
    );

    Auto auto2 = new Auto(
            "nissan",
            "gris",
            4,
            50L,
            BigDecimal.valueOf(999999),
            new Motor("v15", 1500)
    );

    /*
    * El animal es persistido a nivel clase
    * */
    Animal animal1 = new Animal(
            1,
            "mono",
            true,
            false
    );

    Animal animal2 = new Animal(
            13,
            "sapo",
            false,
            true
    );

    /*
    * La clase persona no contiene ninguna anotacion => no es persistible
    */
    Persona persona = new Persona(
        "juan",
            31,
            123456789
    );

    @BeforeEach
    void before() {
        po.addListener(listener);
    }

    @AfterEach
    void after() {
        po.destroySession(1L);
        po.removeListener(listener);

    }

    /* El TO de la session son 5 segundos
     * EL listener loguea cada 2 segundos
     * */
    @Test
    void session1_objects2_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        Thread.sleep(10000L);
        po.store(1L, auto1);
        Thread.sleep(1000L);
        po.load(1L, Auto.class);
        Thread.sleep(1000L);
        manager.printEverything();
        po.store(1L, animal1);
        manager.printEverything();
    }

    @Test
    void session1_objects2sameclass_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        Thread.sleep(10000L);
        po.store(1L, auto1);
        Thread.sleep(1000L);
        po.load(1L, Auto.class);
        Thread.sleep(1000L);
        manager.printEverything();
        po.store(1L, auto2);
        manager.printEverything();
    }

    @Test
    void sessionNotExist_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        Thread.sleep(10000L);
        po.store(1L, auto1);
        Thread.sleep(1000L);
        po.load(1L, Auto.class);
        Thread.sleep(1000L);
        manager.printEverything();
        try {
            po.store(2L, auto2);
        } catch (SessionNotExistsException se) {
            System.out.println("Exception thrown and the message is: %s".formatted(se.getMessage()));
        }
    }

    @Test
    void session1_object1_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        Thread.sleep(10000L);
        po.store(1L, auto1);
        Thread.sleep(1000L);
        po.load(1L, Auto.class);
        Thread.sleep(1000L);
        manager.printEverything();
    }

    @Test
    void session2_object2_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        po.createSession(2L, 8000L);
        Thread.sleep(10000L);
        po.store(1L, auto1);
        po.store(2L, animal2);
        Thread.sleep(1000L);
        po.load(1L, Auto.class);
        po.load(2L, Animal.class);
        Thread.sleep(1000L);
        manager.printEverything();
        po.destroySession(2L);
    }

    @Test
    void session2_object2_destroySession_deleteObjects_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        po.createSession(2L, 8000L);
        Thread.sleep(10000L);
        po.store(1L, auto1);
        po.store(2L, animal2);
        Thread.sleep(1000L);
        manager.printEverything();
        po.destroySession(2L);
        Thread.sleep(1000L);
        manager.printEverything();
    }


    @Test
    void session1_object1NotPersistable_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        Thread.sleep(10000L);
        po.store(1L, persona);
        Thread.sleep(1000L);
        assertThat(po.load(1L, Persona.class), is(nullValue()));
        Thread.sleep(1000L);
        manager.printEverything();
    }

    @Test
    void session1_object1AndRemove_listener1() throws InterruptedException {
        po.createSession(1L, 5000L);
        Thread.sleep(10000L);
        po.store(1L, auto1);
        Thread.sleep(1000L);
        po.load(1L, Auto.class);
        Thread.sleep(1000L);
        manager.printEverything();
        po.remove(1L, Auto.class);
        manager.printEverything();
    }
}
