package ru.app.dao;

import java.util.Date;
import java.util.Optional;

public interface MemorableDateDataAccess extends DataAccessObject<MemorableDate>
{
    Optional<MemorableDate> getNextById(Integer id);
    Optional<MemorableDate> getNextByDate(Date date);
}