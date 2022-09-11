package com.palg.tp.repository;


import com.palg.tp.DAO.Class;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClassRepository extends CrudRepository<Class, Long> {

    List<Class> findByKey(long key);

    void deleteByKey(long key);
}