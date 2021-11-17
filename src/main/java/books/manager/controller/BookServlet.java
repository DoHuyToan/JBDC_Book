package books.manager.controller;

import books.manager.model.Book;
import books.manager.model.Category;
import books.manager.service.bookService.BookService;
import books.manager.service.categoryService.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private final BookService bookService = new BookService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (action==null){
            action="";
        }
        switch (action){
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }



    private void deleteBook(HttpServletRequest request, HttpServletResponse response) {
        int idB = Integer.parseInt(request.getParameter("id"));
        bookService.delete(idB);
        try {
            response.sendRedirect("/books");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList = categoryService.selectAll();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("book/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void creatBook(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");

        String[] categoryList = request.getParameterValues("categoryList");
        int[] idC = new int[categoryList.length];
        for (int i=0; i<categoryList.length; i++){
            idC[i] = Integer.parseInt(categoryList[i]);
        }

        Book book = new Book(name, price, description);
        bookService.insert(book, idC);
        request.setAttribute("categoryList", categoryService.selectAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("book/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) {
        List<Book> bookList;
        String name = request.getParameter("name");
        if(name != null && name != ""){
            bookList = bookService.selectByName(name);
        } else {
            bookList = bookService.selectAll();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("book/list.jsp");
        request.setAttribute("bookList", bookList);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (action==null){
            action="";
        }
        switch (action){
            case "create":
                creatBook(request, response);
                break;
            case "edit":
                updateBook(request, response);
                break;
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        int idB = Integer.parseInt(request.getParameter("id"));
        Book book = bookService.selectById(idB);
        request.setAttribute("book", book);
        request.setAttribute("categoryList", categoryService.selectAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("books/edit.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList = new ArrayList<>();
        int bId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        String description = request.getParameter("description");
        String[] categories = request.getParameterValues("categories");
        int[] cId = new int[categories.length];
        for (int i = 0; i < categories.length; i++) {
            cId[i] = Integer.parseInt(categories[i]);
        }
        for (int c: cId) {
            Category category = categoryService.selectById(c);
            categoryList.add(category);
        }
        Book book = new Book(bId, name, price, description, categoryList);
        request.setAttribute("chooseList", categoryService.selectAll());
        request.setAttribute("message","Sach da duoc cap nhat!");
        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("books/edit.jsp");
        try {
            bookService.update(book,cId);
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }


}
