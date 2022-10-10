package com.palg.tp.mapper;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.palg.tp.annotations.NotPersistable;
import com.palg.tp.annotations.Persistable;
public class PersistableStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        /* si la clase "contenedora"
         // a) existe
                        -> clase contenedora no tiene la annotation Persistable -> skipeo (true)
                        -> clase contenedora tiene la annotation Persistable -> no skipeo (false)
         // b) no existe
                        -> no tiene la annotation Persistable -> skipeo (true)
                        -> tiene la annotation Persistable -> no skipeo (false)
         */
        Class<?> declaringClass = f.getDeclaringClass();

        // TODO: si el Persistable a nivel class hace todo persistible salvo los explícitos, hay que cambiar esto
        if (declaringClass.getAnnotation(Persistable.class) != null){
            return f.getAnnotation(Persistable.class) == null || f.getAnnotation(NotPersistable.class) != null;
        }

        return true;
    }
}
