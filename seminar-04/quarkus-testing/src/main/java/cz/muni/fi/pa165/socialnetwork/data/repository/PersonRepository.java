package cz.muni.fi.pa165.socialnetwork.data.repository;

import cz.muni.fi.pa165.socialnetwork.data.model.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

    public Optional<Person> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public int setEmail(Long id, String email) {
        return update("email = ?1 where id = ?2", email, id);
    }
}
