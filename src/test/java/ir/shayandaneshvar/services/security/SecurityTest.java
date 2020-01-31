package ir.shayandaneshvar.services.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class SecurityTest {
    private static final MD5Algorithm md5 = new MD5Algorithm();

    @Test
    public void testMD5() {
        String pass1 = new String("myPassword");
        String pass2 = new String("myPassword");
        Assertions.assertFalse(md5.getHashedValue(pass1).equals(pass2));
        Assertions.assertTrue(md5.getHashedValue(pass1).equals(md5.getHashedValue(pass2)));
    }
}
