import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDB {
    // Database connection details
    String url = "jdbc:mysql://localhost:3306/ShootingGame";
    String username = "root";
    String password = "Sujal@2006";

    // Method to insert score into the highScore table
    public void insertScore(int score) {
        // SQL query with placeholder for score only
        
        String sql = "INSERT INTO highscore (score) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preStatement = connection.prepareStatement(sql)) {

            // Set the score value in the SQL query
            preStatement.setInt(1, score);

            // Execute the SQL statement
            int rowsAffected = preStatement.executeUpdate();
            System.out.println("Score Inserted into Database. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
