package com.sergosoft.productservice.repository.faker;

import java.io.Serializable;
import java.util.Optional;

public interface FakeCrudRepository <T, ID extends Serializable> {

    T save(T entity);
    Optional<T> findById(ID primaryKey);
    void deleteById(ID id);
    void deleteAll();
    boolean existsById(ID id);
}
