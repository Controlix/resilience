(1)  Dit is nodig om de `CompletableFuture`s in paralel te laten uitvoeren.
Zoniet worden ze sequentieel afgehandeld, ttz elke `CompletableFuture` moet eindigen voordat de volgende aangemaakt wordt.
Dit gebeurt omdat java stream sequentieel is by default.
> collect(...).stream()

Door de `collect` wordt de stream beëindigd, waardoor de `CompletableFuture`s allemaal aangemaakt worden voordat de join operatie ervoor zorgt dat gewacht wordt tot ze allemaal uitgevoerd zijn.