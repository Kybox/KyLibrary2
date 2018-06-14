package fr.kybox.dao;

import fr.kybox.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Iterable<Author> findAllByNameIgnoreCaseContaining(String name);
}
