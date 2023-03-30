package ir.bigz.spring.jpasample.resultsetmapping;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
