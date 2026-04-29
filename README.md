
---

## README для Lost Property Office (Бюро находок)

```markdown
# Lost Property Office - Бюро находок

Веб-приложение для учета и управления найденными вещами.

## Описание

Система предназначена для организаций, занимающихся хранением найденных вещей (бюро находок, транспортные компании, торговые центры и т.д.). Приложение позволяет регистрировать найденные вещи, отслеживать их статус и отмечать возврат владельцам.

## Функциональные возможности

### Для пользователей
- **Регистрация и авторизация** - создание учетной записи с валидацией email и телефона
- **Просмотр списка вещей** - просмотр всех найденных вещей (доступно без регистрации)
- **Добавление найденных вещей** - регистрация новой находки (требуется авторизация)
- **Отметка о возврате** - изменение статуса вещи на "возвращено" (требуется авторизация)

### Для системы
- **Типизация вещей** - 18 предопределенных типов (электроника, документы, украшения, ключи и т.д.)
- **Правила хранения** - для каждого типа указаны сроки и стоимость хранения
- **Маршрутизация** - определение куда отправляется невостребованная вещь (аукцион, полиция, утилизация)

## Архитектура

### Структура базы данных
```
Users (пользователи)
└── FoundProperties (найденные вещи)
└── PropertyType (тип вещи)
└── NextDestination (пункт назначения)
```

### Компоненты системы

**Сущности (entities)**
- `User` - пользователь системы
- `FoundProperty` - найденная вещь
- `PropertyType` - тип вещи с правилами хранения
- `NextDestination` - пункт назначения для невостребованных вещей

**Репозитории (repository)**
- `UserRepository` - работа с пользователями
- `FoundPropertyRepository` - работа с найденными вещами
- `PropertyTypeRepository` - работа с типами вещей
- `NextDestinationRepository` - работа с пунктами назначения

**Сервисы (services)**
- `UserService` - бизнес-логика пользователей
- `FoundPropertyService` - бизнес-логика найденных вещей

**Сервлеты (servlets)**
- `LoginServlet` - обработка входа
- `RegisterUserServlet` - обработка регистрации
- `AddPropertyServlet` - добавление вещи
- `DisplayPropertiesServlet` - отображение списка
- `UpdatePropertyServlet` - обновление статуса

**Фильтры (filters)**
- `AuthorizationFilter` - проверка авторизации
- `EditableFilter` - контроль доступа к редактированию

**Подключение к БД (connection)**
- `DatabaseProperties` - загрузка настроек из `.properties`
- `DataSourceProvider` - настройка HikariCP connection pool

## Типы вещей (PropertyTypeName)

| Категория | Примеры |
|-----------|---------|
| ELECTRONICS | Телефоны, ноутбуки, планшеты |
| DOCUMENTS | Паспорта, права, свидетельства |
| JEWELRY | Украшения, часы |
| BANK_CARD | Банковские карты |
| CLOTHES | Одежда, обувь |
| KEYS | Ключи, брелоки |
| MONEY | Наличные деньги |
| BAGS | Сумки, рюкзаки |
| BOOKS | Книги, учебники |
| и другие | Всего 18 типов |

## Статусы возврата (ReturnStatus)

- `NOT_RETURNED` - вещь еще не возвращена владельцу
- `RETURNED` - вещь возвращена

## Пункты назначения (NextDestinationName)

- `AUCTION` - отправка на аукцион
- `POLICE` - передача в полицию
- `DISPOSAL` - утилизация
- `RECYCLING` - переработка

## Структура проекта
```
lost-property-office/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── connection/ # Подключение к БД
│ │ │ ├── enams/ # Перечисления (enum)
│ │ │ ├── entities/ # Классы-сущности
│ │ │ ├── filters/ # Servlet фильтры
│ │ │ ├── repository/ # DAO слой
│ │ │ ├── services/ # Бизнес-логика
│ │ │ ├── servlets/ # Контроллеры
│ │ │ └── Main.java # Точка входа (CLI)
│ │ ├── resources/
│ │ │ ├── db.properties # Настройки БД
│ │ │ └── sql/ # SQL скрипты
│ │ └── webapp/
│ │ ├── WEB-INF/
│ │ │ └── web.xml # Конфигурация web-app
│ │ ├── index.jsp # Главная страница
│ │ ├── login.jsp # Страница входа
│ │ ├── register.jsp # Страница регистрации
│ │ ├── authorization.jsp # Выбор входа/регистрации
│ │ ├── addProperty.jsp # Добавление вещи
│ │ └── listProperties.jsp # Список вещей
│ └── test/
│ ├── java/ # Unit и интеграционные тесты
│ └── resources/ # Тестовые SQL скрипты
└── build.gradle
```

## Технологии

**Backend**
- Java 11
- Servlets (javax.servlet 4.0.1)
- JSP + JSTL для представления
- HikariCP 5.1.0 (connection pooling)
- PostgreSQL 42.7.5 (основная БД)

**Тестирование**
- JUnit 5
- Mockito 4.11.0
- H2 Database (in-memory для тестов)
- Tomcat Embedded (для тестов)

**Логирование**
- SLF4J + Simple Logger

## SQL схема

```sql
-- Основные таблицы
Users           -- пользователи
NextDestination -- пункты назначения
PropertyType    -- типы вещей (ссылается на NextDestination)
FoundProperties -- найденные вещи (ссылается на Users и PropertyType)

-- Индексы для оптимизации
CREATE INDEX IDXFoundPropertiesPropertyTypeID ON FoundProperties(PropertyTypeID);
CREATE INDEX IDXFoundPropertiesUserID ON FoundProperties(UserID);
CREATE INDEX IDXPropertyTypeNextDestinationID ON PropertyType(NextDestinationID);
