package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import data.Book;
import data.User;
import data.Author;
import dal.BookInsertDBContext;
import dal.AuthorDBContext;

public class BookCreateController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        AuthorDBContext db = new AuthorDBContext();
        ArrayList<Author> authors = db.list();

        HttpSession session = request.getSession();
        session.setAttribute("authors", authors);
        request.getRequestDispatcher("../view/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String bookName = request.getParameter("BookName");
        String[] authorIds = request.getParameterValues("authors");
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));

        // Tạo đối tượng Book
        Book book = new Book();
        book.setBookName(bookName);
        book.setCreatedby(user.getUserID());
        book.setCreatedAt(new Date(System.currentTimeMillis()));
        book.setAvailability(availability);

        // Thêm sách vào cơ sở dữ liệu
        BookInsertDBContext db = new BookInsertDBContext();
        db.insert(book, authorIds);

        // Chuyển hướng về trang thành công
        response.sendRedirect(request.getContextPath() + "/view/success.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Handles book creation for authorized users";
    }
}