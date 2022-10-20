package com.palg.tp.repository;


import com.palg.tp.dao.ObjectDetail;
import com.palg.tp.dao.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectDetailRepository extends CrudRepository<ObjectDetail, ObjectId> {
    void deleteByKey(long key);
}