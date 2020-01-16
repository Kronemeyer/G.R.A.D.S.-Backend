package GRADS_Backend.CustomErrors;

public class RecordNotFoundException extends Exception {

    private int id;

    /**
     *
     */
    public RecordNotFoundException() {
        id = 1;
    }

    /**
     * @return
     */
    public String toString() {
        return "RecordNotFoundException[" + id + "]";
    }
}