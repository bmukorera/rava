package zw.co.cryptosine.rava.util;

import zw.co.cryptosine.rava.exception.RavaException;

import java.security.MessageDigest;

public class SecurityUtilImpl  {


    public static String applySha256(String dataInput) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies sha256 to our input,
            byte[] hash = digest.digest(dataInput.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RavaException(e.getMessage());
        }
    }
}
