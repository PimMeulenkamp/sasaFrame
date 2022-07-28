package nl.saxion.sasa.io;

import nl.saxion.sasa.SasaApplication;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClassLoader {

    private static Reflections reflections;

    public ClassLoader(SasaApplication application){
        if (reflections == null) {
            reflections = new Reflections(application.getPrimarySource().getPackageName());
        }
    }

    public <T extends Annotation> Set<Class<?>> loadClassFromAnnotation(Class<T> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }

    public <T> Set<Class<? extends T>> loadClassFromSubType(Class<T> clazz) {
        return reflections.getSubTypesOf(clazz);
    }
}
