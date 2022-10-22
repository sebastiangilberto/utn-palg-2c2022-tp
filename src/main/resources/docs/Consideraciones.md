# Consideraciones / Asunciones:

## 1) Listeners

- Un Listener por PersistentObject.
- Puede existir más de una implementación. La única requerida para este trabajo es una que loguee por consola.
- El tiempo de scan de los mismos está como una property por si se deseara cambiar con el tiempo. Es para todos el mismo.
- Un listener siempre está asociado a un PersistentObjectImpl.
- El listener siempre loguea en su tiempo de scan, aunque en el delta t hayan sucedido cambios de estado (se entera unos segundos/minutos más tarde).

## 2) Session
 
- Si bien tienen un status asociado (OPEN, CLOSED), es solo interno a la app. 
- Se persisten en una db.
- En load, store, destroy y remove, si la session no existe, se arroja una exception.
- Cuando creamos una session que ya existe, se arroja una exception.
- Cuando hacemos un destroy de la session, se eliminan todos los objetos asociados a la misma de la base.

## 3) Annotations - Persistencia de objetos

- Persistable a nivel clase → todos los atributos persistibles salvo el NotPersistable.
- Persistable a atributo → se persiste ese atributo en particular sin la necesidad de ponerlo a nivel clase.
- Not Persistable → es solo a nivel atributo para los que no quisieramos persistir. Para una clase que no lleva el Persistable a nivel clase es lo mismo ponerlo o no en algún atributo.
- Solo puede haber un objeto de un solo tipo de clase asociado a una session (key).
- Si deseo agregar un objeto de una clase a una session y la misma ya tiene uno de esa clase, se pisa por el nuevo.
- Si mi objeto no es persistible (termina generando un json vacío), lo ignoro.
- Cuando quiero hacer un load o remove de un objeto el cual no existe, devolvemos null.
