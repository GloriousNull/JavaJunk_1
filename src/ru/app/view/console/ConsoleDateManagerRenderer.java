package ru.app.view.console;

import ru.app.view.DateManagerRenderInfo;
import ru.app.view.DateManagerRenderer;

public class ConsoleDateManagerRenderer implements DateManagerRenderer
{
    @Override
    public void render(DateManagerRenderInfo info)
    {
        if (!info.getRenderables().isEmpty())
        {
            System.out.println("-----------------------------------");

            for (var renderable : info.getRenderables())
                renderable.render();

            System.out.print("-----------------------------------\nCommand: ");
        }
    }
}
