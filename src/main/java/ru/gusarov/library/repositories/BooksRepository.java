package ru.gusarov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gusarov.library.modules.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
