(1) Dit is geen correcte oplossing.
De waarde van de `AtomicInteger` kan nl. al gewijzigd zijn tussen de test in de `await` en dit statement.
Om dit correct te doen moeten we met een lock werken; voor dit voorbeeld is deze oplossing voldoende.