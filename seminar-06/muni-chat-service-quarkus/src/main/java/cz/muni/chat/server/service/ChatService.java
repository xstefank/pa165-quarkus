package cz.muni.chat.server.service;

import java.util.List;

public interface ChatService {

    List<StoredMessage> getAllMessages();

    StoredMessage createNewMessage(String text, String author, String textColor, String backgroundColor);

    StoredMessage getMessage(String id);

    List<StoredMessage> getPageOfMessages(int pageOffset, int pageSize);
}
