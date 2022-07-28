package nl.saxion.sasa;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRunners {
    @Getter
    List<ApplicationRunner> runners;

    public ApplicationRunners(List<ApplicationRunner> runners) {
        this.runners = runners;
    }

}
