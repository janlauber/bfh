# bfh-rsa
Implementation of the RSA algorithm for encrypting and decrypting.  
This exercise is part of the [Project & Training 2 Block 2](https://github.com/janlauber/bfh/tree/main/sem3/project%20and%20training%202/block2) mathematics topic.  

| **Variable** | **Value** |
| ------------- | ------------- |
| `p_1` | `6451` |
| `q_1` | `13367` |
| `e` | `7` |
| `p_2` | `7879` |
| `q_2` | `11587` |
| `e` | `5` |
| `n` | `94729993` |
| `e` | `11` *(didn't work) ->* `5` |
| `m_1` | `with the novel` |
| `m_2` | `3680043    82411494    52490519     3680043    11293606    82411494     5802786    9936269` |
| `m_3` | `68088123    41089920    61640887    68088123    33554432     3638134    41851974   39858633    10131853    68996563    36901199    10131853    76136512` |

## Execution
```bash
# change to src directory, where you find the RSA.java file
cd src
# compile the java file
javac RSA.java
# run the java file
java RSA
```

## Task b)
- `(hash' = hash^d mod n)` Der Sender erzeugt den Hashwert h der Nachricht (bevor die Blöcke gebildet werden) und kann nun diesen Hashwert mit seinem eigenen privaten Schlüssel signieren.  
- `(c = m^e mod n)` Dann wird die Nachricht in Blöcke aufgeteilt und verschlüsselt. Der öffentliche Schlüssel vom Empfänger wird zur Verschlüsselung verwendet.
- Der Sender sendet nun das geordnete Paar bestehend aus Hashwert und verschlüsselter Nachricht (h', c) an den Empfänger.
- `(c^d mod n = m)` Der Empfänger entschlüsselt die Nachricht mit seinem eigenen privaten Schlüssel und berechnet erneut den Hashwert der Klartextnachricht.
- `(hash'^e mod n = hash)` Mit dem öffentlichen Schlüssel von A kann B aus dem signierten Hashwert h' wieder h berechnen. Sind diese beiden Werte identisch, kann mit hoher Wahrscheinlichkeit davon ausgegangen werden, dass die Nachricht fehlerfrei übermittelt wurde und nicht gefälscht ist.  

## Task e)
Derzeit sind einige hundert Dezimalstellen (mindestens *2048-bit*) für die Wahl einer Primzahlen erforderlich, um RSA sicher zu verwenden, aber die Leistung nimmt mit der Grösse des öffentlichen Schlüssels ab. 
Für eine dem symmetrischen Verfahren ähnliche Sicherheitsstufe benötigt RSA einen längeren Schlüssel, sodass er nicht zum Austausch grosser Datenmengen verwendet wird. 
Gegenüber der symmetrischen Verschlüsselung hat RSA jedoch einen **Vorteil**: Mit dem RSA-Verfahren können Nachrichten nicht nur verschlüsselt, sondern auch **signiert** werden. Damit kann mit RSA die Authentizität des Absenders überprüft werden.
Daher eignet sich RSA besonders für die **Authentifizierung** und den **Schlüsselaustausch** und wird häufig bei symmetrischer Verschlüsselung verwendet. Generiert man dazu zunächst einen symmetrischen Sitzungsschlüssel (z. B. AES) und verwenden dann RSA, um die Übertragung zu verschlüsseln. Nach dem Schlüsselaustausch wird die Kommunikation über diesen symmetrischen Schlüssel fortgesetzt.
Der **Nachteil** ist, dass RSA deutlich **langsamer** ist als symmetrische Verfahren wie AES. Ausserdem ist es theoretisch knackbar, und nur eine ausreichend grosse Schlüssellänge kann eine Berechnung des Schlüssels verhindern.
Im Vergleich zu RSA erfordert der symmetrische AES-Algorithmus deutlich weniger Rechenaufwand für die Ver- und Entschlüsselung, sodass er auch für grössere Daten Übertragungen auf schwächeren Endgeräten geeignet ist. Die Sicherheit von AES steigt exponentiell mit der Länge des Schlüssels und kann nicht per Brute-Force-Angriffe geknackt werden.
RSA wird unter anderem in den Protokollen HTTPS, TLS, GPG und X.509-Zertifikate verwendet.