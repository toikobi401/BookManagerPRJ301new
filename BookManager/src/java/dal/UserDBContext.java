package dal;
import data.User;
import java.util.ArrayList;
import java.sql.*;



public class UserDBContext extends DBContext<User>{

    
        public User get(String Username, String Password) {
      
            try {
                String sql = """
                            SELECT [UserID]
                            ,[Username]
                            ,[Password]
                            FROM [Test2DB].[dbo].[User]
                            WHERE Username = ? AND Password = ?
                             """;
                PreparedStatement stm = connection.prepareStatement(sql);
                
                stm.setString(1, Username.trim());
                stm.setString(2, Password.trim());
                ResultSet rs = stm.executeQuery();
                User user = null;
                while(rs.next()){
                    user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setPassword(rs.getString("Password"));
                }
                
                return user;
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(UserDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            return null;
                    
        }
    
    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
