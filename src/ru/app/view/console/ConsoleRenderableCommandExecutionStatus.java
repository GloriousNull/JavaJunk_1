package ru.app.view.console;

import ru.app.model.CommandExecutionInfo;
import ru.app.view.Renderable;

public class ConsoleRenderableCommandExecutionStatus implements Renderable
{
    private final CommandExecutionInfo info;

    ConsoleRenderableCommandExecutionStatus(CommandExecutionInfo info)
    {
        this.info = info;
    }

    @Override
    public void render()
    {
        var status = "";

        if (info.getStatus() == CommandExecutionInfo.ExecutionStatus.FAILURE)
        {
            switch (info.getCommand().getType())
            {
                case UNKNOWN_EVENT -> status = "Unknown command or invalid syntax, type \"help\" for usage information.";

                case ADD_DATE_EVENT -> status = "Failed to add date";

                case GIVE_NEXT_DATE_EVENT, GIVE_NEXT_AFTER_DATE_EVENT, GIVE_ALL_DATES_EVENT -> status = "Failed to retrieve next date";

                case HELP_EVENT, EXIT_EVENT -> status = "We are doomed";

                case DELETE_DATE_EVENT -> status = "Failed to delete date";
            }
        }
        else
            status = "Command executed successfully";

        System.out.println(status);
    }
}
