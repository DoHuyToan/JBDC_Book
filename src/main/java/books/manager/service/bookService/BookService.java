package books.manager.service.bookService;

import books.manager.config.ConnectionSingleton;
import books.manager.model.Book;
import books.manager.model.Category;
import books.manager.service.categoryService.CategoryService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IBookService{
    private Connection connection = ConnectionSingleton.getConnection();
//    ???
    private final CategoryService categoryService = new CategoryService();

    @Override
    public List<Book> selectAll() {
        List<Book> bookList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from book");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idB = rs.getInt("idB");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                List<Category> categoryList = categoryService.findAllCategoryForOneBook(idB);
                bookList.add(new Book(idB, name, price, description, categoryList));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public void insert(Book book, int[] arr) {
        boolean rowInsert = false;
        int idB = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("insert into book(name, price, description) value (?,?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getName());
            ps.setDouble(2, book.getPrice());
            ps.setString(3, book.getDescription());
            ps.executeUpdate();
            rowInsert = ps.executeUpdate() > 0;

            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()){
                idB = rs.getInt(1);
            }
            PreparedStatement ps1 = connection.prepareStatement("insert into book_category value (?,?)");
            for (int a: arr) {
                ps1.setInt(1, idB);
                ps1.setInt(2, a);
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Book book, int[] arr) {
        boolean rowUpdate =  false;
        try {
            PreparedStatement ps = connection.prepareStatement("update book set name=?, price=?, description=? where idB=?");
            ps.setString(1, book.getName());
            ps.setDouble(2, book.getPrice());
            ps.setString(3, book.getDescription());
            ps.setInt(4, book.getId());
            rowUpdate = ps.executeUpdate() >0;

            PreparedStatement ps1 = connection.prepareStatement("delete  from book_category where idB=?");
            for (int a: arr){
                ps1.setInt(1, book.getId());
                ps1.executeUpdate();
            }

            PreparedStatement ps2 = connection.prepareStatement("insert into book_category value (?,?)");
            for (int a: arr){
                ps2.setInt(1, book.getId());
                ps2.setInt(2, a);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdate;
    }

    @Override
    public boolean delete(int idB) {
        boolean rowDelete = false;
        try {
            PreparedStatement ps = connection.prepareStatement("delete from book where idB=?");
            ps.setInt(1, idB);
            rowDelete = ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDelete;
    }

    @Override
    public Book selectById(int idB) {
        List<Category> categoryList = new ArrayList<>();
        Book book = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from book where idB=?");
            ps.setInt(1, idB);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                book = new Book(idB, name, price, description);
            }
            PreparedStatement ps1 = connection.prepareStatement("select * from book_category where idB=?");
            ps1.setInt(1, idB);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()){
                int idC = rs1.getInt("idC");
                Category category = categoryService.selectById(idC);
                categoryList.add(category);
            }
            book.setCategoryList(categoryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> selectByName(String name) {
        List<Book> bookList = new ArrayList<>();
        List<Category> categoryList;
        Book book;
        try {
            PreparedStatement ps = connection.prepareStatement("select *from book where name =?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idB = rs.getInt("idB");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                categoryList = categoryService.findAllCategoryForOneBook(idB);
                book = new Book(idB, name, price, description, categoryList);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}
