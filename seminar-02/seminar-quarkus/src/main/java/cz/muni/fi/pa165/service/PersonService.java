package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.data.domain.Person;
import cz.muni.fi.pa165.data.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class PersonService {

    PersonRepository personRepository;

    /**
     * <p>propagation = TxType.REQUIRES_NEW -> forces Quarkus to create a new transaction instead of sharing the "parent" one. The "parent" transaction is suspended until this new one is finished.</p>
     *
     * @param id ID of a person to find
     * @return Person record
     */
    @Transactional(value = Transactional.TxType.REQUIRED)
    public Person findById(Long id) {
        return personRepository.findById(id);
    }

}
