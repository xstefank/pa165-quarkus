package cz.muni.pa165.generated.server;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;

public class MyApiResource implements ApiResource {
    @Override
    public Response hello() {
        Log.info("hello() called");
        return Response.ok("{\"message\": \"Hello World!\"}").build();
    }
}
