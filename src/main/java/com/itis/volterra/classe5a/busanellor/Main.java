package com.itis.volterra.classe5a.busanellor;
import java.io.*;
import java.math.BigInteger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.net.URISyntaxException;
import java.util.*;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws URISyntaxException {

        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);

        // key pub (n, e)
        // key pri (n, d)

        int choice = cryptOrEncrypt();

        String message;

        try{
            if (choice == 1) {

                BigInteger p = RSA.randomPrime();
                BigInteger q;

                do {
                    q = RSA.randomPrime();
                } while (q.equals(p));

                logger.debug(p + " " + q);

                BigInteger n = RSA.module(p, q);
                logger.debug(n);

                BigInteger phi = RSA.eulerPhi(p, q);
                logger.debug(phi);

                BigInteger e = RSA.encryptionExp(phi);
                logger.debug(e);

                BigInteger d = RSA.decryptionExp(phi, e);
                logger.debug(d);

                message = RSA.encrypt(RSA.fileReadEncryption(RSA.getFileFromResource("plainMessage.txt")), e, n);

                logger.info("Encrypted message: " + message);

                RSA.fileWriteEncryption(new File("output.txt"), message, d, n);

            } else {
                ArrayList<String> file = RSA.fileReadDecryption(RSA.getFileFromResource("encryptedMessage.txt"));
                Scanner sc = new Scanner(file.getFirst());

                BigInteger d = sc.nextBigInteger();
                BigInteger n = sc.nextBigInteger();
                sc.close();
                String m = "";
                for (int i = 1; i < file.size(); i++) {
                    m += file.get(i) + " -1 ";
                }

                message = RSA.decrypt(m, d, n);
                RSA.fileWriteDecryption(new File("output.txt"), message);
            }
        } catch (IOException ex) {
            logger.error("Something went wrong with the file");
            throw new RuntimeException(ex);
        }

    }

    static int cryptOrEncrypt() {
        Scanner sc = new Scanner(System.in);
        logger.info("Choose to encrypt or decrypt");
        logger.info("1) Encrypt");
        logger.info("2) Decrypt");

        int choice;

        do{
            choice = sc.nextInt();
            if(choice != 1 && choice != 2) logger.warn("Choose a correct value");
        } while (choice < 1 || choice > 2);

        sc.close();
        return choice;
    }

}
