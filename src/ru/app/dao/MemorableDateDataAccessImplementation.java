package ru.app.dao;

import ru.app.di.InjectByType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MemorableDateDataAccessImplementation implements MemorableDateDataAccess
{
    @InjectByType
    private final Database dataSource;

    private static final String GET_ALL_QUERY = "SELECT * FROM date";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM date WHERE id = ? LIMIT 1";
    private static final String GET_NEXT_BY_ID_QUERY = "SELECT * FROM date WHERE date > (SELECT date from date where id = ?) LIMIT 1";
    private static final String GET_NEXT_BY_DATE_QUERY = "SELECT * FROM date WHERE date > ? LIMIT 1";
    private static final String INSERT_QUERY = "INSERT INTO date (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE date SET date = ?, description = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM date WHERE id = ?";

    public MemorableDateDataAccessImplementation()
    {
        dataSource = new StaticDatabase();
    }

    @Override
    public List<MemorableDate> getAll()
    {
        dataSource.prepareQuery(GET_ALL_QUERY);

        return dataSource.executeQueryAndReturn((datasourceResult) ->
        {
            var results = new ArrayList<MemorableDate>();
            try
            {
                while (datasourceResult.next())
                {
                    results.add(new MemorableDate
                            (
                                    datasourceResult.getInt(1),
                                    new Date(datasourceResult.getDate(2).getTime()),
                                    datasourceResult.getString(3)
                            ));
                }
            }
            catch (SQLException exception)
            {
                exception.printStackTrace();
            }

            return results;
        });
    }

    @Override
    public Optional<MemorableDate> getById(Integer id)
    {
        dataSource.prepareQuery(GET_BY_ID_QUERY, id.toString());

        MemorableDate result = composeOneDate();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<MemorableDate> getNextById(Integer id)
    {
        dataSource.prepareQuery(GET_NEXT_BY_ID_QUERY, id.toString());

        MemorableDate result = composeOneDate();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<MemorableDate> getNextByDate(Date date)
    {
        dataSource.prepareQuery(GET_NEXT_BY_DATE_QUERY, new java.sql.Date(date.getTime()).toString());

        MemorableDate result = composeOneDate();

        return Optional.ofNullable(result);
    }

    @Override
    public boolean insert(MemorableDate object)
    {
        dataSource.prepareQuery
                (
                    INSERT_QUERY,
                    new java.sql.Date(object.getDate().getTime()).toString(),
                    object.getDescription()
                );

        try
        {
            dataSource.executeQuery();
        }
        catch (SQLException exception)
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean update(MemorableDate object)
    {
        dataSource.prepareQuery
                (
                    UPDATE_QUERY,
                    new java.sql.Date(object.getDate().getTime()).toString(),
                    object.getDescription(),
                    object.getId().toString()
                );

        try
        {
            dataSource.executeQuery();
        }
        catch (SQLException exception)
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Integer id)
    {
        dataSource.prepareQuery(DELETE_QUERY, id.toString());

        try
        {
            dataSource.executeQuery();
        }
        catch (SQLException exception)
        {
            return false;
        }

        return true;
    }

    private MemorableDate composeOneDate()
    {
       return dataSource.executeQueryAndReturn((datasourceResult) ->
        {
            try
            {
                if (datasourceResult.next())
                {
                    return new MemorableDate
                            (
                                    datasourceResult.getInt(1),
                                    new Date(datasourceResult.getDate(2).getTime()),
                                    datasourceResult.getString(3)
                            );
                }
            }
            catch (SQLException exception)
            {
                exception.printStackTrace();
            }

            return null;
        });
    }
}
