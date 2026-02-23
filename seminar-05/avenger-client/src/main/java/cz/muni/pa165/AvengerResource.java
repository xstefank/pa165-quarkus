package cz.muni.pa165;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/")
public class AvengerResource {

    @ConfigProperty(name = "target.server.url")
    String targetServerURL;

    // Purposely parse as string even if we know that it's JSONs because we want to
    // handle errors from avenger-generator in the same way
    @GET
    @Path("/avenger/{number}")
    public List<String> getAvengers(@PathParam("number") int number) {
        return IntStream.range(0, number).mapToObj(_ -> {
            try (Client client = ClientBuilder.newClient()) {
                return client.target(targetServerURL)
                    .request().get().readEntity(String.class);
            }
        }).collect(Collectors.toList());
    }

    @GET
    @Path("/targetServerURL")
    public String getTargetServerURL() {
        return targetServerURL;
    }
}
