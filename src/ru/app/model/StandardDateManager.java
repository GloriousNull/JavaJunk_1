package ru.app.model;

import ru.app.dao.MemorableDate;
import ru.app.dao.MemorableDateDataAccess;
import ru.app.di.InjectByType;
import ru.app.view.DateManagerRenderInfo;
import ru.app.view.input.DateManagerInput;
import ru.app.view.input.DateManagerInputEvent;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class StandardDateManager implements DateManager
{
    @InjectByType
    private DateManagerInput input;
    @InjectByType
    private MemorableDateDataAccess dao;
    @InjectByType
    private DateManagerRenderInfo renderInfo;

    @PostConstruct
    public void Init()
    {
        renderInfo.addRenderableWelcome();
    }

    @Override
    public boolean processInput()
    {
        renderInfo.clear();

        var event = input.pollEvent();

        CommandExecutionInfo result = null;

        switch (event.getType())
        {
            case EXIT_EVENT -> { return false; }

            case HELP_EVENT -> renderInfo.addRenderableHelp();

            case GIVE_ALL_DATES_EVENT -> result = handleGiveAllEvent(event);

            case GIVE_NEXT_DATE_EVENT -> result = handleGiveNextEvent(event);

            case GIVE_NEXT_AFTER_DATE_EVENT -> result = handleGiveNextAfterEvent(event);

            case ADD_DATE_EVENT -> result = handleAddDateEvent(event);

            case DELETE_DATE_EVENT -> result = handleDeleteDateEvent(event);

            case UNKNOWN_EVENT -> result = new CommandExecutionInfo(CommandExecutionInfo.ExecutionStatus.FAILURE,
                                                                    event);
        }

        if (result != null)
            renderInfo.addRenderAbleCommandExecutionStatus(result);

        return true;
    }

    @Override
    public DateManagerRenderInfo getRenderInfo()
    {
        return renderInfo;
    }

    private CommandExecutionInfo handleGiveAllEvent(DateManagerInputEvent event)
    {
        var allDates = dao.getAll();
        var status = addTableRenderInfo(allDates);

        status.setCommand(event);

        return status;
    }

    private CommandExecutionInfo handleGiveNextEvent(DateManagerInputEvent event)
    {
        var dateToGive = dao.getNextById(event.getDate().getId());

        var dates = new ArrayList<MemorableDate>();
        dateToGive.ifPresent(dates::add);

        var status = addTableRenderInfo(dates);
        status.setCommand(event);

        return status;
    }

    private CommandExecutionInfo handleGiveNextAfterEvent(DateManagerInputEvent event)
    {
        var dateToGive = dao.getNextByDate(event.getDate().getDate());

        var dates = new ArrayList<MemorableDate>();
        dateToGive.ifPresent(dates::add);

        var status = addTableRenderInfo(dates);
        status.setCommand(event);

        return status;
    }

    private CommandExecutionInfo addTableRenderInfo(List<MemorableDate> datesToAdd)
    {
        var status = new CommandExecutionInfo();

        if (!datesToAdd.isEmpty())
        {
            renderInfo.addRenderableDateTable(datesToAdd);
            status.setStatus(CommandExecutionInfo.ExecutionStatus.SUCCESS);
        }
        else
            status.setStatus(CommandExecutionInfo.ExecutionStatus.FAILURE);

        return status;
    }

    private CommandExecutionInfo handleAddDateEvent(DateManagerInputEvent event)
    {
        var success = dao.insert(event.getDate());
        var result = new CommandExecutionInfo();

        if (success)
            result.setStatus(CommandExecutionInfo.ExecutionStatus.SUCCESS);
        else
            result.setStatus(CommandExecutionInfo.ExecutionStatus.FAILURE);

        result.setCommand(event);

        return result;
    }

    private CommandExecutionInfo handleDeleteDateEvent(DateManagerInputEvent event)
    {
        var success = dao.delete(event.getDate().getId());
        var result = new CommandExecutionInfo();

        if (success)
            result.setStatus(CommandExecutionInfo.ExecutionStatus.SUCCESS);
        else
            result.setStatus(CommandExecutionInfo.ExecutionStatus.FAILURE);

        result.setCommand(event);

        return result;
    }
}
