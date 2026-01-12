package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.api.PersonDetailViewDto;
import cz.muni.fi.pa165.data.domain.Person;
import cz.muni.fi.pa165.service.PersonService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

public class PersonFacade {

    private final PersonService personService;

    public PersonFacade(PersonService personService) {
        this.personService = personService;
    }

    public PersonDetailViewDto findById(Long id) {
        Person person = personService.findById(id);
        // in-practice use MapStruct or other mapping libraries
        PersonDetailViewDto personDetailViewDto = new PersonDetailViewDto();
        personDetailViewDto.setId(person.getId());
        personDetailViewDto.setEmail(person.getEmail());
        personDetailViewDto.setFamilyName(person.getFamilyName());
        personDetailViewDto.setGivenName(person.getGivenName());
        return personDetailViewDto;
    }

}
