package ru.gusarov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gusarov.library.modules.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByTitleContains(String str);
    List<Book> searchBookByTitleContaining(String str);
}
