import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class RSA {
    public static void main(String[] args) {

        /***************************************************************************************
         * Exercise a)
         **************************************************************************************/
        System.out.println("==============================");
        System.out.println("Exercise a)");
        System.out.println("==============================");
        int p_1 = 6451;
        System.out.println("p: " + p_1);
        int q_1 = 13367;
        System.out.println("q: " + q_1);
        int e_1 = 7;
        System.out.println("e: " + e_1);
        String m_1 = "with the novel";

        System.out.println("Encrypting: " + m_1);
        // get encrypted message of m_1
        String[] encryptedMessage = encryptString(m_1, p_1, q_1, e_1);
        // Print the encrypted message without the brackets and commas
        System.out.println("Encrypted message: " + Arrays.toString(encryptedMessage).replace("[", "").replace("]", "").replace(",", ""));
        System.out.println("\n");

        /***************************************************************************************
         * Exercise b)
         **************************************************************************************/
        System.out.println("==============================");
        System.out.println("Exercise b)");
        System.out.println("==============================");
        System.out.println("Check out the README.md file for the solution to this exercise.");
        System.out.println("\n");

        /***************************************************************************************
         * Exercise c)
         **************************************************************************************/
        System.out.println("==============================");
        System.out.println("Exercise c)");
        System.out.println("==============================");
        BigInteger p_2 = BigInteger.valueOf(7879);
        System.out.println("p: " + p_2);
        BigInteger q_2 = BigInteger.valueOf(11587);
        System.out.println("q: " + q_2);
        BigInteger e_2 = BigInteger.valueOf(5);
        System.out.println("e: " + e_2);
        // Convert the message to an array of longs
        long[] m_2 = {3680043, 82411494, 52490519, 3680043, 11293606, 82411494, 5802786, 9936269};
        // Convert the m_2 to an array of BigIntegers
        BigInteger[] m_2_big = new BigInteger[m_2.length];
        for (int i = 0; i < m_2.length; i++) {
            m_2_big[i] = BigInteger.valueOf(m_2[i]);
        }

        // print the encrypted message without the brackets and commas
        printRSAKeys(m_2_big);
        // get the decrypted message of m_2_big
        String decryptedMessage = decryptString(m_2_big, p_2, q_2, e_2);
        System.out.println("Decrypted message: " + decryptedMessage);
        System.out.println("\n");


        /***************************************************************************************
         * Exercise d)
         **************************************************************************************/
        System.out.println("==============================");
        System.out.println("Exercise d)");
        System.out.println("==============================");
        // Public key
        BigInteger n_3 = BigInteger.valueOf(94729993);
        BigInteger p_3 = null;
        BigInteger q_3 = null;
        BigInteger e_3 = BigInteger.valueOf(5);
        // Encrypted message
        long[] m_3 = {68088123, 41089920, 61640887, 68088123, 33554432, 3638134, 41851974, 39858633, 10131853, 68996563, 36901199, 10131853, 76136512};
        // Convert the m_2 to an array of BigIntegers
        BigInteger[] m_3_big = new BigInteger[m_3.length];
        for (int i = 0; i < m_3.length; i++) {
            m_3_big[i] = BigInteger.valueOf(m_3[i]);
        }

        BigInteger[] crackedValues = crackPublicKey(n_3);
        p_3 = crackedValues[0];
        q_3 = crackedValues[1];

        System.out.println("found p: " + p_3);
        System.out.println("found q: " + q_3);
        // print the encrypted message without the brackets and commas
        printRSAKeys(m_3_big);
        // get the decrypted message of m_3_big
        String decryptedMessage_3 = decryptString(m_3_big, p_3, q_3, e_3);
        System.out.println("Decrypted message: " + decryptedMessage_3);
        System.out.println("\n");


        /***************************************************************************************
         * Exercise e)
         **************************************************************************************/
        System.out.println("==============================");
        System.out.println("Exercise e)");
        System.out.println("==============================");
        System.out.println("Check out the README.md file for the solution to this exercise.");

    }

    public static String[] encryptString(String m_1, int p_1, int q_1, int e) {
        // calculate n
        long n_1 = p_1 * q_1;
        // Convert the message to an array of longs
        long[] m = new long[m_1.length()];
        // Convert the message to an array of longs
        for (int i = 0; i < m_1.length(); i++) {
            m[i] = m_1.charAt(i);
        }

        // Loop through each character in the message with e and n
        // Basically, we are encrypting each character in the message
        for (int i = 0; i < m.length; i++) {
            m[i] = (long) (Math.pow(m[i], e) % n_1);
        }

        // return the encrypted message
        return Arrays.toString(m).split(", ");
    }


    public static String decryptString(BigInteger[] m, BigInteger p, BigInteger q, BigInteger e) {
        // calculate n
        BigInteger n = p.multiply(q);
        // calculate phi
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        // calculate e
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }
        // calculate d
        BigInteger d = e.modInverse(phi);
        String decryptedMessage = "";
        // Loop through each character in the message
        for (int i = 0; i < m.length; i++) {
            // decrypt the character
            m[i] = m[i].modPow(d, n);
            // convert the decrypted character to a char
            decryptedMessage += (char) m[i].intValue();
        }
        // return the decrypted message
        return decryptedMessage;
    }

    public static BigInteger[] crackPublicKey(BigInteger n) {
        BigInteger[] crackedValues = new BigInteger[2];
        // calculate sqrt(n)
        int squareRoot = (int) n.sqrt().intValue();
        // loop through all possible values of sqrt(n) + 1...
        for (int i = squareRoot; i < n.intValue(); i++) {
            // calculate i^2 - n for each value of i
            int j = i*i-n.intValue();
            // if i^2 - n is a perfect square, then we have found a possible p
            if (isSquare(j)) {
                int u = i;
                int v = (int) Math.sqrt(i*i-n.intValue());
                System.out.println("u: " + u);
                System.out.println("v: " + v);

                /***************************************************************************************
                 * Calculate p and q with the cramer's rule
                 * https://en.wikipedia.org/wiki/Cramer's_rule
                 * *************************************************************************************/

                // the denominator of x2 and y2 is the same -> -0.5, so lets fix them
                // using BigDecimal to avoid rounding errors
                BigDecimal determinantX2 = BigDecimal.valueOf(-0.5);
                BigDecimal determinantY2 = BigDecimal.valueOf(-0.5);

                // calculate the determinant of x1
                BigDecimal determinantX1 = BigDecimal.valueOf(u).multiply(BigDecimal.valueOf(-0.5)).subtract(BigDecimal.valueOf(v).multiply(BigDecimal.valueOf(0.5)));
                // calculate the determinant of y1
                int p_cracked = determinantX1.divide(determinantX2).intValue();

                // calculate the determinant of y1
                BigDecimal determinantY1 = BigDecimal.valueOf(0.5).multiply(BigDecimal.valueOf(v)).subtract(BigDecimal.valueOf(u).multiply(BigDecimal.valueOf(0.5)));
                // calculate q
                int q_cracked = determinantY1.divide(determinantY2).intValue();

                // add the cracked values to the array
                crackedValues[0] = BigInteger.valueOf(p_cracked);
                crackedValues[1] = BigInteger.valueOf(q_cracked);
                break;
            }
        }
        // return the cracked values
        return crackedValues;
    }

    public static boolean isSquare(double n) {
        // calculate the square root of n
        double sqrt = Math.sqrt(n);
        // if the square root is an integer, then n is a perfect square
        return ((sqrt - Math.floor(sqrt)) == 0);
    }

    public static void printRSAKeys(BigInteger[] keys) {
        // print the public key in a readable format
        System.out.println("Encrypted message: " + Arrays.toString(keys).replace("[", "").replace("]", "").replace(",", ""));
    }
}
