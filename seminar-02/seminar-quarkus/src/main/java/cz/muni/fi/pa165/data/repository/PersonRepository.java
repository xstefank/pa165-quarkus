package cz.muni.fi.pa165.data.repository;

import cz.muni.fi.pa165.data.domain.Person;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class PersonRepository implements PanacheRepository<Person> {

    @Inject
    EntityManager entityManager;

    /**
     * Returns a person by ID.
     *
     * @param id ID of a person to find
     * @return Person record
     */
    public Person findById(Long id) {
        return Optional.ofNullable(entityManager.find(Person.class, id))
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));
    }

    /**
     * <Note> For such a method batching should be implemented with suitable batch size.</Note>
     *
     * @param persons receive a list of persons
     */
    @Transactional
    public void saveAll(List<Person> persons) {
        if (persons != null && !persons.isEmpty()) {
            persons.forEach(person ->
                    entityManager.persist(person)
            );
        }
    }
}
