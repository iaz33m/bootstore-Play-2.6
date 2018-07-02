package utils;
import org.mindrot.jbcrypt.BCrypt;

public class Hash {

    public static String create(String clearString) throws Exception {
        if (clearString == null) {
            throw new Exception("No password defined!");
        }
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }

    public static boolean check(String candidate, String encryptedPassword) {
        if (candidate == null || encryptedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(candidate, encryptedPassword);
    }

}
