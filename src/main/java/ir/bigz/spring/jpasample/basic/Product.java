package ir.bigz.spring.jpasample.basic;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long productId;

    private String productName;
}
