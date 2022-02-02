package com.ssafy.challympic.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
    private String result;

    public MD5Generator(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] md5Hash = md5.digest();
        StringBuilder sb = new StringBuilder();

        for(byte b : md5Hash){
            String hexString = String.format("%02x", b);
            sb.append(hexString);

        }
        result = sb.toString();
    }

    public String toString(){
        return result;
    }

}
