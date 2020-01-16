package GRADS_Backend.CustomErrors;

public class UnsupportedRoleException extends Exception {

    private int id;

    /**
     *
     */
    public UnsupportedRoleException() {
        id = 9;
    }

    /**
     * @return
     */
    public String toString() {
        return "UnsupportedRoleException[" + id + "]";
    }
}
