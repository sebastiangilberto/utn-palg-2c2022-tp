package com.palg.tp.repository;


import com.palg.tp.dao.ObjectDetail;
import com.palg.tp.dao.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface ObjectDetailRepository extends CrudRepository<ObjectDetail, ObjectId> {
}