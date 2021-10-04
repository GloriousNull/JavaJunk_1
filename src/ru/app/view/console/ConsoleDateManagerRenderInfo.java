package ru.app.view.console;

import ru.app.dao.MemorableDate;
import ru.app.model.CommandExecutionInfo;
import ru.app.view.DateManagerRenderInfo;
import ru.app.view.Renderable;

import java.util.ArrayList;
import java.util.List;

public class ConsoleDateManagerRenderInfo implements DateManagerRenderInfo
{
    private List<Renderable> renderables;

    public ConsoleDateManagerRenderInfo()
    {
        renderables = new ArrayList<>();
    }

    @Override
    public void addRenderableHelp()
    {
        renderables.add(new ConsoleRenderableHelp());
    }

    @Override
    public void addRenderAbleCommandExecutionStatus(CommandExecutionInfo commandInfo)
    {
        renderables.add(new ConsoleRenderableCommandExecutionStatus(commandInfo));
    }

    @Override
    public void addRenderableDateTable(List<MemorableDate> datesToRender)
    {
        renderables.add(new ConsoleRenderableDateTable(datesToRender));
    }

    @Override
    public List<Renderable> getRenderables()
    {
        return renderables;
    }


    @Override
    public void clear()
    {
        renderables.clear();
    }
}
