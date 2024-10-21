import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class GetHIghScore {
    String url = "jdbc:mysql://localhost:3306/ShootingGame";
    String username = "root";
    String password = "Sujal@2006";
    public int getHighScore(){
        int highScore = 0;
        String sql = "SELECT MAX(score) As maxScore FROM highscore";
        try(Connection connection = DriverManager.getConnection(url,username,password)) {
            PreparedStatement preStatement = connection.prepareStatement(sql);
            ResultSet resultset = preStatement.executeQuery();
            if(resultset.next()){
                highScore = resultset.getInt("maxScore");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highScore;

    } 
}
