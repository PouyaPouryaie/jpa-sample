package ir.bigz.spring.jpasample.resultsetmapping;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "author")
@Getter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "AuthorMapping",
                entities = @EntityResult(
                        entityClass = Author.class,
                        fields = {
                                @FieldResult(name = "id", column = "author_id"),
                                @FieldResult(name = "firstName", column = "first_name"),
                                @FieldResult(name = "lastName", column = "last_name")
                        })),
        @SqlResultSetMapping(
                name = "BookAuthorMapping",
                entities = {
                        @EntityResult(
                                entityClass = Book.class,
                                fields = {
                                        @FieldResult(name = "id", column = "book_id"),
                                        @FieldResult(name = "title", column = "title"),
                                        @FieldResult(name = "author", column = "author_id")}),
                        @EntityResult(
                                entityClass = Author.class,
                                fields = {
                                        @FieldResult(name = "id", column = "id"),
                                        @FieldResult(name = "firstName", column = "first_name"),
                                        @FieldResult(name = "lastName", column = "last_name")})})
})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String firstName;
    private String lastName;
    private String authorNumber;
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL)
    private Book book;
}
