package ru.app.view.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleDateManagerInput implements DateManagerInput
{
    @Override
    public DateManagerInputEvent pollEvent()
    {
        var scanner = new Scanner(System.in);

        var event  = new DateManagerInputEvent();
        var command = scanner.next();

        if (command.equals("help"))
        {
            event.setType(DateManagerInputEvent.eventType.HELP_EVENT);

            return event;
        }

        if (command.equals("exit"))
        {
            event.setType(DateManagerInputEvent.eventType.EXIT_EVENT);

            return event;
        }

        if (command.equals("showAll"))
        {
            event.setType(DateManagerInputEvent.eventType.GIVE_ALL_DATES_EVENT);

            return event;
        }

        if (command.equals("showNext"))
        {
            event.setType(DateManagerInputEvent.eventType.GIVE_NEXT_DATE_EVENT);

            try
            {
                var id = scanner.nextInt();
                event.getDate().setId(id);
            }
            catch (InputMismatchException exception)
            {
                event.setType(DateManagerInputEvent.eventType.UNKNOWN_EVENT);

                return event;
            }

            return event;
        }

        if (command.equals("showNextAfter"))
        {
            event.setType(DateManagerInputEvent.eventType.GIVE_NEXT_AFTER_DATE_EVENT);

            var date = scanner.next();

            try
            {
                event.getDate().setDate(new SimpleDateFormat("MM/dd/yyyy").parse(date));
            }
            catch (ParseException exception)
            {
                event.setType(DateManagerInputEvent.eventType.UNKNOWN_EVENT);

                return event;
            }

            return event;
        }

        if (command.equals("add"))
        {
            event.setType(DateManagerInputEvent.eventType.ADD_DATE_EVENT);

            var date = scanner.next();

            try
            {
                event.getDate().setDate(new SimpleDateFormat("MM/dd/yyyy").parse(date));
            }
            catch (ParseException exception)
            {
                event.setType(DateManagerInputEvent.eventType.UNKNOWN_EVENT);

                return event;
            }

            var description = scanner.nextLine().trim();
            event.getDate().setDescription(description);

            return event;
        }

        if (command.equals("delete"))
        {
            event.setType(DateManagerInputEvent.eventType.DELETE_DATE_EVENT);

            try
            {
                var id = scanner.nextInt();
                event.getDate().setId(id);
            }
            catch (InputMismatchException exception)
            {
                event.setType(DateManagerInputEvent.eventType.UNKNOWN_EVENT);

                return event;
            }

            return event;
        }

        event.setType(DateManagerInputEvent.eventType.UNKNOWN_EVENT);

        return event;
    }
}
