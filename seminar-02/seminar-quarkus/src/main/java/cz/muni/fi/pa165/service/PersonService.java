package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.data.domain.Person;
import cz.muni.fi.pa165.data.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class PersonService {

    PersonRepository personRepository;

    /**
     * @param id ID of a person to find
     * @return Person record
     */
    public Person findById(Long id) {
        return personRepository.findById(id);
    }

}
