package com.kotlarz.config.container;

import com.google.inject.AbstractModule;
import com.kotlarz.App;
import org.reflections.Reflections;

import java.util.Set;

public class GuiceModule
        extends AbstractModule {
    @Override
    protected void configure() {
        Reflections reflections = new Reflections(App.class.getPackage().getName());
        Set<Class<?>> beanTypes = reflections.getTypesAnnotatedWith(Bean.class);

        for (Class<?> clazz : beanTypes) {
            bind(clazz);
        }
    }
}
