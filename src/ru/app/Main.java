package ru.app;

import ru.app.dao.Database;
import ru.app.dao.MemorableDateDataAccess;
import ru.app.dao.MemorableDateDataAccessImplementation;
import ru.app.dao.StaticDatabase;
import ru.app.di.Application;
import ru.app.model.DateManager;
import ru.app.model.StandardDateManager;
import ru.app.view.DateManagerRenderInfo;
import ru.app.view.DateManagerRenderer;
import ru.app.view.console.ConsoleDateManagerRenderInfo;
import ru.app.view.console.ConsoleDateManagerRenderer;
import ru.app.view.input.ConsoleDateManagerInput;
import ru.app.view.input.DateManagerInput;

import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        var context = Application.run("ru.app", new HashMap<>()
        {{
            put(DateManager.class, StandardDateManager.class);
            put(DateManagerRenderer.class, ConsoleDateManagerRenderer.class);
            put(DateManagerRenderInfo.class, ConsoleDateManagerRenderInfo.class);
            put(DateManagerInput.class, ConsoleDateManagerInput.class);
            put(MemorableDateDataAccess.class, MemorableDateDataAccessImplementation.class);
            put(Database.class, StaticDatabase.class);
        }});

        var demo = context.getObject(ru.app.Application.class);
        demo.run();
    }
}
