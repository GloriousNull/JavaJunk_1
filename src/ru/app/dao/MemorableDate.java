package ru.app.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class MemorableDate implements Serializable
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

    @Override
    public Object getField(Integer index)
    {
        if (index == 1)
            return id;

        if (index == 2)
            return date;

        if (index == 3)
            return description;

        return null;
    }
}
