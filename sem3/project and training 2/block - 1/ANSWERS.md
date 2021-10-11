# Answers to Exercise 10

## a)
In der Dokumentation unter https://docs.oracle.com/javase/7/docs/api/ findet man zu java.lang.String folgende Berechung der Methode **hashCode()**:


`s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]`


Verwendet werden natürliche Zahlen.
Glossar:
- **s[i]** ist der i-te Character des Strings
- **n** ist die länge des Strings
- **^** bedeutet das die folgenden Zeichen als Exponent gerechnet werden
Der Hashwert von einem leeren String ist 0.


sources: 
- https://docs.oracle.com/javase/7/docs/api/
- https://www.geeksforgeeks.org/how-string-hashcode-value-is-calculated/

//TODO
- Was ist mit `"Describe an efficient way to comput this hash value efficiently?"` gemeint?

## b)
**Universal Hashing** beschreibt in der Informatik und Mathematik das zufällige Auswählen einer HashFunktion aus einer HashFunktions-Sammlung.\
Das garantiert eine sehr geringe Wahrscheinlichkeit einer Kollision.\
Universal Hashing hat viele Anwendungsbereiche in der Informatik, z.B. bei der Implementation von Hash Tables, zufälliger Algorithmen und in der Kryptographie.

//TODO
- Was meint man mit `Describe two different function sets`?

sources: 
- https://en.wikipedia.org/wiki/Universal_hashing
- https://www.youtube.com/watch?v=3cTTzYc3gnE

## c)

//TODO
- Müssen jetzt diese Zwei function sets implementiert werden oder reicht es eine universelle Implementation mit random erzeugter Hashfunction zu erzeugen?

```java
import java.util.Random;

class HashFunction {

    private final int randomPrime;
    private final int[] distinctNumbers;

    private static int randomPrimeNumber() {
        int num = 0;
        Random r = new Random();
        num = r.nextInt(1000) + 1;

        while (!isPrime(num)) {
            num = r.nextInt(1000) + 1;
        }
        return num;
    }

    private static boolean isPrime(int num) {
        if (num <=3 || num % 2 == 0) {
            return false;
        }
        int div = 3;
        while ((div <= Math.sqrt(num)) && (num % div != 0)) {
            div += 2;
        }
        return num % div != 0;
    }

    private static int[] randomIntegers(int primary) {
        int a = 0;
        int b = 0;
        int low = 1;
        int[] values = new int[2];

        Random r = new Random();

        a = r.nextInt(primary -low) + low;
        b = r.nextInt(primary -low) + low;

        while (a == b) {
            a = r.nextInt(primary -low) + low;
            b = r.nextInt(primary -low) + low;
        }

        values[0] = a;
        values[1] = b;

        return values;
    }

    public HashFunction() {
        this.randomPrime = randomPrimeNumber();
        this.distinctNumbers = randomIntegers(randomPrime);
    }

    public int hashValue(String value) {
        int hash = 0;
        int m = 2^30;
        int a = this.distinctNumbers[0];
        int b = this.distinctNumbers[1];

        for (char ch: value.toCharArray()) {
            int i = (int) ch;
            hash = (hash) + (((a*i + b)% this.randomPrime)*m); // ((a*i + b)% this.randomPrime)%m
        }

        return hash;
    }
}

class UniversalHashCode {

    private final HashFunction hashFunction = new HashFunction();


    private final int key;
    private final String value;


    public UniversalHashCode(String value) {
        this.key = hashFunction.hashValue(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

    public HashFunction getHashFunction() {
        return hashFunction;
    }
}

public class Main {
    public static void main(String[] args) {
        // Directly create a random hash function
        HashFunction hashFunction = new HashFunction();
        System.out.println("Random Hashfunction: " + hashFunction.hashValue("Test"));

        // Automatically create a universal hash code
        // The hash function which will be random selected/generated, will be stored in the UniversalHashCode Object
        UniversalHashCode universalHashCode = new UniversalHashCode("Test");
        System.out.println("Universal Hashing: " + universalHashCode.getKey());

        String normalHashString = "Test";
        System.out.println("Standard hashCode function: " + normalHashString.hashCode());
    }
}
```

sources: 
- https://www.chegg.com/homework-help/questions-and-answers/implement-following-universal-hash-function-java-universalhash-int-m-pick-random-prime-num-q19351954
- http://underpop.online.fr/j/java/help/universal-hash-function-for-string-keys.html.gz

## d)

