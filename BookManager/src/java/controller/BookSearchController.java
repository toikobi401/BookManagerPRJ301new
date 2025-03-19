package controller;

import dal.BookSearchDBContext;
import data.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class BookSearchController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, data.User user)
            throws ServletException, IOException {
        dal.AuthorDBContext authorDB = new dal.AuthorDBContext();
        ArrayList<data.Author> authors = authorDB.list();
        HttpSession session = request.getSession();
        session.setAttribute("authors", authors);
        request.getRequestDispatcher("/view/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, data.User user)
            throws ServletException, IOException {
        String bookName = request.getParameter("bookName");
        String available = request.getParameter("available"); // "true", "false", hoặc "all"
        String publishedFrom = request.getParameter("publishedFrom");
        String publishedTo = request.getParameter("publishedTo");
        String[] authorIds = request.getParameterValues("authors");

        BookSearchDBContext db = new BookSearchDBContext();
        ArrayList<Book> books = db.search(bookName, available, publishedFrom, publishedTo, authorIds);

        request.setAttribute("books", books);
        request.getRequestDispatcher("/view/searchresult.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles book search for authorized users";
    }
}