/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha256;

import static java.lang.Math.pow;
import java.math.BigInteger;

/**
 *
 * @author anhkma
 */
public class SHA256 {

    private static BigInteger rotrate_right(BigInteger a, int n) {
        return (a.shiftRight(n).or(a.shiftLeft(32 - n))).and(new BigInteger("ffffffff", 16));
    }

    private static BigInteger shift_right(BigInteger a, int n) {
        return a.shiftRight(n);
    }

    private static BigInteger ch(BigInteger e, BigInteger f, BigInteger g) {
        return (e.and(f)).xor(e.not().and(g));
    }

    private static BigInteger maj(BigInteger a, BigInteger b, BigInteger c) {
        return (a.and(b)).xor(a.and(c)).xor(b.and(c));
    }

    private static BigInteger xichma0(BigInteger a) {
        return rotrate_right(a, 2).xor(rotrate_right(a, 13)).xor(rotrate_right(a, 22));
    }

    private static BigInteger xichma1(BigInteger e) {
        return rotrate_right(e, 6).xor(rotrate_right(e, 11)).xor(rotrate_right(e, 25));
    }

    private static BigInteger gama0(BigInteger a) {
        return rotrate_right(a, 7).xor(rotrate_right(a, 18)).xor(shift_right(a, 3));
    }

    private static BigInteger gama1(BigInteger a) {
        return rotrate_right(a, 17).xor(rotrate_right(a, 19)).xor(shift_right(a, 10));
    }

    public static String SHA256(String message) {
        String H[] = {"6a09e667", "bb67ae85", "3c6ef372", "a54ff53a", "510e527f", "9b05688c", "1f83d9ab", "5be0cd19"};
        String K[] = {"428a2f98", "71374491", "b5c0fbcf", "e9b5dba5", "3956c25b", "59f111f1", "923f82a4", "ab1c5ed5",
            "d807aa98", "12835b01", "243185be", "550c7dc3", "72be5d74", "80deb1fe", "9bdc06a7", "c19bf174",
            "e49b69c1", "efbe4786", "0fc19dc6", "240ca1cc", "2de92c6f", "4a7484aa", "5cb0a9dc", "76f988da",
            "983e5152", "a831c66d", "b00327c8", "bf597fc7", "c6e00bf3", "d5a79147", "06ca6351", "14292967",
            "27b70a85", "2e1b2138", "4d2c6dfc", "53380d13", "650a7354", "766a0abb", "81c2c92e", "92722c85",
            "a2bfe8a1", "a81a664b", "c24b8b70", "c76c51a3", "d192e819", "d6990624", "f40e3585", "106aa070",
            "19a4c116", "1e376c08", "2748774c", "34b0bcb5", "391c0cb3", "4ed8aa4a", "5b9cca4f", "682e6ff3",
            "748f82ee", "78a5636f", "84c87814", "8cc70208", "90befffa", "a4506ceb", "bef9a3f7", "c67178f2"};
        String text = padding.Message_pad(message);
        System.out.println(text);
        int N = text.length() / 512;
        String[][] M = new String[100][100];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 16; j++) {
                M[i][j] = text.substring(i * 512 + j * 32, i * 512 + (j + 1) * 32);
                BigInteger chuyen = new BigInteger(M[i][j], 2);
                M[i][j] = chuyen.toString(16);
            }
        }
        String[] W = new String[65];
        for (int i = 0; i < N; i++) {
            for (int t = 0; t < 16; t++) {
                W[t] = M[i][t];
            }
            for (int t = 16; t < 64; t++) {
                BigInteger tg = new BigInteger(W[t - 15], 16);
                BigInteger tg2 = new BigInteger(W[t - 16], 16);
                BigInteger tg3 = new BigInteger(W[t - 2], 16);
                BigInteger tg4 = new BigInteger(W[t - 7], 16);

                W[t] = sum(new BigInteger(sum(gama0(tg), tg2), 16), new BigInteger(sum(gama1(tg3), tg4), 16));
            }

            BigInteger a = convert(H[0]);
            BigInteger b = convert(H[1]);
            BigInteger c = convert(H[2]);
            BigInteger d = convert(H[3]);
            BigInteger e = convert(H[4]);
            BigInteger f = convert(H[5]);
            BigInteger g = convert(H[6]);
            BigInteger h = convert(H[7]);
            System.out.println(a);
            for (int t = 0; t < 64; t++) {
                BigInteger k_big = new BigInteger(K[t], 16);
                BigInteger w_big = new BigInteger(W[t], 16);
                String t1 = sum(new BigInteger(sum(ch(e, f, g), xichma1(e)), 16), new BigInteger(sum(new BigInteger(sum(h, k_big), 16), w_big), 16));
                String t2 = sum(xichma0(a), maj(a, b, c));
                System.out.println(xichma0(a).toString(16));
                System.out.println(maj(a, b, c).toString(16));
                h = g;
                g = f;
                f = e;
                String e_text = sum(new BigInteger(t1, 16), d);
                e = new BigInteger(e_text, 16);
                d = c;
                c = b;
                b = a;
                String a_text = sum(new BigInteger(t1, 16), new BigInteger(t2, 16));
                a = new BigInteger(a_text, 16);
                System.out.println(K[t]);
                System.out.println(W[t]);
                System.out.println("T=" + t);
                System.out.println(a.toString(16));
                System.out.println(b.toString(16));
                System.out.println(c.toString(16));
                System.out.println(d.toString(16));
                System.out.println(e.toString(16));
                System.out.println(f.toString(16));
                System.out.println(g.toString(16));
                System.out.println(h.toString(16));
            }
            H[0] = sum(a, new BigInteger(H[0], 16));
            H[1] = sum(b, new BigInteger(H[1], 16));
            H[2] = sum(c, new BigInteger(H[2], 16));
            H[3] = sum(d, new BigInteger(H[3], 16));
            H[4] = sum(e, new BigInteger(H[4], 16));
            H[5] = sum(f, new BigInteger(H[5], 16));
            H[6] = sum(g, new BigInteger(H[6], 16));
            H[7] = sum(h, new BigInteger(H[7], 16));

        }
        String hash = "";
        for (int i = 0; i < 8; i++) {

            while (H[i].length() < 8) {
                H[i] = "0" + H[i];
            }
            hash = hash + H[i];
        }

        return hash;
    }

    private static String sum(BigInteger a, BigInteger n) {
        String text = "";
        BigInteger kq = a.add(n).mod(new BigInteger("2").pow(32));
        text = kq.toString(16);
        return text;
    }

    private static BigInteger convert(String a) {
        return new BigInteger(a, 16);
    }

}
