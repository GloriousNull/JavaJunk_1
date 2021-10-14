package ru.app.view.input;

import lombok.Getter;
import lombok.Setter;
import ru.app.dao.MemorableDate;

public class DateManagerInputEvent
{
    @Getter
    @Setter
    private eventType type;

    public enum eventType
    {
        HELP_EVENT,
        EXIT_EVENT,
        GIVE_ALL_DATES_EVENT,
        GIVE_NEXT_DATE_EVENT,
        GIVE_NEXT_AFTER_DATE_EVENT,
        ADD_DATE_EVENT,
        DELETE_DATE_EVENT,
        UNKNOWN_EVENT
    }

    @Getter
    private MemorableDate date;

    public DateManagerInputEvent()
    {
        date = new MemorableDate();
    }
}
