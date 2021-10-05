package ru.app.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StaticDatabase implements Database
{
    private String currentQuery;
    private List<MemorableDate> staticData;
    private List<MemorableDate> results;
    private Integer id;

    public StaticDatabase()
    {
        id = 0;
        currentQuery = null;
        staticData = new ArrayList<>();
        results = new ArrayList<>();

        var s = new SimpleDateFormat("yyyy-MM-dd");
        s.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        try
        {
            TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
            staticData.add(new MemorableDate(++id, s.parse("1994-02-22"), "42"));
            staticData.add(new MemorableDate(++id, s.parse("1997-02-22"), "451"));
            staticData.add(new MemorableDate(++id, s.parse("1999-02-22"), "1984"));
        } catch (ParseException ignored) {}

    }

    @Override
    public void prepareQuery(String query, String... valuesToBind)
    {
        results = null;

        currentQuery = query;

        if (valuesToBind.length == 0)
        {
            results = staticData;

            return;
        }

        var scanner = new Scanner(query);

        if (query.equals("SELECT * FROM date WHERE id = ? LIMIT 1"))
        {
            var id = Integer.parseInt(valuesToBind[0]);

            if (id >= staticData.size() || id < 1)
            {
                results = new ArrayList<>();
                return;
            }

            for (var date : staticData)
                if (date.getId() == id)
                {
                    results = new ArrayList<>();
                    results.add(date);
                    return;
                }
        }

        if (query.equals("SELECT * FROM date WHERE date > (SELECT date from date where id = ?) LIMIT 1"))
        {
            Integer id = Integer.parseInt(valuesToBind[0]);

            if (id >= staticData.size() || id < 1)
            {
                results = new ArrayList<>();
                return;
            }

            MemorableDate dateToCompare = null;

            for (var date : staticData)
                if (date.getId() == id)
                {
                    dateToCompare = date;
                    break;
                }

            MemorableDate min = null;

            for (var date : staticData)
            {
                if (date.getDate().compareTo(dateToCompare.getDate()) > 0)
                {
                    min = date;
                    break;
                }
            }

            if (min != null)
                for (var date : staticData)
                {
                    if (min.getDate().compareTo(date.getDate()) > 0 && date.getDate().compareTo(dateToCompare.getDate()) > 0)
                    {
                        min = date;
                    }
                }

            results = new ArrayList<>();
            if (min != null)
                results.add(min);
        }

        if (query.equals("SELECT * FROM date WHERE date > ? LIMIT 1"))
        {
            Date dateToCompare = null;
            try
            {
                var s = new SimpleDateFormat("yyyy-MM-dd");
                s.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

                dateToCompare = s.parse(valuesToBind[0]);
            }
            catch (ParseException e)
            {
                if (id >= staticData.size() || id < 1)
                {
                    results = new ArrayList<>();
                    return;
                }
            }

            MemorableDate min = null;

            for (var date : staticData)
            {
                if (date.getDate().compareTo(dateToCompare) > 0)
                {
                    min = date;
                    break;
                }
            }

            if (min != null)
                for (var date : staticData)
                {
                    if (min.getDate().compareTo(date.getDate()) > 0 && date.getDate().compareTo(dateToCompare) > 0)
                    {
                        min = date;
                    }
                }

            results = new ArrayList<>();
            if (min != null)
                results.add(min);
            return;
        }

        if (query.equals("INSERT INTO date (?, ?)"))
        {
            Date date = null;

            try
            {
                var s = new SimpleDateFormat("yyyy-MM-dd");
                s.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

                date = s.parse(valuesToBind[0]);
            }
            catch (ParseException e)
            {
                if (id >= staticData.size() || id < 0)
                {
                    results = new ArrayList<>();
                    return;
                }
            }
            var desc = valuesToBind[1];

            staticData.add(new MemorableDate(++id, date, desc));

            return;
        }

        if (query.equals("UPDATE date SET date = ?, description = ? WHERE id = ?"))
        {
            var date = new Date(valuesToBind[0]);
            var desc = valuesToBind[1];
            var id = Integer.parseInt(valuesToBind[2]);

            if (id >= staticData.size() || id < 0)
            {
                results = new ArrayList<>();
                return;
            }

            for (var d : staticData)
                if (d.getId() == id)
                {
                    d.setDate(date);
                    d.setDescription(desc);

                    return;
                }
        }

        if (query.equals("DELETE FROM date WHERE id = ?"))
        {
            var id = Integer.parseInt(valuesToBind[0]);

            staticData.removeIf((obj) -> { return obj.getId() == id; });
        }
    }

    @Override
    public <T> T executeQueryAndReturn(QueryResultComposer<T> composer)
    {
        var results = new StaticResultSet<MemorableDate>(this.results);

        return composer.invoke(results);

//        List<Object> ret = new ArrayList<>();
//        ret.add(new MemorableDate(1, new Date(), "451"));
//
//        Scanner s = new Scanner(currentQuery);
//
//        while (s.hasNext())
//        {
//            var temp = s.next();
//            if (!s.hasNext())
//                if (temp.equals("1"))
//                    return (T)ret.get(0);
//        }
//
//        return (T) ret;
    }

    @Override
    public void executeQuery() throws SQLException {}
}
