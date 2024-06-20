## ToDoList REST API 


The project is developed using Spring Boot. There are end points for 
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

- Decouple domain object from request and response object:
This struck me after I had setup swagger and I was looking at the documentation generated. Gor the request and response parameters, I saw all the fields of Task object was sent. But as per the logic I did not need to send all the fields of Task. Hence I thought for it to align with the correct requirement, it will be best to decouple the domain object from the request and response object.

- Immutability and Java Records :
Immutability: Spring Data JDBC promotes the use of immutable entities.
Java Records: Ideal for modeling entities because they are immutable by design and have an all-args constructor that the framework can utilize for object population.

I used this book for reference: Cloud Native Spring In Action, Thomas Vitale

- Store the max limit of incomplete tasks in application.yml and injected it through the constructor:
Injection through constructor enables the value being available for running the tests
Storing the constant in a yml file enables changing this number in the future without modifying the code.

- Used CORS configuration:
This is not a secure way. But as the webclient was hosted on a different domain I was getting CORS error. Hence I have set the configuration such that the client is able to connect to the server.
