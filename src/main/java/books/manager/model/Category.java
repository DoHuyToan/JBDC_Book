package books.manager.model;

import java.util.List;

public class Category {
    private int idC;
    private String name;
    private String description;
    private List<Book> bookList;

    public Category(int idC, String name, String description) {
        this.idC = idC;
        this.name = name;
        this.description = description;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(int idC, String name, String description, List<Book> bookList) {
        this.idC = idC;
        this.name = name;
        this.description = description;
        this.bookList = bookList;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
