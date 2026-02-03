package cz.muni.fi.pa165.socialnetwork.data.repository;

import cz.muni.fi.pa165.socialnetwork.data.enums.ContactType;
import cz.muni.fi.pa165.socialnetwork.data.model.Contact;
import cz.muni.fi.pa165.socialnetwork.data.model.Person;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Set;

@QuarkusTest
class PersonRepositoryTest {

    @Inject
    PersonRepository personRepository;

    // Cannot be for now in @BeforeEach see https://github.com/quarkusio/quarkus/discussions/40119
    // Call this method at the beginning of each test needing data
    void initData() {
        Person person1 = new Person();
        person1.setEmail("john.doe@microsoft.com");
        person1.setFamilyName("Doe");
        person1.setGivenName("John");

        Contact contact1 = new Contact();
        contact1.setContact("john.doe@microsoft.com");
        contact1.setContactType(ContactType.EMAIL);
        contact1.setPerson(person1);

        Contact contact2 = new Contact();
        contact2.setContact("xdoe@fi.muni.cz");
        contact2.setContactType(ContactType.EMAIL);
        contact2.setPerson(person1);

        Contact contact3 = new Contact();
        contact3.setContact("+421 424 242 420");
        contact3.setContactType(ContactType.MOBILE);
        contact3.setPerson(person1);

        person1.setContacts(Set.of(contact1, contact2, contact3));
        personRepository.persist(person1);
    }

}
