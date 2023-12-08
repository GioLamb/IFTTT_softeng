# IFTTT_softeng
## Descrizione del problema
Il sistema deve affrontare la sfida di consentire agli utenti di creare regole personalizzate che coinvolgono non solo condizioni semplici del tipo "se questo evento accade, esegui questa azione", ma anche scenari più complessi. Ad esempio, potrebbe essere richiesto di creare regole come "se questo evento accade e quest'altra condizione è vera, esegui questa azione, altrimenti esegui un'altra azione".

Ulteriormente, il problema si complica quando gli utenti vogliono creare catene di azioni, dove l'esecuzione di una azione specifica può influenzare il flusso di lavoro successivo. Ad esempio, "se questo evento accade, esegui questa azione, quindi valuta una nuova condizione e, in base a ciò, esegui un'altra azione".

La sfida principale è fornire un'interfaccia utente intuitiva che permetta agli utenti di definire e modificare questi flussi di lavoro in modo flessibile e senza complicare eccessivamente il processo.
## Architettura Software
Per la realizzazione del nostro software abbiamo deciso di servirci di un pattern MVC (Model-View-Controller) in quanto risulta la scelta più ottimale 
per la creazione di software che prevedono l'interazione con l'utente tramite una GUI (Graphical User Interface) interpretata tramite un FXMLDocument.
Il ruolo del controllore verrà svolto dalla classe `FXMLDocumentController`.

Per gestire la creazione di regole da parte dell'utente, ci siamo avvalsi del pattern FactoryMethod, ovvero abbiamo implementato una classe `Rule` la quale si occuperà di permettere all'utente di istanziare nuove regole indipendentemente dall'azione e dal trigger specificati, favorendo l'aggiunta di nuove regole in futuro senza dover modificare il codice esistente. In particolare fornisce un metodo che si occuperà di fare riferimento alle classi che implementano le interfacce `Action` e `Trigger` per gestire rispettivamente la scelta, da parte dell'utente, dell'azione e dell'orario associati.
La classe `Rule` viene invocata da `RuleManager`, il cui obiettivo è quello di gestire un insieme di regole, non solo raccogliendo la creazione di queste ultime da interfaccia grafica, ma anche permettendo l'aggiunta di nuove regole all'intero sistema. Per questa classe abbiamo utilizzato il pattern Singleton, in modo tale da poter avere sempre una sola istanza di `RuleManager`. 
La classe `Check` estende la classe `Thread` e gestisce l'attivazione del trigger, permettendo l'esecuzione dell'azione associata, e quindi dell'intera regola.
Per la rappresentazione delle sottoclassi abbiamo sfruttato il pattern Composite, utile a fornire una struttura ad albero al nostro programma.
Tra le foglie dell'albero di `Action` notiamo diverse classi come:
1. `DisplayMessage`
   Rappresenta la classe che si occuperà di legare alla regola la funzione di visualizzare un messaggio scritto dall'utente allo scoccare di un determinato orario anch'esso selezionato dall'utente.
2. `AlarmClock`
    Rappresenta la classe che si occuperà di legare alla regola la funzione di riprodurre un file audio scelto dall'utente allo scoccare di un determinato orario anch'esso selezionato dall'utente.
3. `WriteToFileAction`
   Rappresenta la classe che si occupa della scrittura di un testo scritto dall'untente su un file selezionato in fase id creazione
4. `MoveFileAction`
   Rappresenta la classe che si occupa di spostare un file selezionato all'interno di una cartella scelta
5. `ActionCopy`
   Rappresenta la classe che si occupa di copiare un file selezionato all'interno di una cartella scelta
6. `DeleteFileAction`
   Rappresenta la classe che si occupa di eliminare un file selezionato dall'utente.

Mentra tra le foglie dell'albero `Trigger` notiamo una singola classe:
1. `TriggerTime`
   Rappresenta la classe che si occuperà di impostare un orario per l'oggetto regola annesso.
2. `TriggerDayOfMonth`
   Rappresenta la classe che si occuperà di impostare l'esecuzione della regola al giorno del mese selezionato. 
3. `TriggerDayOfWeek`
   Rappresenta la classe che si occuperà di impostare l'esecuzione della regola al giorno della settimana selezionato.


Di seguito è possibile visualizzare un file .svg, in cui è rappresentata nel totale l'architettura dell'intero software, [a questo link.](https://raw.githubusercontent.com/GioLamb/IFTTT_softeng/main/Architettura_IFTTT.svg)
## Definiton of Done (DoD)
È stato stilato un documento per la Definition of Done dell'intero software ed è consultabile al seguente [link.](https://docs.google.com/document/d/17hKDsjm6unqMskwzPeQu_q7Cmel3s3g-sp2IlAWASrQ/edit?usp=sharing)
## Product Backlog
Per la realizzazione del Product Backlog abbiamo utilizzato un foglio di calcolo Google Sheets disponibile al seguente [link.](https://docs.google.com/spreadsheets/d/1Jg-jbu-lqLK0X6i5f5sSLkYn4Eur-_I_bi0ZIpJWjm4/edit?usp=sharing)
Abbiamo diviso le diverse User Story secondo il numero dell'User Epic corrispondente, per ognuna di esse abbiamo tracciato delle Acceptance Criteria (criteri di accettazione).
## Sprint Backlog n°1
Per la Sprint Backlog abbiamo realizzato un foglio di calcolo su Google Sheets, consultabile a questo [link.](https://docs.google.com/spreadsheets/d/1Jg-jbu-lqLK0X6i5f5sSLkYn4Eur-_I_bi0ZIpJWjm4/edit#gid=1742790855)
Abbiamo selezionato le User Story a priorità maggiore e abbiamo assegnato ad ognuna di esse un peso valutato in Story Points (per il costo degli Story Points abbiamo fatto riferimento ai valori della sequenza di Fibonacci).
In questa sprint ci siamo concentrati sulla realizzazione dell'interfaccia grafica e delle funzionalità principali del software come, ad esempio, la realizzazione di un promemoria o di una sveglia.
## Sprint Backlog n°2
Per la seconda Sprint Backlog abbiamo realizzato un foglio di calcolo su Google Sheets, consultabile a questo [link.](https://docs.google.com/spreadsheets/d/1Jg-jbu-lqLK0X6i5f5sSLkYn4Eur-_I_bi0ZIpJWjm4/edit#gid=1465735659).
In questa sprint abbiamo implementato nuove funzionalità come la scrittura e lettura su file delle regole in maniera automatica oppure la possibilità di eseguire una regola una o più volte.
## Sprint Backlog n°3
Per la terza Sprint Backlog abbiamo realizzato un foglio di calcolo su Google Sheets, consultabile a questo [link.](https://docs.google.com/spreadsheets/d/1Jg-jbu-lqLK0X6i5f5sSLkYn4Eur-_I_bi0ZIpJWjm4/edit#gid=156001873).
In questa sprint abbiamo implementato nuove funzionalità come l'aggiunta di nuove azioni e nuovi trigger di attivazione.
Sono state aggiunte azioni di: scrittura in un file, copia di un file, spostamento di un file ed eliminazione di un file.
Sono stati aggiunti trigger "giorno specifico della settimana" e "giorno specifico del mese.
