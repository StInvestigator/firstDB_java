package org.example;

import java.sql.*;

import static java.lang.Thread.sleep;

/**
 * Hello world!
 */
public class WarnHandler {
    public static final String query = """
            SELECT id, message, type, processed
            FROM notice
            WHERE type='WARN' and processed=false""";

    public static final String queryUpdate = """
            UPDATE notice
            SET processed=true
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

                    PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
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
