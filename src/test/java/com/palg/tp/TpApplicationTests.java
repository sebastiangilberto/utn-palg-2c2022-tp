package com.palg.tp;

import com.palg.tp.dao.ObjectDetail;
import com.palg.tp.examples.Animal;
import com.palg.tp.mapper.ObjectMapper;
import com.palg.tp.persistence.PersistentObjects;
import com.palg.tp.persistence.PersistentObjectsImpl;
import com.palg.tp.repository.ObjectDetailRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class TpApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(TpApplication.class);

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ObjectDetailRepository repository;

    @Test
    void prueba() {
        PersistentObjects po = new PersistentObjectsImpl(mapper, repository);
        po.creteSession(1L, 10L);
        print(repository);
        po.store(1L, "hola mundo");
        print(repository);
        String fromDB = (String) po.load(1L, String.class);

        log.info("hello: " + fromDB);

        Animal mono = new Animal(100, "mono", false, true);

        log.info("agrego mono a la base");
        po.store(1L, mono);

        print(repository);

        log.info("recupero mono de la base");
        Animal monoDB = (Animal) po.load(1L, Animal.class);

        log.info("mono from DB: " + monoDB);

        po.creteSession(2L, 15L);

        Animal gorila = new Animal(1, "gorila", true, false);

        po.store(2L, gorila);

        print(repository);

        log.info("recupero gorila de la base");
        Animal gorilaDB = (Animal) po.load(2L, Animal.class);

        log.info("gorila from DB: " + gorilaDB);
    }

    private static void print(ObjectDetailRepository repository) {
        log.info("ObjectDetail found with findAll():");
        log.info("-------------------------------");
        for (
                ObjectDetail objectDetail : repository.findAll()) {
            log.info(objectDetail.toString());
        }
        log.info("");
    }
}
