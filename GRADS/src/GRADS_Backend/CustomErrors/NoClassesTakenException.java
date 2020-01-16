package GRADS_Backend.CustomErrors;

public class NoClassesTakenException extends Exception {


    private int id;

    /**
     *
     */
    public NoClassesTakenException() {
        id = 7;
    }

    /**
     * @return
     */
    public String toString() {
        return "NoClassesTakenException[" + id + "]";
    }
}