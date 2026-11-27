package com.example.stegnography.service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class EncryptionService {

    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);

        return  keyGenerator.generateKey() ;
    }
}
