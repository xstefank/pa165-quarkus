package cz.muni.fi.pa165.socialnetwork.facade;

import cz.muni.fi.pa165.socialnetwork.api.PersonDetailedViewDto;
import cz.muni.fi.pa165.socialnetwork.mappers.PersonMapper;
import cz.muni.fi.pa165.socialnetwork.service.PersonService;
import cz.muni.fi.pa165.socialnetwork.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class PersonFacadeTest {

    private PersonService personService;

    private PersonMapper personMapper;

    private PersonFacade personFacade;

    @BeforeEach
    void setUp() {
        personService = Mockito.mock(PersonService.class);
        personMapper = Mockito.mock(PersonMapper.class);
        personFacade = new PersonFacade(personService, personMapper);
    }

    void findById_personFound_returnsPerson() {
        // Arrange
        Mockito.when(personService.findById(1L)).thenReturn(TestDataFactory.personEntity);
        Mockito.when(personMapper.mapToDetailViewDto(any())).thenReturn(TestDataFactory.personDetailedViewDto);

        // Act
        PersonDetailedViewDto foundEntity = personFacade.findById(1L);

        // Assert
        assertThat(foundEntity).isEqualTo(TestDataFactory.personDetailedViewDto);
    }
}

