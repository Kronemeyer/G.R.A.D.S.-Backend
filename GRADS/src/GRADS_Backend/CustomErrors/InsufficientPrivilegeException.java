package GRADS_Backend.CustomErrors;

public class InsufficientPrivilegeException extends Exception{

    private int id;

    /**
     *
     */
    public InsufficientPrivilegeException() { id = 2; }

    /**
     * @return
     */
    public String toString() { return "InsufficientPrivilegeException[" + id + "]"; }
}
