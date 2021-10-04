package ru.app.di;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;


public class ObjectFactory
{
    private final ApplicationContext context;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context)
    {
        this.context = context;

        for (Class<? extends ObjectConfigurator> configuratorClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class))
            configurators.add(configuratorClass.getDeclaredConstructor().newInstance());
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implementationClass)
    {
        T object = create(implementationClass);

        configure(object);

        invokeInit(implementationClass, object);

        return object;
    }

    private <T> void invokeInit(Class<T> implClass, T object) throws IllegalAccessException, InvocationTargetException
    {
        for (Method method : implClass.getMethods())
        {
            if (method.isAnnotationPresent(PostConstruct.class))
                method.invoke(object);
        }
    }

    private <T> void configure(T object)
    {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(object, context));
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return implClass.getDeclaredConstructor().newInstance();
    }
}




