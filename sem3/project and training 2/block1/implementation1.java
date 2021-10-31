import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        // String to hash
        String testString = "DiesIstEinTest";
        // Generate m hash functions out of the universal hash family
        int m = 5000;
        // Define a prime number, should be bigger than the count of keys in your HashTable
        int p = 1010111;

        UniversalHash universalHash1 = new UniversalHash(testString, m, 1010111);
        System.out.println();
        System.out.println("Universal Hash: " + universalHash1.hashCode());
        System.out.println("Universal Hash (same as above): " + universalHash1.hashCode());

        // You can call defineHashFunction again, if you got an collision in your HashTable
        universalHash1.defineHashFunction();
        System.out.println("New Hash Function selected: " + universalHash1.hashCode());
    }
}

interface hashFunction {
    int hashFunc(String s,int p,int m);
}

class UniversalHash {
    private ArrayList<hashFunction> hashFunctions = new ArrayList<>();
    private hashFunction hashF;
    private String string;
    // prime number of the next bit modulo calculation
    private int prime = 1;
    // next prime number of the size of the HashTable
    private int m;

    private int nextPrime(int num) {
        num++;
        for (int i = 2; i < num; i++) {
            if(num%i == 0) {
                num++;
                i=2;
            }
        }
        return num;
    }

    @Override
    public int hashCode() {
        return hashF.hashFunc(string, prime, m);
    }


    public void defineHashFunction() {
        Random rand = new Random();
        hashF = hashFunctions.get(rand.nextInt(hashFunctions.size()));
    }

    private int defineA(int prime) {
        return ThreadLocalRandom.current().nextInt(1, prime);
    }
    private int defineB(int prime) {
        return ThreadLocalRandom.current().nextInt(0, prime);
    }

    public UniversalHash(String string, int m) {

        this.string = string;

        this.m = m;

        int prime = 31;
        this.prime = prime;

        createHashFunctions(m, prime);

        defineHashFunction();
    }

    public UniversalHash(String string, int m, int p) {
        this.string = string;
        this.m = nextPrime(m);
        this.prime = p;

        createHashFunctions(m, p);

        defineHashFunction();
    }

    private void createHashFunctions(int m3, int prm) {
        for (int i = 0; i < m3; i++) {
            // create as much hashFunctions as defined in m
            int a = defineA(prm);
//            System.out.println("A["+i+"]: " + a);
            int b = defineB(prm);
//            System.out.println("B["+i+"]: " + b);
            hashFunction hashFunc = (String s, int p, int m2) -> {
                char[] chars = s.toCharArray();
                // Universal hash family of Carter and Wegman
                int hash = 0;
//                int counter = 0;
                for (char character : chars) {
                    hash += ((a*character + b) % p) % m2;
                    // For Debugging:
//                    System.out.println("Function ["+counter+"]: (("+a+"*"+character+" + "+b+") % "+p+") % "+m2);
//                    counter++;
                }

                return hash;
            };
            hashFunctions.add(hashFunc);
        }
    }

    public hashFunction getHashF() {
        return hashF;
    }

    public int getPrime() {
        return prime;
    }

    public int getM() {
        return m;
    }
}