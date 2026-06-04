# FLOWFRAME — Backend
> Logic First, Pixel Second.

Infrastruttura di servizi core per FLOWFRAME. Gestisce la persistenza dei dati dei progetti di interfaccia, il controllo degli accessi basato su token stateless, i cicli di vita degli utenti e l'invio automatizzato di comunicazioni di sistema.

## Funzionalità Chiave

* **Autenticazione Stateless**: Architettura di sicurezza basata su token JWT con filtro di intercettazione delle richieste HTTP e gestione dei ruoli.
* **Flusso di Verifica Account**: Logica transazionale per l'attivazione dei profili utente tramite token univoci inviati via email.
* **Persistenza Relazionale**: Modellazione dell'alberatura dei progetti e dei dati utente tramite database PostgreSQL e Hibernate/JPA.
* **Validazione e Gestione Errori**: Architettura centralizzata per l'intercettazione delle eccezioni di runtime e la restituzione di payload di errore consistenti.

## Stack Tecnologico

* Java 21
* Spring Boot 3
* Spring Security (JWT Auth)
* PostgreSQL
* Hibernate / JPA
* JavaMailSender

## Configurazione Variabili d'Ambiente

Le seguenti chiavi devono essere configurate nel sistema host o definite all'interno del file `application.properties`:

```properties
DB_URL=jdbc:postgresql://localhost:5432/flowframe
DB_USERNAME=il_tuo_username
DB_PASSWORD=la_tua_password
JWT_SECRET=la_tua_chiave_segreta_jwt
MAIL_USERNAME=la_tua_email_smtp
MAIL_PASSWORD=la_tua_password_smtp
FRONTEND_URL=http://localhost:5173