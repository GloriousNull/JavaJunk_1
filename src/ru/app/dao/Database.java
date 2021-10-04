package ru.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

interface QueryResultFetcher<RetType>
{
    RetType invoke(ResultSet results);
}

public interface Database
{
    void prepareQuery(String query, String... valuesToBind);
    <T> T executeQueryAndReturn(QueryResultFetcher<T> composer);
    void executeQuery() throws SQLException;
}
