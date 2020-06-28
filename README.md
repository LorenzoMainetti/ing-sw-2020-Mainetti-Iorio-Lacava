# Prova Finale di Ingegneria del Software - a.a. 2019-2020
![alt text](https://github.com/LorenzoMainetti/ing-sw-2020-Mainetti-Iorio-Lacava/blob/master/src/main/resources/SantoriniLogo.png)

Scopo del progetto è quello di implementare il gioco da tavola [Santorini](https://roxley.com/products/santorini) in linguaggio java, seguendo il pattern architetturale Model-View-Controller e secondo il paradigma di programmazione orientato agli oggetti. Il risultato finale copre completamente le regole definite dal gioco e permette di interagirci sia da linea di comando (CLI) che con interfaccia grafica (GUI), la rete è stata gestita attraverso l'approccio delle socket.

## Documentazione
### Coverage Report
Documentazione HTML che riporta nel dettaglio la test coverage complessiva raggiunta dai singoli package e dalle singole classi. Si è testato in particolare il Model, che raggiunge una copertura delle istruzioni del 96%. 
È consultabile al seguente indirizzo: [Report](https://github.com/LorenzoMainetti/ing-sw-2020-Mainetti-Iorio-Lacava/tree/master/deliveries/final/report)

### UML
I seguenti diagrammi delle classi rappresentano rispettivamente il progetto iniziale del model, aggiornato per rispecchiare lo stato attuale, e i diagrammi finali dell'intero progetto generati con il tool di IntelliJ.
- [UML Iniziale](https://github.com/LorenzoMainetti/ing-sw-2020-Mainetti-Iorio-Lacava/tree/master/deliveries/final/uml/initial)
- [UML Finali](https://github.com/LorenzoMainetti/ing-sw-2020-Mainetti-Iorio-Lacava/tree/master/deliveries/final/uml/generated)

### JavaDoc
La seguente documentazione include una descrizione per la maggiore parte delle classi e dei metodi utilizzati, segue le tecniche di documentazione di Java e può essere consultata al seguente indirizzo: [Javadoc](https://github.com/LorenzoMainetti/ing-sw-2020-Mainetti-Iorio-Lacava/tree/master/deliveries/final/javadoc)

### Jar
I seguenti jar permettono il lancio del gioco secondo le funzionalità richieste dalla specifica. Le funzionalità realizzate sono elencate nella sezione __Funzionalità__, mentre i dettagli per come lanciare i jar sono definiti nella sezione __Esecuzione dei jar__. I file .jar di Client e Server sono dispobibili al seguente indirizzo: [Jar](https://github.com/LorenzoMainetti/ing-sw-2020-Mainetti-Iorio-Lacava/tree/master/deliveries/final/jar)

## Librerie e Plugins
|Libreria/Plugin|Descrizione|
|---------------|-----------|
|__maven__|strumento di gestione per software basati su Java e build automation|
|__maven-shade-plugin__|plugin per la creazione di un uber-jar|
|__junit__|framework dedicato a Java per unit testing|
|__JavaFx__|libreria grafica di Java|

## Funzionalità
### Funzionalità Sviluppate
- Regole Complete
- CLI
- GUI
- Socket

### Funzionalità Avanzate Sviluppate
- Partite Multiple
- Divinità Avanzate

## Esecuzione dei JAR
### Server
Per eseguire il server digitare il seguente comando da terminale:
```
java -jar serverApp.jar 
```
La porta predefinita utilizzata dal server è 9090.

### Client
Il client viene eseguito scegliendo l'interfaccia con cui giocare, le possibili scelte sono da linea di comando o interfaccia grafica. Le seguenti sezioni descrivono come eseguire il client in un modo o nell'altro.
#### CLI
Per una migliore esperienza di gioco da linea di comando è necessario lanciare il client con un terminale che supporti la codifica UTF-8 e gli ANSI escape. 
Per lanciare il client in modalità CLI digitare il seguente comando:
```
java -jar clientApp.jar -cli
```
#### GUI
Le dipendenza necessarie al funzionamento di javaFX per i principali sistemi operativi (Windows, macOs, Linux) sono già state inserite nel pom, il client è quindi cross-platform.
Per poter lanciare il client con l'interfaccia grafica è sufficiente un doppio click sul jar, oppure digitare da terminale, senza passare alcun parametro:
```
java -jar clientApp.jar 
```

## Componenti del gruppo
- [__Lorenzo Mainetti__](https://github.com/LorenzoMainetti)
- [__Ginevra Iorio__](https://github.com/ginevraiorioo)
- [__Marco Lacava__](https://github.com/LacavaMarco)
