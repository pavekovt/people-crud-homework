package me.pavekovt.homework.domain;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class Person {
  final private Integer id;
  @NonNull final private String firstName;
  @NonNull final private String lastName;
  final private String phone;
  final private String email;
  @NonNull final private Date dateOfBirth;
}
