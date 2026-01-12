package cz.muni.fi.pa165.rest;

import cz.muni.fi.pa165.facade.PersonFacade;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

// expose path on /persons
public class PersonRestController {

    private final PersonFacade personFacade;

    public PersonRestController(PersonFacade personFacade) {
        this.personFacade = personFacade;
    }

    // expose path on /persons/{id} (note that you can combine @Path on class and method level)
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(personFacade.findById(id)).build();
    }
}
