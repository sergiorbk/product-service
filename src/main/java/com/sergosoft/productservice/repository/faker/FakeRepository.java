package com.sergosoft.productservice.repository.faker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a data repository of type T and id-type ID.<br>
 * Simulates CRUD operations on Category domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 * @param <T> Saving data type.
 * @param <ID> Data identifier type.
 */
//@Deprecated(forRemoval = true)
public abstract class FakeRepository <T, ID extends Number> {

    protected ID lastId = (ID) (Number) 0;
    protected final Map<ID, T> database = new HashMap<>();

    protected abstract ID nextId();
    public abstract T save(T entity);
    public abstract Optional<T> findById(ID primaryKey);
    public abstract void delete(ID id);
}
