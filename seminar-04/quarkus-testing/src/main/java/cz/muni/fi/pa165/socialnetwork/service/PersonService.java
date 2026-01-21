package cz.muni.fi.pa165.socialnetwork.service;

import cz.muni.fi.pa165.socialnetwork.data.model.Person;
import cz.muni.fi.pa165.socialnetwork.data.repository.PersonRepository;
import cz.muni.fi.pa165.socialnetwork.exceptions.EmailValidationFailedException;
import cz.muni.fi.pa165.socialnetwork.exceptions.ResourceNotFoundException;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class PersonService {

    private final PersonRepository personRepository;

    @Inject
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person findById(Long id) {
        return personRepository.findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));
    }

    @Transactional
    public void updateEmail(Long id, String email) {
        Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            throw new EmailValidationFailedException("Email validation failed on: " + email + " email.");
        }

        int rowsUpdated = personRepository.setEmail(id, email);
        if (rowsUpdated != 1) {
            throw new ResourceNotFoundException("Person with id: " + id +
                " was not found, email: " + email + " not changed.");
        }
    }

    @Transactional
    public List<Person> findAll(Page page) {
        return personRepository.findAll().page(page).list();
    }
}
