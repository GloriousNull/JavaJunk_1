package ru.app.view.console;

import ru.app.view.Renderable;

public class ConsoleRenderableWelcome implements Renderable
{
    @Override
    public void render()
    {
        var welcome = "Welcome to Memorable Date manager\nType \"help\" for command information";
        System.out.println(welcome);
    }
}
