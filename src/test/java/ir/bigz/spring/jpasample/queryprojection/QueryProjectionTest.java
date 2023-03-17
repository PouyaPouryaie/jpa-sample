package ir.bigz.spring.jpasample.queryprojection;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import java.util.stream.IntStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = {"classpath:application.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
public class QueryProjectionTest {

    @Autowired
    private PersonRepository personRepository;

    private PersonService personService;

    List<Person> people = new ArrayList<>();

    @BeforeAll
    void setUp(){
        personService = new PersonService(personRepository);
        people = generateData();
        personRepository.saveAll(people);
    }

    Faker faker = new Faker();

    @Test
    @Rollback(value = false)
    public void get_person_by_jpa_default(){

        Optional<Person> person = personService.getPersonByName(people.get(0).getFirstName());

        //then
        Assertions.assertTrue(person.isPresent());
    }

    @Test
    @Rollback(value = false)
    public void get_person_view_interface_projection(){

        Optional<PersonView> personView = personService.getPersonView(people.get(0).getFirstName());

        //then
        Assertions.assertTrue(Objects.nonNull(personView.get().getFirstName()));
    }

    @Test
    @Rollback(value = false)
    public void get_person_dto_class_projection(){

        Optional<PersonDto> personDto = personService.getPersonDto(people.get(0).getFirstName());

        //then
        Assertions.assertTrue(personDto.get().getAge() != 0);
    }

    @Test
    @Rollback(value = false)
    public void get_person_by_dynamic_projection(){

        PersonDto personDto = personService.getByDynamicProjection(people.get(0).getLastName());

        //then
        Assertions.assertTrue(personDto.getAge() != 0);
    }

    @Test
    @Rollback(value = false)
    public void get_person_dto_class_with_query_projection(){

        PersonDto personDto = personRepository.findDtoByFirstNameWithQueryDsl(people.get(0).getFirstName());

        //then
        Assertions.assertTrue(personDto.getAge() != 0);
    }

    List<Person> generateData(){
        String[] firstName = {"jack", "paul", "david", "pouya"};
        String[] lastName = {"jonson", "goto", "anderson", "pouryaei"};
        return IntStream.range(0, 3)
                .mapToObj(value ->
                        Person.builder()
                                .firstName(firstName[value])
                                .lastName(lastName[value])
                                .age(faker.number().numberBetween(10, 90))
                                .email(faker.bothify("????##@gmail.com"))
                                .gender("male")
                                .build()).toList();
    }

}
