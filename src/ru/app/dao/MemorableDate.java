package ru.app.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class MemorableDate
{
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String description;

    public MemorableDate()
    {
        id = null;
        date = null;
        description = null;
    }

    public MemorableDate(Integer id, Date date, String description)
    {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public MemorableDate(Date date, String description)
    {
        this.id = null;
        this.date = date;
        this.description = description;
    }
}
