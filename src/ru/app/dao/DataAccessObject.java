package ru.app.dao;

import java.util.List;
import java.util.Optional;

public interface DataAccessObject<T>
{
    List<T> getAll();
    Optional<T> getById(Integer id);
    boolean insert(T object);
    boolean update(T object);
    boolean delete(Integer id);
}
