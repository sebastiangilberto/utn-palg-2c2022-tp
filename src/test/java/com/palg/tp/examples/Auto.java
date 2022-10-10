package com.palg.tp.examples;

import com.palg.tp.annotations.NotPersistable;
import com.palg.tp.annotations.Persistable;

import java.math.BigDecimal;
import java.util.Objects;

@Persistable
public class Auto {
    @NotPersistable
    String marca;
    @Persistable
    String color;

    @Persistable
    int ruedas;
    @NotPersistable
    long combustible;
    @Persistable
    BigDecimal precio;

    @Persistable
    Motor motor;

    public Auto(String marca, String color, int ruedas, long combustible, BigDecimal precio, Motor motor) {
        this.marca = marca;
        this.color = color;
        this.ruedas = ruedas;
        this.combustible = combustible;
        this.precio = precio;
        this.motor = motor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return ruedas == auto.ruedas && combustible == auto.combustible && Objects.equals(marca, auto.marca) && Objects.equals(color, auto.color) && Objects.equals(precio, auto.precio) && Objects.equals(motor, auto.motor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, color, ruedas, combustible, precio, motor);
    }
}

