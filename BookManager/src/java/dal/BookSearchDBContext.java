package dal;

import data.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookSearchDBContext extends DBContext<Book> {

    public ArrayList<Book> search(String bookName, Boolean availability, String publishedFrom, String publishedTo, String[] authorIds) {
        ArrayList<Book> books = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT b.BookID, b.BookName, b.CreatedBy, b.CreatedDate, b.Availability " +
                                              "FROM [Test2DB].[dbo].[Book] b ");
        
        // Join with Book_Author only if authors are specified
        if (authorIds != null && authorIds.length > 0) {
            sql.append("INNER JOIN [Test2DB].[dbo].[Book_Author] ba ON b.BookID = ba.BookID ");
        }
        
        // Build WHERE clause dynamically
        ArrayList<String> conditions = new ArrayList<>();
        if (bookName != null && !bookName.trim().isEmpty()) {
            conditions.add("b.BookName LIKE ?");
        }
        if (availability != null) {
            conditions.add("b.Availability = ?");
        }
        if (publishedFrom != null && !publishedFrom.trim().isEmpty()) {
            conditions.add("b.CreatedDate >= ?");
        }
        if (publishedTo != null && !publishedTo.trim().isEmpty()) {
            conditions.add("b.CreatedDate <= ?");
        }
        if (authorIds != null && authorIds.length > 0) {
            String placeholders = String.join(",", new String[authorIds.length]).replace("\0", "?");
            conditions.add("ba.AuthorID IN (" + placeholders + ")");
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions));
        }

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            // Set parameters only for non-empty conditions
            if (bookName != null && !bookName.trim().isEmpty()) {
                stm.setString(paramIndex++, "%" + bookName.trim() + "%");
            }
            if (availability != null) {
                stm.setBoolean(paramIndex++, availability);
            }
            if (publishedFrom != null && !publishedFrom.trim().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateFrom = sdf.parse(publishedFrom.trim());
                stm.setDate(paramIndex++, new java.sql.Date(dateFrom.getTime()));
            }
            if (publishedTo != null && !publishedTo.trim().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateTo = sdf.parse(publishedTo.trim());
                stm.setDate(paramIndex++, new java.sql.Date(dateTo.getTime()));
            }
            if (authorIds != null && authorIds.length > 0) {
                for (String authorId : authorIds) {
                    stm.setInt(paramIndex++, Integer.parseInt(authorId));
                }
            }

            // Execute query
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookID(rs.getInt("BookID"));
                book.setBookName(rs.getString("BookName"));
                book.setCreatedby(rs.getInt("CreatedBy"));
                book.setCreatedAt(rs.getDate("CreatedDate"));
                book.setAvailability(rs.getBoolean("Availability"));
                books.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookSearchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BookSearchDBContext.class.getName()).log(Level.SEVERE, "Invalid date format. Use yyyy-MM-dd", ex);
        }
        return books;
    }

    @Override
    public ArrayList<Book> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Book get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Book model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Book model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Book model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}