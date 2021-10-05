package ru.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

interface QueryResultComposer<RetType>
{
    RetType invoke(ResultSet results);
}

public interface Database
{
    void prepareQuery(String query, String... valuesToBind);
    <T> T executeQueryAndReturn(QueryResultComposer<T> composer);
    void executeQuery() throws SQLException;
}
