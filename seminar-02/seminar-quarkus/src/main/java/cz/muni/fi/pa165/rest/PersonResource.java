package cz.muni.fi.pa165.rest;

import cz.muni.fi.pa165.facade.PersonFacade;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

// expose path on /persons
public class PersonResource {

    private final PersonFacade personFacade;

    public PersonResource(PersonFacade personFacade) {
        this.personFacade = personFacade;
    }

    // expose path on /persons/{id} (note that you can combine @Path on class and method level)
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(personFacade.findById(id)).build();
    }
}
