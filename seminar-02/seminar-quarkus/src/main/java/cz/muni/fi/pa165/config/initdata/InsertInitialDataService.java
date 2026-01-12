package cz.muni.fi.pa165.config.initdata;

import cz.muni.fi.pa165.data.domain.Person;
import cz.muni.fi.pa165.data.repository.PersonRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class InsertInitialDataService {
    private final PersonRepository personRepository;

    @Inject
    public InsertInitialDataService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void insertDummyData(@Observes StartupEvent event) {
        insertDummyPersons();
    }

    private void insertDummyPersons() {
        var persons = new ArrayList<Person>(40);
        for (int i = 0; i < 40; i++) {
            Person person = new Person();
            person.setEmail(generateRandomEmail());
            person.setFamilyName("Doe" + i);
            person.setGivenName("John" + i);
            persons.add(person);
        }
        personRepository.saveAll(persons);
    }

    private String generateRandomEmail() {
        return String.format("%s@%s", getUniqueId(), "mail.muni.cz");
    }

    private String getUniqueId() {
        return String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000);
    }

}
