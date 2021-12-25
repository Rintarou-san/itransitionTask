package com.mycompany.gameproject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class KeyGenerator {

    private String key;
    private String hmac;

    public KeyGenerator() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        this.key = DatatypeConverter.printHexBinary(bytes);
    }

    public String getKey() {
        return key;
    }

    public String getHMAC(String move, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        this.hmac = DatatypeConverter.printHexBinary(mac.doFinal(move.getBytes()));
        return hmac;
    }
}
