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

    public ArrayList<Book> search(String bookName, String available, String publishedFrom, String publishedTo, String[] authorIds) {
        ArrayList<Book> books = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT b.BookID, b.BookName, b.Createdby, b.CreatedAt, b.Availability " +
                                              "FROM [Test2DB].[dbo].[Book] b " +
                                              "LEFT JOIN [Test2DB].[dbo].[User] u ON b.Createdby = u.UserID " +
                                              "LEFT JOIN [Test2DB].[dbo].[Book_Author] ba ON b.BookID = ba.BookID " +
                                              "LEFT JOIN [Test2DB].[dbo].[Author] a ON ba.AuthorID = a.AuthorID ");
        
        ArrayList<String> conditions = new ArrayList<>();

        // Tìm kiếm theo tên sách
        if (bookName != null && !bookName.trim().isEmpty()) {
            conditions.add("b.BookName LIKE ?");
        }

        // Tìm kiếm theo trạng thái Availability
        if (available != null && !"all".equalsIgnoreCase(available)) {
            conditions.add("b.Availability = ?");
        }

        // Tìm kiếm theo khoảng thời gian xuất bản
        if (publishedFrom != null && !publishedFrom.trim().isEmpty()) {
            conditions.add("b.CreatedAt >= ?");
        }
        if (publishedTo != null && !publishedTo.trim().isEmpty()) {
            conditions.add("b.CreatedAt <= ?");
        }

        // Tìm kiếm theo tác giả
        if (authorIds != null && authorIds.length > 0) {
            String placeholders = String.join(",", new String[authorIds.length]).replace("\0", "?");
            conditions.add("ba.AuthorID IN (" + placeholders + ")");
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions));
        }

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (bookName != null && !bookName.trim().isEmpty()) {
                stm.setString(paramIndex++, "%" + bookName.trim() + "%");
            }
            if (available != null && !"all".equalsIgnoreCase(available)) {
                stm.setBoolean(paramIndex++, "true".equalsIgnoreCase(available));
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

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookID(rs.getInt("BookID"));
                book.setBookName(rs.getString("BookName"));
                book.setCreatedby(rs.getInt("Createdby"));
                book.setCreatedAt(rs.getDate("CreatedAt"));
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