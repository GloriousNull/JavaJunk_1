package ru.app;

import ru.app.di.InjectByType;
import ru.app.model.DateManager;
import ru.app.view.DateManagerRenderer;

public class Application
{
    @InjectByType
    private DateManager manager;
    @InjectByType
    private DateManagerRenderer renderer;

    public void run()
    {
        boolean running = true;

        while (running)
        {
            renderer.render(manager.getRenderInfo());

            running = manager.processInput();
        }
    }
}
