package me.pavekovt.homework.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.pavekovt.homework.dao.PersonDAO;
import me.pavekovt.homework.domain.Person;
import me.pavekovt.homework.service.PersonService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PersonServiceImpl implements PersonService {

  static final int ONE_YEAR = 1;

  final PersonDAO dao;
  final ObjectMapper mapper;

  public PersonServiceImpl(PersonDAO dao, ObjectMapper mapper) {
    this.dao = dao;
    this.mapper = mapper;
  }

  @Override
  @Transactional(value = Transactional.TxType.REQUIRED)
  public void importFile(InputStream file) {
    try {
      List<Person> people = mapper.readValue(file, new TypeReference<>() {});
      dao.erase();
      dao.insertAll(people);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to parse file as proper person data array.");
    }
  }

  @Override
  public List<Person> getWithAgeBetween(int youngest, int oldest) {
    Date eldestBirthDate = Date.valueOf(LocalDate.now().minus(oldest + ONE_YEAR, ChronoUnit.YEARS));
    Date youngestBirthDate = Date.valueOf(LocalDate.now().minus(youngest, ChronoUnit.YEARS));
    return dao.getAllInAgeRange(eldestBirthDate, youngestBirthDate);
  }

  @Override
  public Stream<Person> exportAll() {
    return dao.getAll();
  }

  @Override
  public Person getById(Integer id) {
    return dao.getById(id);
  }

  @Override
  public Person add(Person person) {
    return dao.insert(person);
  }

  @Override
  public Person update(Person person) {
    return dao.update(person);
  }

  @Override
  public void delete(Integer id) {
    dao.delete(id);
  }
}
