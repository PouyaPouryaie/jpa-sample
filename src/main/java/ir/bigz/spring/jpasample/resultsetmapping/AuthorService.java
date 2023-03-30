package ir.bigz.spring.jpasample.resultsetmapping;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final EntityManager entityManager;

    private final AuthorRepository authorRepository;

    public AuthorService(EntityManager entityManager, AuthorRepository authorRepository) {
        this.entityManager = entityManager;
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getResultSetFromEntityManager(){

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

    public List<AuthorDto> getResultWithDefaultMapping(){

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

    public List<AuthorDto> getResultWithCustomMapping(){

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

    public List<AuthorDto> getComplexResultWithCustomMapping(){

        List<Object[]> results = this.entityManager.createNativeQuery
                ("SELECT b.id as book_id, b.title, b.author_id, a.id, a.first_name, a.last_name FROM Book b JOIN Author a ON b.author_id = a.id", "BookAuthorMapping").getResultList();

        List<AuthorDto> collect = results.stream().map((record) -> {
            Book book = (Book) record[0];
            Author author = (Author) record[1];
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
}
