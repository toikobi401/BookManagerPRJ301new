package controller;

import dal.AuthorDBContext;
import dal.BookSearchDBContext;
import data.Author;
import data.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class BookSearchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         AuthorDBContext db = new AuthorDBContext();
        ArrayList<Author> authors = db.list();

        HttpSession session = request.getSession();
        session.setAttribute("authors", authors);
        request.getRequestDispatcher("/view/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract form parameters (all can be null or empty)
        String bookName = request.getParameter("bookName"); // Can be null or ""
        String available = request.getParameter("available"); // "true", "false", or "all"
        String publishedFrom = request.getParameter("publishedFrom"); // Can be null or ""
        String publishedTo = request.getParameter("publishedTo"); // Can be null or ""
        String[] authorIds = request.getParameterValues("authors"); // Can be null if no checkboxes selected

        // Handle availability
        Boolean availability = null;
        if ("true".equals(available)) {
            availability = true;
        } else if ("false".equals(available)) {
            availability = false;
        } // "all" or null means no filter

        // Query the database with potentially null/empty values
        BookSearchDBContext db = new BookSearchDBContext();
        ArrayList<Book> books = db.search(
            bookName != null ? bookName : "",
            availability,
            publishedFrom != null ? publishedFrom : "",
            publishedTo != null ? publishedTo : "",
            authorIds
        );

        // Set results and forward
        request.setAttribute("books", books);
        request.getRequestDispatcher("/view/searchresult.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles book search requests with optional fields";
    }
}