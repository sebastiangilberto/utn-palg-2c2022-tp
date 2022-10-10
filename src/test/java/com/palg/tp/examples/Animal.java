package com.palg.tp.examples;

import com.palg.tp.annotations.Persistable;


@Persistable
public class Animal {

    @Persistable
    private Integer edad;
    @Persistable
    private String raza;

    private Boolean salvaje;
    @Persistable
    private Boolean hambriento;

    public Animal(Integer edad, String raza, Boolean salvaje, Boolean hambriento) {
        this.edad = edad;
        this.raza = raza;
        this.salvaje = salvaje;
        this.hambriento = hambriento;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "edad=" + edad +
                ", raza='" + raza + '\'' +
                ", salvaje=" + salvaje +
                ", hambriento=" + hambriento +
                '}';
    }
}
