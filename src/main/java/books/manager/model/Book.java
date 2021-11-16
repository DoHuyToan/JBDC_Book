package books.manager.model;

import java.util.List;

public class Book {
    private int idB;
    private String name;
    private double price;
    private String description;
    private List<Category> categoryList;

    public Book(int idB, String name, double price, String description, List<Category> categoryList) {
        this.idB = idB;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryList = categoryList;
    }

    public Book(String name, double price, String description, List<Category> categoryList) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryList = categoryList;
    }

    public Book(int idB, String name, double price, String description) {
        this.idB = idB;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Book(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return idB;
    }

    public void setId(int id) {
        this.idB = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
