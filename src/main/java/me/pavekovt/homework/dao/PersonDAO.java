package me.pavekovt.homework.dao;

import me.pavekovt.homework.domain.Person;
import org.jooq.Condition;

import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

public interface PersonDAO {

  Stream<Person> getAll();
  Person getById(Integer id);
  Person insert(Person person);
  Person update(Person person);
  void insertAll(List<Person> people);
  void delete(Integer id);
  void erase();

  List<Person> getAllInAgeRange(Date fromBirthDay, Date toBirthday);
}
