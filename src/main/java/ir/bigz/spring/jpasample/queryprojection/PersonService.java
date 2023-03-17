package ir.bigz.spring.jpasample.queryprojection;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    Optional<Person> getPersonByName(String firstName){
        return personRepository.findFirstByFirstName(firstName);
    }

    Optional<PersonView> getPersonView(String firstName){
        return personRepository.findViewByFirstName(firstName);
    }

    Optional<PersonDto> getPersonDto(String firstName){
        return personRepository.findDtoByFirstName(firstName);
    }

    PersonDto getByDynamicProjection(String lastName){
        return personRepository.findByLastName(lastName, PersonDto.class);
    }
}
