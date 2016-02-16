package model;

public class Note {
    private int id;
    private String title, message, createDate;

    public Note() {}

    public Note(int id, String title, String message, String createDate) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createDate = createDate;
    }

    public Note(String title, String message, String createDate) {
        this.title = title;
        this.message = message;
        this.createDate = createDate;
    }

    public Note(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String toString() {
        return "[\"id\"=>" + id + ",\"title\"=>" + title + "\"message\"=>" + message + "\"createDate\"=>" + createDate + "]";
    }
}
