package ir.bigz.spring.jpasample.resultsetmapping;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final EntityManager entityManager;

    public AuthorService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a native query and get the result as arrays of objects and manually map values into the object
     */
    public List<AuthorDto> getResultSetFromEntityManager() {

        List<Object[]> results = this.entityManager.createNativeQuery
                ("SELECT a.id, a.first_name, a.last_name, a.author_number FROM Author a").getResultList();

        return results.stream().map((record) -> {
            Long id = ((Long) record[0]);
            return AuthorDto.builder()
                    .firstName((String) record[1])
                    .lastName((String) record[2])
                    .authorNumber((String) record[3])
                    .build();
        }).toList();
    }

    /**
     * Create a native query and get the result and use Jpa to map data into object,
     * Also in this way you have to return all field of table
     */
    public List<AuthorDto> getResultWithDefaultMapping() {

        List<Author> results = this.entityManager.createNativeQuery
                ("SELECT a.id, a.first_name, a.last_name, a.author_number FROM Author a", Author.class).getResultList();

        return results.stream().map(record ->
                AuthorDto.builder()
                        .firstName(record.getFirstName())
                        .lastName(record.getLastName())
                        .authorNumber(record.getAuthorNumber())
                        .build()
        ).collect(Collectors.toList());
    }

    /**
     * Create a native query and use SqlResultSetMapping feature to project selective fields of table into object
     */
    public List<AuthorDto> getResultWithCustomMapping() {

        List<Author> results = this.entityManager.createNativeQuery
                ("SELECT a.id as author_id, a.first_name, a.last_name FROM Author a", "AuthorMapping").getResultList();

        return results.stream().map(record ->
                AuthorDto.builder()
                        .authorId(record.getId())
                        .firstName(record.getFirstName())
                        .lastName(record.getLastName())
                        .build()
        ).collect(Collectors.toList());
    }

    /**
     * this method show how we can extract complex entity with have relation between them by using EntityResult Mapping
     */
    public List<AuthorDto> getComplexResultWithCustomMapping() {

        List<Object[]> results = this.entityManager.createNativeQuery
                ("SELECT b.id as book_id, b.title, b.author_id, a.id, a.first_name, a.last_name FROM Book b JOIN Author a ON b.author_id = a.id",
                        "AuthorWithBookMapping").getResultList();

        List<AuthorDto> collect = results.stream().map((record) -> {
            Author author = (Author) record[0];
            Book book = (Book) record[1];
            return AuthorDto.builder()
                    .authorId(author.getId())
                    .firstName(author.getFirstName())
                    .lastName(author.getLastName())
                    .bookDto(
                            BookDto.builder()
                                    .bookId(book.getId())
                                    .title(book.getTitle())
                                    .build()
                    ).build();

        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * this method show how we can add new column to result like count and mapped into SqlResultSetMapping
     * This kind of mapping comes quite handy, if your query becomes complex and the result has no exact mapping to your entity model.
     * Reasons for this can be additional attributes calculated by the database or queries that select only some specific columns
     * from related tables.
     */
    public List<AuthorDto> getComplexResultWithAdditionalColumn() {

        List<Object[]> results = this.entityManager.createNativeQuery
                ("SELECT a.id, a.first_name, a.last_name, count(b.id) as bookCount FROM Book b JOIN Author a ON b.author_id = a.id GROUP BY a.id, a.first_name, a.last_name",
                        "AuthorWithBookCountMapping").getResultList();

        List<AuthorDto> collect = results.stream().map((record) -> {
            Author author = (Author) record[0];
            long bookCount = (Long) record[1];
            System.out.println("Author: Id: " + author.getId() + ", First Name: " + author.getFirstName() +
                    ", Last Name: " + author.getLastName() + ", Number of Books: " + bookCount);
            return AuthorDto.builder()
                    .authorId(author.getId())
                    .firstName(author.getFirstName())
                    .lastName(author.getLastName())
                    .build();

        }).collect(Collectors.toList());

        return collect;
    }

    /**
     * Constructor Result Mapping
     * JPQL supports constructor expressions that can be specified in the select part of the JPQL query
     * and define the constructor call for each selected record.
     */
    public List<BookView> getResultAndMapToValueObjectByConstructor() {

        List<BookView> results = this.entityManager.createNativeQuery
                ("SELECT b.id, b.title, a.first_name || ' ' || a.last_name as authorName FROM Book b JOIN Author a ON b.author_id = a.id",
                        "BookViewMapping").getResultList();

        return results;
    }
}
