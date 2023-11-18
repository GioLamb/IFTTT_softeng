# IFTTT_softeng
## Descrizione del problema
Il sistema deve affrontare la sfida di consentire agli utenti di creare regole personalizzate che coinvolgono non solo condizioni semplici del tipo "se questo evento accade, esegui questa azione", ma anche scenari più complessi. Ad esempio, potrebbe essere richiesto di creare regole come "se questo evento accade e quest'altra condizione è vera, esegui questa azione, altrimenti esegui un'altra azione".

Ulteriormente, il problema si complica quando gli utenti vogliono creare catene di azioni, dove l'esecuzione di una azione specifica può influenzare il flusso di lavoro successivo. Ad esempio, "se questo evento accade, esegui questa azione, quindi valuta una nuova condizione e, in base a ciò, esegui un'altra azione".

La sfida principale è fornire un'interfaccia utente intuitiva che permetta agli utenti di definire e modificare questi flussi di lavoro in modo flessibile e senza complicare eccessivamente il processo.
## Architettura Software
Per la realizzazione del nostro software abbiamo deciso di servirci di un pattern MVC (Model-View-Controller) in quanto risulta la scelta più ottimale 
per la creazione di software che prevedono l'interazione con l'utente tramite una GUI (Graphical User Interface).

Per gestire la comunicazione tra utente e sistema, ci siamo avvalsi del pattern Facade (Facciata), ovvero abbiamo implementato una classe `FacadeRule`
la quale si occuperà di eseguire tutte le chiamate necessarie alla creazione, gestione ed esecuzione di una regola.
Tra le sottoclassi che abbiamo finora introdotto notiamo:
1. `Display Message`
   Rappresenta la classe che si occuperà di legare alla regola la funzione di visualizzare un messaggio scritto dall'utente allo scoccare di un determinato orario anch'esso selezionato dall'utente.
2. `Alarm Clock`
    Rappresenta la classe che si occuperà di legare alla regola la funzione di riprodurre un file audio scelto dall'utente allo scoccare di un determinato orario anch'esso selezionato dall'utente.

Di seguito è presente un collegamento ad un file .docx su Google Documents in cui si può visualizzare nel totale l'architettura dell'intero software [Architettura_Software_IFTTT](https://docs.google.com/document/d/1m6LYMVZjep5ySxO0Ugfd-RhB5yGPY-G1rPBpMyIHfxc/edit?usp=sharing)
## Definiton of Done (DoD)
È stato stilato un documento per la Definition of Done dell'intero software ed è consultabile al seguente [link.](https://docs.google.com/document/d/17hKDsjm6unqMskwzPeQu_q7Cmel3s3g-sp2IlAWASrQ/edit?usp=sharing)
## Product Backlog
Per la realizzazione del Product Backlog abbiamo utilizzato un foglio di calcolo Google Sheets disponibile al seguente [link.](https://docs.google.com/spreadsheets/d/1Jg-jbu-lqLK0X6i5f5sSLkYn4Eur-_I_bi0ZIpJWjm4/edit?usp=sharing)
Abbiamo diviso le diverse User Story secondo il numero dell'User Epic corrispondente, per ognuna di esse abbiamo tracciato delle Acceptance Criteria (criteri di accettazione).
## Sprint Backlog n°1
Per la Sprint Backlog abbiamo realizzato un foglio di calcolo su Google Sheets, consultabile a questo [link.](https://docs.google.com/spreadsheets/d/1Jg-jbu-lqLK0X6i5f5sSLkYn4Eur-_I_bi0ZIpJWjm4/edit?usp=sharing)
Abbiamo selezionato le User Story a priorità maggiore e abbiamo assegnato ad ognuna di esse un peso valutato in Story Points (per il costo degli Story Points abbiamo fatto riferimentop ai valori della sequenza di Fibonacci).
