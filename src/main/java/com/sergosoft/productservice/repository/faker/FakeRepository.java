package com.sergosoft.productservice.repository.faker;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.io.Serializable;

/**
 * Represents a data repository of type T and id-type ID.<br>
 * Simulates CRUD operations on Category domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 * @param <T> Saving data type.
 * @param <ID> Data identifier type.
 */
//@Deprecated(forRemoval = true)
public abstract class FakeRepository <T, ID extends Serializable> implements FakeCrudRepository<T, ID> {

    protected ID lastId;
    protected final Map<ID, T> database = new HashMap<>();

    protected abstract ID nextId();

    @Override
    public Optional<T> findById(ID primaryKey) {
        return Optional.ofNullable(database.get(primaryKey));
    }

    @Override
    public void deleteById(ID id) {
        database.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return database.containsKey(id);
    }
}
