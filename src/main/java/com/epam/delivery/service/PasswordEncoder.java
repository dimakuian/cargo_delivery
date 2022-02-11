package com.epam.delivery.service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordEncoder {
    private PasswordEncoder() {
    }

        public static String getHash(String psw) {
        if (psw == null) return "";
        byte[] bytesEncoded;
        MessageDigest md5;
        StringBuffer hexString = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(psw.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = md5.digest();
            for (Byte b : bytesEncoded) {
                hexString.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

}