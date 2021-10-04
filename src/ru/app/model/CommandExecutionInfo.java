package ru.app.model;

import lombok.Getter;
import lombok.Setter;
import ru.app.view.input.DateManagerInputEvent;

public class CommandExecutionInfo
{
    public enum ExecutionStatus
    {
        SUCCESS,
        FAILURE
    }

    @Getter
    @Setter
    private ExecutionStatus status;

    @Getter
    @Setter
    private DateManagerInputEvent command;

    CommandExecutionInfo()
    {
        this.status = null;
        this.command = null;
    }

    CommandExecutionInfo(ExecutionStatus status, DateManagerInputEvent command)
    {
        this.status = status;
        this.command = command;
    }
}
