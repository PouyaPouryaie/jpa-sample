package ir.bigz.spring.jpasample.queryprojection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findFirstByFirstName(String firstName);
    Optional<PersonView> findViewByFirstName(String firstName);
    Optional<PersonDto> findDtoByFirstName(String firstName);
    @Query("SELECT new ir.bigz.spring.jpasample.queryprojection.PersonDto(p.firstName, p.lastName, p.age) FROM Person p" +
            " WHERE p.firstName = :firstName")
    PersonDto findDtoByFirstNameWithQueryDsl(String firstName);
    <T> T findByLastName(String lastName, Class<T> type);
}
