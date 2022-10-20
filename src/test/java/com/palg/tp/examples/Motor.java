package com.palg.tp.examples;

import com.palg.tp.annotations.Persistable;

import java.util.Objects;

public class Motor {
    String tipo;
    @Persistable
    int caballosFuerza;

    public Motor(String tipo, int caballosFuerza) {
        this.tipo = tipo;
        this.caballosFuerza = caballosFuerza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Motor motor = (Motor) o;
        return caballosFuerza == motor.caballosFuerza && Objects.equals(tipo, motor.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, caballosFuerza);
    }
}
