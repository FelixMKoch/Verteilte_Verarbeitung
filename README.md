# Verteilte Verarbitung Aufträge (4. Semester)

## Übung 02 : Rest API

### Aufgabe:
Die zweite Übung bestand daraus, eine Rest API Schnittstelle zu erstellen, welche eine Art Smart-Home realisieren soll.
Hierbei sollte nicht nur die eigene API erstellt werden, sondern auch Informationen von
externen APIs erhalten werden. <br/>
Die eigene Smart-Home Steuerung besteht dabei aus mehreren Teilen:
 - SamrtHomeService: Verwaltet Sensordaten und steuert AKtoren mithilfe von Regeln
 - Sensoren, welche in Zimmern verbaut sind und Daten an den SmartHomeService senden
 - Aktoren, die vom SmartHomeService gesteuert werden, und Aktionen in der umgebung auslösen
 - Rules, welche die Steuerung von dem SmartHomeService beeinflussen
 - Informationen, welche von einer externen API empfangen werden

### Voraussetzungen:
 - Die SMartHomeService API Schnittstelle muss laufen
 - Es können Aktoren, Rules und Sensoren, sowie Sensor-Daten mit GET/POST/PUT/DELETE Requests gesteuert werden
 - In einem bestimmten Zeitintervall werden Rules überprüft, und Aktoren dementsprechend manipuliert
 - Das Ganze soll über Docker-Container bzw. mithilfe einer Docker-Compose Datei laufen

### Setup
``` bash
git clone "https://inf-git.fh-rosenheim.de/vv-inf-sose21/kochfelix.git"
```
Das Projekt besteht hierbei aus mehreren Bestandteilen, welche unterschiedliche Environment-Variables haben:
##### SmartHomeService:
##### Aktor:
- SmartHomeServiceRegistrationURL (default: "http://localhost:8080/aktors/")

##### Sensor:
 - SmartHomeServiceRegistrationURL (default: "http://localhost:8080/sensors/")
 - SmartHomeServicePublishURL (default: "http://localhost:8080/sensors/")
 - SensorId (default: "2")

### Sonstiges:
Zudem sollten noch ein Aktor und ein Sensor von der Th-Rosenheim mithilfe der Docker-Compose Datei mit eingebunden werden. <br/>
Diese liefern auch in einem bestimmten Zeitintervall Daten und senden diese an den SmartHomeService.

### Technisches:
Folgende Technologien wurden benutzt:
 - Java (jdk-11)
 - Gradle
 - Spring Boot Framework
 - Docker
 - Microsoft SQL Datenbank
 
### Docker:
Beim starten der Docker Container laufen aktuell folgende Sachen auf folgenden Ports:
 - SmarHomeService: 8001
 - SQL-Server: 1422
 - Aktor-Service: 3456
 - Sensor-Service: 3457
 - DemoAktor: 9212/4502
 - DemoSensor: 9211/4501

Nachdem man Docker installiert hat: <br/>
``` bash
docker run inf-docker.fh-rosenheim.de/vv-inf-sose21/kochfelix
```


----------------------------------------------
## Übung 01 : Reaktives System

### Aufgabe:
Die Aufgabe war es ein Reaktives System zu entwickeln was einer Messstation ähneln soll.
Dieses Programm muss mithilfe von Sockets nach außen eine Verbindung zu anderen Sockets etablieren,
die gesendeten Daten einlesen, und schlussendlich die erhaltenen Messdaten in eine JSON-Datei schreiben.

### Voraussetzungen:
1. Jeder Socket nach außen soll eine eigener Thread sein
2. Das schreiben in die JSON-File wird auch über einen eigenen Thread geregelt
3. Wichtige Parameter wie Port oder Name der Logdatei sollen per Umgebungsvariablen übergeben werden
4. Das alles soll in der Station per Automat geregelt sein

### Setup
``` bash
git clone "https://inf-git.fh-rosenheim.de/vv-inf-sose21/kochfelix.git"
```
Der Server muss dann gestartet werden (SensorStart.java).
Aber davor noch müssen bestimmte Umgebungsvariablen gesetzt werden:
- PORT  (Standard: 1024 wenn nicht gesetzt)
- LOG_LEVEL (-> INFO oder SEVERE)
- LOG_FILE (-> Ort, wo das Log-File gespeichert werden soll)
- JSON_FILE (-> Ort, an dem das Json-File gespeichert werden soll)

#### Measurement - JSON
Das Measurement Json, welches an den Server gesendet werden sollte, soll so aussehen:
``` json
{
 "value": 42,
 "unit": "celsius",
 "type": "temperature",
 "timestamp": "2021-04-19T17:39:03.577474300"
}
```

#### Docker - Container
Das Projekt kann auch über einen Docker-Container lokal zum laufen gebracht werden.
Nachdem man Docker installiert hat:
``` bash
docker run inf-docker.fh-rosenheim.de/vv-inf-sose21/kochfelix
```
Der Container dürfte jetzt laufen (Standardmässig auf Port 1024).