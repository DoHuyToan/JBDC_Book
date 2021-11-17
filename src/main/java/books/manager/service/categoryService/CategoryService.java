package books.manager.service.categoryService;

import books.manager.config.ConnectionSingleton;
import books.manager.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService {
    private Connection connection = ConnectionSingleton.getConnection();
    @Override
    public List<Category> selectAll() {
        List<Category> categoryList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from category;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idC = rs.getInt("idC");
                String name = rs.getString("name");
                String description = rs.getString("description");
                categoryList.add(new Category(idC, name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public boolean insert(Category category, int[] arr) {
        boolean rowInsert = false;
        try {
            PreparedStatement ps = connection.prepareStatement("insert into category(name, description) value (?, ?)");
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
           rowInsert = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInsert;
    }

   @Override
    public boolean update(Category category, int[] arr) {
        boolean rowUpdate = false;
        try {
            PreparedStatement ps = connection.prepareStatement("update set name =?, description=? where idC=?");
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getIdC());

            rowUpdate = ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return rowUpdate;
    }

    @Override
    public boolean delete(int idC) {
        boolean rowDelete = false;
        try {
            PreparedStatement ps = connection.prepareStatement("delete from category where idC=?");
            ps.setInt(1, idC);
            rowDelete = ps.executeUpdate() >0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDelete;
    }

    @Override
    public Category selectById(int idC) {
        Category category = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from category where idC=?");
            ps.setInt(1, idC);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String description = rs.getString("description");
                category = new Category(idC, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> selectByName(String name) {
        List<Category> categoryList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select  * from category where name=?");
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idC = rs.getInt("idC");
                String description = rs.getString("description");
                categoryList.add(new Category(idC, name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TÌM TẤT CẢ CÁC THỂ LOẠI CỦA 1 QUYỂN SÁCH THEO idB
    public List<Category> findAllCategoryForOneBook(int idB){
        List<Category> categoryList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from book_category where idB=?");
            ps.setInt(1,idB);
            // NHẬN DANH SÁCH CÓ idB TRONG BẢNG book_category
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idC = rs.getInt("idC");
                Category category =  selectById(idC);
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

}
