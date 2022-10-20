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
        /*
        a) clase sin Persistable
            1) field sin Persistable -> skipeo (true)
            2) field con NotPersistable -> skipeo (true)
            3) field con Persistable -> no skipeo (false)
        b) clase con Persistable
            1) field sin Persistable -> no skipeo (false)
            2) field con NotPersistable -> skipeo (true)
            3) field con Persistable -> no skipeo (false)
         */
        boolean skip;

        Class<?> declaringClass = f.getDeclaringClass();

        if (declaringClass.getAnnotation(Persistable.class) != null){
            // La clase tiene la annotation Persistable, entonces omito solo si el campo tiene el NotPersistable
            skip = f.getAnnotation(NotPersistable.class) != null;
        } else {
            // La clase NO tiene la annotation Persistable, entonces omito solo si el campo NO tiene el Persistable
            skip = f.getAnnotation(Persistable.class) == null;
        }

        return skip;
    }
}
