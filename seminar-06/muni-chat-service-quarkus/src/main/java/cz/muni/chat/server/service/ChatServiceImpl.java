package cz.muni.chat.server.service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class ChatServiceImpl implements ChatService {

    private static final Logger LOG = Logger.getLogger(ChatServiceImpl.class);
    private static final Random random = new Random();

    // in-memory storage of messages
    private final List<StoredMessage> messages = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        this.createNewMessage("first message", "\uD83D\uDEC8 system", "black", "lightblue");
        LOG.info("the first message added");
    }

    @Override
    public List<StoredMessage> getAllMessages() {
        return messages;
    }

    @Override
    public StoredMessage createNewMessage(String text, String author, String textColor, String backgroundColor) {
        UUID uuid = UUID.randomUUID();
        StoredMessage c = new StoredMessage(uuid.toString(), ZonedDateTime.now(), text, author,
            textColor, backgroundColor, random.nextLong());
        messages.addFirst(c);
        return c;
    }

    @Override
    public StoredMessage getMessage(String id) {
        return messages.stream().filter(x -> x.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<StoredMessage> getPageOfMessages(int pageOffset, int pageSize) {
        List<StoredMessage> msgs = messages.stream()
            .skip(pageOffset)
            .limit(pageSize)
            .toList();
        LOG.debug("page: " + msgs);
        return msgs;
    }
}
