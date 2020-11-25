package me.pavekovt.homework.controller;

import io.swagger.annotations.ApiOperation;
import me.pavekovt.homework.domain.Person;
import me.pavekovt.homework.service.PersonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/persistance")
public class ExportController {
  final
  PersonService service;

  public ExportController(PersonService service) {
    this.service = service;
  }

  @GetMapping("/export")
  @ApiOperation(value = "Export all people from DB")
  public Stream<Person> export() {
    return service.exportAll();
  }

  @PostMapping("/import")
  @ApiOperation(value = "Replace db content with people from file")
  public void importFile(
    @RequestParam(name = "import") MultipartFile json
  ) throws IOException {
    service.importFile(json.getInputStream());
  }
}
