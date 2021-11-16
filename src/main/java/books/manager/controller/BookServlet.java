package books.manager.controller;

import books.manager.model.Book;
import books.manager.service.bookService.BookService;
import books.manager.service.categoryService.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action==null){
            action="";
        }
        switch (action){
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                break;
            case "delete":
                break;
            default:
                listBooks(request, response);
                break;
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("book/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action==null){
            action="";
        }
        switch (action){
            case "create":
                creatBook(request, response);
                break;
        }
    }

    private void creatBook(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        double price = Integer.parseInt(request.getParameter("price"));
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
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
