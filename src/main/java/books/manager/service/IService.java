package books.manager.service;

import java.util.List;

public interface IService<T> {
    List<T> selectAll();
    void insert(T t, int[] arr);
    boolean update(T t, int[] arr);
    boolean delete(int id);
    T selectById(int id);
    List<T> selectByName(String name);
}
