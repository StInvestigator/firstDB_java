package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Hello world!
 */
public class Writer {
    public static final String[] notices = new String[]{"""
            insert into notice(message, type, processed)
            values (?, 'INFO', false)
            """, """
            insert into notice(message, type, processed)
            values (?, 'WARN', false)
            """};

    public static void main(String[] args) {
        Random rand = new Random();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5437/test-db",
                "sa", "admin")) {
            while (true) {
                int index = rand.nextInt(0, 2);

                PreparedStatement ps = connection.prepareStatement(notices[index]);
                ps.setString(1, (index == 0 ? "New message from " : "Error occurred at ") + LocalDateTime.now());
                ps.execute();

                sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
