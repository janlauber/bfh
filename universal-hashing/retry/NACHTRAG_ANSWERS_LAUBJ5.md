# Nachtrag Universal Hashing
Beim Nachtrag der Universal Hash Function wurde folgender Pseudo Code implementiert:
```python
uint hash(String x, int a, int p)
	uint h = INITIAL_VALUE
	for (uint i=0 ; i < x.length ; ++i)
		h = ((h*a) + x[i]) mod p
	return h
```
*Quelle: https://en.wikipedia.org/wiki/Universal_hashing#Hashing_strings*

Die Implementation ist im File [implementation3.java](./implementation3.java) zu finden.

## Ausführen in der Commandline
1. Kompilieren
```bash
javac implementation3.java
```

2. Ausführen
```bash
java implementation3
```