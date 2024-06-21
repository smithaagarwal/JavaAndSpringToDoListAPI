## ToDoList REST API 


The project is developed using Spring Boot. There are REST endpoints for 
- getting all the tasks that is persisted
- adding a new task 
- editing the task description and complete status
- deleting a task

The documentation for the API can be found at: http://localhost:8888/swagger-ui/index.html
### How to run locally

Create a .env.properties file in the resources folder, with the db credentials
DB_USER=dbusername
DB_PASS=password

Open a terminal and cd into project directory

- `mvn clean package`
- java -jar  .\target\JavaAndSpringToDoListAPI-1.0.0.jar --spring.profiles.active=local`


_How to run via Docker_

Open a terminal and cd into project directory

- `mvn clean package`
- `docker-compose up --build`

_PS : While running from within the Docker container, Spring reads the DB connection and user details from the environment variable set in the `docker-compose.yml`_ 


_How to debug in Intellij community edition_
![Debugging](intellij-debug.png "IntelliJ Debugging")

## Design decisions

- Decouple `domain object` from request and response object:

    Initially I had just the domain object. But after the OpenAPI documentation via Swagger I realised that contract is not well-defined. 
At first I added `@JsonIgnore` on the domain object field to hide the internal field from Response. But that breaks the Single Responsibility Principle.
So I decoupled the domain object from Request and Response objects.


- Immutability and Java Records :

    Immutability: `Spring Data JDBC` promotes the use of immutable entities.
`Java Records`: Ideal for modeling entities because they are immutable by design and have an all-args constructor that the framework can utilize for object population.
PS: `JPA` does not work with Records as it requires mutability


- Construction Injection using `@Value` annotation

    Store the max limit of incomplete tasks in application.yml and injected it through the constructor.
  Injection of value via Constructor allows the entity to be correctly unit tested. Injecting the value on a private member variable did not let me test the functionality. 
  Storing the constant in a yml file enables changing this number in the future without modifying the code.


- Used `CORS` configuration:

    Since the webclient is hosted on a different domain it resulted in CORS error. 
Hence, I have set up the Spring CORS configuration such that the client is able to connect to the server (both hosted on different domains).
TODO: Make it more secure.


- Using Spring Profile

    I initially wrote all the code by running the DB locally on my machine. Once I moved to Docker container, I saw my integration tests started failing.
This allowed me to think and segregate the runtime configuration of my application such that Integration Tests only run in local. I used Spring profiles
to conditionally load the class. I made a local and docker profile. I added the `@ActiveProfiles` annotation on my integration test class.

    TODO: I should enhance the local profile to connect to a postgres container rather than a locally running instance and use `TestContainers`

Reference: I used this book - `Cloud Native Spring In Action, Thomas Vitale`