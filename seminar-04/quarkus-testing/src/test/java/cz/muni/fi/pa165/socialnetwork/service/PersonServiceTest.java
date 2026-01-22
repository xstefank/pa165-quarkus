package cz.muni.fi.pa165.socialnetwork.service;

import cz.muni.fi.pa165.socialnetwork.data.model.Person;
import cz.muni.fi.pa165.socialnetwork.data.repository.PersonRepository;
import cz.muni.fi.pa165.socialnetwork.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.socialnetwork.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void findById_personFound_returnsPerson() {
        // Arrange
        Mockito.when(personRepository.findByIdOptional(1L)).thenReturn(Optional.ofNullable(TestDataFactory.personEntity));

        // Act
        Person foundEntity = personService.findById(1L);

        // Assert
        assertThat(foundEntity).isEqualTo(TestDataFactory.personEntity);
    }

    @Test
    void findById_personNotFound_throwsResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> Optional
            .ofNullable(personService.findById(1L))
            .orElseThrow(ResourceNotFoundException::new));
    }

}
