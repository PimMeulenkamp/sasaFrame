package nl.saxion.sasa;

import nl.saxion.sasa.anotations.Runner;
import nl.saxion.sasa.event.EventHandlerRunner;
import nl.saxion.sasa.event.EventManager;
import nl.saxion.sasa.event.anotations.Handler;
import nl.saxion.sasa.io.ClassLoader;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ApplicationRunnerFactory {
    private final SasaApplication application;

    public ApplicationRunnerFactory(SasaApplication application) {
        this.application = application;
    }

    public ApplicationRunners loadApplicationRunners()  {
        Set<Class<?>> runners = new ClassLoader(application).loadClassFromAnnotation(Runner.class);

        List<ApplicationRunner> applicationRunners = new ArrayList<>();

        // Manually add the event handler runner
        applicationRunners.add(new EventHandlerRunner());

        for (Class<?> clazz: runners) {
            if(!clazz.isInstance(ApplicationRunner.class)) {
                throw new RuntimeException("Class does not implement " + ApplicationRunner.class);
            }
            try {
                applicationRunners.add((ApplicationRunner) clazz.getConstructor().newInstance());
            } catch (Exception e) {
                // TODO add logger.
            }
        }
        return new ApplicationRunners(applicationRunners);
    }
}
