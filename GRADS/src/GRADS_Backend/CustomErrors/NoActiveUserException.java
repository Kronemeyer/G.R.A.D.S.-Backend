package GRADS_Backend.CustomErrors;

public class NoActiveUserException  extends Exception{

    private int id;

    /**
     *
     */
    public NoActiveUserException() { id = 3; }

    /**
     * @return
     */
    public String toString() { return "NoActiveUserException[" + id + "]"; }
}
