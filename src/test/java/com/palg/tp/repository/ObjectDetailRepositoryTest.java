package com.palg.tp.repository;

import com.palg.tp.dao.ObjectDetail;
import com.palg.tp.dao.ObjectId;
import org.junit.jupiter.api.Test;

class ObjectDetailRepositoryTest {

    ObjectDetailRepository repository;

    @Test
    void save() {
        // save a few customers
        repository.save(new ObjectDetail(1L, Long.class.getCanonicalName(),"a"));
        repository.save(new ObjectDetail(2L,  Long.class.getCanonicalName(),"b"));
        repository.save(new ObjectDetail(2L,  Long.class.getCanonicalName(),"g"));
        repository.save(new ObjectDetail(3L,  Long.class.getCanonicalName(),"c"));

        // fetch all customers
        System.out.println("ObjectDetail found with findAll():");
        System.out.println("-------------------------------");
        for (
                ObjectDetail objectDetail : repository.findAll()) {
            System.out.println(objectDetail.toString());
        }
        System.out.println("");

        // fetch an individual customer by ID
        ObjectDetail objectDetail = repository.findById(new ObjectId(1L, Long.class.getCanonicalName())).get();
        System.out.println("Object detail with key 1L:");
        System.out.println("--------------------------------");
        System.out.println(objectDetail);
        System.out.println("");

        // fetch customers by last name
        System.out.println("Delete ObjectDetail with key 1L:");
        System.out.println("--------------------------------------------");
        repository.deleteById(new ObjectId(1L, Long.class.getCanonicalName()));
        System.out.println("ObjectDetail found with findAll():");
        System.out.println("-------------------------------");
        for (ObjectDetail ob : repository.findAll()) {
            System.out.println(ob.toString());
        }
        System.out.println("");
    }

}
