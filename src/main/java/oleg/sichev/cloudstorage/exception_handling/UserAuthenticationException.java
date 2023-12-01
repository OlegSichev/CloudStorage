package oleg.sichev.cloudstorage.exception_handling;

/**
 * @author OlegSichev
 */

public class UserAuthenticationException extends RuntimeException{

    public UserAuthenticationException(String message) {
        super(message);
    }
}
