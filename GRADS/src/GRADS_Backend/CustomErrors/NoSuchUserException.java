package GRADS_Backend.CustomErrors;

public class NoSuchUserException extends Exception{

    private int id;

    /**
     *
     */
    public NoSuchUserException() { id = 4; }

    /**
     * @return
     */
    public String toString() { return "NoSuchUserException[" + id + "]"; }
}
