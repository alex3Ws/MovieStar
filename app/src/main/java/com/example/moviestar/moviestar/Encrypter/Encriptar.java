package com.example.moviestar.moviestar.Encrypter;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class Encriptar {

    private String encoded = null;
    private byte[] encrypted = null;
    private String texto;
    private String publicKey="-----BEGIN PUBLIC KEY-----" +
            "MIIBITANBgkqhkiG9w0BAQEFAAOCAQ4AMIIBCQKCAQB8QdMyfPa3swMndiZ3i+oe" +
            "31y6GSviH+gmzh671KTAgiv9Ac4bXoGIVcI3dmK35+s5nLlPdZn2tbrCPqwEYMbS" +
            "1TmiYdU958JNQ6QQ/BRZmyOCtU/aAzvPiClvlqnLQBGbuhaZx/WZJQyU1xwXDiEV" +
            "lFFmv/UlWCcDh8kLgsyiR7MPJT7u+rCgSoxHGe1ImQPPgh2UiSa+VIqaagj4II20" +
            "2c9TQLVCxBoeYDCqz49IjM+T+4xw9rOhke0ScvtIOP7DedsvNLdptSALVw4uf0V3" +
            "2ALoqwP3Trl5kCyKVzd9DVJqGjjJqtOBob+ryI+2DX9QIMfhq3icP2dT3IYM/wnX" +
            "AgMBAAE=" +
            "-----END PUBLIC KEY-----";


    public Encriptar(){

    }

    public Encriptar(String texto){

        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String encriptar(){

        try {

            byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubKey = kf.generatePublic(spec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            encrypted = cipher.doFinal(texto.getBytes());
            encoded = Base64.encodeToString(encrypted, Base64.DEFAULT);

            return  encoded;
        }
        catch (Exception e) {
            e.printStackTrace();
            return(null);
        }

    }

}
