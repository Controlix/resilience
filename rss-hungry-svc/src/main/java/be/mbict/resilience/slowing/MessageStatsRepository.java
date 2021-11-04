package be.mbict.resilience.slowing;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class MessageStatsRepository {

    private static final List<MessageStats> MESSAGE_STATS = List.of(
            new MessageStats("Word", List.of(5, 6, 5, 7, 3)),
            new MessageStats("Kirk", List.of(5, 8, 9, 7, 9, 5, 3, 3, 9)),
            new MessageStats("Gump", List.of(3, 2, 8))
    );

    public List<MessageStats> findAll() {
        return MESSAGE_STATS;
    }

    public List<Integer> findById(String id) {
        return MESSAGE_STATS.stream().filter(stat -> stat.getId().equals(id)).map(MessageStats::getStats).findFirst().orElse(Collections.emptyList());
    }
}
