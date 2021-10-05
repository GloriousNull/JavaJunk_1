package ru.app.di;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JavaConfig implements Config
{
    @Getter
    private final Reflections scanner;
    private final Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass)
    {
        this.ifc2ImplClass = Objects.requireNonNullElseGet(ifc2ImplClass, HashMap::new);

        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc)
    {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass ->
        {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

            if (classes.size() != 1)
            {
                throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
            }

            return classes.iterator().next();
        });

    }
}












