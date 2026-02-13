package cz.muni.fi.pa165;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import java.util.UUID;

@Path("/")
public class WebResource {

    private final String id = "Web Server Node " + UUID.randomUUID();

    @GET
    @Path("/whereami")
    public String whereami() {
        try (Client client = ClientBuilder.newClient()) {
            return client.target("http://ip-api.com/json")
                .request().get().readEntity(String.class);
        }
    }

    @GET
    @Path("/whoami")
    public String whoami() {
        return "Id: " + id;
    }
}
