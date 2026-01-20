package cz.muni.fi.pa165.socialnetwork.mappers;

import cz.muni.fi.pa165.socialnetwork.api.PersonBasicViewDto;
import cz.muni.fi.pa165.socialnetwork.api.PersonDetailedViewDto;
import cz.muni.fi.pa165.socialnetwork.data.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface PersonMapper {

    PersonBasicViewDto mapToDto(Person person);

    PersonDetailedViewDto mapToDetailViewDto(Person person);

    List<PersonBasicViewDto> mapToList(List<Person> persons);
}
