package ru.app.view;

import ru.app.dao.MemorableDate;
import ru.app.model.CommandExecutionInfo;

import java.util.List;

public interface DateManagerRenderInfo
{
    void addRenderableHelp();
    void addRenderAbleCommandExecutionStatus(CommandExecutionInfo command);
    void addRenderableDateTable(List<MemorableDate> datesToRender);
    void addRenderableWelcome();
    List<Renderable> getRenderables();
    void clear();
}
