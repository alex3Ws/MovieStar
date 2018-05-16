package com.example.moviestar.moviestar.Encrypter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;


import android.util.Base64;
import android.util.Log;

public class RSAEncrypt {





        private String publicKey;

        public RSAEncrypt(String clave) {

            this.publicKey = clave;

        }


        /*
         * Function to encrypt the data.
         *
         */

        public String encrypt(String data) throws Exception {


            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding", "BC");
            byte[] keyBytes = Base64.decode(this.publicKey, 0);

            PublicKey publickey = strToPublicKey(new String(keyBytes));
            cipher.init(Cipher.ENCRYPT_MODE, publickey);
// Base 64 encode the encrypted data
            byte[] encryptedBytes = Base64.encode(cipher.doFinal(data.getBytes()), 0);

            return new String(encryptedBytes);


        }


        public static PublicKey strToPublicKey(String clave) {

            PublicKey pbKey = null;

            try {
                clave = clave.replace("-","").replace("RSA","").replace("\r\n","").replace("END","").replace("BEGIN","").replace("PUBLIC","").replace("KEY","").replace(" ","");

                byte[] decodedKey = Base64.decode(clave,Base64.DEFAULT);
                X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

                KeyFactory keyFactory = null;
                keyFactory = KeyFactory.getInstance("RSA");

                pbKey = keyFactory.generatePublic(x509);


                return pbKey;

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return pbKey;
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                return pbKey;
            }

        }

}

