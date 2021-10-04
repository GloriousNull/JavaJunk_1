package ru.app.view.console;

import ru.app.view.Renderable;

public class ConsoleRenderableHelp implements Renderable
{
    @Override
    public void render()
    {
        String help = "Usage:\n" +
                "exit - stop application\n" +
                "add <MM/dd/yyyy> <text> - add new memorable date with following description\n" +
                "delete <id> - delete memorable date with following id\n" +
                "showAll - show all your memorable dates\n" +
                "showNext <id> - show memorable date next to date with following id\n" +
                "showNextAfter <MM/dd/yyyy> - show memorable date next to memorable date with following date";
        System.out.println(help);
    }
}
