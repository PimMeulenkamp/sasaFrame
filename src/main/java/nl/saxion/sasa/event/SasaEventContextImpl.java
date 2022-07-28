package nl.saxion.sasa.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
class SasaEventContextImpl implements SasaEventContext{
    String prefix;
    List<String> args;
    String suffix;
}
