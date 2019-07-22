# Kostka do gry

Celem zadania było utworzenie uniwersalnej kostki do gry o następującym wzorze: 
```
xDy+z
```

gdzie:
- y – rodzaj kostek, których należy użyć (np. D6, D10),
- x – liczba rzutów kośćmi,
- z – (opcjonalnie) liczba, którą należy dodać (lub odjąć) do wyniku rzutów.


### Treść zadania
Napisz funkcję, która:
1. przyjmie w parametrze taki kod w postaci String,
2. rozpozna wszystkie dane wejściowe:
   - rodzaj kostki,
   - liczbę rzutów,
   - modyfikator,
3. wykona symulację rzutów i zwróci wynik.

> Typy kostek występujące w grach:
D3, D4, D6, D8, D10, D12, D20, D100.
