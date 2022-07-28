package nl.saxion.sasa.event;

import nl.saxion.sasa.ApplicationRunner;
import nl.saxion.sasa.SasaApplication;
import nl.saxion.sasa.anotations.Runner;
import nl.saxion.sasa.event.anotations.Handler;
import nl.saxion.sasa.event.anotations.Listener;
import nl.saxion.sasa.event.stream.StreamSubscription;
import nl.saxion.sasa.io.ClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Runner
public class EventHandlerRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(EventHandlerRunner.class);
    ClassLoader classLoader;

        private void loadHandlers(SasaApplication application) {
        Set<Class<?>> classes = classLoader.loadClassFromAnnotation(Handler.class);
        for(Class<?> clazz : classes) {
            if (EventHandler.class.isAssignableFrom(clazz)) {
              try {
                  application.getEnvironment().getEventManager().addHandler(
                          new EventHandlerBuilder()
                                  .setHandler((EventHandler) clazz.getConstructor().newInstance())
                                  .addSuffix(clazz.getAnnotation(Handler.class).suffix())
                                  .setPrefix(clazz.getAnnotation(Handler.class).prefix())
                  );
              } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                       InvocationTargetException e) {
                  throw new RuntimeException(e.getMessage());
              }
            } else {
                log.error("Class {} does not implement {}", clazz.getName(), EventHandler.class.getName());
            }
        }
    }

    private void loadListeners(SasaApplication application) {
        Set<Class<?>> classes = classLoader.loadClassFromAnnotation(Listener.class);
        for(Class<?> clazz : classes) {
            if (StreamSubscription.class.isAssignableFrom(clazz)) {
                try {
                    EventHandler handler = application.getEnvironment().getEventManager()
                            .getHandler(clazz.getAnnotation(Listener.class).target());
                    handler.getStream().listen((StreamSubscription) clazz.getConstructor().newInstance());
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(clazz.getName() + " does not extends " + Listener.class.getName());
                }
            } else {
                throw new RuntimeException(clazz.getName() + " does not extends " + Listener.class.getName());
            }
        }
    }

    @Override
    public void run(SasaApplication application) {
        this.classLoader = new ClassLoader(application);
        application.getEnvironment().setEventManager(new EventManager());

        loadHandlers(application);
        loadListeners(application);
    }
}
