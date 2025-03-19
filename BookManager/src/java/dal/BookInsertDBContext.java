package dal;

import data.Book;
import data.Author;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookInsertDBContext extends DBContext<Book> {

    public void insert(Book book, String[] authorIds) {
        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Thêm sách vào bảng Book
            String sqlInsertBook = """
                                        INSERT INTO [Test2DB].[dbo].[Book]
                                              ([BookName]
                                              ,[CreatedBy]
                                              ,[CreatedDate]
                                              ,[Availability])
                                        VALUES
                                              (?
                                              ,?
                                              ,?
                                              ,?)""";
            PreparedStatement stmInsertBook = connection.prepareStatement(sqlInsertBook, Statement.RETURN_GENERATED_KEYS);
            stmInsertBook.setString(1, book.getBookName());
            stmInsertBook.setInt(2, book.getCreatedby());
            stmInsertBook.setDate(3, book.getCreatedAt());
            stmInsertBook.setBoolean(4, book.isAvailable());

            stmInsertBook.executeUpdate();

            // Lấy BookID vừa tạo
            ResultSet rs = stmInsertBook.getGeneratedKeys();
            int bookId = -1;
            if (rs.next()) {
                bookId = rs.getInt(1);
            }

            // Thêm liên kết với tác giả vào bảng Book_Author
            if (authorIds != null && bookId != -1) {
                String sqlInsertRelation = "INSERT INTO [Test2DB].[dbo].[Book_Author] (BookID, AuthorID) VALUES (?, ?)";
                PreparedStatement stmInsertRelation = connection.prepareStatement(sqlInsertRelation);
                for (String authorId : authorIds) {
                    stmInsertRelation.setInt(1, bookId);
                    stmInsertRelation.setInt(2, Integer.parseInt(authorId));
                    stmInsertRelation.addBatch();
                }
                stmInsertRelation.executeBatch();
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException ex) {
            try {
                // Rollback nếu có lỗi
                connection.rollback();
            } catch (SQLException rollbackEx) {
                java.util.logging.Logger.getLogger(BookInsertDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, rollbackEx);
            }
            java.util.logging.Logger.getLogger(BookInsertDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(BookInsertDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
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