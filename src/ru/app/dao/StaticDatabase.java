package ru.app.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StaticDatabase implements Database
{
    private String currentQuery;
    private final List<MemorableDate> staticData;

    public StaticDatabase()
    {
        currentQuery = null;
        staticData = new ArrayList<>();
        staticData.add(new MemorableDate(1, new Date(), "42"));
        staticData.add(new MemorableDate(2, new Date(), "451"));
        staticData.add(new MemorableDate(3, new Date(), "1984"));
    }

    @Override
    public void prepareQuery(String query, String... valuesToBind)
    { currentQuery = query; }

    @Override
    public <T> T executeQueryAndReturn(QueryResultFetcher<T> composer)
    {
        List<Object> ret = new ArrayList<>();
        ret.add(new MemorableDate(1, new Date(), "42"));

        Scanner s = new Scanner(currentQuery);

        while (s.hasNext())
        {
            var temp = s.next();
            if (!s.hasNext())
                if (temp.equals("1"))
                    return (T)ret.get(0);
        }

        return (T) ret;
    }

    @Override
    public void executeQuery() throws SQLException {}
}
