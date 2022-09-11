package com.palg.tp.DAO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Class {

    @Id
    private long key;
    private String json;
}
