package cz.muni.fi.pa165.socialnetwork.rest;

import cz.muni.fi.pa165.socialnetwork.api.PersonBasicViewDto;
import cz.muni.fi.pa165.socialnetwork.api.PersonDetailedViewDto;
import cz.muni.fi.pa165.socialnetwork.facade.PersonFacade;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/persons")
public class PersonResource {

    private final PersonFacade personFacade;

    @Inject
    public PersonResource(PersonFacade personFacade) {
        this.personFacade = personFacade;
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(personFacade.findById(id)).build();
    }

    @PATCH
    @Path("/emails/{id}")
    public Response updateEmail(@PathParam("id") Long id, @Valid PersonDetailedViewDto userDetails) {
        personFacade.updateEmail(id, userDetails.getEmail());
        return Response.noContent().build();
    }

    @GET
    public List<PersonBasicViewDto> findAll(@QueryParam("page") @DefaultValue("1") int pageNumber, @QueryParam("size") @DefaultValue("10") int pageSize) {
        return personFacade.findAll(Page.of(pageNumber, pageSize));
    }
}
