package ir.shayandaneshvar.services.security;

import java.util.Base64;

/**
 * used for Extreme Security
 */
public class Base64Algorithm {

    public String encode(String message) {
        Base64.Encoder encoder = Base64.getUrlEncoder();
        return encoder.encodeToString(message.getBytes());
    }


    public String decode(String cipher) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(cipher));
    }
}
