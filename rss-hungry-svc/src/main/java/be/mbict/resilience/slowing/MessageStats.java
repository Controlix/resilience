package be.mbict.resilience.slowing;

import lombok.Value;

import java.util.List;

@Value
public class MessageStats {

    String id;
    List<Integer> stats;
}
