package cz.muni.fi.pa165.socialnetwork.data.repository;

import cz.muni.fi.pa165.socialnetwork.data.model.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

}
