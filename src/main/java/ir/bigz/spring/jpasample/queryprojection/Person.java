package ir.bigz.spring.jpasample.queryprojection;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Getter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long personId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String email;
}
