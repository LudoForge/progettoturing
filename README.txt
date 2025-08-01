Questo file descrive il progetto, come è stato elaborato e quali sono i punti di miglioramento.

Il progetto è stato semplice e non dispendioso, ha grandi margini di miglioramento.
Rispetta tutte le richieste base della consegna e alcune richieste secondarie.

PUNTI DI MIGLIORAMENTO:
-Il progetto comprende sia l'esecuzione delle crud su un file txt, sia su un database.
Chiaramente però ci si aspetta che un applicativo esegua le CRUD su un database e basta
anche per un fattore di persistenza. In questo caso però si è mantenuto il salvataggio
delle informazioni sul file txt (anche se obsoleto).

-Il progetto non comprende i bottoni con la JToolBar e le immagini di ogni bottone
per un semplice discorso di tempistiche.
Chiaramente si sarebbe potuto eseguire così:
JToolBar toolbar = new JToolBar();
JButton nuovoBtn = new JButton(new ImageIcon("img/nuovo.png"));
nuovoBtn.setToolTipText("Nuova persona");
nuovoBtn.addActionListener(e -> creaNuovaPersona());
toolbar.add(nuovoBtn);

- Il progetto potrebbe avere diverse librerie con cui integrarsi:
log4j per loggare dei messaggi migliori inserendo il tipo di messaggio (debug, info. error..)
Spring e le librerie Spring boot, Spring MVC e spring data JPA per poter gestire correttamente
le classi, i comportamenti col database e le informazioni passate e gestite da componente a
componente
Junit test per testare il codice scritto in automatico