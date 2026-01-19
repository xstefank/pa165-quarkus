package cz.muni.fi.pa165.socialnetwork.init;

import cz.muni.fi.pa165.socialnetwork.data.enums.ContactType;
import cz.muni.fi.pa165.socialnetwork.data.model.Contact;
import cz.muni.fi.pa165.socialnetwork.data.model.Person;
import cz.muni.fi.pa165.socialnetwork.data.repository.PersonRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class InitialDataService {

    private final PersonRepository personRepository;

    @Inject
    public InitialDataService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void insertDummyData(@Observes StartupEvent event) {
        insertPersonWithContact();
        insertDummyPersons();
    }

    private void insertPersonWithContact() {
        Person person1 = new Person();
        person1.setEmail("pavel.seda@sap.com");
        person1.setFamilyName("Šeda");
        person1.setGivenName("Pavel");

        Contact contact1 = new Contact();
        contact1.setContact("pavel.seda@sap.com");
        contact1.setContactType(ContactType.EMAIL);
        contact1.setPerson(person1);

        Contact contact2 = new Contact();
        contact2.setContact("seda@fi.muni.cz");
        contact2.setContactType(ContactType.EMAIL);
        contact2.setPerson(person1);

        Contact contact3 = new Contact();
        contact3.setContact("+420 777 777 777");
        contact3.setContactType(ContactType.MOBILE);
        contact3.setPerson(person1);

        person1.setContacts(Set.of(contact1, contact2, contact3));
        personRepository.persist(person1);
    }

    private void insertDummyPersons() {
        List<Person> persons = new ArrayList<>(40);
        for (int i = 0; i < 40; i++) {
            Person person = new Person();
            person.setEmail(generateRandomEmail());
            person.setFamilyName("Doe" + i);
            person.setGivenName("John" + i);
            persons.add(person);
        }
        personRepository.persist(persons);
    }

    private String generateRandomEmail() {
        return String.format("%s@%s", getUniqueId(), "mail.muni.cz");
    }

    private String getUniqueId() {
        return String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000);
    }

}
