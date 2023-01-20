package ru.gusarov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gusarov.library.modules.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
