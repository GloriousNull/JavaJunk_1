package ru.app;

import ru.app.di.*;
import ru.app.di.Application;

import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        ApplicationContext context = Application.run("ru.app", new HashMap<>());
        var demo = context.getObject(ru.app.Application.class);
        demo.run();
    }
}
