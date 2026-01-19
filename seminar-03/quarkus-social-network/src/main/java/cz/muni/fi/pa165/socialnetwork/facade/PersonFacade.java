package cz.muni.fi.pa165.socialnetwork.facade;

import cz.muni.fi.pa165.socialnetwork.annotations.MethodExecutionTimeTracker;
import cz.muni.fi.pa165.socialnetwork.api.PersonBasicViewDto;
import cz.muni.fi.pa165.socialnetwork.api.PersonDetailedViewDto;
import cz.muni.fi.pa165.socialnetwork.mappers.PersonMapper;
import cz.muni.fi.pa165.socialnetwork.service.PersonService;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@ApplicationScoped
@Transactional
public class PersonFacade {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @Inject
    public PersonFacade(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @CacheResult(cacheName = "person-cache")
    @Transactional
    public PersonDetailedViewDto findById(@CacheKey Long id) {
        return personMapper.mapToDetailViewDto(personService.findById(id));
    }

    @MethodExecutionTimeTracker
    @Transactional
    public List<PersonBasicViewDto> findAll(Page page) {
        return personMapper.mapToList(personService.findAll(page));
    }

}