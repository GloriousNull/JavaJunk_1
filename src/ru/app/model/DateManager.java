package ru.app.model;

import ru.app.view.DateManagerRenderInfo;

public interface DateManager
{
    boolean processInput();
    DateManagerRenderInfo getRenderInfo();
}
