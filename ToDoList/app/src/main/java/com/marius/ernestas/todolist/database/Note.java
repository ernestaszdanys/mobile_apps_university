package com.marius.ernestas.todolist.database;

public class Note {
    private int id;
    private String date;
    private int importance;
    private String title;
    private String description;


    public Note(int id, String date, int importance, String title, String description) {
        setId(id);
        setDate(date);
        setImportance(importance);
        setTitle(title);
        setDescription(description);
    }

    public Note(int importance, String title, String description) {
        setImportance(importance);
        setTitle(title);
        setDescription(description);
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getImportance() {
        return importance;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", importance=" + importance +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
