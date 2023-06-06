# Konferencja IT

Projekt serwisu dla strony do obsługi konferencji IT.

## Technologie:
- Java 17
- Baza danych H2 (zapisywana w pamięci komputera, w katalogu projektu `data`)

## Instrukcja uruchomienia:

Aby uruchomić projekt, należy:
- otworzyć terminal w głównym katalogu projektu,
- wykonać polecenie:
```bash
  mvn clean package
``` 
- przejść do katalogu `target`, gdzie powinien znajdować się plik: `it-conference-0.0.1-SNAPSHOT.jar`
- będąc w lokalizacji wspomnianego pliku, wykonać polecenie: 
```bash
  java -jar it-conference-0.0.1-SNAPSHOT.jar
``` 

## Powiadomienia mailowe:

Po dokonaniu rezerwacji prelekcji przez użytkownika, treść powiadomienia mailowego trafia do tabeli `notifications`, reprezentowanej przez encję `NotificationHistory`. Informacje zawarte w tej tabeli mogą zostać wykorzystane w przyszłości do zaimplementowania funkcjonalności wysyłania wiadomości mailowych, gdyż każdy rekord posiada flagę, czy dana notyfikacja została wysłana.

## Przegląd endpoint'ów:

### - dostępne dla wszystkich:

#### Signup

**opis:** endpoint do rejestracji\
**url:** http://localhost:8080/api/auth/signup \
**http-method:** POST\
**przykładowe body:** {
"username": "user3",
"email": "user@itconference.com",
"password": "zaq1@WSX"
}

#### Signin

**opis:** endpoint do autoryzacji\
**url:** http://localhost:8080/api/auth/signin \
**http-method:** POST\
**przykładowe body:** {
"username": "user1",
"password": "password"
}

#### Signout

**opis:** endpoint do wylogowywania użytkownika\
**url:** http://localhost:8080/api/auth/signout \
**http-method:** POST

### - dostępne dla zalogowanych:

#### getAllUsers

**opis:** endpoint zwracający listę zarejestrowanych użytkowników\
**url:** http://localhost:8080/api/users \
**http-method:** GET

#### changeEmail

**opis:** endpoint do zmiany emaila zalogowanego użytkownika\
**url:** http://localhost:8080/api/users/user/email \
**http-method:** PATCH\
**przykładowe body:** {
"email": "nowymail@gmail.com"
}

#### getConferenceInfo

**opis:** endpoint zwracający informacje o konferencji oraz o przypisanych do niej prelekcjach, należy podac id konferencji\
**url:** http://localhost:8080/api/conferences/conference/{id} \
**http-method:** GET

#### getUserLectures

**opis:** endpoint zwracający informacje o prelekcjach, na które zapisany jest zalogowany użytkownik\
**url:** http://localhost:8080/api/conferences/lectures/user \
**http-method:** GET

#### generateLectureReport

**opis:** endpoint zwracający raport obecności na prelekcjach dla podanej w parametrze id konferencji\
**url:** http://localhost:8080/api/conferences/{id}/lectures/report \
**http-method:** GET

#### generateCategoryReport

**opis:** endpoint zwracający raport średniej frekwencji w danej kategorii dla określonej w parametrze id konferencji\
**url:** http://localhost:8080/api/conferences/{id}/category/report \
**http-method:** GET

#### joinToLecture

**opis:** endpoint zapisujący zalogowanego użytkownika na prelekcję o podanym w parametrze id \
**url:** http://localhost:8080/api/conferences/lecture/{id}/users \
**http-method:** POST

#### cancelLecture

**opis:** endpoint wypisujący zalogowanego użytkownika z prelekcji o podanym w parametrze id\
**url:** http://localhost:8080/api/conferences/lecture/{id}/users \
**http-method:** DELETE
