Personal Banking Recommender
--Описание проекта

Сервис рекомендаций банковских продуктов на основе пользовательских данных и бизнес-правил.

Система анализирует информацию о пользователе и формирует персонализированные рекомендации с использованием статических и динамических правил.

--Основной функционал
Получение рекомендаций по пользователю
Управление динамическими правилами
Интеграция с внешними системами
Telegram-бот для взаимодействия с пользователем
REST API для получения данных

--Архитектура

Проект построен по многослойной архитектуре:

Controller — обработка HTTP-запросов
Service — бизнес-логика
Repository — работа с базой данных
Integration — взаимодействие с внешними API

Используются:

PostgreSQL — основная база данных
Liquibase — миграции базы данных
Spring Boot — основной фреймворк

--API
Получить рекомендации

GET /recommendation/{userId}

Пример:

GET /recommendation/john_doe
Работа с правилами
Получить все правила
GET /rule
Создать правило
POST /rule
Удалить правило
DELETE /rule/{id}

--Telegram Bot

Бот позволяет получать рекомендации через Telegram.

Команды:

/start
/recommend @username

--Запуск проекта
1. Клонирование
git clone <URL_репозитория>

2. Настройка application.yml

Указать:

подключение к PostgreSQL
токен Telegram-бота

3. Запуск
mvn spring-boot:run

--Swagger

Swagger UI доступен по адресу:

http://localhost:8080/swagger-ui.html

--Структура проекта
controller/
service/
repository/
entities/
config/
bot/

--Диаграммы

Диаграммы находятся в разделе Wiki:

Use Case Diagram
Component Diagram
Activity Diagram

--Технологии
Java 17+
Spring Boot
Spring Data JPA
PostgreSQL
Liquibase
Telegram Bots API

--Автор
Разработчики: До этапа 3: Алексеева Александра, Коломеец Татьяна, Преловская Мария
После этапа 3 (начиная с этого): Алексеева Александра, Преловская Мария
