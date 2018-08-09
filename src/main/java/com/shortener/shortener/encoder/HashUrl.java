package com.shortener.shortener.encoder;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class HashUrl {

    /*
    encodes the url id using base64
     */
    public static String base64Encode(String token) {
        return Base64.encode(token.getBytes());
    }

    /*
    decodes the url id using base64
     */
    public static String base64Decode(String token) {
        return new String(Base64.decode(token));
    }
}
