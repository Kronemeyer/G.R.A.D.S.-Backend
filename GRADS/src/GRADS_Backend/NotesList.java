package GRADS_Backend;

import com.google.gson.annotations.SerializedName;

public class NotesList {

    @SerializedName("notes")
    private String[] notes;

    /**
     * default constructor for NotesList
     * @param notes changes notes to given value
     */
    public NotesList(String[] notes) {
        this.notes = notes;
    }

    /**
     * gets the list of notes
     * @return the list of notes
     */
    public String[] getNotes() {
        return notes;
    }

    /**
     * sets the list of notes to the given value
     * @param notes the new list of notes
     */
    public void setNotes(String[] notes) {
        this.notes = notes;
    }


}
