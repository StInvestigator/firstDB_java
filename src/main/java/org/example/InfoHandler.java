package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Hello world!
 */
public class InfoHandler {
    public static final String query = """
            SELECT id, message, type, processed
            FROM notice
            WHERE type='INFO' and processed=false""";

    public static final String queryDelete = """
            DELETE FROM notice
            WHERE id=?""";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5437/test-db",
                "sa", "admin")) {

            while (true) {

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String message = resultSet.getString("message");
                    System.out.println(message);

                    PreparedStatement preparedStatement = connection.prepareStatement(queryDelete);
                    preparedStatement.setInt(1, id);
                    preparedStatement.execute();
                }
                sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
