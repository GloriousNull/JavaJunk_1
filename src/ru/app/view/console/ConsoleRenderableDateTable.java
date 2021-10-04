package ru.app.view.console;

import ru.app.dao.MemorableDate;
import ru.app.view.Renderable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ConsoleRenderableDateTable implements Renderable
{
    List<MemorableDate> datesToRender;

    ConsoleRenderableDateTable(List<MemorableDate> datesToRender)
    {
        this.datesToRender = datesToRender;
    }

    @Override
    public void render()
    {
        for (var date : datesToRender)
        {
            System.out.printf("Id: %s\n", date.getId().toString());
            System.out.printf("Date: %s\n", date.getDate().toString());
            System.out.printf("Description: %s\n", date.getDescription());
        }
    }
}
