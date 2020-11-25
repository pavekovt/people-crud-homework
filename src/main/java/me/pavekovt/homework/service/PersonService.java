package me.pavekovt.homework.service;

import me.pavekovt.homework.domain.Person;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

public interface PersonService {

  void importFile(InputStream file);
  Stream<Person> exportAll();
  Person getById(Integer id);
  Person add(Person person);
  Person update(Person person);
  void delete(Integer id);

  List<Person> getWithAgeBetween(int youngest, int oldest);
}
