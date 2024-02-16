Technical Specification - Техническое задание

Создание монолитного RESTful CRUD-приложения по управлению работниками на языке Java с использованием Spring, JPA, Hibernate, Lombok с подключением PostgreSQL, Redis, Elasticsearch и контейнеризацией через Docker, а также c осуществлением тестирования с помощью Junit и Mockito и Postman.

Цель проекта: создание приложения, которое позволит менеджеру вести реестр работников и изменять его в реальном времени.

Начало проекта: создание нового проекта через IntelliJIDEA с использованием Gradle и Spring Boot, настройкой PostgreSQL.

Функции, которыми должно обладать приложение:

Получать информацию о работниках из базы данных;
Сохранять информацию о работниках, включая внесение записей о новых работниках;
Обновлять информацию о работниках;
Удалять информацию о работниках.
Файлы, которые будут созданы в процессе написания приложения:

build.gradle
gradlew
иные файлы инструмента Gradle
Dockerfile
docker-compose.yml
.gitignore
Employee.java
EmployeeController.java
EmployeeControllerTest.java
EmployeeRepository.java
ResourceNotFoundException.java
ErrorDetails.java
GlobalExceptionHandler.java
EmployeeCrudApplication.java (содержит метод main)
Сроки выполнения проекта: 2023\02\14 — 2023\02\17
