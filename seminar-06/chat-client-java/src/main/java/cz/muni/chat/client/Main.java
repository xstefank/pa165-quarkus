package cz.muni.chat.client;

import cz.muni.chat.client.api.ChatResourceApi;
import cz.muni.chat.client.model.NewChatMessageRequest.BackgroundColorEnum;
import cz.muni.chat.client.model.ChatMessage;
import cz.muni.chat.client.model.NewChatMessageRequest;
import io.quarkus.logging.Log;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

@QuarkusMain
public class Main implements QuarkusApplication {

    @Inject
    @RestClient
    ChatResourceApi chat;

    @Override
    public int run(String... args) throws Exception {
        Log.info("Starting");

        // list all messages
        for (ChatMessage chatMessage : chat.apiMessagesGet()) {
            Log.info("message: " + chatMessage);
        }

        // create a new message
        BackgroundColorEnum[] colors = BackgroundColorEnum.values();
        BackgroundColorEnum bg = colors[new Random().nextInt(colors.length)];
        Response response = chat.apiMessagesPost(
            new NewChatMessageRequest()
                .text("Hello!")
                .textColor(NewChatMessageRequest.TextColorEnum.BLACK)
                .backgroundColor(bg),
            "me");

        ChatMessage message = response.readEntity(ChatMessage.class);
        Log.info("new message = " + message);
        ZonedDateTime timestamp = message.getTimestamp().atZoneSameInstant(ZoneId.systemDefault());
        Log.info("timestamp: " + timestamp);

        // create more messages
        for (int i = 3; i <= 8; i++) {
            chat.apiMessagesPost(new NewChatMessageRequest().text("Message num " + i), "robot");
        }

        //get paged messages
        int pageIndex = 2;
        int pageSize = 3;
        List<ChatMessage> chatMessages = chat.apiPagedGet(pageIndex, pageSize);
        Log.infof("paged messages: page=%d/%d", pageIndex, pageSize);
        for (ChatMessage chatMessage : chatMessages) {
            Log.info("msg: " + chatMessage);
        }

        // deliberately make a wrong call to show catching an exception
        try {
            chat.apiMessageIdGet("1");
        } catch (WebApplicationException ex) {
            Log.info("expected exception caught");
        }

        return 0;
    }
}
