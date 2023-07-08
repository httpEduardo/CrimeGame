Endpoints:


**POST: investigations/{investigationId}/detectives/{detectiveId}/commands**
Pozwala wyslac komende.


**GET: investigations/{investigationId}/detectives/{detectiveId}/locations**
Zwraca wszystkie odkryte lokalizacje z zaznaczeniem, która jest aktualna.
Lokalizacja - kiedy jest otwarta (rozkminic jak to bedzie).
Opis.
//TODO: Dodać event LocationRevealed - dodaje do znanych lokalizacji


**GET: investigations/{investigationId}/detectives/{detectiveId}/characters**
Zwraca osoby, o których wie detektyw. Opis, czy żyją.


**GET: investigations/{investigationId}/detectives/{detectiveId}/characters-locations**
Zwraca lokalizacje danych osób, o których wie detektyw (unknown - jak nie wiadomo gdzie sa).
Przemyslec czy to jednak nie bedzie czescia characters. A moze GraphQL w tym miejscu!?


**GET: investigations/{investigationId}/detectives/{detectiveId}/characters-phones**
Zwraca osoby, do których można zadzwonić z jakiekolwiek miejsca. Zazwyczaj są to np. policyjni technicy.


**GET: investigations/{investigationId}/detectives/{detectiveId}/items**
Zwraca przedmioty o jakich wie detektyw. 
Z informacja czy je znalazl i gdzie.
Kto jest w ich posiadaniu, jesli wie. Moze inny detektyw w multi?



**GET: investigations/{investigationId}/detectives/{detectiveId}/commands**
Wszystkie komendy jakie wykonał gracz wraz z rezultatem.


SIMPLE VERSION,
ENDPOINTS:

investigations/{investigationId} - investigation state based on events in JSON!



//EVENT:
SCENARIO ID, INVESTIGATION ID itp.
