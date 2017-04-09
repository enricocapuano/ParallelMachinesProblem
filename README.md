# Opis założeń projektu
---
### 1. Temat i cel projektu
System wspomagający szeregowanie zadań na równoległych procesorach.

Cel projektu: projekt oraz implementacja systemu szeregowania zadań na równoległych procesorach, dla ustalonych ilości procesorów i zadań do wykonania na tych procesorach.

### 2. Sformułowanie problemu

1. Problem przyporządkowania zadań dla procesorów przy uwzględnieniu następujących założeń:

    * każdy procesor działa z równą szybkością;
    * zadania mają określony czas wykonania, niekoniecznie każde zadanie będzie miało taki sam czas wykonania;
    * uruchomienie i zatrzymanie zadania odbywa się w czasie zerowym;
    * zadania mogą mieć priorytety wykonania;


2. Problem decyzyjny: Czy istnieje takie przyporządkowanie zadań dla procesorów, tak czas wykonania zadań na każdym procesorze był jednakowy?

3. Problem optymalizacyjny: przyporządkować tak zadania do procesorów aby sumaryczny czas ich wykonania spełniał kryteria:
    * zadania z wyższym priorytetem zostają wykonane jako pierwsze;
    * sumaryczny czas wykonania jest jak najmniejszy;
    * każdy procesor jest wykorzystywany do wykonywania zadań.
	
### 3. Analiza złożoności obliczeniowej problemu

Niech k oznacza ilość procesorów, n ilość zadań do wykonania, a ti czas wykonania określonego zadania. Problem szeregowania zadań na wielu procesorach sprowadza się do wyznaczenia, dla każdego procesora podzbioru, ze zbioru zadań do wykonania, których sumaryczny czas nie przekroczy: 
 
Zatem nasz problem sprowadza się do powtórzenia k razy problemu plecakowego, gdzie plecakiem będzie wybrany procesor ki, przedmiotami pula zadań a pojemnością plecaka n/k. Złożoność obliczeniowa problemu wynosi 2n.

### 4. Metoda i algorytmy rozwiązywania problemu

Problem decyzyjny będzie rozwiązywany za pomocą podstawowych równań matematycznych i fizycznych, dzięki czemu będzie można szybko stwierdzić, czy możliwe jest takie przyporządkowanie zadań aby sumaryczny czas wykonania na każdym procesorze był [porównywalny].

W przypadku problemu optymalizacyjnego obiecującym wydaje się być algorytm: 
- brute force;
- podziału i ograniczeń.

### 5. Metoda, technologie i narzędzia implementacji

Program realizujący rozwiązanie problemu będzie zaimplementowany w języku Java, projekt będzie realizowany w środowisku IntelliJ.

### 6. Sposób testowania i oceny jakości rozwiązań

Skonfigurowany system zostanie zweryfikowany pod kątem poprawności i efektywności działania. Przewiduje się wykonanie testów jednostkowych. Ewentualny prosty interfejs graficzny zostanie zrealizowany z wykorzystaniem modułów JavaFX. 

W projekcie nie przewiduje się zastosowania interfejsu graficznego do prezentacji wyników prowadzonych symulacji. Wszystkie istotne informacje pozwalające ocenić opracowane rozwiązania będą zapisywane do odpowiednich plików (np. csv), a następnie analizowane. W celu wizualnej prezentacji wyników badań przedstawione zostaną odpowiednie wykresy.

### 7. Literatura
[1]. http://staff.iiar.pwr.wroc.pl/wojciech.bozejko/papers/2016/t1_0547.pdf
[2]. http://kkapd.f11.com.pl/zsw/maszyny_rownolegle.htm
[3]. http://www.cs.put.poznan.pl/rwalkowiak/pliki/pr/algorytmy.pdf
[4]. http://www.scl.pjwstk.edu.pl/studium_doktoranckie,materialy,dopobrania/Wyklad13.pdf
