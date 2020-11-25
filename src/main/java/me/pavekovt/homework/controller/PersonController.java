package me.pavekovt.homework.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.pavekovt.homework.domain.Person;
import me.pavekovt.homework.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "persons")
public class PersonController {

  final PersonService service;

  public PersonController(PersonService service) {
    this.service = service;
  }

  @GetMapping("/age_range")
  @ApiOperation(value = "Get people with age interval")
  public List<Person> getInAgeInterval(
    @RequestParam("from") Integer from,
    @RequestParam("to") Integer to
  ) {
    return service.getWithAgeBetween(from, to);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get person by id")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Successful retrieval of person"),
      @ApiResponse(code = 404, message = "No such user exists")
    }
  )
  public ResponseEntity<Person> get(
    @PathVariable("id") Integer id
  ) {
    var person = service.getById(id);
    return person != null ?
      ResponseEntity.ok().body(person) :
      ResponseEntity.notFound().build();
  }

  @PostMapping()
  public Person post(
    @RequestBody Person person
  ) {
    return service.add(person);
  }

  @PutMapping()
  public Person update(
    @RequestBody Person person
  ) {
    return service.update(person);
  }
}
