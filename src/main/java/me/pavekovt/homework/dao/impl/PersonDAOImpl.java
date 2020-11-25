package me.pavekovt.homework.dao.impl;

import com.google.common.collect.Iterables;
import me.pavekovt.homework.dao.PersonDAO;
import me.pavekovt.homework.db.Tables;
import me.pavekovt.homework.domain.Person;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PersonDAOImpl implements PersonDAO {

  static final int BATCH_INSERT_SIZE = 100;

  final DSLContext dsl;

  public PersonDAOImpl(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public Stream<Person> getAll() {
    return dsl
      .select()
      .from(Tables.PERSON)
      .fetchStreamInto(Person.class);
  }

  @Override
  public Person getById(Integer id) {
    return dsl
      .select()
      .from(Tables.PERSON)
      .where(Tables.PERSON.ID.eq(id))
      .fetchOneInto(Person.class);
  }

  @Override
  public Person insert(Person person) {
    return dsl
      .insertInto(Tables.PERSON)
      .set(dsl.newRecord(Tables.PERSON, person))
      .returning()
      .fetchOne()
      .into(Person.class);
  }

  @Override
  public Person update(Person person) {
    return dsl
      .update(Tables.PERSON)
      .set(dsl.newRecord(Tables.PERSON, person))
      .where(Tables.PERSON.ID.eq(person.getId()))
      .returning()
      .fetchOptional()
      .orElseThrow(() -> new IllegalStateException("Person doesn't exist [id: " + person.getId() + "]"))
      .into(Person.class);
  }

  @Override
  public void insertAll(List<Person> people) {
    Iterables.partition(people, BATCH_INSERT_SIZE).forEach(batch ->
      dsl.batchStore(
        batch.stream().map(it -> dsl.newRecord(Tables.PERSON, it)).collect(Collectors.toList())
      ).execute()
    );
  }

  @Override
  public void delete(Integer id) {
    dsl
      .deleteFrom(Tables.PERSON)
      .where(Tables.PERSON.ID.eq(id));
  }

  @Override
  public void erase() {
    dsl
      .delete(Tables.PERSON)
      .execute();
  }

  @Override
  public List<Person> getAllInAgeRange(
    Date fromBirthDay,
    Date toBirthday
  ) {
    return dsl
      .select()
      .from(Tables.PERSON)
      .where(
        Tables.PERSON.DATE_OF_BIRTH.greaterThan(fromBirthDay)
          .and(
            Tables.PERSON.DATE_OF_BIRTH.lessOrEqual(toBirthday)
          )
      )
      .fetchInto(Person.class);
  }
}
