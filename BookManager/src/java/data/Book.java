package data;
import java.sql.*;


public class Book {
    private int BookID;
    private String BookName;
    private int Createdby;
    private java.sql.Date CreatedAt;
    private boolean Availability;

    public boolean isAvailable() {
        return Availability;
    }


    public void setAvailability(boolean Availability) {
        this.Availability = Availability;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int BookID) {
        this.BookID = BookID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }

    public int getCreatedby() {
        return Createdby;
    }

    public void setCreatedby(int Createdby) {
        this.Createdby = Createdby;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }
    
}
