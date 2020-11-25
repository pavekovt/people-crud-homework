package me.pavekovt.homework.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.pavekovt.homework.dao.PersonDAO;
import me.pavekovt.homework.domain.Person;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

  PersonServiceImpl getMockedService(
    PersonDAO dao,
    ObjectMapper mapper
  ) {
    return new PersonServiceImpl(
      dao != null ? dao : mock(PersonDAO.class),
      mapper != null ? mapper : mock(ObjectMapper.class)
    );
  }

  @Test
  void importFile() throws IOException {
    //given
    var dao = mock(PersonDAO.class);
    var mapper = mock(ObjectMapper.class);
    var service = getMockedService(dao, mapper);

    var peopleList = new ArrayList<Person>();

    doReturn(peopleList).when(mapper).readValue(any(InputStream.class), any(TypeReference.class));

    //when
    service.importFile(mock(InputStream.class));

    //then
    verify(dao).erase();
    verify(dao).insertAll(eq(peopleList));
  }

  @Test
  void getWithAgeBetween() {
    //given
    var dao = mock(PersonDAO.class);
    var service = getMockedService(dao, null);
    int youngest = 10;
    int eldest = 20;

    ArgumentCaptor<Date> from = ArgumentCaptor.forClass(Date.class);
    ArgumentCaptor<Date> to = ArgumentCaptor.forClass(Date.class);

    //when
    service.getWithAgeBetween(youngest, eldest);

    //then
    verify(dao).getAllInAgeRange(from.capture(), to.capture());
    assertEquals(eldest, Period.between(from.getValue().toLocalDate(), LocalDate.now()).getYears() - 1);
    assertEquals(youngest, Period.between(to.getValue().toLocalDate(), LocalDate.now()).getYears());
  }
}