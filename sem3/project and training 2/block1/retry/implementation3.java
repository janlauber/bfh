class UniversalHashing {
    private long a;
    private long b;
    // prime number
    private long p;


    public UniversalHashing(long a, long b, long p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }

    // universal hashing for strings
    public long hash(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * a + s.charAt(i)) % p;
        }
        return (hash + b) % p;
    }

    public long getA() {
        return a;
    }

    public long getB() {
        return b;
    }

    public long getP() {
        return p;
    }
}

public class implementation3 {
    // implement main method for testing
    public static void main(String[] args) {
        long p = 1000000007;
        // random a, b where a not 0 and a,b p-1
        long a = (int) (Math.random() * (p - 1) + 1);
        long b = (int) (Math.random() * (p - 1));
        UniversalHashing uh = new UniversalHashing(a, b, p);
        System.out.println("===== Values Set for the Universal Hashing test =====");
        System.out.println("A (randomly chosen, not 0, < p): " + uh.getA());
        System.out.println("B (randomly chosen, < p): " + uh.getB());
        System.out.println("P (prime number): " + uh.getP());
        System.out.print("\n");
        System.out.println("===== Testing Universal Hashing =====");
        System.out.println("hash(\"hello\") = " + uh.hash("hello"));
        System.out.println("hash(\"world\") = " + uh.hash("world"));
        System.out.println("hash(\"UniversalHashing\") = " + uh.hash("UniversalHashing"));
    }
}