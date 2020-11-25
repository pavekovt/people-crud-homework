# Homework

### Technologies

I've decided to experiment and not use traditional Hibernate ORM.
jOOq and Flyway seemed to suit needs of this homework.
Although I have encountered numerous of unexpected issues like:
 * jOOQ's integration with Spring and it's transaction manager.
 * Code duplication within between flyway and jooq's configuration.

### Things to improve
 * Add layer between models and domain. Currently [Person](src/main/java/me/pavekovt/homework/domain/Person.java)
 is being used in both cases.
 * Find a way to properly stream import's file content rather than loading 
 everything into memory with Jackson first.
 * Export as file rather than stream.
 * Get rid of magic numbers :)
 * Maybe add integration tests
 