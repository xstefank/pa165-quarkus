package cz.muni.chat.server.ui;

import cz.muni.chat.server.service.ChatService;
import cz.muni.chat.server.service.StoredMessage;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import java.io.IOException;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Path("/chat")
public class ChatUIResource {

    private final ChatService chatService;

    @Inject
    public ChatUIResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response chat() throws IOException {
        // generate messages in reverse order
        StreamingOutput stream = output -> {
            PrintStream out = new PrintStream(output);
            out.println("<html><body><h1>Chat Service</h1>");
            // prevent race condition on concurrent accesses
            List<StoredMessage> chatMessages = chatService.getAllMessages();
            // iterate messages in reverse order
            for (StoredMessage cm : chatMessages) {
                out.println("<div class=\"message\" style=\"" +
                    "margin: 10px ; padding: 10px " +
                    "; color: " + escapeHtml4(cm.textColor()) +
                    "; background-color: " + escapeHtml4(cm.backgroundColor())
                    + "\" >");
                out.println("from: <b>" + escapeHtml4(cm.author()) + "</b>");

                out.println("time: <b>" + escapeHtml4(DateTimeFormatter.RFC_1123_DATE_TIME.format(cm.timestamp())) + "</b><br/><br/>");
                out.println(escapeHtml4(cm.text()) + "<br/>");
                out.println("</div>");
            }
        };

        return Response.ok(stream).header("Refresh", "2").build();
    }

}
