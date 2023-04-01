package ir.bigz.spring.jpasample.resultsetmapping;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = {"classpath:application.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
public class ResultSetMappingTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private AuthorService authorService;

    List<Author> authors = new ArrayList<>();
    List<Book> books = new ArrayList<>();

    Faker faker = new Faker();

    @BeforeAll
    void setUp(){
        authorService = new AuthorService(entityManager, authorRepository);
        authors = generateAuthorData().stream()
                .map(author -> Author.builder()
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .authorNumber(author.getAuthorNumber())
                .book(Book.builder()
                        .title(faker.regexify("[a-zA-Z]{10}"))
                        .author(author).build())
                        .build())
                .collect(Collectors.toList());
        authorRepository.saveAll(authors);
    }

    @Test
    @Rollback(value = false)
    public void get_author_by_raw_result_set(){

        List<AuthorDto> result = authorService.getResultSetFromEntityManager();

        //then
        Assertions.assertTrue(result.size() > 0);
    }

    @Test
    @Rollback(value = false)
    public void get_author_by_default_mapping(){

        List<AuthorDto> result = authorService.getResultWithDefaultMapping();

        //then
        Assertions.assertTrue(result.size() > 0);
    }

    @Test
    @Rollback(value = false)
    public void get_author_by_custom_mapping(){

        List<AuthorDto> result = authorService.getResultWithCustomMapping();

        //then
        Assertions.assertTrue(result.size() > 0);
    }

    @Test
    @Rollback(value = false)
    public void get_author_with_book_by_complex_mapping(){

        List<AuthorDto> result = authorService.getComplexResultWithCustomMapping();

        //then
        Assertions.assertTrue(result.size() > 0);
    }

    @Test
    @Rollback(value = false)
    public void get_book_view_by_using_constructor_as_a_map(){

        List<BookView> result = authorService.getResultAndMapToValueObjectByConstructor();

        //then
        Assertions.assertTrue(result.size() > 0);
        Assertions.assertTrue(!result.get(0).getAuthorName().equals(""));
    }

    @Test
    @Rollback(value = false)
    public void get_author_with_book_count(){

        List<AuthorDto> result = authorService.getComplexResultWithAdditionalColumn();

        //then
        Assertions.assertTrue(result.size() > 0);
    }

    List<Author> generateAuthorData(){
        String[] firstName = {"jack", "paul", "david", "pouya"};
        String[] lastName = {"jonson", "goto", "anderson", "pouryaei"};
        return IntStream.range(0, 3)
                .mapToObj(value ->
                        Author.builder()
                                .firstName(firstName[value])
                                .lastName(lastName[value])
                                .authorNumber(String.valueOf(faker.number().numberBetween(10, 90)))
                                .build())
                .toList();
    }
}
