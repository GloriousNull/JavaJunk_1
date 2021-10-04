package ru.app.di;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext
{
    @Setter
    private ObjectFactory factory;
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private final Config config;

    public ApplicationContext(Config config)
    {
        this.config = config;
    }

    public <T> T getObject(Class<T> type)
    {
        if (cache.containsKey(type))
            return (T)cache.get(type);

        Class<? extends T> implementationClass = type;

        if (type.isInterface())
            implementationClass = config.getImplClass(type);

        T object = factory.createObject(implementationClass);

        if (implementationClass.isAnnotationPresent(Singleton.class))
            cache.put(type, object);

        return object;
    }
}
