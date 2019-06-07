## JavaExpressionCalculator

This is my personal take on an exercise that should have been way more basic, but I liked the challenge so here we are

The original exercise is:

>Si implenti la classe Calcolatrice che permette la valutazione di un’espressione aritmetica.
La classe in esame ha i due metodi seguenti:
• boolean controllaParentesi(String espressione): verifica il corretto bilanciamento delle
parentesi dell’espressione aritmetica espressione; in caso di bilanciamento errato, viene
lanciata l’eccezione ParentesiErrateException;
• void valutaEspressione(String espressione): nel caso in cui le parentesi di espressione
siano correttamente bilanciate, stampa a video il risultato calcolato su espressione.
Si utilizzi la classe Stack<E> del package java.util e il metodo toCharArray della classe String
come ausilio per l’implementazione dei metodi appena presentati.
Le espressioni da valutare sono:

```
• ( 2 + 3 ) * 5
• 30 / { 4 + [ 2 * 9 / ( 8 – 6 + 1 ) ] }
• 150 / { [ ( 7 + 5 ] - 3 ) + 9 } + 2
```

But I didn't use neither Stack<E> nor toCharArray because they are useless for this exercise, come on

**Please notice that this program doesn't implement multiplication and division priority yet!!! Because I'm a lazy ass**