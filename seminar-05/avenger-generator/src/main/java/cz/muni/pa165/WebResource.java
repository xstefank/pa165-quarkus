package cz.muni.pa165;

import cz.muni.pa165.model.Avenger;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class WebResource {

    private final AvengerService avengerService;

    @Inject
    public WebResource(AvengerService avengerService) {
        this.avengerService = avengerService;
    }

    @GET
    @Path("/avenger/generate")
    public Avenger generateAvenger() {
        Avenger avenger = avengerService.generateRandomAvenger();
        System.out.println("Generated new Avenger - " + avenger);
        return avenger;
    }

    @GET
    @Path("/avenger/wrong")
    public String wrong() {
        return "!!! WRONG URL configured in your client service !!!";
    }
}
