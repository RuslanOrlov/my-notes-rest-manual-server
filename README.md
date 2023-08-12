# my-notes-rest-manual-server
EN: A project in Java and Spring Boot for taking notes using a manually developed REST API (SERVER SIDE ONLY).

The project implements a REST controller that processes client requests, as well as a repository for storing notes (Note entity) in the database.

The application runs in the IDE and is available at http://localhost:8081/

1. The server supports operations:
- adding notes,
- selecting a list of notes entries,
- selecting a single note,
- changing a note,
- logical deletion (changing the value of the IsDeleted field),
- physical deletion of a note.
2. In addition, the repository implements methods that support SELECT selections by the value of any of the fields of the note (filtering at the client level), SELECT selections of record portions (pagination at the client level), as well as SELECT selections that return the number of records in the database in total or with a filtering criterion (to calculate the number of pages in the client).
3. A DTO object is used to exchange data with the client.

P.S.: This version of the application implements only the server.

/----------------------------------------------------------------------------------------------/

RU: Проект на языке Java и Spring Boot по учету заметок с использованием вручную разработанного REST API (ТОЛЬКО СЕРВЕРНАЯ ЧАСТЬ).

Проект реализует REST контроллер, обрабатывающий запросы клиента, а также репозиторий для хранения заметок (сущность Note) в БД.

Приложение запускается в IDE и доступно по адресу http://localhost:8081/

1. Сервер поддерживает операции:
- добавления заметки,
- выборка списка записей заметок,
- выборка одной заметки,
- изменение заметки,
- логическое удаление (изменение значения поля isDeleted),
- физическое удаление заметки.
2. Кроме того, в репозитории реализованы методы, поддерживающие SELECT выборки по значению любого из полей заметки (фильтрация на уровне клиента), SELECT выборки порций записи (пострачный просмотр на уровне клиента), а также SELECT выборки, возвращающие количество записей в БД всего или с критерием фильтрации (для расчета количества страниц в клиенте).
3. Для обмена данными с клиентом используется DTO объект.

P.S.: Данная версия приложения реализует только сервер.
