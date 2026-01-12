package cz.muni.fi.pa165;

import cz.muni.fi.pa165.api.PersonDetailViewDto;
import cz.muni.fi.pa165.facade.PersonFacade;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class StartupObserver {

    @Inject
    PersonFacade personFacade;

    public void onStart(@Observes StartupEvent event) {
        System.out.println("Application is starting up!");

        // you can also get beans programmatically if needed
        // PersonFacade personFacade = Arc.container().select(PersonFacade.class).get();

        PersonDetailViewDto personDetailViewDto = personFacade.findById(5L); // just an example call to ensure the bean is working
        System.out.println(personDetailViewDto);
    }
}
