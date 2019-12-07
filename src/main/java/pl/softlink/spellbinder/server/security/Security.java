package pl.softlink.spellbinder.server.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

    public static void main(String args[]) {
        System.out.println(md5("asdf"));
    }

    public void register(String email, String passwordRaw) {

    }

    public static String md5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
