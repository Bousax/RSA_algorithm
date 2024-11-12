package com.itis.volterra.classe5a.busanellor;
import java.io.*;
import java.math.BigInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class RSA {

    private static final Logger logger = LogManager.getLogger(RSA.class);

    static BigInteger randomPrime() {
        Random random = new Random();
        return BigInteger.probablePrime(Long.BYTES*8, random);
    }

    static BigInteger module(BigInteger a, BigInteger b){
        return a.multiply(b);
    }

    static BigInteger eulerPhi(BigInteger a, BigInteger b){
        return a.subtract(BigInteger.ONE).multiply(b.subtract(BigInteger.ONE));
    }

    static BigInteger encryptionExp(BigInteger phi){
        BigInteger e;
        Random random = new Random();
        do {
            e = BigInteger.probablePrime(16, random);
        } while (!e.gcd(phi).equals(BigInteger.ONE));
        logger.debug("e: " + e);
        return e;
    }

    static BigInteger decryptionExp(BigInteger phi, BigInteger e){
        return e.modInverse(phi);
    }

    static File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            logger.error("File " + fileName + " not found, try again");
            throw new IllegalArgumentException();
        } else {
            return new File(resource.toURI());
        }
    }

    static String encrypt(ArrayList<String> lines, BigInteger e, BigInteger n) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (String line : lines) {
            for (char ch : line.toCharArray()) {
                BigInteger m = BigInteger.valueOf((int) ch);
                BigInteger c = m.modPow(e, n);
                encryptedMessage.append(c).append(" ");
            }
            encryptedMessage.append("\n");
        }
        return encryptedMessage.toString();
    }

    static String decrypt(String encryptedMessage, BigInteger d, BigInteger n) {
        StringBuilder decryptedMessage = new StringBuilder();
        Scanner scanner = new Scanner(encryptedMessage);

        while (scanner.hasNextBigInteger()) {
            BigInteger c = scanner.nextBigInteger();
            if(!c.equals(BigInteger.valueOf(-1))){
                BigInteger m = c.modPow(d, n);
                decryptedMessage.append((char) m.intValue());
            } else {
                decryptedMessage.append("\n");
            }
        }
        scanner.close();
        return decryptedMessage.toString();
    }

    static ArrayList<String> fileReadEncryption(File f) throws IOException {
        FileReader fr = new FileReader(f);
        Scanner sc = new Scanner(fr);
        ArrayList<String> s = new ArrayList<String>();
        while(sc.hasNextLine()){
            s.add(sc.nextLine());
            logger.debug(s) ;
        }
        fr.close();
        return s;
    }

    static ArrayList<String> fileReadDecryption(File f) throws IOException {
        FileReader fr = new FileReader(f);
        Scanner sc = new Scanner(fr);
        ArrayList<String> s = new ArrayList<String>();
        while(sc.hasNextLine()){
            s.add(sc.nextLine());
            logger.debug(s);
        }
        fr.close();
        return s;
    }

    static void fileWriteEncryption(File f, String s, BigInteger d, BigInteger n) throws IOException{
        FileWriter fw = new FileWriter(f);
        PrintWriter pr = new PrintWriter(fw);
        pr.println(d + " " + n);
        pr.println(s);
        pr.close();
        fw.close();
    }

    static void fileWriteDecryption(File f, String s) throws IOException{
        FileWriter fw = new FileWriter(f);
        PrintWriter pr = new PrintWriter(fw);
        pr.println(s);
        pr.close();
        fw.close();
    }

}