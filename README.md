# IT Conference

Website project for IT conference support.

## Wymagania

Do uruchomienia aplikacji niezbędna jest Java w wersji min. 17.

Aby uruchomić projekt w środowisku deweloperskim Eclipse nalezy pobrać projekt z repozytorium, a następnie w Eclipse wybrać File -> Import -> Maven -> Existing Maven Projects

Projekt oparty jest na bazie H2, baza zapisywana jest w pamięci komputera w katalogu projektu data.

Aplikacja po zapisie do wykladu, nie wysyla maila do uzytkownikow, informacja trafia do tabeli notifications reprezentowanej w kodzie przez encje NotificationHistory, informacje zawarte w tabeli mogą zostać wykorzystane w przyszłości do zaimplementowania tej funkcjonalności,
kazdy rekord ma flagę czy dana notyfikacja została wysłana.

## Endpointy

### Dostępne dla wszystkich

#### Signup

Opis: endpoint do rejestracji
url: http://localhost:8080/api/auth/signup
http-method: POST
przykładowe body: {
"username": "user3",
"email": "user@itconference.com",
"password": "zaq1@WSX"
}

#### Signin

Opis: endpoint do autoryzacji
url: http://localhost:8080/api/auth/signin
http-method: POST
przykładowe body: {
"username": "user1",
"password": "password"
}

#### Signout

Opis: endpoint do wylogowywania uzytkownika
url: http://localhost:8080/api/auth/signout
http-method: POST

### Dostępne dla zalogowanych

#### getAllUsers

Opis: endpoint zwracający listę zarejestrowanych uytkowników
url: http://localhost:8080/api/users
http-method: GET

#### changeEmail

Opis: endpoint do zmiany emaila zalogowanego uzytkownika
url: http://localhost:8080/api/users/user/email
http-method: PATCH
przykładowe body: {
"email": "nowymail@gmail.com"
}

#### getConferenceInfo

Opis: endpoint zwracający informacje o konferencji oraz o przypisanych do niej wykladach, nalezy podac parametr id - jest to id konferencji
url: http://localhost:8080/api/conferences/conference/{id}
http-method: GET

#### getUserLectures

Opis: endpoint zwracający informacje o wykladach, na ktore jest zapisany zalogowany uzytkownik
url: http://localhost:8080/api/conferences/lectures/user
http-method: GET

#### generateLectureReport

Opis: endpoint zwracający raport obecności na wykładach dla podanej w parametrze id konferencji
url: http://localhost:8080/api/conferences/{id}/lectures/report
http-method: GET

#### generateCategoryReport

Opis: endpoint zwracający raport średnią frekwencje dla danej kategorii dla podanej w parametrze id konferencji
url: http://localhost:8080/api/conferences/{id}/category/report
http-method: GET

#### joinToLecture

Opis: endpoint zapisujący zalogowanego uzytkownika do wykladu o podanym w parametrze id
url: http://localhost:8080/api/conferences/lecture/{id}/users
http-method: POST

#### cancelLecture

Opis: endpoint wypisujący zalogowanego uzytkownika z wykladu o podanym w parametrze id
url: http://localhost:8080/api/conferences/lecture/{id}/users
http-method: DELETE
