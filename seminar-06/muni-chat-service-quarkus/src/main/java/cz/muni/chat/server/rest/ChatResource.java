package cz.muni.chat.server.rest;

import cz.muni.chat.server.facade.BackgroundColor;
import cz.muni.chat.server.facade.ChatMessage;
import cz.muni.chat.server.facade.ErrorMessage;
import cz.muni.chat.server.facade.NewChatMessageRequest;
import cz.muni.chat.server.service.ChatService;
import cz.muni.chat.server.service.StoredMessage;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/api")
public class ChatResource {

    @Inject
    UriInfo uriInfo;

    private final static Logger LOG = Logger.getLogger(ChatResource.class);

    private final ChatService chatService;

    @Inject
    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * REST method returning all messages.
     */
    @Operation( // metadata for inclusion into OpenAPI document
        summary = "Get all messages",
        description = """
            Returns an array of objects representing chat messages, ordered from the newest to the oldest.
            Each message must have a **text** and **timestamp**, and optionally may have an **author**,
            a **text color** and a **background color**.
            It is possible to use [MarkDown](https://www.markdownguide.org/) in descriptions.
            """)
    @GET
    @Path("/messages") // URL mapping of this operation
    public List<ChatMessage> getAllMessages() {
        // get messages from the service and convert them into DTOs that get serialized into JSON
        return chatService.getAllMessages().stream().map(ChatMessage::fromStoredMessage).toList();
    }


    /**
     * REST method returning message with specified id.
     */
    @Operation(
        summary = "Returns identified message",
        description = "Looks up a message by its id.")
    @APIResponse(responseCode = "200", ref = "#/components/responses/SingleMessageResponse")
    @APIResponse(responseCode = "404", description = "message not found",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    @GET
    @Path("/message/{id}")
    public ChatMessage getMessage(@PathParam("id") String id) {
        StoredMessage m = chatService.getMessage(id);
        if (m != null) {
            return ChatMessage.fromStoredMessage(m);
        } else {
            throw new WebApplicationException("message with id=" + id + " not found", Response.Status.NOT_FOUND);
        }
    }


    /**
     * REST method for creating a new message.
     */
    @Operation(
        summary = "Create a new message",
        description = """
            Receives data both in request body and as URL parameter and stores them as a new message.
            Returns the new message as its response.
            """)
    @APIResponse(responseCode = "201", ref = "#/components/responses/SingleMessageResponse")
    @APIResponse(responseCode = "400", description = "input data were not correct",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class))
    )
    @POST
    @Path("/messages")
    public Response createMessage(@Valid @RequestBody NewChatMessageRequest r,
                                     @QueryParam("author") String author) {
        // default values for author and backgroundColor
        if (author == null) {
            author = uriInfo.getRequestUri().toASCIIString();
        }
        BackgroundColor bc = (r.getBackgroundColor() == null) ? BackgroundColor.LIGHTGRAY : r.getBackgroundColor();
        // create message
        StoredMessage message = chatService.createNewMessage(r.getText(), author, r.getTextColor(), bc.getValue());
        // return the created message
        return Response.status(Response.Status.CREATED).entity(ChatMessage.fromStoredMessage(message)).build();
    }

    /**
     * REST method returning messages in pages.
     */
    @Operation(
        summary = "Paged messages",
        description = """
            Returns a page of chat messages.
            The parameter `page` specifies zero-based index of the requested page,
            the parameter `size` specifies the size of the page.
            The parameter `sort` is ignored, sorting is always from the newest to the oldest.
            """)
    @GET
    @Path("/paged")
    public List<ChatMessage> paged(@QueryParam("pageOffset") int pageOffset, @QueryParam("pageSize") int pageSize) {
        return chatService.getPageOfMessages(pageOffset, pageSize).stream().map(ChatMessage::fromStoredMessage).toList();
    }


}
