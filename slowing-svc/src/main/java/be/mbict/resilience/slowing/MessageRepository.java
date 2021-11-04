package be.mbict.resilience.slowing;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    private static final List<Message> MESSAGES = List.of(
            new Message("Word", "The quick brown fox jumps over the lazy dog"),
            new Message("Kirk", "To go where no man has gone before"),
            new Message("Gump", "Life is like a box of chocolates")
    );

    public List<Message> findAll() {
        return MESSAGES;
    }
}
