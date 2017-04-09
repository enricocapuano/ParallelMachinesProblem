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