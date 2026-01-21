package cz.muni.fi.pa165.socialnetwork.util;

import cz.muni.fi.pa165.socialnetwork.api.PersonContactDto;
import cz.muni.fi.pa165.socialnetwork.api.PersonDetailedViewDto;
import cz.muni.fi.pa165.socialnetwork.data.enums.ContactType;
import cz.muni.fi.pa165.socialnetwork.data.model.Contact;
import cz.muni.fi.pa165.socialnetwork.data.model.Person;

import java.util.List;
import java.util.Set;

public class TestDataFactory {

    public static PersonDetailedViewDto personDetailedViewDto = getPersonDetailedViewDtoFactory();
    public static Person personEntity = getPersonEntityFactory();

    private static PersonDetailedViewDto getPersonDetailedViewDtoFactory() {
        PersonDetailedViewDto personDetailedViewDto = new PersonDetailedViewDto();
        personDetailedViewDto.setEmail("john.doe@example.com");
        personDetailedViewDto.setGivenName("John");
        personDetailedViewDto.setFamilyName("Doe");
        personDetailedViewDto.setId(1L);

        PersonContactDto contact1 = new PersonContactDto();
        contact1.setContact("john.doe@gmail.com");
        contact1.setContactType(ContactType.EMAIL);

        PersonContactDto contact2 = new PersonContactDto();
        contact2.setContact("doe@fi.muni.cz");
        contact2.setContactType(ContactType.EMAIL);

        PersonContactDto contact3 = new PersonContactDto();
        contact3.setContact("+420 777 777 777");
        contact3.setContactType(ContactType.MOBILE);

        personDetailedViewDto.setContacts(List.of(contact1, contact2, contact3));
        return personDetailedViewDto;
    }

    private static Person getPersonEntityFactory() {
        Person person = new Person();
        person.setEmail("john.doe@example.com");
        person.setGivenName("John");
        person.setFamilyName("Doe");
        person.setId(1L);

        Contact contact1 = new Contact();
        contact1.setContact("john.doe@gmail.com");
        contact1.setContactType(ContactType.EMAIL);

        Contact contact2 = new Contact();
        contact2.setContact("doe@fi.muni.cz");
        contact2.setContactType(ContactType.EMAIL);

        Contact contact3 = new Contact();
        contact3.setContact("+420 777 777 777");
        contact3.setContactType(ContactType.MOBILE);

        person.setContacts(Set.of(contact1, contact2, contact3));
        return person;
    }
}

